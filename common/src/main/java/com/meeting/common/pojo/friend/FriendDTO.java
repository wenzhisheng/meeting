package com.meeting.common.pojo.friend;

import com.meeting.common.pojo.base.BaseVO;
import com.meeting.common.pojo.member.MemberVO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.util.List;

/**
 * @author dameizi
 * @description 分类列表
 * @dateTime 2019-04-19 12:42
 * @className com.meeting.meeting.pojo.FriendVO
 */
@ApiModel(value = "FriendDTO", description = "分类列表")
public class FriendDTO extends BaseVO implements Serializable {

    private static final long serialVersionUID = -1288426358068533797L;

    /** 分组ID */
    @ApiModelProperty(value = "分组ID")
    private Integer friendClassId;
    /** 组名称 */
    @ApiModelProperty(value = "组名称")
    private String namespace;
    /** 好友计数 */
    @ApiModelProperty(value = "好友计数")
    private int friendCount;
    /** 好友列表 */
    @ApiModelProperty(value = "好友列表")
    private List<FriendResultVO> friendList;

    public Integer getFriendClassId() {
        return friendClassId;
    }

    public void setFriendClassId(Integer friendClassId) {
        this.friendClassId = friendClassId;
    }

    public String getNamespace() {
        return namespace;
    }

    public void setNamespace(String namespace) {
        this.namespace = namespace;
    }

    public int getFriendCount() {
        return friendCount;
    }

    public void setFriendCount(int friendCount) {
        this.friendCount = friendCount;
    }

    public List<FriendResultVO> getFriendList() {
        return friendList;
    }

    public void setFriendList(List<FriendResultVO> friendList) {
        this.friendList = friendList;
    }
}
