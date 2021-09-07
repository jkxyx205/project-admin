package com.rick.project.admin.exception;

import com.rick.project.admin.common.ResultCode;

/**
 * All rights Reserved, Designed By www.xhope.top
 *
 * @version V1.0
 * @Description: 爬取价格异常
 * @author: Rick.Xu
 * @date: 12/16/19 7:05 PM
 * @Copyright: 2019 www.yodean.com. All rights reserved.
 */
public class PriceCrawlerException extends AcException {

    public PriceCrawlerException(Throwable t) {
        super(ResultCode.PRICE_CRAWLER_ERROR, t);
    }
}
