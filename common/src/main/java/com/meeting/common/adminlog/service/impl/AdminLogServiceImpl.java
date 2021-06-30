package com.meeting.common.adminlog.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.meeting.common.constant.CommonConst;
import com.meeting.common.pojo.adminlog.AdminLogVO;
import com.meeting.common.pojo.base.PageVO;
import com.meeting.common.util.CommonUtil;
import com.meeting.common.adminlog.dao.IAdminLogDao;
import com.meeting.common.adminlog.service.IAdminLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author: dameizi
 * @description: 管理员登录日志业务层
 * @dateTime 2019-04-02 19:33
 * @className com.weilaizhe.common.adminloginlog.service.AdminLoginLogServiceImpl
 */
@Service
public class AdminLogServiceImpl implements IAdminLogService {

    @Autowired
    private IAdminLogDao iAdminLoginLogDao;

    /**
     * @author: dameizi
     * @dateTime: 2019-04-02 20:35
     * @description: 批量删除管理员登录日志
     * @param: [adminLoginLogVO]
     * @return: int
     */
    @Override
    public int batchDelete(AdminLogVO adminLoginLogVO){
        CommonUtil.paramEmptyVerify(String.valueOf(adminLoginLogVO.getIds()), CommonConst.ID_NOT_EMPTY);
        return iAdminLoginLogDao.batchDelete(adminLoginLogVO);
    }

    /**
     * @author: dameizi
     * @dateTime: 2019-04-02 20:15
     * @description: 分页查询管理员登录日志
     * @param: [adminLoginLog, pageVO]
     * @return: com.baomidou.mybatisplus.core.metadata.IPage<AdminLoginLogVO>
     */
    @Override
    public IPage<AdminLogVO> page(AdminLogVO adminLoginLog, PageVO pageVO){
        // 分页参数校验
        CommonUtil.pageParamVerify(pageVO);
        //mybatis plus分页查询插件，第一个参数必须是Page<T>，返回类型必须IPage<T>接收
        Page<AdminLogVO> page = new Page<AdminLogVO>(pageVO.getPageNo(), pageVO.getPageSize());
        return iAdminLoginLogDao.page(page, adminLoginLog);
    }

    /**
     * @author: dameizi
     * @dateTime: 2019-04-02 19:37
     * @description: 管理员会员登录日志
     * @param: [merchantLoginLogVO]
     * @return: int
     */
    @Override
    public int insert(AdminLogVO adminLoginLog){
        return iAdminLoginLogDao.insert(adminLoginLog);
    }


}
