package com.meeting.common.pojo.friend;

import com.meeting.common.pojo.base.BaseVO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;

/**
 * @author dameizi
 * @description 好友返回数据
 * @dateTime 2019-07-02 12:42
 * @className com.meeting.meeting.pojo.FriendVO
 */
@ApiModel(value = "FriendResultVO", description = "好友返回数据")
public class FriendResultVO extends BaseVO implements Serializable {

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
    /** 所在分组ID */
    @ApiModelProperty(value = "所在分类ID")
    private Integer friendClassId;
    /** 好友类型（0：普通好友 1：特别关注 2：黑名单好友） */
    @ApiModelProperty(value = "好友类型（0：普通好友 1：特别关注 2：黑名单好友）")
    private Integer status;

    /** 头像 */
    @ApiModelProperty(value = "头像")
    private String avatar;
    /** 性别（0：保密 1：男 2：女） */
    @ApiModelProperty(value = "性别（0：保密 1：男 2：女）")
    private Integer gender;
    /** 昵称 */
    @ApiModelProperty(value = "昵称")
    private String nickname;
    /** 出生日期 */
    @ApiModelProperty(value = "生日")
    @DateTimeFormat(pattern="yyyy-MM-dd", iso=DateTimeFormat.ISO.DATE)
    private Date birthday;
    /** 邮箱 */
    @ApiModelProperty(value = "邮箱")
    private String email;
    /** 手机号码 */
    @ApiModelProperty(value = "手机号码")
    private String telephone;
    /** 地区 */
    @ApiModelProperty(value = "地区")
    private String region;
    /** 个性签名 */
    @ApiModelProperty(value = "个性签名")
    private String motto;
    /** 登录时间差 */
    @ApiModelProperty(value="登录时间差")
    private String difftime;

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

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public Integer getGender() {
        return gender;
    }

    public void setGender(Integer gender) {
        this.gender = gender;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public String getMotto() {
        return motto;
    }

    public void setMotto(String motto) {
        this.motto = motto;
    }

    public String getDifftime() {
        return difftime;
    }

    public void setDifftime(String difftime) {
        this.difftime = difftime;
    }
}
