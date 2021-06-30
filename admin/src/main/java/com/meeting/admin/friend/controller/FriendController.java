package com.meeting.admin.friend.controller;

import com.meeting.common.friend.service.IFriendService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author dameizi
 * @description 好友关系
 * @dateTime 2019-07-12 23:15
 * @className com.meeting.admin.friend.controller.FriendController
 */
@RestController
@RequestMapping("/friend")
@Api(value="FriendController", tags="Friend", description="好友关系")
public class FriendController {

    @Autowired
    private IFriendService friendService;



}