package com.meeting.member.memberlog;

import com.meeting.common.member.service.IMemberService;
import com.meeting.common.memberlog.service.IMemberLogService;
import io.swagger.annotations.Api;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author: dameizi
 * @description: 会员日志
 * @dateTime 2019-05-29 15:55
 * @className MerchantController
 */
@RestController
@RequestMapping("/memberLog")
@Api(value="MemberLogController", tags="MemberLog", description="会员日志")
public class MemberLogController {

    @Autowired
    private IMemberLogService iMemberLogService;



}
