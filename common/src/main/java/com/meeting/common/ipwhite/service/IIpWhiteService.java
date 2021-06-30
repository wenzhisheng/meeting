package com.meeting.common.ipwhite.service;

import com.meeting.common.pojo.base.PageVO;
import com.meeting.common.pojo.ipwhite.IpWhiteVO;

/**
 * @author dameizi
 * @description IP白名单接口层
 * @dateTime 2019-07-13 21:18
 * @className com.meeting.common.ipwhite.service.IIpWhiteService
 */
public interface IIpWhiteService {

    /**
     * @author: dameizi
     * @dateTime: 2019-08-02 11:20
     * @description: IP白名单分页
     * @param: [ipWhiteVO, pageVO]
     * @return: java.lang.Object
     */
    Object page(IpWhiteVO ipWhiteVO, PageVO pageVO);

    /**
     * @author: dameizi
     * @dateTime: 2019-07-15 10:23
     * @description: IP是否过白
     * @param: [ipWhiteVO]
     * @return: boolean
     */
    boolean isIpWhite(IpWhiteVO ipWhiteVO);

    /**
     * @author: dameizi
     * @dateTime: 2019-07-15 10:23
     * @description: IP白名单添加
     * @param: [ipWhiteVO]
     * @return: boolean
     */
    Object insert(IpWhiteVO ipWhiteVO);

    /**
     * @author: dameizi
     * @dateTime: 2019-07-15 10:23
     * @description: IP白名单更新
     * @param: [ipWhiteVO]
     * @return: boolean
     */
    Object update(IpWhiteVO ipWhiteVO);

    /**
     * @author: dameizi
     * @dateTime: 2019-07-15 10:23
     * @description: IP白名单删除
     * @param: [ipWhiteVO]
     * @return: boolean
     */
    Object delete(IpWhiteVO ipWhiteVO);

}
