package com.meeting.member.groupmessage.controller;

import com.meeting.common.groupmessage.service.IGroupMessageService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author dameizi
 * @description 群聊消息
 * @dateTime 2019-04-25 17:38
 * @className com.meeting.meeting.groupmessage.controller.GroupMessageController
 */
@RestController
@RequestMapping("/groupMessage")
@Api(value = "GroupMessageController", tags = "GroupMessage", description = "群聊消息")
public class GroupMessageController {

    @Autowired
    private IGroupMessageService iGroupMessageService;



}
