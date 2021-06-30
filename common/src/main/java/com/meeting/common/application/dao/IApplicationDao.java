package com.meeting.common.application.dao;

import com.meeting.common.pojo.application.ApplicationVO;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author dameizi
 * @description 应用数据层
 * @dateTime 2019-06-15 12:47
 * @className com.meeting.common.navigation.dao.IApplicationDao
 */
@Repository
public interface IApplicationDao {

    /**
     * @author: dameizi
     * @dateTime: 2019-07-10 22:02
     * @description: 应用列表
     * @param: [applicationVO]
     * @return: java.util.List<com.meeting.common.pojo.application.ApplicationVO>
     */
    List<ApplicationVO> list(@Param("vo") ApplicationVO applicationVO);

}
