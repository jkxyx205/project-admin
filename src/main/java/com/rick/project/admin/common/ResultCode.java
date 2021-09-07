package com.rick.project.admin.common;

import lombok.Getter;
import lombok.ToString;

/**
 * All rights Reserved, Designed By www.xhope.top
 *
 * @version V1.0
 * @Description: (用一句话描述该文件做什么)
 * @author: Rick.Xu
 * @date: 9/10/19 2:03 PM
 * @Copyright: 2019 www.yodean.com. All rights reserved.
 */
@Getter
@ToString
public enum ResultCode {
    OK(0, "OK"), ERROR(-1, "服务器异常"),
    REQUEST_PARAM_ERROR(30001, "参数传递错误"),
    BEAN_VALIDATE_ERROR(30002, "BEAN_VALIDATE_ERROR"),
    ACCESS_FORBIDDEN_ERROR(40002, "访问未授权"),
    VALIDATE_CODE_ERROR(40003, "验证码输入不正确"),
    PRICE_CRAWLER_ERROR(40004, "网站价格获取失败"),
    DEFAULT_FORMULA_NOT_EXISTS_ERROR(40005, "该型号还未设置公式"),
    FORMULA_ERROR(40006, "公式设置有误"),
    RESERVE_YMAL_ERROR(40007, "国内仓锁货权限配置解析错误，请联系管理员"),
    REPORT_SQL_ERROR(40008, "报表SQL不能为空或含有非法字符"),
    ROLE_NULL_ERROR(40009, "ROLE_NAME_CANNOT_BE_EMPTY"),
    RADIO_RANGE_ERROR(400010, "供应链&库存预警比例格式不正确，正确格式如：3.5-8"),
    INVALID_SESSION(400011, "访问超时，请退出后重新登录");

    private int code;

    private String msg;

    ResultCode(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }
}
