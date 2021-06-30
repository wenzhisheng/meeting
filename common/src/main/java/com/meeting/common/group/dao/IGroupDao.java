package com.meeting.common.group.dao;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.meeting.common.pojo.dialog.DialogVO;
import com.meeting.common.pojo.group.GroupDialogDTO;
import com.meeting.common.pojo.group.GroupVO;
import com.meeting.common.pojo.member.MemberVO;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author dameizi
 * @description 群组数据层
 * @dateTime 2019-07-21 15:19
 * @className com.meeting.meeting.groups.dao.IGroupDao
 */
@Repository
public interface IGroupDao {

    /**
     * @author: dameizi
     * @dateTime: 2019-08-02 10:36
     * @description: 群组分页
     * @param: [page, groupVO]
     * @return: com.baomidou.mybatisplus.core.metadata.IPage<com.meeting.common.pojo.group.GroupVO>
     */
    IPage<GroupVO> page(Page<GroupVO> page, @Param("vo") GroupVO groupVO);

    /**
     * @author: dameizi
     * @dateTime: 2019-07-05 13:54
     * @description: 构建群组
     * @param: [groupVO]
     * @return: int
     */
    int insert(@Param("vo") GroupVO groupVO);

    /**
     * @author: dameizi
     * @dateTime: 2019-07-15 20:22
     * @description: 编辑群组
     * @param: [groupVO]
     * @return: int
     */
    int update(@Param("vo") GroupVO groupVO);

    /**
     * @author: dameizi
     * @dateTime: 2019-07-03 22:31
     * @description: 获取群消息控制
     * @param: [dialogVO]
     * @return: com.meeting.common.pojo.group.GroupVO
     */
    GroupVO getGroupByDialog(@Param("vo") DialogVO dialogVO);

    /**
     * @author: dameizi
     * @dateTime: 2019-07-05 16:19
     * @description: 获取群组
     * @param: [groupVO]
     * @return: com.meeting.common.pojo.group.GroupVO
     */
    GroupVO getGroup(@Param("vo") GroupVO groupVO);

    /**
     * @author: dameizi
     * @dateTime: 2019-07-16 11:27
     * @description: 是否是群组管理员
     * @param: [groupVO]
     * @return: boolean
     */
    boolean isGroupAdministrator(@Param("vo") GroupVO groupVO);

    /**
     * @author: dameizi
     * @dateTime: 2019-07-17 19:37
     * @description: 解散群组
     * @param: [groupVO]
     * @return: int
     */
    int delete(@Param("vo") GroupVO groupVO);

    /**
     * @author: dameizi
     * @dateTime: 2019-07-18 17:45
     * @description: 修改群组
     * @param: [groupVO]
     * @return: int
     */
    int updateGroup(@Param("vo") GroupVO groupVO);

    /**
     * @author: dameizi
     * @dateTime: 2019-07-19 12:17
     * @description: 群组数量
     * @param: [groupVO]
     * @return: int
     */
    int countGroup(@Param("vo") GroupVO groupVO);

    /**
     * @author: dameizi
     * @dateTime: 2019-07-20 20:00
     * @description: 对话信息
     * @param: [dialogVO]
     * @return: java.lang.Object
     */
    GroupDialogDTO dialogueInfo(@Param("vo") DialogVO dialogVO);

    /**
     * @author: dameizi
     * @dateTime: 2019-08-03 19:42
     * @description: 群组列表
     * @param: [memberVO]
     * @return: java.util.List<com.meeting.common.pojo.group.GroupVO>
     */
    List<GroupVO> list(@Param("vo") MemberVO memberVO);

}
