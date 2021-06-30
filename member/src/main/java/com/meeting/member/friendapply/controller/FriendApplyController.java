package com.meeting.member.friendapply.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.meeting.common.friend.service.IFriendService;
import com.meeting.common.friendapply.service.IFriendApplyService;
import com.meeting.common.friendclass.service.IFriendClassService;
import com.meeting.common.pojo.friend.FriendVO;
import com.meeting.common.pojo.friendapply.FriendApplyVO;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author dameizi
 * @description 好友申请
 * @dateTime 2019-04-24 21:47
 * @className com.meeting.meeting.friendapply.controller.FriendApplyController
 */
@RestController
@RequestMapping("/friendApply")
@Api(value="Friend", tags="FriendController", description="好友关系")
public class FriendApplyController {

    @Autowired
    private IFriendApplyService friendApplyService;



}
