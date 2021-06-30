package com.meeting.admin.friendclass.controller;

import com.meeting.common.friendclass.service.IFriendClassService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author dameizi
 * @description 好友分组
 * @dateTime 2019-04-21 15:04
 * @className com.meeting.meeting.friendtype.controller.FriendTypeController
 */
@RestController
@RequestMapping("/friendClass")
@Api(value = "FriendClassController", tags = "FriendClass", description = "好友分组")
public class FriendClassController {

    @Autowired
    private IFriendClassService iFriendClassService;



}
