package com.meeting.common.friendapply.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.meeting.common.friendapply.dao.IFriendApplyDao;
import com.meeting.common.friendapply.service.IFriendApplyService;
import com.meeting.common.pojo.base.PageVO;
import com.meeting.common.pojo.friendapply.FriendApplyVO;
import com.meeting.common.pojo.member.MemberVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author dameizi
 * @description 好友申请业务层
 * @dateTime 2019-04-24 21:50
 * @className com.meeting.meeting.friendapply.service.impl.FriendApplyServiceImpl
 */
@Service
public class FriendApplyServiceImpl implements IFriendApplyService {

    @Autowired
    private IFriendApplyDao friendApplyDao;

    /**
     * @author: dameizi
     * @dateTime: 2019-08-01 22:11
     * @description: 好友申请分页
     * @param: [friendApplyVO, pageVO]
     * @return: com.baomidou.mybatisplus.core.metadata.IPage<com.meeting.common.pojo.friendapply.FriendApplyVO>
     */
    @Override
    public IPage<FriendApplyVO> page(FriendApplyVO friendApplyVO, PageVO pageVO) {
        Page<FriendApplyVO> page = new Page<>(pageVO.getPageNo(), pageVO.getPageSize());
        return friendApplyDao.page(page, friendApplyVO);
    }

    /**
     * @author: dameizi
     * @dateTime: 2019-07-09 12:08
     * @description: 保存申请好友
     * @param: [friendApplyVO]
     * @return: int
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public int save(FriendApplyVO friendApplyVO) {
        return friendApplyDao.save(friendApplyVO);
    }

    /**
     * @author: dameizi
     * @dateTime: 2019-07-09 12:09
     * @description: 申请中数量
     * @param: [memberVO]
     * @return: java.lang.Object
     */
    @Override
    public Object applyingCount(MemberVO memberVO) {
        return friendApplyDao.applyingCount(memberVO);
    }
}
