package com.rick.project.admin.auth.filter;

import com.rick.common.http.HttpServletRequestUtils;
import com.rick.project.admin.auth.authentication.AcUserDetails;
import com.rick.project.admin.auth.constant.AuthConstants;
import com.rick.project.admin.auth.model.UserContextHolder;
import com.rick.project.admin.auth.model.UserDTO;
import com.rick.project.admin.common.AcConstants;
import com.rick.project.admin.common.ResultCode;
import com.rick.project.admin.exception.AcException;
import com.rick.project.admin.util.WebUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.lang.Nullable;
import org.springframework.mobile.device.Device;
import org.springframework.mobile.device.DeviceUtils;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Objects;

/**
 * All rights Reserved, Designed By www.xhope.top
 *
 * @version V1.0
 * @Description: (用一句话描述该文件做什么)
 * @author: Rick.Xu
        * @date: 9/10/19 8:06 PM
        * @Copyright: 2019 www.yodean.com. All rights reserved.
        */
@Slf4j
public class UrlHandlerInterceptor implements HandlerInterceptor {


    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler,
                                @Nullable Exception ex) {
        UserContextHolder.remove();
    }

    @Override
    public boolean preHandle(HttpServletRequest request,
                             HttpServletResponse response, Object handler) throws IOException {
        // SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Object principal = request.getUserPrincipal();
        String username = "anon";
        String name = "anno";
        AcUserDetails userDetails = null;
        if (Objects.nonNull(principal)) {
            userDetails = (AcUserDetails) ((UsernamePasswordAuthenticationToken) principal).getPrincipal();
            UserDTO userDTO = userDetails.getUser();
            UserContextHolder.set(userDTO);
            username = userDTO.getUsername();
            name = userDTO.getName();
        }

        Device device = DeviceUtils.getCurrentDevice(request);
        if (Objects.nonNull(device)) {
            request.setAttribute("device", device);
        }

        String params = "";

        if (!request.getRequestURI().startsWith("/password")) {
            params = HttpServletRequestUtils.getParameterMap(request).toString();
        }

        log.info("VISIT: 用户{}-{}访问地址{}, method={}, ip={}, 设备类型={}, 参数={}", username, name,  request.getRequestURI()
                , request.getMethod()
                , WebUtils.getClientIpAddress(request)
                , device
                , " params => " + params);

        String servletPath = request.getServletPath();

        if (HttpServletRequestUtils.isAjaxRequest(request) && "/login".equals(servletPath)) {
            if (HttpServletRequestUtils.isAjaxRequest(request)) {
                throw new AcException(ResultCode.INVALID_SESSION);
            }
        }

        if (AuthConstants.IGNORE_URL.contains(servletPath) && !"/forbidden".equals(servletPath)) {
            return true;
        }

        return true;
    }

}
