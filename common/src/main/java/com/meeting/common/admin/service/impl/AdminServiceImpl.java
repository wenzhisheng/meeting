package com.meeting.common.admin.service.impl;

import com.meeting.common.admin.dao.IAdminDao;
import com.meeting.common.admin.service.IAdminService;
import com.meeting.common.constant.CommonConst;
import com.meeting.common.constant.RedisKeyConst;
import com.meeting.common.exception.DescribeException;
import com.meeting.common.pojo.admin.AdminVO;
import com.meeting.common.util.CommonUtil;
import com.meeting.common.util.RedissonUtil;
import org.redisson.api.RBucket;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.text.MessageFormat;
import java.util.concurrent.TimeUnit;

/**
 * @author: dameizi
 * @description: 管理员业务层
 * @dateTime 2019-03-27 12:24
 * @className com.meeting.common.exception.OtherReturn
 */
@Service
public class AdminServiceImpl implements IAdminService {

    @Autowired
    private IAdminDao iAdminDao;
    @Autowired
    private RedissonClient redissonClient;
    /**
     * @author: dameizi
     * @dateTime: 2019-05-31 17:19
     * @description: 获取管理员信息
     * @param: [adminVO]
     * @return: AdminVO
     */
    @Override
    public AdminVO getAdminById(AdminVO adminVO) {
        return iAdminDao.getAdminById(adminVO);
    }

    /**
     * @author: dameizi
     * @dateTime: 2019-06-01 13:44
     * @description: 修改手机
     * @param: [adminVO]
     * @return: java.lang.Object
     */
    @Override
    public Object updatePhone(AdminVO adminVO) {
        CommonUtil.checkMobileNumber(adminVO.getTelphone());
        // 校验手机短信
        if (!RedissonUtil.verifySms(adminVO.getPhoneSmsCode(), adminVO.getOldTelPhone())){
            return MessageFormat.format(CommonConst.ERROR, CommonConst.VERIFY_CODE_ERROR);
        }
        iAdminDao.update(adminVO);
        //更新缓存
        updateCache(adminVO);
        return 1;
    }

    /**
     * @author: dameizi
     * @dateTime: 2019-06-01 13:44
     * @description: 更改密码
     * @param: [adminVO]
     * @return: java.lang.Object
     */
    @Override
    public Object updatePassword(AdminVO adminVO) {
        CommonUtil.checkMobileNumber(adminVO.getTelphone());
        // 校验手机短信
        if (!RedissonUtil.verifySms(adminVO.getPhoneSmsCode(), adminVO.getTelphone())){
            return MessageFormat.format(CommonConst.ERROR, CommonConst.VERIFY_CODE_ERROR);
        }
        // 参数不能为空
        if (paramNotEmpty(adminVO)) {
            return MessageFormat.format(CommonConst.ERROR, CommonConst.PARAM_NOT_EMPTY);
        }
        // 原始密码校验
        AdminVO adminUserTemp = iAdminDao.getAdminById(adminVO);
        String oldPassword = CommonUtil.md5Password16(adminVO.getOldPassword());
        if(!oldPassword.equals(adminUserTemp.getPassword())){
            return MessageFormat.format(CommonConst.ERROR, CommonConst.OLD_PASSWORD_ERROR);
        }
        // 密码校验
        this.passwordVerify(adminVO);
        // 两个密码比较以免有误
        if(!adminVO.getPassword().equals(adminVO.getConfirmPassword()) ){
            return MessageFormat.format(CommonConst.ERROR, CommonConst.CONFIRM_PASSWORD_ERROR);
        }
        // 密码MD532
        adminVO.setPassword(CommonUtil.md5Password16(adminVO.getPassword()));
        iAdminDao.update(adminVO);
        //更新缓存
        updateCache(adminVO);
        return 1;
    }

    /**
     * @author: dameizi
     * @dateTime: 2019-06-01 13:50
     * @description: 参数不能为空
     * @param: [adminVO]
     * @return: boolean
     */
    private boolean paramNotEmpty(AdminVO adminVO) {
        if(StringUtils.isEmpty(adminVO.getOldPassword())) {
            return true;
        }
        if(StringUtils.isEmpty(adminVO.getPassword())) {
            return true;
        }
        if(StringUtils.isEmpty(adminVO.getConfirmPassword())) {
            return true;
        }
        if(StringUtils.isEmpty(adminVO.getPhoneSmsCode())) {
            return true;
        }
        return false;
    }

    /**
     * @author: dameizi
     * @dateTime: 2019/3/27 0027 下午 12:40
     * @description: 管理员账号注册
     * @param: [accountVO]
     * @return: int
     */
    @Override
    public int insert(AdminVO adminVO) {
        //账号,密码,验证码非空判断
        CommonUtil.paramEmptyVerify(adminVO.getAccount(), CommonConst.ACCOUNT_NOT_EMPTY);
        CommonUtil.paramEmptyVerify(adminVO.getPassword(), CommonConst.PASSWORD_NOT_EMPTY);
        //账号是否已被使用
        if (iAdminDao.getAccountIsUse(adminVO) > 0){
            throw new DescribeException(CommonConst.ACCOUNT_IS_USE, -3);
        }
        //密码校验
        this.passwordVerify(adminVO);
        //密码MD5加密
        adminVO.setPassword(CommonUtil.md5Password(adminVO.getPassword()));
        return iAdminDao.insert(adminVO);
    }

    /**
     * @author: dameizi
     * @dateTime: 2019-03-28 15:01
     * @description: 管理员登录
     * @param: [adminVO]
     * @return: AdminVO
     */
    @Override
    public AdminVO login(AdminVO adminVO) {
        // 账号密码非空判断
        CommonUtil.paramEmptyVerify(adminVO.getAccount(), CommonConst.ACCOUNT_NOT_EMPTY);
        CommonUtil.paramEmptyVerify(adminVO.getPassword(), CommonConst.PASSWORD_NOT_EMPTY);
        // 密码MD5
        adminVO.setPassword(CommonUtil.md5Password16(adminVO.getPassword()));
        return iAdminDao.login(adminVO);
    }

    /**
     * @author: dameizi
     * @dateTime: 2019-04-03 13:44
     * @description: 密码校验
     * @param: [merchantVO]
     * @return: void
     */
    private void passwordVerify(AdminVO adminVO) {
        // 密码必须是6-18位数字字母组合，字母区分大小写（MD5加密字符串校验）
        String regex = "^([a-zA-Z0-9]{6,18})$";
        String pwd = adminVO.getPassword();
        if(StringUtils.isEmpty(pwd) || !pwd.matches(regex)) {
            throw new DescribeException(CommonConst.MERCHANT_PASSWORD_VERIFY, 0);
        }
    }

    /**
     * @author: dameizi
     * @dateTime: 2019-06-01 0:19
     * @description: 更新缓存
     * @param: [adminVO]
     * @return: void
     */
    public void updateCache(AdminVO adminVO){
        AdminVO admin = iAdminDao.getAdminById(adminVO);
        String redisSessionKey = MessageFormat.format(RedisKeyConst.REDIS_ADMIN_SESSION_KEY, CommonUtil.getToken());
        RBucket<AdminVO> sessionInfo =  redissonClient.getBucket(redisSessionKey);
        sessionInfo.delete();
        sessionInfo.set(admin);
        sessionInfo.expire(30, TimeUnit.MINUTES);
    }

}
