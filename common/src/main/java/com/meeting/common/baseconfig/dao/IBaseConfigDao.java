package com.meeting.common.baseconfig.dao;

import com.meeting.common.pojo.baseconfig.BaseConfigVO;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/**
 * @author dameizi
 * @description 基础配置数据层
 * @dateTime 2019-07-18 22:13
 * @className com.meeting.common.baseconfig.dao.IBaseConfigDao
 */
@Repository
public interface IBaseConfigDao {

    /**
     * @author: dameizi
     * @dateTime: 2019-07-18 22:23
     * @description: 插入基础配置
     * @param: [baseConfigVO]
     * @return: int
     */
    int insert(@Param("vo") BaseConfigVO baseConfigVO);

    /**
     * @author: dameizi
     * @dateTime: 2019-07-18 22:26
     * @description: 获取基础配置
     * @param: []
     * @return: com.meeting.common.pojo.baseconfig.BaseConfigVO
     */
    BaseConfigVO getBaseConfig();

}
