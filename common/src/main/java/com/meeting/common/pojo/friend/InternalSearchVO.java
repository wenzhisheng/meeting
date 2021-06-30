package com.meeting.common.pojo.friend;

import com.meeting.common.pojo.base.BaseVO;
import com.meeting.common.pojo.groupmember.GroupMemberVO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * @author dameizi
 * @description 内部搜索
 * @dateTime 2019-07-02 12:42
 * @className com.meeting.meeting.pojo.FriendVO
 */
@ApiModel(value = "InternalSearchVO", description = "内部搜索")
public class InternalSearchVO extends BaseVO implements Serializable {

    private static final long serialVersionUID = -1288426358068533797L;

    /** 好友列表 */
    @ApiModelProperty(value = "好友列表")
    private  List<FriendVO> friend;
    /** 群组列表 */
    @ApiModelProperty(value = "群组列表")
    private  List<GroupMemberVO> group;

    public List<FriendVO> getFriend() {
        return friend;
    }

    public void setFriend(List<FriendVO> friend) {
        this.friend = friend;
    }

    public List<GroupMemberVO> getGroup() {
        return group;
    }

    public void setGroup(List<GroupMemberVO> group) {
        this.group = group;
    }
}
