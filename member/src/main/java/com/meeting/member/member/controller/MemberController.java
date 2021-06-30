package com.meeting.member.member.controller;

import com.meeting.common.constant.CommonConst;
import com.meeting.common.constant.RedisKeyConst;
import com.meeting.common.friendmessage.dao.IFriendMessageDao;
import com.meeting.common.friendmessage.service.IFriendMessageService;
import com.meeting.common.member.service.IMemberService;
import com.meeting.common.memberlog.service.IMemberLogService;
import com.meeting.common.pojo.base.LoginDTO;
import com.meeting.common.pojo.friendmessage.FriendMessageVO;
import com.meeting.common.pojo.member.MemberVO;
import com.meeting.common.pojo.memberlog.MemberLogVO;
import com.meeting.common.pojo.socketio.ChatMessageVO;
import com.meeting.common.util.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.redisson.api.RBucket;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.text.MessageFormat;
import java.util.concurrent.TimeUnit;

/**
 * @author: dameizi
 * @description: 会员信息
 * @dateTime 2019-05-29 15:55
 * @className MemberController
 */
@RestController
@RequestMapping("/member")
@Api(value="MemberController", tags="Member", description="会员信息")
public class MemberController {

    private final static Logger logger = LogManager.getLogger(MemberController.class);

    @Autowired
    private MongoTemplate mongoTemplate;
    @Autowired
    private RedissonClient redissonClient;
    @Autowired
    private IMemberService iMemberService;
    @Autowired
    private IMemberLogService iMemberLogService;
    @Autowired
    private IFriendMessageService iFriendMessageService;

    /**
     * @author: dameizi
     * @dateTime: 2019-07-05 19:12
     * @description: 注册聊天会员
     * @param: [file, memberVO]
     * @return: java.lang.Object
     */
    @CrossOrigin
    @RequestMapping(value = "/insert", method = RequestMethod.POST)
    @ApiOperation(value="注册会员",notes="必填参数：")
    public Object insert(@RequestParam(value = "file", required = false) MultipartFile file, @RequestBody MemberVO memberVO){
        return iMemberService.insert(file, memberVO);
    }

    /**
     * @author: dameizi
     * @dateTime: 2019-07-06 20:56
     * @description: 搜索会员
     * @param: [merchantVO]
     * @return: java.lang.Object
     */
    @CrossOrigin
    @RequestMapping(value = "/getMember" , method = {RequestMethod.GET})
    @ApiOperation(value="搜索会员",notes="根据账号搜索")
    public Object getMember(MemberVO memberVO){
        FriendMessageVO friendMessageVO = new FriendMessageVO();
        friendMessageVO.setMessageType("000");
        friendMessageVO.setContent("000");
        iFriendMessageService.save(friendMessageVO);
        ChatMessageVO chatMessageVO = new ChatMessageVO();
        chatMessageVO.setMessageType("single");
        chatMessageVO.setMemberId(1);
        chatMessageVO.setTargetId(2);
        return iFriendMessageService.listMessage(chatMessageVO);
//        return iMemberService.getMember(memberVO);
    }

    /**
     * @author: dameizi
     * @dateTime: 2019-07-06 20:56
     * @description: 修改密码
     * @param: [merchantVO]
     * @return: java.lang.Object
     */
    @CrossOrigin
    @RequestMapping(value = "/changePassword" , method = {RequestMethod.POST})
    @ApiOperation(value="修改密码",notes="找回类型，邮箱、手机号、验证码")
    public Object changePassword(@RequestBody MemberVO memberVO){
        return iMemberService.changePassword(memberVO);
    }

    /**
     * @author: dameizi
     * @dateTime: 2019-04-02 22:00
     * @description: 更新会员信息
     * @param: [merchant, request]
     * @return: java.lang.Object
     */
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    @ApiOperation(value="更新会员信息",notes="必填参数：主键")
    public Object updateMerchant(@RequestBody MemberVO merchantVO){
        MemberVO merchantInfo = RedissonUtil.getContextMerchantInfo();
        merchantVO.setAccount(merchantInfo.getAccount());
        return iMemberService.update(merchantVO);
    }

    /**
     * @author: dameizi
     * @dateTime: 2019-04-02 19:51
     * @description: 记录会员登录日志
     * @param: [user, request]
     * @return: java.lang.Object
     */
    public Object insertMerchantLog(MemberVO user, HttpServletRequest request){
        MemberLogVO memberLogVO = new MemberLogVO();
        // 会员账号
        memberLogVO.setAccount(user.getAccount());
        // 登录ip
        memberLogVO.setLoginIp(CommonUtil.getIpAddr(request));
        // ip转换物理地址
        IpAccessCityUtil ipAccessCity = new IpAccessCityUtil();
        try {
            String ipAddr = MessageFormat.format("ip={0}", CommonUtil.getIpAddr(request));
            memberLogVO.setLoginRealAddress(ipAccessCity.getAddresses(ipAddr, "utf-8"));
        } catch (Exception e) {
            logger.error(CommonConst.GET_IP_ADDRESS_FAIL, e);
        }
        return iMemberLogService.insert(memberLogVO);
    }

}
