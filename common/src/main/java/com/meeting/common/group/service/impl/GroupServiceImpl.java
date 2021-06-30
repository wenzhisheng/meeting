package com.meeting.common.group.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.meeting.common.constant.CommonConst;
import com.meeting.common.group.dao.IGroupDao;
import com.meeting.common.group.service.IGroupService;
import com.meeting.common.groupmember.dao.IGroupMemberDao;
import com.meeting.common.pojo.base.PageVO;
import com.meeting.common.pojo.group.GroupVO;
import com.meeting.common.pojo.groupmember.GroupMemberVO;
import com.meeting.common.pojo.member.MemberVO;
import com.meeting.common.util.CommonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author dameizi
 * @description 群组业务层
 * @dateTime 2019-04-21 15:12
 * @className com.meeting.meeting.groups.service.impl.GroupServiceImpl
 */
@Service
public class GroupServiceImpl implements IGroupService {

    @Autowired
    private IGroupDao iGroupDao;
    @Autowired
    private IGroupMemberDao iGroupMemberDao;

    /**
     * @author: dameizi
     * @dateTime: 2019-08-02 10:35
     * @description: 群组分页
     * @param: [groupVO, pageVO]
     * @return: com.baomidou.mybatisplus.core.metadata.IPage<com.meeting.common.pojo.group.GroupVO>
     */
    @Override
    public IPage<GroupVO> page(GroupVO groupVO, PageVO pageVO) {
        Page<GroupVO> page = new Page<>(pageVO.getPageNo(), pageVO.getPageSize());
        return iGroupDao.page(page, groupVO);
    }

    /**
     * @author: dameizi
     * @dateTime: 2019-07-05 13:52
     * @description: 构建群组
     * @param: [groupVO]
     * @return: int
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Object insert(GroupVO groupVO) {
        // 创建群组
        insertGroup(groupVO);
        // 添加群成员
        insertGroupMember(groupVO);
        return CommonConst.ACTION_SUCCESS;
    }

    /** 添加群成员 */
    private void insertGroupMember(GroupVO groupVO) {
        // 查询创建群组
        GroupVO group = iGroupDao.getGroup(groupVO);
        // 根据用户创建群用户数据
        Integer[] memberIds = groupVO.getMemberIds();
        for(int i=0; i<memberIds.length+1; i++){
            GroupMemberVO groupMemberVO = new GroupMemberVO();
            // 把创建者添加进群成员
            if (i == memberIds.length){
                groupMemberVO.setMemberId(groupVO.getMemberId());
            }else{
                groupMemberVO.setMemberId(memberIds[i]);
            }
            groupMemberVO.setGroupId(group.getGroupsId());
            iGroupMemberDao.save(groupMemberVO);
        }
    }

    /** 创建群组 */
    private void insertGroup(GroupVO groupVO) {
        groupVO.setAnnouncement("...");
        // 群账号（15位时间戳）
        groupVO.setAccount(CommonUtil.generateGroupNo());
        groupVO.setAvatar("https://img.alicdn.com/tfs/TB1JomaQVXXXXaMXFXXXXXXXXXX-160-160.png");
        iGroupDao.insert(groupVO);
    }

    /**
     * @author: dameizi
     * @dateTime: 2019-08-01 21:20
     * @description: 更新群组
     * @param: [groupVO]
     * @return: int
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public int update(GroupVO groupVO) {
        return iGroupDao.updateGroup(groupVO);
    }

    /**
     * @author: dameizi
     * @dateTime: 2019-07-15 20:17
     * @description: 编辑群组
     * @param: [groupVO]
     * @return: java.lang.Object
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Object editGroup(GroupVO groupVO) {
        return iGroupDao.update(groupVO);
    }

    /**
     * @author: dameizi
     * @dateTime: 2019-07-16 11:26
     * @description: 是否是群组管理员
     * @param: [groupVO]
     * @return: java.lang.Object
     */
    @Override
    public boolean isGroupAdministrator(GroupVO groupVO) {
        return iGroupDao.isGroupAdministrator(groupVO);
    }

    /**
     * @author: dameizi
     * @dateTime: 2019-07-19 12:17
     * @description: 群组数量
     * @param: [groupVO]
     * @return: int
     */
    @Override
    public int countGroup(GroupVO groupVO) {
        return iGroupDao.countGroup(groupVO);
    }

    /**
     * @author: dameizi
     * @dateTime: 2019-08-03 19:41
     * @description: 群组列表
     * @param: [memberVO]
     * @return: java.util.List<com.meeting.common.pojo.group.GroupVO>
     */
    @Override
    public List<GroupVO> list(MemberVO memberVO) {
        return iGroupDao.list(memberVO);
    }

}
