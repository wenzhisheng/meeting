package com.meeting.admin.friendapply.controller;

import com.meeting.common.friend.service.IFriendService;
import com.meeting.common.friendapply.service.IFriendApplyService;
import com.meeting.common.friendclass.service.IFriendClassService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author dameizi
 * @description 好友申请
 * @dateTime 2019-06-24 21:47
 * @className com.meeting.admin.friendapply.controller.FriendApplyController
 */
@RestController
@RequestMapping("/friendApply")
@Api(value="FriendApplyController", tags="FriendApply", description="好友申请")
public class FriendApplyController {

    @Autowired
    private IFriendApplyService friendApplyService;



}
