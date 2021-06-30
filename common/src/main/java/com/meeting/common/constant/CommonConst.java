package com.meeting.common.constant;

/**
 * @author: dameizi
 * @description: 公共常量
 * @dateTime 2019-03-26 23:56
 * @className com.meeting.common.exception.OtherReturn
 */
public class CommonConst {

    /** Http Token 安全认证 */
    public static final String AUTHORIZATION = "Authorization";
    /** 验证码存放Session */
    public static final String RANDOM_CODE_KEY= "random_code_key";
    /** 年月格式 */
    public static final String DATE_FORMAT_YYYYMM = "yyyyMM";
    /** 返回错误码标识 */
    public static final String ERROR = "ERROR:{0}";
    /** 登录缓存获取失败 */
    public static final String LOGIN_CACHE_FAIL = "登录缓存获取失败";
    /** 验证码错误 */
    public static final String VERIFY_CODE_ERROR = "验证码错误";
    /** 动态口令已过期 */
    public static final String DYNAMIC_PASSWORD_PAST = "动态口令已过期";
    /** 动态口令错误 */
    public static final String DYNAMIC_PASSWORD_ERROR = "动态口令错误";
    /** 账号或密码错误 */
    public static final String ACCOUNT_PASSWORD_ERROR = "账号或密码错误";
    /** 请求路径错误 */
    public static final String REQUEST_PATH_ERROR = "请求路径错误";
    /** 参数不能为空 */
    public static final String PARAM_NOT_EMPTY = "参数不能为空";
    /** 参数异常 */
    public static final String PARAM_EXCEPTION = "参数非法";
    /** 分页参数不能为空 */
    public static final String PAGE_PARAM_NOT_EMPTY = "分页参数不能为空";
    /** 登录已过期 */
    public static final String LOGIN_PAST = "登录已过期";
    /** 登录成功 */
    public static final String LOGIN_SUCCESS = "{}登录成功";
    /** 退出成功 */
    public static final String LOGOUT_SUCCESS = "退出成功";
    /** 添加成功 */
    public static final String INSERT_SUCCESS = "添加成功";
    /** 修改成功 */
    public static final String EDIT_SUCCESS = "修改成功";
    /** 删除成功 */
    public static final String DELETE_SUCCESS = "删除成功";
    /** 操作成功 */
    public static final String ACTION_SUCCESS = "操作成功";
    /** 账号已被使用 */
    public static final String ACCOUNT_IS_USE = "账号已被使用";
    /** 会员编码或账号已存在 */
    public static final String CODE_OR_ACCOUNT_IS_USE = "会员编码或账号已存在";
    /** 账号不能为空 */
    public static final String ACCOUNT_NOT_EMPTY = "账号不能为空";
    /** id不能为空 */
    public static final String ID_NOT_EMPTY = "ID不能为空";
    /** 密码不能为空 */
    public static final String PASSWORD_NOT_EMPTY = "密码不能为空";
    /** 原密码输入错误 */
    public static final String OLD_PASSWORD_ERROR = "原密码输入错误";
    /** 短信发送失败 */
    public static final String SMS_SENG_FAIL = "短信发送失败";
    /** 确认密码不能为空 */
    public static final String CONFIRM_PASSWORD_NOT_EMPTY = "确认密码不能为空";
    /** 确认密码不一致 */
    public static final String CONFIRM_PASSWORD_ERROR = "确认密码不一致";
    /** IP白名单不合法 */
    public static final String IP_ILLEGAL = "：IP白名单不合法";
    /** 密码必须是6-12位数字、字母组合，字母区分大小写 */
    public static final String MERCHANT_PASSWORD_VERIFY = "密码必须是6-18位数字与字母组合，字母区分大小写";
    /** 简单密码，提现密码 */
    public static final String PASSWORD_EASY = "必须六位数字密码";
    /** 上传的文件不能为空 */
    public static final String UPLOAD_FILE_NOT_EMPTY = "上传的文件不能为空";
    /** 图片大小最大不能超过 */
    public static final String IMAGE_MAX = "图片大小最大不能超过";
    /** 获取IP物理地址失败 */
    public static final String GET_IP_ADDRESS_FAIL = "获取IP物理地址失败{}";
    /** OK */
    public static final String OK = "OK";
    /** 空字符 */
    public static final String NULL_CHAR = "";
    /** utf-8 */
    public static final String CODING_UTF8 = "utf-8";
    /** 错误路径 */
    public static final String ERROR_PATH = "/error";
    /** 是或否 */
    public static final String YES = "yes";
    public static final String NO = "no";

    /** 非法操作 */
    public static final String ILLEGAL_OPERATE = "非法操作";
    /** 默认分组名称 */
    public static final String DEFAULT_CLASS = "我的好友";

    /** 空字符null */
    public static final String STRING_NULL = "null";
    /** 在线 */
    public static final String ONLINE_OFFLINE = "online";
    /** 离线 */
    public static final String OFFLINE_ONLINE = "offline";

    /** 列表搜索联系人 */
    public static final String SEARCH_LIST = "list";
    /** 分类搜索联系人 */
    public static final String SEARCH_CLASS = "class";

    /** 密码找回类型邮箱 */
    public static final String FIND_TYPE_EMAIL = "email";
    /** 密码找回类型手机号 */
    public static final String FIND_TYPE_TELEPHONE = "telephone";

    /** 发送消息类型 */
    public static final String SEND_MSG_TYPE = "sendMsg";
    /** 已读消息类型 */
    public static final String READ_MSG_TYPE = "readMsg";
    /** 撤回消息类型 */
    public static final String RECALL_MSG_TYPE = "recallMsg";
    /** 删除消息类型 */
    public static final String DELETE_MSG_TYPE = "deleteMsg";

    /** 对话类型单聊 */
    public static final String DIALOG_TYPE_SINGLE = "single";
    /** 对话类型群聊 */
    public static final String DIALOG_TYPE_CLUSTER = "cluster";

    /** 找回密码类型邮箱 */
    public static final String RETRIEVE_PASSWORD_TYPE_EMAIL = "email";
    /** 找回密码类型手机号 */
    public static final String RETRIEVE_PASSWORD_TYPE_TELEPHONE = "telephone";

    /** 消息分类好友 */
    public static final String MESSAGE_CLASS_SINGLE = "single";
    /** 消息分类群组 */
    public static final String MESSAGE_CLASS_CLUSTER = "cluster";
    /** 消息分类系统 */
    public static final String MESSAGE_CLASS_SYSTEM = "system";

    /** 最大单聊文本内容长度 */
    public static final Integer MAX_SINGLE_TEXT_CONTENT_FILE_LENGTH = 1024000;
    /** 最大单聊文本内容长度 */
    public static final Integer MAX_SINGLE_TEXT_CONTENT_LENGTH = 1024;
    /** 最大群聊文本内容长度 */
    public static final Integer MAX_CLUSTER_TEXT_CONTENT_LENGTH = 600;

    /** 群组最大人数 */
    public static final int GROUP_MAX_SIZE = 500;

}
