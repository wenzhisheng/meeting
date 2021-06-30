package com.meeting.common.util;

import com.meeting.common.constant.RedisKeyConst;
import com.meeting.common.pojo.admin.AdminVO;
import com.meeting.common.pojo.member.MemberVO;
import org.redisson.api.RBucket;
import org.redisson.api.RedissonClient;

import java.text.MessageFormat;

/**
 * @author dameizi
 * @description redisson工具类
 * @dateTime 2019-05-22 14:32
 * @className RedissonUtil
 */
public class RedissonUtil {

    /** 通过上下文获取Redisson客户端 */
    private static RedissonClient redissonClient = SpringContextHolder.getBean("redissonClient");

    /**
     * @author: dameizi
     * @dateTime: 2019-04-03 21:28
     * @description: 校验短信
     * @param: [validCode]
     * @return: void
     */
    public static boolean verifySms(String phoneSmsCode, String oldTelPhone){
        RBucket<String> bucket = redissonClient.getBucket(MessageFormat.format(RedisKeyConst.MEMBER_PHONE_SMS, oldTelPhone));
        String smsCode = bucket.get();
        if (smsCode != null){
            if (!smsCode.equals(phoneSmsCode)){
                return false;
            }
            return true;
        }else{
            bucket.delete();
            return false;
        }
    }

    /**
     * @author: dameizi
     * @dateTime: 2019-05-22 17:50
     * @description: 当前会员缓存信息
     * @param: []
     * @return: MerchantVO
     */
    public static MemberVO getContextMerchantInfo() {
        String Authorization = CommonUtil.getToken();
        String key = MessageFormat.format(RedisKeyConst.REDIS_MEMBER_SESSION_KEY , Authorization);
        RBucket<MemberVO> merchantVO = redissonClient.getBucket(key);
        return merchantVO.get();
    }

    /**
     * @author: dameizi
     * @dateTime: 2019-05-22 17:51
     * @description: 当前后台缓存信息
     * @param: []
     * @return: AdminVO
     */
    public static AdminVO getContextAdminInfo() {
        String Authorization = CommonUtil.getToken();
        String key = MessageFormat.format(RedisKeyConst.REDIS_ADMIN_SESSION_KEY , Authorization);
        RBucket<AdminVO> adminVO = redissonClient.getBucket(key);
        return adminVO.get();
    }

}
