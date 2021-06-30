package com.meeting.common.memberlog.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.meeting.common.pojo.base.PageVO;
import com.meeting.common.pojo.memberlog.MemberLogVO;

/**
 * @author: dameizi
 * @description: 会员登录日志接口层
 * @dateTime 2019-04-02 19:32
 * @className IMerchantLoginLogService
 */
public interface IMemberLogService {

    /**
     * @author: dameizi
     * @dateTime: 2019-04-02 20:35
     * @description: 批量删除会员登录日志
     * @param: [merchantLoginLogVO]
     * @return: int
     */
    int batchDelete(MemberLogVO merchantLoginLogVO);

    /**
     * @author: dameizi
     * @dateTime: 2019-04-02 20:15
     * @description: 分页查询会员登录日志
     * @param: [merchantLoginLogVO, pageVO]
     * @return: com.baomidou.mybatisplus.core.metadata.IPage<MerchantLoginLogVO>
     */
    IPage<MemberLogVO> page(MemberLogVO merchantLoginLogVO, PageVO pageVO);

    /**
     * @author: dameizi
     * @dateTime: 2019-04-02 19:37
     * @description: 新增会员登录日志
     * @param: [merchantLogVO]
     * @return: int
     */
    int insert(MemberLogVO merchantLogVO);

}
