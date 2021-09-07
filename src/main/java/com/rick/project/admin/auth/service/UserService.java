package com.rick.project.admin.auth.service;

import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.rick.db.service.SharpService;
import com.rick.project.admin.auth.authentication.AcUserDetails;
import com.rick.project.admin.auth.entity.Role;
import com.rick.project.admin.auth.entity.User;
import com.rick.project.admin.auth.model.UserDTO;
import com.rick.project.admin.auth.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.context.annotation.Lazy;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

/**
 * All rights Reserved, Designed By www.xhope.top
 *
 * @version V1.0
 * @Description: (用一句话描述该文件做什么)
 * @author: Rick.Xu
 * @date: 9/10/19 2:21 PM
 * @Copyright: 2019 www.yodean.com. All rights reserved.
 */
@Service
@Slf4j
public class UserService {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private SharpService sharpService;

    public Optional<User> findDOByUsername(String username) {
        // language=SQL
        String sql = "SELECT id, username, password, name, status FROM sys_user WHERE username = :username";
        Map<String, Object> params = Maps.newHashMapWithExpectedSize(1);
        params.put("username", username);

        return sharpService.queryForObject(sql,
                params, User.class);
    }

    /**
     * @param username
     * @return
     */
    public UserDTO findByUsername(String username) {
        Optional<User> optional = findDOByUsername(username);

        UserDTO userDTO = null;
        if (optional.isPresent()) {
            userDTO = UserDTO.builder().build();
            BeanUtils.copyProperties(optional.get(), userDTO);
            // TODO
            userDTO.setRoles(getRoles(username));
        }

        return userDTO;
    }

    /**
     * 查询NC所有的"启用"用户
     *
     * @return
     */
    public List<User> findAllUser() {
        // language=SQL
        return sharpService.query("SELECT id, username, password, name, status FROM sys_user"
                , null
                , User.class);
    }


    /**
     * 更新密码
     *
     * @param username
     * @param newPassword
     */
    @CacheEvict(value = "acUser", key = "#cuserId")
    public void updatePassword(String username, String newPassword) {
        String encodePassword = passwordEncoder.encode(newPassword);
        String update = "UPDATE sys_user SET password = ? WHERE username = ?";
        jdbcTemplate.update(update, encodePassword, username);
    }

    /**
     * 用户是否存在
     *
     * @param username
     * @param password
     * @return
     */
    public boolean validateUser(String username, String password) {
        Optional<User> optional = findDOByUsername(username);
        if (!optional.isPresent()) {
            return false;
        }

        return passwordEncoder.matches(password, optional.get().getPassword());
    }

    public Boolean hasAuthority(String role) {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return ((AcUserDetails) principal).getUser().getRoles().contains(role);
    }

    private Set<String> getRoles(String username) {
        User user = userRepository.findByUsername(username);

        List<Role> roleList = user.getRoleList();

        Set<String> roleSet = Sets.newHashSetWithExpectedSize(roleList.size());
        // 通用角色
        roleSet.addAll(roleList.stream().map(role -> "ROLE_" + role.getValue()).collect(Collectors.toList()));

        for (Role role : roleList) {
            roleSet.addAll(role.getPermissionList().stream().map(permission -> permission.getPermission()).collect(Collectors.toList()));
        }

        return roleSet;
    }

}
