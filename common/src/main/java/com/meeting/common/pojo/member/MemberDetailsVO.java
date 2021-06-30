package com.meeting.common.pojo.member;

import com.meeting.common.pojo.base.BaseVO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.util.Date;

/**
 * @author: dameizi
 * @description: 会员详情
 * @dateTime 2019-06-11 14:26
 * @className MemberVO
 */
@ApiModel(value="会员详情",description="会员详情")
public class MemberDetailsVO extends BaseVO implements Serializable {

    private static final long serialVersionUID = -2384110528181282647L;
    /** 会员主键ID */
    @ApiModelProperty(value = "会员主键ID")
    private Integer memberDetailsId;
    /** 账号 */
    @ApiModelProperty(value = "账号")
    private String account;
    /** 密码 */
    @ApiModelProperty(value = "密码")
    private String password;
    /** 昵称 */
    @ApiModelProperty(value = "昵称")
    private String nickname;
    /** 旧密码 */
    @ApiModelProperty(value = "旧密码")
    private String oldPassword;
    /** 确认密码 */
    @ApiModelProperty(value = "确认密码")
    private String confirmPassword;
    /** 年龄 */
    @ApiModelProperty(value = "年龄")
    private Integer age;
    /** 出生日期 */
    @ApiModelProperty(value = "生日")
    private Date birthday;
    /** 邮箱 */
    @ApiModelProperty(value = "邮箱")
    private String email;
    /** 头像 */
    @ApiModelProperty(value = "头像")
    private String avatar;
    /** 手机号码 */
    @ApiModelProperty(value = "手机号码")
    private String telephone;
    /** 个性签名 */
    @ApiModelProperty(value = "个性签名")
    private String motto;
    /** 性别（0：保密 1：男 2：女） */
    @ApiModelProperty(value = "性别（0：保密 1：男 2：女）")
    private Integer gender;
    /** 是否启用（0：禁用 1：启用） */
    @ApiModelProperty(value = "是否启用（0：禁用 1：启用）")
    private Integer isEnable;
    /** 角色类型（1：会员 2：管理员） */
    @ApiModelProperty(value = "角色类型（1：会员 2：管理员）")
    private Integer roleType;
    /** 登录时间 */
    @ApiModelProperty(value="登录时间")
    private Date gmtLogin;
    /** 简介 */
    @ApiModelProperty(value = "简介")
    private String intro;
    /** 名字 */
    @ApiModelProperty(value = "名字")
    private String firstName;
    /** 血型 */
    @ApiModelProperty(value = "血型")
    private Integer bloodType;
    /** 星座 */
    @ApiModelProperty(value = "星座")
    private String constellation;
    /** 生肖 */
    @ApiModelProperty(value = "生肖")
    private String zodiac;
    /** 职业 */
    @ApiModelProperty(value = "职业")
    private String vocation;
    /** 学校 */
    @ApiModelProperty(value = "学校")
    private String school;
    /** 联系地址 */
    @ApiModelProperty(value = "联系地址")
    private String address;
    /** 添加策略（0：全部 1：账号 2：手机 3：邮箱 4：问题） */
    @ApiModelProperty(value = "添加策略（0：全部 1：账号 2：手机 3：邮箱 4：问题）")
    private Integer addPolicy;
    /** 添加策略问题 */
    @ApiModelProperty(value = "添加策略问题")
    private String addPolicyQuestion;
    /** 添加策略答案 */
    @ApiModelProperty(value = "添加策略答案")
    private String addPolicyAnswer;
    /** 密保问题 */
    @ApiModelProperty(value = "密保问题")
    private String encryptedQuestion;
    /** 密保答案 */
    @ApiModelProperty(value = "密保答案")
    private String encryptedAnswer;

    public Integer getMemberDetailsId() {
        return memberDetailsId;
    }

    public void setMemberDetailsId(Integer memberDetailsId) {
        this.memberDetailsId = memberDetailsId;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getOldPassword() {
        return oldPassword;
    }

    public void setOldPassword(String oldPassword) {
        this.oldPassword = oldPassword;
    }

    public String getConfirmPassword() {
        return confirmPassword;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
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

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getMotto() {
        return motto;
    }

    public void setMotto(String motto) {
        this.motto = motto;
    }

    public Integer getGender() {
        return gender;
    }

    public void setGender(Integer gender) {
        this.gender = gender;
    }

    public Integer getIsEnable() {
        return isEnable;
    }

    public void setIsEnable(Integer isEnable) {
        this.isEnable = isEnable;
    }

    public Integer getRoleType() {
        return roleType;
    }

    public void setRoleType(Integer roleType) {
        this.roleType = roleType;
    }

    public Date getGmtLogin() {
        return gmtLogin;
    }

    public void setGmtLogin(Date gmtLogin) {
        this.gmtLogin = gmtLogin;
    }

    public String getIntro() {
        return intro;
    }

    public void setIntro(String intro) {
        this.intro = intro;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public Integer getBloodType() {
        return bloodType;
    }

    public void setBloodType(Integer bloodType) {
        this.bloodType = bloodType;
    }

    public String getConstellation() {
        return constellation;
    }

    public void setConstellation(String constellation) {
        this.constellation = constellation;
    }

    public String getZodiac() {
        return zodiac;
    }

    public void setZodiac(String zodiac) {
        this.zodiac = zodiac;
    }

    public String getVocation() {
        return vocation;
    }

    public void setVocation(String vocation) {
        this.vocation = vocation;
    }

    public String getSchool() {
        return school;
    }

    public void setSchool(String school) {
        this.school = school;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Integer getAddPolicy() {
        return addPolicy;
    }

    public void setAddPolicy(Integer addPolicy) {
        this.addPolicy = addPolicy;
    }

    public String getAddPolicyQuestion() {
        return addPolicyQuestion;
    }

    public void setAddPolicyQuestion(String addPolicyQuestion) {
        this.addPolicyQuestion = addPolicyQuestion;
    }

    public String getAddPolicyAnswer() {
        return addPolicyAnswer;
    }

    public void setAddPolicyAnswer(String addPolicyAnswer) {
        this.addPolicyAnswer = addPolicyAnswer;
    }

    public String getEncryptedQuestion() {
        return encryptedQuestion;
    }

    public void setEncryptedQuestion(String encryptedQuestion) {
        this.encryptedQuestion = encryptedQuestion;
    }

    public String getEncryptedAnswer() {
        return encryptedAnswer;
    }

    public void setEncryptedAnswer(String encryptedAnswer) {
        this.encryptedAnswer = encryptedAnswer;
    }
}
