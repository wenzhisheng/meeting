package com.meeting.member.navigation.controller;

import com.meeting.common.navigation.service.INavigationService;
import com.meeting.common.pojo.navigation.NavigationVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author dameizi
 * @description 主导航栏
 * @dateTime 2019-06-15 12:40
 * @className com.meeting.member.navigation.controller.NavigationController
 */
@RestController
@RequestMapping("/navigation")
@Api(value="NavigationController", tags="Navigation", description="主导航栏")
public class NavigationController {

    @Autowired
    private INavigationService iNavigationService;

    /**
     * @author: dameizi
     * @dateTime: 2019-06-15 13:01
     * @description: 查询导航栏列表
     * @param: [navigationVO]
     * @return: java.lang.Object
     */
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    @ApiOperation(value="查询导航栏列表",notes="无需参数")
    public Object list(NavigationVO navigationVO){
        return iNavigationService.list(navigationVO);
    }

}
