package com.meeting.common.adminlog.dao;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.meeting.common.pojo.adminlog.AdminLogVO;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/**
 * @author: dameizi
 * @description: 管理员登录日志数据层
 * @dateTime 2019-04-02 19:32
 * @className com.meeting.common.adminloginlog.service.IAdminLoginLogDao
 */
@Repository
public interface IAdminLogDao {

    /**
     * @author: dameizi
     * @dateTime: 2019-04-02 20:35
     * @description: 批量删除管理员登录日志
     * @param: [ids]
     * @return: int
     */
    int batchDelete(@Param("vo") AdminLogVO adminLoginLogVO);

    /**
     * @author: dameizi
     * @dateTime: 2019-04-02 20:19
     * @description: 分页查询管理员登录日志
     * @param: [page, adminLoginLog]
     * @return: com.baomidou.mybatisplus.core.metadata.IPage<com.meeting.common.pojo.adminloginlog.AdminLoginLog>
     */
    IPage<AdminLogVO> page(Page<AdminLogVO> page, @Param("vo") AdminLogVO adminLoginLog);

    /**
     * @author: dameizi
     * @dateTime: 2019-04-02 19:37
     * @description: 新增管理员登录日志
     * @param: [adminLoginLog]
     * @return: int
     */
    int insert(@Param("vo") AdminLogVO adminLoginLog);

}
