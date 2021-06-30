package com.meeting.common.areacascade.dao;

import com.meeting.common.pojo.areacascade.AreaCascadeVO;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author dameizi
 * @description 地区级联数据层
 * @dateTime 2019-07-11 12:24
 * @className com.meeting.common.areacascade.dao.IAreaCascadeDao
 */
@Repository
public interface IAreaCascadeDao {

    /**
     * @author: dameizi
     * @dateTime: 2019-07-11 12:32
     * @description: 级联列表
     * @param: [areaCascadeVO]
     * @return: java.util.List<com.meeting.common.pojo.areacascade.AreaCascadeVO>
     */
    List<AreaCascadeVO> cascadeList(@Param("vo") AreaCascadeVO areaCascadeVO);

    /**
     * @author: dameizi
     * @dateTime: 2019-07-11 12:40
     * @description: 插入级联
     * @param: [areaCascadeVO]
     * @return:
     */
    int insert(@Param("vo") AreaCascadeVO areaCascadeVO);

}
