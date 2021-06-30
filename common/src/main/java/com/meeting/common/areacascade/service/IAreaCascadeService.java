package com.meeting.common.areacascade.service;

import com.meeting.common.pojo.areacascade.AreaCascadeVO;

/**
 * @author dameizi
 * @description 地区级联
 * @dateTime 2019-07-11 12:24
 * @className com.meeting.common.areacascade.service.IAreaCascadeService
 */
public interface IAreaCascadeService {

    /**
     * @author: dameizi
     * @dateTime: 2019-07-11 12:29
     * @description: 级联列表
     * @param: []
     * @return: java.lang.Object
     */
    Object cascadeList();

    /**
     * @author: dameizi
     * @dateTime: 2019-07-11 15:52
     * @description: 地区省列表
     * @param: []
     * @return: java.lang.Object
     */
    Object provinceList();

    /**
     * @author: dameizi
     * @dateTime: 2019-07-11 16:10
     * @description: 地区市县查询
     * @param: []
     * @return: java.lang.Object
     */
    Object cityList(AreaCascadeVO areaCascadeVO);

}
