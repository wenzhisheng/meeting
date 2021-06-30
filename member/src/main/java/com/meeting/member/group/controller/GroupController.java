package com.meeting.member.group.controller;

import com.meeting.common.group.service.IGroupService;
import com.meeting.common.groupmember.service.IGroupMemberService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author dameizi
 * @description 群组
 * @dateTime 2019-04-21 15:07
 * @className com.meeting.meeting.groups.controller.GroupController
 */
@RestController
@RequestMapping("/group")
@Api(value = "GroupController", tags = "Group", description = "群组")
public class GroupController {

    @Autowired
    private IGroupService groupService;



}
