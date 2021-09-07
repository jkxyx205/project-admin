package com.rick.project.admin.auth.authentication;

import com.rick.project.admin.auth.constant.AuthConstants;
import com.rick.project.admin.auth.model.UserContextHolder;
import com.rick.project.admin.auth.model.UserDTO;
import com.rick.project.admin.common.AcConstants;
import com.rick.project.admin.common.ImageNameHelper;
import com.rick.project.admin.util.WebUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mobile.device.Device;
import org.springframework.mobile.device.LiteDeviceResolver;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.cache.Cache;
import javax.cache.CacheManager;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * All rights Reserved, Designed By www.xhope.topø
 *
 * @version V1.0
 * @Description: (用一句话描述该文件做什么)
 * @author: Rick.Xu
 * @date: 9/10/19 4:29 PM
 * @Copyright: 2019 www.yodean.com. All rights reserved.
 */
@Component
@Slf4j
public class AcAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    @Autowired
    private CacheManager cacheManager;


    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException {
//        Device device = DeviceUtils.getCurrentDevice(request);

        Cache tryCache = cacheManager.getCache("loginMaxTry");
        tryCache.remove(authentication.getName().toUpperCase());
        request.getSession().removeAttribute(AuthConstants.IMAGE_CODE_SESSION_KEY);

        AcUserDetails userDetails = ((AcUserDetails) authentication.getPrincipal());
        UserDTO user = userDetails.getUser();
        user.setImgName(ImageNameHelper.generateImgName(user.getName()));

        request.getSession().setAttribute("user", user);


        // 管理员放行
        LiteDeviceResolver liteDeviceResolver = new LiteDeviceResolver();
        Device device = liteDeviceResolver.resolveDevice(request);


        log.info("LOGIN:用户{}进入系统,ip:{},device={}", userDetails.getUsername(), WebUtils.getClientIpAddress(request), device.getDevicePlatform());

//        RequestCache cache = new HttpSessionRequestCache();
//        SavedRequest savedRequest = cache.getRequest(request, response);
        String url = "/";
//        if (Objects.nonNull(savedRequest)) {
//            url = savedRequest.getRedirectUrl();
//            if (StringUtils.isBlank(url) || url.endsWith("/error")) {
//                url = "/";
//            }
//        }
        UserContextHolder.set(userDetails.getUser());

        response.sendRedirect(url);
    }

}
