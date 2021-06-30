package com.meeting.common.constant;

/**
 * @author: dameizi
 * @description: 公共RedisKey
 * @dateTime 2019-03-29 23:56
 * @className com.weilaizhe.common.exception.OtherReturn
 */
public class RedisKeyConst {

    /** session会话存放 */
    public static final String REDIS_ADMIN_SESSION_KEY = "redis_admin_session_key_{0}";
    public static final String REDIS_MEMBER_SESSION_KEY = "redis_member_session_key_{0}";

    /** redis单账号登录比对标识 */
    public static final String REDIS_ADMIN_TOKEN = "redis_admin_token_{0}";
    public static final String REDIS_MEMBER_TOKEN = "redis_member_token_{0}";

    /** 会员手机短信 */
    public static final String MEMBER_PHONE_SMS = "member_phone_sms_{0}_{1}";
    /** 会员邮箱验证 */
    public static final String MAIL_AUTH_CODE = "mail_auth_code_{0}_{1}";

    /** socketio客户端 */
    public static final String MEETING_SOCKETIO_ONESELF ="meeting_socketio_oneself_{0}";
    public static final String MEETING_SOCKETIO_TARGET ="meeting_socketio_target_{0}";
    /** 客户端uuid */
    public static final String MEETING_SOCKETIO_UUID ="meeting_socketio_uuid";
    /** 在线状态 */
    public static final String MEETING_ONLINE_STATUS ="meeting_online_status";
    /** 登录信息 */
    public static final String MEETING_LOGIN_MEMBER ="meeting_login_member";
    /** 客户端ID */
    public static final String MEETING_SOCKETIO_SESSIONID ="meeting_socketio_sessionid";

    /** 离线消息列表 */
    public static final String MEETING_OFFLINE_MESSAGE ="meeting_offline_message_{0}";
    /** 离线群消息列表 */
    public static final String MEETING_OFFLINE_GROUP_MESSAGE ="meeting_offline_group_message_{0}";
    /** 对话列表 */
    public static final String MEETING_DIALOGUE_SET ="meeting_dialogue_set_{0}";

    /** 同意添加好友 */
    public static final String AGREE_ADD_FRIEND ="agree_add_friend_{0}";

    /** 消息列表 */
    public static final String MESSAGE_LIST ="message_list_{0}";
    /** 上次留言群 */
    public static final String MESSAGE_QUN_LAST_TIME ="message_qun_last_time_{0}";


}
