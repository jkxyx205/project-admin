package com.rick.project.admin.auth.constant;

import com.google.common.collect.Lists;

import java.util.List;

/**
 * All rights Reserved, Designed By www.xhope.top
 *
 * @version V1.0
 * @Description: (用一句话描述该文件做什么)
 * @author: Rick.Xu
 * @date: 12/20/19 7:04 PM
 * @Copyright: 2019 www.yodean.com. All rights reserved.
 */
public final class AuthConstants {

    public static final List<String> IGNORE_URL = Lists.newArrayList("/login", "/forbidden", "/kaptcha", "/error");

    public static final int MAX_TRY_COUNT = 5;

    public static final int MAX_TRY_IMAGE_CODE_COUNT = 2;

    public static final int LOCK_MINUTES = 10;

    public static final String IMAGE_CODE_SESSION_KEY = "showCode";

}


