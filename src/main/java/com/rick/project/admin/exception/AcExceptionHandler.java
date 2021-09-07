package com.rick.project.admin.exception;

import com.rick.common.http.HttpServletRequestUtils;
import com.rick.project.admin.common.Result;
import com.rick.project.admin.common.ResultCode;
import com.rick.project.admin.common.ResultUtils;
import com.rick.project.admin.util.MessageUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintStream;

/**
 * All rights Reserved, Designed By www.xhope.top
 *
 * @version V1.0
 * @Description: (用一句话描述该文件做什么)
 * @author: Rick.Xu
 * @date: 12/16/19 6:59 PM
 * @Copyright: 2019 www.yodean.com. All rights reserved.
 */
@Slf4j
@RestControllerAdvice
@RequiredArgsConstructor
public class AcExceptionHandler {

    /**
     * 业务异常
     * @param ex
     * @return
     */
    @ExceptionHandler(AcException.class)
    public Result<String> acExceptionHandler(HttpServletRequest request, HttpServletResponse response, AcException ex) throws IOException, ServletException {
        ResultCode resultCode = ex.getResultCode();
        // resolve i18n
        String message = MessageUtils.getMessage(resultCode.getMsg(), ex.getParams());

        if (log.isErrorEnabled()) {
            log.error(resultCode.getCode() + "," + message, ex.getCause());
            this.logStackTrace(ex);
        }

        if (HttpServletRequestUtils.isAjaxRequest(request)) {
            return ResultUtils.exception(resultCode, message);
        }

        request.setAttribute("result", resultCode);
        request.setAttribute("message", message);
        request.getRequestDispatcher("/error/index").forward(request, response);
        return null;
    }

    /**
     * 未授权异常
     * @param ex
     * @return
     */
    @ExceptionHandler(AccessDeniedException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public Result<String> AccessDeniedHandler(HttpServletRequest request, HttpServletResponse response, Exception ex) throws IOException, ServletException {
        this.logStackTrace(ex);
        if (HttpServletRequestUtils.isAjaxRequest(request)) {
            return ResultUtils.exception(ResultCode.ACCESS_FORBIDDEN_ERROR);
        }

        request.getRequestDispatcher("/error").forward(request, response);
        return null;
    }

    /**
     * 未知异常
     * @param ex
     * @return
     */
    @ExceptionHandler(Exception.class)
    @ResponseStatus
    public Result<String> exceptionHandler(HttpServletRequest request, HttpServletResponse response, Exception ex) throws IOException, ServletException {
        this.logStackTrace(ex);
        if (HttpServletRequestUtils.isAjaxRequest(request)) {
          return ResultUtils.exception(ResultCode.ERROR);
        }

        request.getRequestDispatcher("/error").forward(request, response);
        return null;
    }

    private void logStackTrace(Exception  ex) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ex.printStackTrace(new PrintStream(baos));
        String exception = baos.toString();
        if (log.isErrorEnabled()) {
            log.error(exception);
        }
    }
}
