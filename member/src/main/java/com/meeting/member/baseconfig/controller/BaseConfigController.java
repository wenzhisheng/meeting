package com.meeting.member.baseconfig.controller;

import com.meeting.common.baseconfig.service.IBaseConfigService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author dameizi
 * @description 基础配置
 * @dateTime 2019-07-21 13:39
 * @className BaseConfigController
 */
@RestController
@RequestMapping("/baseConfig")
@Api(value="BaseConfigController", tags="BaseConfig", description="基础配置")
public class BaseConfigController {

    @Autowired
    private IBaseConfigService iBaseConfigService;

    /**
     * @author: dameizi
     * @dateTime: 2019-07-18 22:29
     * @description: 获取基础配置
     * @param: []
     * @return: java.lang.Object
     */
    @GetMapping("/get")
    @ApiOperation(value="获取基础配置",notes="无需参数")
    public Object getBaseConfig(){
        return iBaseConfigService.getBaseConfig();
    }

}
