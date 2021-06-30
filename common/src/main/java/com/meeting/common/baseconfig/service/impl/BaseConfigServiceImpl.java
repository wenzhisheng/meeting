package com.meeting.common.baseconfig.service.impl;

import com.meeting.common.baseconfig.dao.IBaseConfigDao;
import com.meeting.common.baseconfig.service.IBaseConfigService;
import com.meeting.common.pojo.baseconfig.BaseConfigVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author dameizi
 * @description 基础配置业务层
 * @dateTime 2019-07-18 22:14
 * @className com.meeting.common.baseconfig.service.impl.BaseConfigServiceImpl
 */
@Service
public class BaseConfigServiceImpl implements IBaseConfigService {

    @Autowired
    private IBaseConfigDao iBaseConfigDao;

    /**
     * @author: dameizi
     * @dateTime: 2019-07-18 22:24
     * @description: 获取基础配置
     * @param: [baseConfigVO]
     * @return: com.meeting.common.pojo.baseconfig.BaseConfigVO
     */
    @Override
    public BaseConfigVO getBaseConfig() {
        return iBaseConfigDao.getBaseConfig();
    }
}
