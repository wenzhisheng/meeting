package com.meeting.common.navigation.service;

import com.meeting.common.pojo.navigation.NavigationVO;

import java.util.List;

/**
 * @author dameizi
 * @description 底部导航菜单接口层
 * @dateTime 2019-06-15 12:47
 * @className com.meeting.common.navigation.dao.INavigationDao
 */
public interface INavigationService {

    /**
     * @author: dameizi
     * @dateTime: 2019-08-02 11:54
     * @description: 底部导航菜单列表
     * @param: [navigationVO]
     * @return: java.util.List<com.meeting.common.pojo.navigation.NavigationVO>
     */
    List<NavigationVO> list(NavigationVO navigationVO);

}
