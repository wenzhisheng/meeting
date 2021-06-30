package com.meeting.common.memberlog.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.meeting.common.memberlog.dao.IMemberLogDao;
import com.meeting.common.memberlog.service.IMemberLogService;
import com.meeting.common.pojo.base.PageVO;
import com.meeting.common.pojo.memberlog.MemberLogVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author: dameizi
 * @description: 会员登录日志业务层
 * @dateTime 2019-04-02 19:33
 * @className com.weilaizhe.common.merchantloginlog.service.MerchantLoginLogServiceImpl
 */
@Service
public class MemberLogServiceImpl implements IMemberLogService {

    @Autowired
    private IMemberLogDao iMerchantLoginLogDao;

    /**
     * @author: dameizi
     * @dateTime: 2019-04-02 20:35
     * @description: 批量删除会员登录日志
     * @param: [ids]
     * @return: int
     */
    @Override
    public int batchDelete(MemberLogVO merchantLoginLogVO){
        return iMerchantLoginLogDao.batchDelete(merchantLoginLogVO);
    }

    /**
     * @author: dameizi
     * @dateTime: 2019-04-02 20:15
     * @description: 分页查询会员登录日志
     * @param: [merchantLoginLogVO, pageVO]
     * @return: com.baomidou.mybatisplus.core.metadata.IPage<MerchantLoginLogVO>
     */
    @Override
    public IPage<MemberLogVO> page(MemberLogVO merchantLoginLogVO, PageVO pageVO){
        //mybatis plus分页查询插件，第一个参数必须是Page<T>，返回类型必须IPage<T>接收
        Page<MemberLogVO> page = new Page<MemberLogVO>(pageVO.getPageNo(),pageVO.getPageSize());
        return iMerchantLoginLogDao.page(page, merchantLoginLogVO);
    }

    /**
     * @author: dameizi
     * @dateTime: 2019-04-02 19:37
     * @description: 新增会员登录日志
     * @param: [merchantLoginLogVO]
     * @return: int
     */
    @Override
    @Transactional
    public int insert(MemberLogVO merchantLogVO){
        return iMerchantLoginLogDao.insert(merchantLogVO);
    }

}
