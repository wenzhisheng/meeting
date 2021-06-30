package com.meeting.common.friendapply.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.meeting.common.pojo.base.PageVO;
import com.meeting.common.pojo.friendapply.FriendApplyVO;
import com.meeting.common.pojo.member.MemberVO;

/**
 * @author dameizi
 * @description 好友申请接口层
 * @dateTime 2019-04-24 21:48
 * @className com.meeting.meeting.friendapply.service.IFriendTypeService
 */
public interface IFriendApplyService {

    /**
     * @author: dameizi
     * @dateTime: 2019-08-01 22:13
     * @description: 好友申请分页
     * @param: [friendApplyVO, pageVO]
     * @return: com.baomidou.mybatisplus.core.metadata.IPage<com.meeting.common.pojo.friendapply.FriendApplyVO>
     */
    IPage<FriendApplyVO> page(FriendApplyVO friendApplyVO, PageVO pageVO);

    /**
     * @author: dameizi
     * @dateTime: 2019-07-09 12:08
     * @description: 保存申请好友
     * @param: [friendApplyVO]
     * @return: int
     */
    int save(FriendApplyVO friendApplyVO);

    /**
     * @author: dameizi
     * @dateTime: 2019-07-09 12:08
     * @description: 申请中数量
     * @param: [memberVO]
     * @return: java.lang.Object
     */
    Object applyingCount(MemberVO memberVO);
}
