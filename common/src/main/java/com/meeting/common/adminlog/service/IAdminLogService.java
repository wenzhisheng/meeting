package com.meeting.common.adminlog.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.meeting.common.pojo.adminlog.AdminLogVO;
import com.meeting.common.pojo.base.PageVO;

/**
 * @author: dameizi
 * @description: 管理员登录日志接口层
 * @dateTime 2019-04-02 19:32
 * @className IAdminLoginLogService
 */
public interface IAdminLogService {

    /**
     * @author: dameizi
     * @dateTime: 2019-04-02 20:35
     * @description: 批量删除管理员登录日志
     * @param: [adminLoginLogVO]
     * @return: int
     */
    int batchDelete(AdminLogVO adminLoginLogVO);

    /**
     * @author: dameizi
     * @dateTime: 2019-04-02 21:00
     * @description: 分页查询管理员登录日志
     * @param: [adminLoginLog, pageVO]
     * @return: com.baomidou.mybatisplus.core.metadata.IPage<com.weilaizhe.common.pojo.adminloginlog.AdminLoginLog>
     */
    IPage<AdminLogVO> page(AdminLogVO adminLoginLog, PageVO pageVO);

    /**
     * @author: dameizi
     * @dateTime: 2019-04-02 19:37
     * @description: 新增管理员登录日志
     * @param: [adminLoginLog]
     * @return: int
     */
    int insert(AdminLogVO adminLoginLog);

}
