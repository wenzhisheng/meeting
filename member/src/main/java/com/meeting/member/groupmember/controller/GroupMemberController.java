package com.meeting.member.groupmember.controller;

import com.meeting.common.groupmember.service.IGroupMemberService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author dameizi
 * @description 群组成员
 * @dateTime 2019-04-21 15:34
 * @className com.meeting.meeting.groupmember.controller.GroupMemberController
 */
@RestController
@RequestMapping("/groupMember")
@Api(value = "GroupMemberController", tags = "GroupMember", description = "群组成员")
public class GroupMemberController {

    @Autowired
    private IGroupMemberService groupUserService;



}
