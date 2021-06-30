package com.meeting.common.admin.service;

import com.meeting.common.pojo.admin.AdminVO;

/**
 * @author: dameizi
 * @description: 管理员接口层
 * @dateTime 2019-03-26 22:39
 * @className com.meeting.common.exception.OtherReturn
 */
public interface IAdminService {

    /**
     * @author: dameizi
     * @dateTime: 2019-05-31 17:19
     * @description: 获取管理员信息
     * @param: [adminVO]
     * @return: AdminVO
     */
    AdminVO getAdminById(AdminVO adminVO);

    /**
     * @author: dameizi
     * @dateTime: 2019-06-01 12:47
     * @description: 修改手机
     * @param: [adminVO]
     * @return: java.lang.Object
     */
    Object updatePhone(AdminVO adminVO);

    /**
     * @author: dameizi
     * @dateTime: 2019-06-01 13:44
     * @description: 更改密码
     * @param: [adminVO]
     * @return: java.lang.Object
     */
    Object updatePassword(AdminVO adminVO);

    /**
     * @author: dameizi
     * @dateTime: 2019/3/27 0027 下午 12:40
     * @description: 管理员账号注册
     * @param: [accountVO]
     * @return: int
     */
    int insert(AdminVO adminVO);

    /**
     * @author: dameizi
     * @dateTime: 2019-03-28 14:59
     * @description: 管理员登录
     * @param: [adminVO]
     * @return: AdminVO
     */
    AdminVO login(AdminVO adminVO);

}
