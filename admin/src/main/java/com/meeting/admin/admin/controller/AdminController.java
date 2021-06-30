package com.meeting.admin.admin.controller;

import com.meeting.common.admin.service.IAdminService;
import com.meeting.common.adminlog.service.IAdminLogService;
import com.meeting.common.constant.CommonConst;
import com.meeting.common.constant.RedisKeyConst;
import com.meeting.common.pojo.admin.AdminVO;
import com.meeting.common.pojo.adminlog.AdminLogVO;
import com.meeting.common.pojo.base.LoginDTO;
import com.meeting.common.pojo.permission.dto.PermissionDTO;
import com.meeting.common.util.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.redisson.api.RBucket;
import org.redisson.api.RedissonClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.text.MessageFormat;
import java.util.concurrent.TimeUnit;

/**
 * @author: dameizi
 * @description: 管理员控制层
 * @dateTime 2019-03-29 13:30
 * @className com.weilaizhe.common.exception.OtherReturn
 */
@RestController
@RequestMapping(value = "/user")
@Api(value="AdminController", tags="AdminController", description="管理员")
public class AdminController {

    private static Logger logger = LoggerFactory.getLogger(AdminController.class);

    /** 当测试authTest时候，把genSecretTest生成的secret值赋值给它 */
    private static final String SECRET = "QPQV4YQBOQNO3WSR";

    @Autowired
    private IAdminService iAdminService;
    @Autowired
    private RedissonClient redissonClient;
    @Autowired
    private IAdminLogService iAdminLoginLogService;

    /**
     * @author: dameizi
     * @dateTime: 2019-05-31 17:14
     * @description: 获取管理员信息
     * @param: [adminVO]
     * @return: java.lang.Object
     */
    @RequestMapping(value = "/getAdmin" , method = {RequestMethod.GET})
    @ApiOperation(value="获取当前会员",notes="无需参数")
    public Object getAdmin(AdminVO adminVO){
        AdminVO adminInfo = RedissonUtil.getContextAdminInfo();
        if (adminInfo == null){
            return MessageFormat.format(CommonConst.ERROR, CommonConst.LOGIN_CACHE_FAIL);
        }
        adminVO.setAdminId(adminInfo.getAdminId());
        return iAdminService.getAdminById(adminVO);
    }

    /**
     * @author: dameizi
     * @dateTime: 2019-04-03 21:51
     * @description: 修改手机
     * @param: [merchantVO]
     * @return: java.lang.Object
     */
    @RequestMapping(value = "/updatePhone", method = RequestMethod.POST)
    @ApiOperation(value="修改手机",notes="入参：短信验证码、新手机号码、旧手机号码")
    public Object updatePhone(@RequestBody @ApiParam(required=true) AdminVO adminVO){
        AdminVO adminInfo = RedissonUtil.getContextAdminInfo();
        adminVO.setAdminId(adminInfo.getAdminId());
        // 根据会员编码更新手机号码
        return iAdminService.updatePhone(adminVO);
    }

    /**
     * @author: dameizi
     * @dateTime: 2019-04-03 14:01
     * @description: 更改密码
     * @param: [merchantVO]
     * @return: java.lang.Object
     */
    @RequestMapping(value = "/updatePassword", method = RequestMethod.POST)
    @ApiOperation(value="更改密码",notes="必填字段：新密码、确认密码、原密码、手机号码、手机验证码")
    public Object updatePassword(@RequestBody AdminVO adminVO){
        adminVO.setAdminId(RedissonUtil.getContextAdminInfo().getAdminId());
        return iAdminService.updatePassword(adminVO);
    }

    /**
     * @author: dameizi
     * @dateTime: 2019-04-02 21:45
     * @description: 退出登录
     * @param: [request, response]
     * @return: java.lang.Object
     */
    @ResponseBody
    @RequestMapping(value = "/logout", method = RequestMethod.POST)
    @ApiOperation(value="退出登录",notes="必传参数：Authorization")
    public Object login() {
        String token = CommonUtil.getToken();
        String redisSessionKey = MessageFormat.format(RedisKeyConst.REDIS_ADMIN_SESSION_KEY, token);
        RBucket<AdminVO> sessionInfo =  redissonClient.getBucket(redisSessionKey);
        sessionInfo.delete();
        //获取redis全局会话共享 用于单一会员登录  重要依据
        if(RedissonUtil.getContextAdminInfo() != null) {
            String redisTokenKey = MessageFormat.format(RedisKeyConst.REDIS_ADMIN_TOKEN, RedissonUtil.getContextAdminInfo().getAccount());
            redissonClient.getKeys().delete(redisTokenKey);
        }
        return CommonConst.LOGOUT_SUCCESS;
    }

