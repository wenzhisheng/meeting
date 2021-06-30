package com.meeting.admin.memberlog.controller;

import com.meeting.common.memberlog.service.IMemberLogService;
import com.meeting.common.pojo.base.PageVO;
import com.meeting.common.pojo.memberlog.MemberLogVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author: dameizi
 * @description: 会员登录日志
 * @dateTime 2019-04-02 19:17
 * @className MerchantLoginLogController
 */
@RestController
@RequestMapping("/memberLog")
@Api(value="MemberLogController", tags="MemberLog", description="会员登录日志")
public class MemberLogController {

    @Autowired
    private IMemberLogService iMerchantLoginLogService;

    /**
     * @author: dameizi
     * @dateTime: 2019-04-02 20:31
     * @description: 批量删除会员登录日志
     * @param: [ids]
     * @return: java.lang.Object
     */
    @RequestMapping(value = "/batchDelete", method = RequestMethod.GET)
    @ApiOperation(value = "批量删除会员登录日志", notes = "必填参数：会员登录日志ID")
    public Object removeMerchantLoginLog(MemberLogVO merchantLoginLogVO){
        return iMerchantLoginLogService.batchDelete(merchantLoginLogVO);
    }

    /**
     * @author: dameizi
     * @dateTime: 2019-04-02 20:13
     * @description: 分页查询会员登录日志
     * @param: [merchantLoginLogVO, pageVO]
     * @return: java.lang.Object
     */
    @ResponseBody
    @RequestMapping(value = "/page", method = RequestMethod.GET)
    @ApiOperation(value = "分页查询会员登录日志", notes = "必填参数：分页 开始结束时间")
    public Object pageMerchantLoginLog(MemberLogVO merchantLoginLogVO, PageVO pageVO){
        return iMerchantLoginLogService.page(merchantLoginLogVO, pageVO);
    }

}
