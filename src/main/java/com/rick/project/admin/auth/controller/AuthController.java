package com.rick.project.admin.auth.controller;

import com.rick.project.admin.auth.authentication.AcUserDetails;
import com.rick.project.admin.auth.entity.Role;
import com.rick.project.admin.auth.model.RoleInfoDTO;
import com.rick.project.admin.auth.model.UserDTO;
import com.rick.project.admin.auth.model.UserPermissionVO;
import com.rick.project.admin.auth.service.RoleService;
import com.rick.project.admin.auth.service.UserService;
import com.rick.project.admin.common.Result;
import com.rick.project.admin.common.ResultCode;
import com.rick.project.admin.common.ResultUtils;
import com.rick.project.admin.exception.AcException;
import com.rick.project.admin.util.PasswordGenerator;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * All rights Reserved, Designed By www.xhope.top
 *
 * @version V1.0
 * @Description: (用一句话描述该文件做什么)
 * @author: Rick.Xu
 * @date: 9/10/19 1:55 PM
 * @Copyright: 2019 www.yodean.com. All rights reserved.
 */
@Controller
@RequestMapping
public class AuthController {

    @Autowired
    private UserService userService;

    @Autowired
    private RoleService roleService;

    /**
     * login页面
     *
     * @return
     */
    @GetMapping("login")
    public String login(Principal principal) {
        if (Objects.nonNull(principal)) {
            return "redirect:/";
        }
        return "login";
    }

    /**
     * 修改密码
     *
     * @return
     */
    @PostMapping("password/update")
    @ResponseBody
    public Result updatePassword(@RequestBody UserDTO user, Principal principal) {
        String username = ((AcUserDetails) ((UsernamePasswordAuthenticationToken) principal).getPrincipal()).getUser().getUsername();
        userService.updatePassword(username, user.getPassword());
        return ResultUtils.success();
    }

    /**
     * 重置密码
     *
     * @return
     */
    @PostMapping("password/{userId}/reset")
    @PreAuthorize("hasAuthority('auth')")
    @ResponseBody
    public Result updatePassword(@PathVariable String userId) {
        String newPassword = PasswordGenerator.genRandomPwd(10);
        userService.updatePassword(userId, newPassword);
        return ResultUtils.success(newPassword);
    }

    /**
     * 原密码验证
     *
     * @return
     */
    @GetMapping("password/check")
    @ResponseBody
    public boolean checkPassword(String password, Principal principal) {
        String username = ((AcUserDetails) ((UsernamePasswordAuthenticationToken) principal).getPrincipal()).getUser().getUsername();
        return userService.validateUser(username, password);
    }

    @GetMapping("/auth")
    @PreAuthorize("hasAuthority('auth')")
    public String gotoAuthPage(Model model) {
        List<Role> roleList = roleService.findAll();
        RoleInfoDTO roleInfo = roleService.getSettingsInfoByRoleId(roleList.get(0).getId());

        model.addAttribute("roleList", roleList);
        model.addAttribute("userList", userService.findAllUser());
        model.addAttribute("roleInfo", roleInfo);
        model.addAttribute("userIds", roleInfo.getUserList().stream().map(userDTO -> userDTO.getId()).collect(Collectors.toList()));
        model.addAttribute("permissionFullList", roleService.getPermissionsNodes());

        return "sys/auth";
    }

    @GetMapping("/auth/{roleId}/info")
    @ResponseBody
    @PreAuthorize("hasAuthority('auth')")
    public Result<RoleInfoDTO> getPermissionNodesByRoleId(@PathVariable Long roleId) {
        return ResultUtils.success(roleService.getSettingsInfoByRoleId(roleId));
    }

    @GetMapping("/auth/permission")
    @ResponseBody
    @PreAuthorize("hasAuthority('auth')")
    public Result<UserPermissionVO> getPermissionNodesByUserId(String userId) {
        UserPermissionVO userPermissionVO = UserPermissionVO.builder().permissionList(roleService.getPermissionsNodesByUserId(userId))
                .roleList(roleService.getRolesByUserId(userId)).build();

        return ResultUtils.success(userPermissionVO);
    }

    /**
     * 用户保存
     * @param roleId
     * @param userIds
     * @return
     */
    @PostMapping("/auth/assign/role/{roleId}/user")
    @ResponseBody
    @PreAuthorize("hasAuthority('auth')")
    public Result assignAuthUser(@PathVariable Long roleId, @RequestParam(value = "userIds[]", required = false) Set<String> userIds) {
        roleService.addUser(roleId, userIds);
        return ResultUtils.success();
    }

    /**
     * 权限保存
     * @param roleId
     * @param permissionIds
     * @return
     */
    @PostMapping("/auth/assign/role/{roleId}/permission")
    @ResponseBody
    @PreAuthorize("hasAuthority('auth')")
    public Result assignAuthPermission(@PathVariable Long roleId, @RequestParam(value = "permissionIds[]", required = false) Set<Long> permissionIds) {
        roleService.addPermission(roleId, permissionIds);
        return ResultUtils.success();
    }

    /**
     * 角色保存
     * @param roleList
     * @return
     */
    @PostMapping("/auth/add/roles")
    @ResponseBody
    @PreAuthorize("hasAuthority('auth')")
    public Result editAuthRole(@RequestBody List<Role> roleList) {
        for (Role role : roleList) {
            if (StringUtils.isBlank(role.getName())) {
                throw new AcException(ResultCode.ROLE_NULL_ERROR);
            }
        }

        return ResultUtils.success(roleService.editRoles(roleList));
    }

    /**
     * 移除用户角色
     * @param roleId
     * @param userId
     * @return
     */
    @PostMapping("/auth/assign/role/{roleId}/{userId}")
    @ResponseBody
    @PreAuthorize("hasAuthority('auth')")
    public Result removeUserRoleAuth(@PathVariable Long roleId, @PathVariable String userId) {
        roleService.removeRoleByUserId(roleId, userId);
        return ResultUtils.success();
    }

}
