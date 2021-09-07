package com.rick.project.admin.auth.config;

import com.rick.project.admin.auth.authentication.AcUserDetails;
import com.rick.project.admin.common.AcConstants;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Optional;

/**
 * All rights Reserved, Designed By www.xhope.top
 *
 * @version V1.0
 * @Description: (用一句话描述该文件做什么)
 * @author: Rick.Xu
 * @date: 9/10/19 4:20 PM
 * @Copyright: 2019 www.yodean.com. All rights reserved.
 */
@Configuration
public class UserIDAuditorBean implements AuditorAware<Long> {

    @Override
    public Optional<Long> getCurrentAuditor() {
        SecurityContext ctx = SecurityContextHolder.getContext();
        if (ctx == null) {
            return defaultAuditor();
        }
        if (ctx.getAuthentication() == null) {
            return defaultAuditor();
        }
        if (ctx.getAuthentication().getPrincipal() == null) {
            return defaultAuditor();
        }
        Object principal = ctx.getAuthentication().getPrincipal();

        if (principal.getClass().isAssignableFrom(AcUserDetails.class)) {
            return Optional.of(((AcUserDetails) principal).getUser().getId());
        } else {
            return defaultAuditor();
        }
    }

    private Optional<Long> defaultAuditor() {
        return Optional.of(AcConstants.APP_USER_ID);
    }
}