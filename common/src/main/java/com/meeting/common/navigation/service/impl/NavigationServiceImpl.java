package com.meeting.common.navigation.service.impl;

import com.meeting.common.navigation.dao.INavigationDao;
import com.meeting.common.navigation.service.INavigationService;
import com.meeting.common.pojo.navigation.NavigationVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author dameizi
 * @description 底部导航菜单业务层
 * @dateTime 2019-06-15 12:47
 * @className com.meeting.common.navigation.service.impl.NavigationServiceImpl
 */
@Service
public class NavigationServiceImpl implements INavigationService {

    @Autowired
    private INavigationDao iNavigationDao;

    /**
     * @author: dameizi
     * @dateTime: 2019-08-02 11:54
     * @description: 底部导航菜单列表
     * @param: [navigationVO]
     * @return: java.util.List<com.meeting.common.pojo.navigation.NavigationVO>
     */
    @Override
    public List<NavigationVO> list(NavigationVO navigationVO) {
        return iNavigationDao.list(navigationVO);
    }

}
