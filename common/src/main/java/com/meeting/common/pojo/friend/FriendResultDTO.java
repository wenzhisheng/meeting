package com.meeting.common.pojo.friend;

import com.meeting.common.pojo.base.BaseVO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.util.List;

/**
 * @author dameizi
 * @description 好友返回数据
 * @dateTime 2019-07-02 12:42
 * @className com.meeting.meeting.pojo.FriendVO
 */
@ApiModel(value = "FriendResultDTO", description = "好友返回数据")
public class FriendResultDTO extends BaseVO implements Serializable {

    private static final long serialVersionUID = -1288426358068533797L;

    /** 类型 */
    @ApiModelProperty(value = "类型")
    private String type;
    /** 好友分类 */
    @ApiModelProperty(value = "好友分类")
    private List<FriendResultVO> friendList;
    /** 好友分类 */
    @ApiModelProperty(value = "好友分类")
    private List<FriendDTO> friendClass;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public List<FriendResultVO> getFriendList() {
        return friendList;
    }

    public void setFriendList(List<FriendResultVO> friendList) {
        this.friendList = friendList;
    }

    public List<FriendDTO> getFriendClass() {
        return friendClass;
    }

    public void setFriendClass(List<FriendDTO> friendClass) {
        this.friendClass = friendClass;
    }
}
