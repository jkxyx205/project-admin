package com.rick.project.admin.exception;

import com.rick.project.admin.common.ResultCode;
import lombok.Getter;

/**
 * All rights Reserved, Designed By www.xhope.top
 *
 * @version V1.0
 * @Description: (用一句话描述该文件做什么)
 * @author: Rick.Xu
 * @date: 12/16/19 7:04 PM
 * @Copyright: 2019 www.yodean.com. All rights reserved.
 */
@Getter
public class AcException extends RuntimeException {

    private ResultCode resultCode;

    private Object[] params;

    public AcException(ResultCode resultCode) {
        super(resultCode.getMsg());
        this.resultCode = resultCode;
    }

    public AcException(ResultCode resultCode, Object[] params) {
        this(resultCode);
        this.resultCode = resultCode;
        this.params = params;
    }

    public AcException(ResultCode resultCode ,Throwable t) {
        super(resultCode.getMsg(), t);
        this.resultCode = resultCode;
    }

    public AcException(ResultCode resultCode, Object[] params, Throwable t) {
        this(resultCode, t);
        this.params = params;
    }
}
