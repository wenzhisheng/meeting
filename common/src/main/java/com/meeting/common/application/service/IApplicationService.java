package com.meeting.common.application.service;

import com.meeting.common.pojo.application.ApplicationVO;

import java.util.List;

/**
 * @author dameizi
 * @description 应用接口层
 * @dateTime 2019-06-15 12:47
 * @className com.meeting.common.navigation.dao.ApplicationVO
 */
public interface IApplicationService {

    /**
     * @author: dameizi
     * @dateTime: 2019-07-10 22:02
     * @description: 应用列表
     * @param: [applicationVO]
     * @return: java.util.List<com.meeting.common.pojo.application.ApplicationVO>
     */
    List<ApplicationVO> list(ApplicationVO applicationVO);

}
