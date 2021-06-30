package com.meeting.member.ipwhite.Controller;

import com.meeting.common.ipwhite.service.IIpWhiteService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * @author dameizi
 * @description IP白名单
 * @dateTime 2019-07-31 13:39
 * @className IpWhiteController
 */
@RestController
@RequestMapping("/ipWhite")
@Api(value="IpWhiteController", tags="IpWhite", description="IP白名单")
public class IpWhiteController {

    @Autowired
    private IIpWhiteService iIpWhiteService;



}
