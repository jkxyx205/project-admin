package com.rick.project.admin.common;

/**
 * All rights Reserved, Designed By www.xhope.top
 *
 * @version V1.0
 * @Description: (用一句话描述该文件做什么)
 * @author: Rick.Xu
 * @date: 9/10/19 1:57 PM
 * @Copyright: 2019 www.yodean.com. All rights reserved.
 */
public final class ResultUtils {

    public static Result success() {
        return success(null);
    }

    public static <T> Result<T> success(T data) {
        return new Result(true, ResultCode.OK.getCode(), ResultCode.OK.getMsg(), data);
    }

    /**
     * 异常Result
     *
     * @param
     * @return
     */
    public static Result<String> exception(ResultCode rc, String msg) {
        return new Result(false, rc.getCode(), msg, null);
    }

    /**
     * 异常Result
     *
     * @param
     * @return
     */
    public static Result<String> exception(ResultCode rc) {
        return exception(rc, rc.getMsg());
    }

}