package com.rick.project.admin.auth.exception;

import com.rick.project.admin.common.ResultCode;
import org.springframework.security.core.AuthenticationException;

/**
 * All rights Reserved, Designed By www.xhope.top
 *
 * @version V1.0
 * @Description: 验证码错误异常
 * @author: Rick.Xu
 * @date: 12/19/19 11:13 AM
 * @Copyright: 2019 www.yodean.com. All rights reserved.
 */
public class ValidateCodeException extends AuthenticationException {

    public ValidateCodeException() {
        super(ResultCode.VALIDATE_CODE_ERROR.getMsg());
    }
}
