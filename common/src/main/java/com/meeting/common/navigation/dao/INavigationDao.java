package com.meeting.common.navigation.dao;

import com.meeting.common.pojo.navigation.NavigationVO;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author dameizi
 * @description 底部导航菜单数据层
 * @dateTime 2019-06-15 12:47
 * @className com.meeting.common.navigation.dao.INavigationDao
 */
@Repository
public interface INavigationDao {

    /**
     * @author: dameizi
     * @dateTime: 2019-08-02 11:53
     * @description: 底部导航菜单列表
     * @param: [navigationVO]
     * @return: java.util.List<com.meeting.common.pojo.navigation.NavigationVO>
     */
    List<NavigationVO> list(@Param("vo") NavigationVO navigationVO);

}
