package com.meeting.admin.adminlog.controller;

import com.meeting.common.adminlog.service.IAdminLogService;
import com.meeting.common.pojo.adminlog.AdminLogVO;
import com.meeting.common.pojo.base.PageVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author: dameizi
 * @description: 管理员登录日志控制层
 * @dateTime 2019-04-02 19:17
 * @className com.weilaizhe.merchant.adminloginlog.controller.AdminLoginLogController
 */
@RestController
@RequestMapping("/loginLog")
@Api(value="AdminLoginLogController", tags="AdminLoginLogController", description="管理员登录日志")
public class AdminLogController {

    @Autowired
    private IAdminLogService iAdminLoginLogService;

    /**
     * @author: dameizi
     * @dateTime: 2019-04-02 20:31
     * @description: 批量删除管理员登录日志
     * @param: [ids]
     * @return: java.lang.Object
     */
    @RequestMapping(value = "/batchDelete", method = RequestMethod.GET)
    @ApiOperation(value = "批量删除管理员登录日志", notes = "必填参数：管理员登录日志ID")
    public Object batchDelete(AdminLogVO adminLoginLog){
        return iAdminLoginLogService.batchDelete(adminLoginLog);
    }

    /**
     * @author: dameizi
     * @dateTime: 2019-04-02 20:13
     * @description: 分页查询管理员登录日志
     * @param: [merchantLoginLogVO, pageVO]
     * @return: java.lang.Object
     */
    @ResponseBody
    @RequestMapping(value = "/page", method = RequestMethod.GET)
    @ApiOperation(value = "分页查询管理员登录日志", notes = "必填参数：分页 开始结束时间")
    public Object page(AdminLogVO adminLoginLog, PageVO pageVO){
        return iAdminLoginLogService.page(adminLoginLog, pageVO);
    }

}
