package com.meeting.common.baseconfig.service;

import com.meeting.common.pojo.baseconfig.BaseConfigVO;

/**
 * @author dameizi
 * @description 基础配置接口层
 * @dateTime 2019-07-18 22:13
 * @className com.meeting.common.baseconfig.service.IBaseConfigService
 */
public interface IBaseConfigService {

    /**
     * @author: dameizi
     * @dateTime: 2019-07-18 22:27
     * @description: 获取基础配置
     * @param: []
     * @return: com.meeting.common.pojo.baseconfig.BaseConfigVO
     */
    BaseConfigVO getBaseConfig();

}
