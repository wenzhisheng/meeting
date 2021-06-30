package com.meeting.common.ipwhite.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.meeting.common.ipwhite.dao.IIpWhiteDao;
import com.meeting.common.ipwhite.service.IIpWhiteService;
import com.meeting.common.pojo.base.PageVO;
import com.meeting.common.pojo.ipwhite.IpWhiteVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author dameizi
 * @description IP白名单业务层
 * @dateTime 2019-07-13 21:18
 * @className com.meeting.common.ipwhite.service.impl.IpWhiteServiceImpl
 */
@Service
public class IpWhiteServiceImpl implements IIpWhiteService {

    @Autowired
    private IIpWhiteDao ipWhiteDao;

    /**
     * @author: dameizi
     * @dateTime: 2019-08-02 11:20
     * @description: IP白名单分页
     * @param: [ipWhiteVO, pageVO]
     * @return: java.lang.Object
     */
    @Override
    public IPage<IpWhiteVO> page(IpWhiteVO ipWhiteVO, PageVO pageVO) {
        Page<IpWhiteVO> page = new Page<IpWhiteVO>(pageVO.getPageNo(), pageVO.getPageSize());
        return ipWhiteDao.page(page, ipWhiteVO);
    }

    /**
     * @author: dameizi
     * @dateTime: 2019-07-15 10:23
     * @description: IP是否过白
     * @param: [ipWhiteVO]
     * @return: boolean
     */
    @Override
    public boolean isIpWhite(IpWhiteVO ipWhiteVO) {
        return ipWhiteDao.isIpWhite(ipWhiteVO);
    }

    /**
     * @author: dameizi
     * @dateTime: 2019-08-02 11:09
     * @description: IP白名单添加
     * @param: [ipWhiteVO]
     * @return: java.lang.Object
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Object insert(IpWhiteVO ipWhiteVO) {
        return ipWhiteDao.insert(ipWhiteVO);
    }

    /**
     * @author: dameizi
     * @dateTime: 2019-08-02 11:10
     * @description: IP白名单修改
     * @param: [ipWhiteVO]
     * @return: java.lang.Object
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Object update(IpWhiteVO ipWhiteVO) {
        return ipWhiteDao.update(ipWhiteVO);
    }

    /**
     * @author: dameizi
     * @dateTime: 2019-08-02 11:11
     * @description: IP白名单删除
     * @param: [ipWhiteVO]
     * @return: java.lang.Object
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Object delete(IpWhiteVO ipWhiteVO) {
        return ipWhiteDao.delete(ipWhiteVO);
    }

}
