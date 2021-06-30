package com.meeting.common.pojo.friendclass;

import com.meeting.common.pojo.base.BaseVO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

/**
 * @author dameizi
 * @description 好友分类
 * @dateTime 2019-04-21 13:20
 * @className com.meeting.meeting.pojo.FriendTypeVO
 */
@ApiModel(value = "FriendTypeVO", description = "好友分类")
public class FriendClassVO extends BaseVO implements Serializable {

    private static final long serialVersionUID = -1731482914256727748L;

    /** 好友分类主键ID */
    @ApiModelProperty(value = "好友分类主键ID")
    private Integer friendClassId;
    /** 会员ID */
    @ApiModelProperty(value = "会员ID")
    private Integer memberId;
    /** 组名称 */
    @ApiModelProperty(value = "组名称")
    private String namespace;
    /** 是否分组（0：默认 1：分组） */
    @ApiModelProperty(value = "是否分组（0：默认 1：分组）")
    private Integer isGrouping;

    /** 分组下好友总数 */
    private int friendCount;
    /** 在线好友数 */
    private int onlineCount;

    public Integer getMemberId() {
        return memberId;
    }

    public void setMemberId(Integer memberId) {
        this.memberId = memberId;
    }

    public int getFriendCount() {
        return friendCount;
    }

    public void setFriendCount(int friendCount) {
        this.friendCount = friendCount;
    }

    public int getOnlineCount() {
        return onlineCount;
    }

    public void setOnlineCount(int onlineCount) {
        this.onlineCount = onlineCount;
    }

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

    public Integer getIsGrouping() {
        return isGrouping;
    }

    public void setIsGrouping(Integer isGrouping) {
        this.isGrouping = isGrouping;
    }
}
