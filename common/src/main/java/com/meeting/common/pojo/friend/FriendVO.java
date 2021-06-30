package com.meeting.common.pojo.friend;

import com.meeting.common.pojo.base.BaseVO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

/**
 * @author dameizi
 * @description 好友关系
 * @dateTime 2019-04-19 12:42
 * @className com.meeting.meeting.pojo.FriendVO
 */
@ApiModel(value = "FriendVO", description = "好友关系")
public class FriendVO extends BaseVO implements Serializable {

    private static final long serialVersionUID = -1288426358068533797L;

    /** 好友主键ID */
    @ApiModelProperty(value = "好友主键ID")
    private Integer friendsId;
    /** 会员ID */
    @ApiModelProperty(value = "会员ID")
    private Integer memberId;
    /** 伙伴ID */
    @ApiModelProperty(value = "伙伴ID")
    private Integer targetId;
    /** 伙伴账号 */
    @ApiModelProperty(value = "伙伴账号")
    private String account;
    /** 伙伴别名 */
    @ApiModelProperty(value = "伙伴别名")
    private String alias;
    /** 备注别名 */
    @ApiModelProperty(value = "备注别名")
    private String remark;
    /** 伙伴头像 */
    @ApiModelProperty(value = "伙伴头像")
    private String avatar;
    /** 所在分组ID */
    @ApiModelProperty(value = "所在分类ID")
    private Integer friendClassId;
    /** 好友类型（0：普通 1：特别关注 2：黑名单 3：删除） */
    @ApiModelProperty(value = "好友类型（0：普通 1：特别关注 2：黑名单 3：删除）")
    private Integer status;
    /** 强提醒（no：正常，yes：强提醒） */
    @ApiModelProperty(value = "强提醒（no：正常，yes：强提醒）")
    private String strongReminder;
    /** 聊天置顶（no：不置顶，yes：正常） */
    @ApiModelProperty(value = "聊天置顶（no：不置顶，yes：正常）")
    private String chatTop;
    /** 是否打扰（no：不打扰，yes：正常） */
    @ApiModelProperty(value = "是否打扰（no：不打扰，yes：正常）")
    private String isDisturb;

    /** 搜索类型（list：列表，class：分类） */
    @ApiModelProperty(value = "搜索类型（list：列表，class：分类）")
    private String searchType;

    public Integer getFriendsId() {
        return friendsId;
    }

    public void setFriendsId(Integer friendsId) {
        this.friendsId = friendsId;
    }

    public Integer getMemberId() {
        return memberId;
    }

    public void setMemberId(Integer memberId) {
        this.memberId = memberId;
    }

    public Integer getTargetId() {
        return targetId;
    }

    public void setTargetId(Integer targetId) {
        this.targetId = targetId;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public Integer getFriendClassId() {
        return friendClassId;
    }

    public void setFriendClassId(Integer friendClassId) {
        this.friendClassId = friendClassId;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getStrongReminder() {
        return strongReminder;
    }

    public void setStrongReminder(String strongReminder) {
        this.strongReminder = strongReminder;
    }

    public String getChatTop() {
        return chatTop;
    }

    public void setChatTop(String chatTop) {
        this.chatTop = chatTop;
    }

    public String getIsDisturb() {
        return isDisturb;
    }

    public void setIsDisturb(String isDisturb) {
        this.isDisturb = isDisturb;
    }

    public String getSearchType() {
        return searchType;
    }

    public void setSearchType(String searchType) {
        this.searchType = searchType;
    }
}
