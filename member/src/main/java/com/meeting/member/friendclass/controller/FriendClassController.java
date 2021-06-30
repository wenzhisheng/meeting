package com.meeting.member.friendclass.controller;

import com.alibaba.fastjson.JSON;
import com.meeting.common.friendclass.service.IFriendClassService;
import com.meeting.common.pojo.friendclass.FriendClassVO;
import com.meeting.common.pojo.member.MemberVO;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

/**
 * @author dameizi
 * @description 好友分组
 * @dateTime 2019-04-21 15:04
 * @className com.meeting.meeting.friendtype.controller.FriendTypeController
 */
@RestController
@RequestMapping("/friendType")
@Api(value = "FriendType", tags = "FriendTypeController", description = "好友分组")
public class FriendClassController {

    @Autowired
    private IFriendClassService friendTypeService;



}
