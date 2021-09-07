package com.rick.project.admin.util;

/**
 * All rights Reserved, Designed By www.xhope.top
 *
 * @version V1.0
 * @Description: (用一句话描述该文件做什么)
 * @author: Rick.Xu
 * @date: 9/11/19 1:18 PM
 * @Copyright: 2019 www.yodean.com. All rights reserved.
 */

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Component;

/**
 * 国际化工具类
 */
@Component
public final class MessageUtils {

    private static MessageSource messageSource;

    private MessageUtils() {}

    @Autowired
    public void setMessageSource(MessageSource messageSource) {
        MessageUtils.messageSource = messageSource;
    }

    public static final String getMessage(String code, Object[] params) {
        return messageSource.getMessage(code, params, LocaleContextHolder.getLocale());
    }
}