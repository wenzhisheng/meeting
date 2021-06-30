package com.meeting.common.ipwhite.dao;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.meeting.common.pojo.base.PageVO;
import com.meeting.common.pojo.ipwhite.IpWhiteVO;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/**
 * @author dameizi
 * @description IP白名单数据层
 * @dateTime 2019-07-13 21:17
 * @className com.meeting.common.ipwhite.dao.IIpWhiteDao
 */
@Repository
public interface IIpWhiteDao {

    /**
     * @author: dameizi
     * @dateTime: 2019-08-02 11:18
     * @description: IP白名单分页
     * @param: [page, ipWhiteVO]
     * @return: com.baomidou.mybatisplus.core.metadata.IPage<com.meeting.common.pojo.ipwhite.IpWhiteVO>
     */
    IPage<IpWhiteVO> page(Page<IpWhiteVO> page, @Param("vo") IpWhiteVO ipWhiteVO);

    /**
     * @author: dameizi
     * @dateTime: 2019-07-15 10:24
     * @description: IP是否过白
     * @param: [ipWhiteVO]
     * @return: boolean
     */
    boolean isIpWhite(@Param("vo") IpWhiteVO ipWhiteVO);

    /**
     * @author: dameizi
     * @dateTime: 2019-07-15 10:23
     * @description: IP白名单添加
     * @param: [ipWhiteVO]
     * @return: boolean
     */
    int insert(@Param("vo") IpWhiteVO ipWhiteVO);

    /**
     * @author: dameizi
     * @dateTime: 2019-07-15 10:23
     * @description: IP白名单更新
     * @param: [ipWhiteVO]
     * @return: boolean
     */
    int update(@Param("vo") IpWhiteVO ipWhiteVO);

    /**
     * @author: dameizi
     * @dateTime: 2019-07-15 10:23
     * @description: IP白名单删除
     * @param: [ipWhiteVO]
     * @return: boolean
     */
    int delete(@Param("vo") IpWhiteVO ipWhiteVO);

}
