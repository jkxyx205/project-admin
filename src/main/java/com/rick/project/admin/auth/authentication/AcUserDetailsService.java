package com.rick.project.admin.auth.authentication;

import com.rick.project.admin.auth.constant.AuthConstants;
import com.rick.project.admin.auth.entity.UserStatusEnum;
import com.rick.project.admin.auth.exception.MaxTryLoginException;
import com.rick.project.admin.auth.model.UserDTO;
import com.rick.project.admin.auth.service.UserService;
import com.rick.project.admin.util.MessageUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataAccessResourceFailureException;
import org.springframework.security.authentication.AccountExpiredException;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import javax.cache.Cache;
import javax.cache.CacheManager;
import java.util.List;
import java.util.Objects;

/**
 * All rights Reserved, Designed By www.xhope.top
 *
 * @version V1.0
 * @Description: (用一句话描述该文件做什么)
 * @author: Rick.Xu
 * @date: 9/10/19 4:36 PM
 * @Copyright: 2019 www.yodean.com. All rights reserved.
 */
@Component
@Slf4j
public class AcUserDetailsService implements UserDetailsService {

    @Autowired
    private UserService userService;

    @Autowired
    private CacheManager cacheManager;

    @Autowired
    private SessionRegistry sessionRegistry;


    /**
     * 进行认证授权的工作
     *
     * @param username
     * @return
     * @throws UsernameNotFoundException
     */
    @Override
    public UserDetails loadUserByUsername(String username) {
        Cache tryCache = cacheManager.getCache("loginMaxTry");

        Object object = tryCache.get(username);

        int loginMaxTryCount = 1;
        if (!Objects.isNull(object)) {
            loginMaxTryCount = (Integer) object + 1;
        }
        tryCache.put(username, loginMaxTryCount);

        if (loginMaxTryCount >= AuthConstants.MAX_TRY_COUNT) {
            throw new MaxTryLoginException(MessageUtils.getMessage("MAX_TRY_LOGIN_ERROR"
                    , new Object[] { AuthConstants.MAX_TRY_COUNT, AuthConstants.LOCK_MINUTES }));
        }

        UserDTO user;

        try {
            user = userService.findByUsername(username);
        } catch (DataAccessException e) {
            throw new DataAccessResourceFailureException("数据库连接出错, 请联系管理员");
        }

        if (user == null) {
            throw new UsernameNotFoundException("帐号不存在或者账号被锁定");
        }

        if (user.getStatus() != UserStatusEnum.NORMAL) {
            throw new AccountExpiredException("帐号已被停用");
        }

        if (isLogin(username)) {
            log.info("LOGIN: {}已经登录,踢出登录", username );
        }

        return new AcUserDetails(user, AuthorityUtils.createAuthorityList(user.getRoles().toArray(new String[]{})));
    }

    /**
     * 用户是否处于登录状态
     * @param username
     * @return
     */
    private boolean isLogin(String username) {
        List<Object> list = sessionRegistry.getAllPrincipals();
        for (Object o : list) {
            if (StringUtils.equals(((AcUserDetails)o).getUsername(), username)) {
                return sessionRegistry.getAllSessions(o, false).size() > 0;
            }

        }

        return false;
    }
}
