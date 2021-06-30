package com.meeting.admin.friendmessage.controller;

import com.meeting.common.friendmessage.service.IFriendMessageService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author dameizi
 * @description 单聊消息
 * @dateTime 2019-04-25 17:41
 * @className com.meeting.meeting.friendmessage.controller.FriendMessageController
 */
@RestController
@RequestMapping("/friendMessage")
@Api(value = "FriendMessageController", tags = "FriendMessage", description = "单聊消息")
public class FriendMessageController {

    @Autowired
    private IFriendMessageService iFriendMessageService;



}
