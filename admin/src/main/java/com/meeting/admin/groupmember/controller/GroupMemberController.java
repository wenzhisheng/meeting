package com.meeting.admin.groupmember.controller;

import com.alibaba.fastjson.JSON;
import com.meeting.common.groupmember.service.IGroupMemberService;
import com.meeting.common.pojo.groupmember.GroupMemberVO;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

/**
 * @author dameizi
 * @description 群组成员
 * @dateTime 2019-04-21 15:34
 * @className com.meeting.admin.groupmember.controller.GroupMemberController
 */
@RestController
@RequestMapping("/groupMember")
@Api(value = "GroupMember", tags = "GroupMemberController", description = "群组成员")
public class GroupMemberController {

    @Autowired
    private IGroupMemberService iGroupMemberService;



}
