package com.meeting.common.memberlog.dao;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.meeting.common.pojo.memberlog.MemberLogVO;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/**
 * @author: dameizi
 * @description: 会员登录日志数据层
 * @dateTime 2019-04-02 19:32
 * @className IMerchantLoginLogService
 */
@Repository
public interface IMemberLogDao {

    /**
     * @author: dameizi
     * @dateTime: 2019-04-02 20:35
     * @description: 批量删除会员登录日志
     * @param: [ids]
     * @return: int
     */
    int batchDelete(@Param("vo") MemberLogVO merchantLoginLogVO);

    /**
     * @author: dameizi
     * @dateTime: 2019-04-02 20:19
     * @description: 分页查询会员登录日志
     * @param: [page, merchantLoginLogVO]
     * @return: com.baomidou.mybatisplus.core.metadata.IPage<MerchantLoginLogVO>
     */
    IPage<MemberLogVO> page(Page<MemberLogVO> page, @Param("vo") MemberLogVO merchantLoginLogVO);

    /**
     * @author: dameizi
     * @dateTime: 2019-04-02 19:37
     * @description: 新增会员登录日志
     * @param: [merchantLoginLogVO]
     * @return: int
     */
    int insert(@Param("vo") MemberLogVO merchantLogVO);

}