    /**
     * @author: dameizi
     * @dateTime: 2019/3/27 0027 下午 12:39
     * @description: 管理员账号注册
     * @param: [accountVO]
     * @return: java.lang.Object
     */
    @RequestMapping(value = "/insert", method = RequestMethod.POST)
    @ApiOperation(value="管理员账号注册",notes="管理员账号注册")
    public Object insert(@RequestBody AdminVO adminVO){
        return iAdminService.insert(adminVO);
    }

    /**
     * @author: dameizi
     * @dateTime: 2019-03-28 14:11
     * @description: 管理员登录
     * @param: [adminVO, request]
     * @return: java.lang.Object
     */
    @ResponseBody
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    @ApiOperation(value="管理员登录",notes="管理员账号必填，管理员密码必填，验证码必填")
    public Object login(HttpServletRequest request, @RequestBody LoginDTO loginDTO){
        // DTO转为VO
        AdminVO adminVO = POJOConvertUtil.convertPojo(loginDTO, AdminVO.class);
        // 查询登录信息
        AdminVO admin = iAdminService.login(adminVO);
        // 根据账号密码查询找不到则账号或密码错误
        if(admin == null){
            return MessageFormat.format(CommonConst.ERROR, CommonConst.ACCOUNT_PASSWORD_ERROR);
        }
        // 记录管理员登录日志
        this.insertAdminLoginLog(admin, request);
        admin.setPassword(null);
        // redisson管理会话
        String headerToken = request.getHeader(CommonConst.AUTHORIZATION);
        String redisSessionKey = MessageFormat.format(RedisKeyConst.REDIS_ADMIN_SESSION_KEY, headerToken);
        RBucket<AdminVO> sessionInfo =  redissonClient.getBucket(redisSessionKey);
        sessionInfo.set(admin);
        sessionInfo.expire(30, TimeUnit.MINUTES);
        //写入redis全局会话共享 用于单一会员登录重要依据
        String redisKey = MessageFormat.format(RedisKeyConst.REDIS_ADMIN_TOKEN, admin.getAccount());
        RBucket<String> buck =  redissonClient.getBucket(redisKey);
        buck.set(headerToken);
        // 账号类型 admin/user/guest
        admin.setType("admin");
        // 当前权限 admin/user/guest
        admin.setCurrentAuthority("admin");
        PermissionDTO permissionDTO = new PermissionDTO();
        permissionDTO.setAdminVO(admin);
        return permissionDTO;
    }

    /**
     * @author: dameizi
     * @dateTime: 2019-04-02 19:51
     * @description: 记录管理员登录日志
     * @param: [user, request]
     * @return: java.lang.Object
     */
    public void insertAdminLoginLog(AdminVO user, HttpServletRequest request){
        AdminLogVO adminLoginLogVO = new AdminLogVO();
        //管理员账号
        adminLoginLogVO.setAccount(user.getAccount());
        //登录ip
        adminLoginLogVO.setLoginIp(CommonUtil.getIpAddr(request));
        //ip转换物理地址
        IpAccessCityUtil ipAccessCity = new IpAccessCityUtil();
        try {
            String ipAddr = MessageFormat.format("ip={0}", CommonUtil.getIpAddr(request));
            adminLoginLogVO.setLoginRealAddress(ipAccessCity.getAddresses(ipAddr, CommonConst.CODING_UTF8));
        } catch (Exception e) {
            logger.error(CommonConst.GET_IP_ADDRESS_FAIL, e);
        }
        iAdminLoginLogService.insert(adminLoginLogVO);
    }

    /**
     * @author: dameizi
     * @dateTime: 2019-05-14 15:15
     * @description: 谷歌动态口令
     * @param: [securityCode]
     * @return: boolean
     */
    @ResponseBody
    @ApiOperation(value = "获取动态口令")
    @RequestMapping(value = "/authenticator", method = RequestMethod.GET)
    public Object authenticator(HttpServletRequest request, HttpServletResponse response, String securityCode){
        return GoogleAuthenticator.getAuthenticator(request, response, securityCode, SECRET);
    }

}
