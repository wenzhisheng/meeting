package com.meeting.common.admin.dao;

import com.meeting.common.pojo.admin.AdminVO;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/**
 * @author: dameizi
 * @description: 管理员数据层
 * @dateTime 2019-03-27 12:24
 * @className com.meeting.common.exception.OtherReturn
 */
@Repository
public interface IAdminDao {

    /**
     * @author: dameizi
     * @dateTime: 2019-05-31 17:19
     * @description: 获取管理员信息
     * @param: [adminVO]
     * @return: AdminVO
     */
    AdminVO getAdminById(@Param("vo") AdminVO adminVO);

    /**
     * @author: dameizi
     * @dateTime: 2019-06-01 14:52
     * @description: 更新管理员
     * @param: [adminVO]
     * @return: int
     */
    int update(@Param("vo") AdminVO adminVO);

    /**
     * @author: dameizi
     * @dateTime: 2019/3/27 0027 下午 12:40
     * @description: 管理员账号注册
     * @param: [accountVO]
     * @return: int
     */
    int insert(@Param("vo") AdminVO adminVO);

    /**
     * @author: dameizi
     * @dateTime: 2019-03-28 14:59
     * @description: 管理员登录
     * @param: [adminVO]
     * @return: AdminVO
     */
    AdminVO login(@Param("vo") AdminVO adminVO);

    /**
     * @author: dameizi
     * @dateTime: 2019-03-31 16:50
     * @description: 账号是否存在
     * @param: [adminVO]
     * @return: int
     */
    int getAccountIsUse(@Param("vo") AdminVO adminVO);

}
