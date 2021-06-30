package com.meeting.common.member.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.meeting.common.constant.CommonConst;
import com.meeting.common.constant.RedisKeyConst;
import com.meeting.common.exception.DescribeException;
import com.meeting.common.friend.dao.IFriendDao;
import com.meeting.common.friend.service.IFriendService;
import com.meeting.common.friendapply.dao.IFriendApplyDao;
import com.meeting.common.friendclass.dao.IFriendClassDao;
import com.meeting.common.group.dao.IGroupDao;
import com.meeting.common.groupmember.dao.IGroupMemberDao;
import com.meeting.common.member.dao.IMemberDao;
import com.meeting.common.member.service.IMemberService;
import com.meeting.common.pojo.base.PageVO;
import com.meeting.common.pojo.dialog.DialogVO;
import com.meeting.common.pojo.friend.FriendVO;
import com.meeting.common.pojo.friendapply.FriendApplyVO;
import com.meeting.common.pojo.friendclass.FriendClassVO;
import com.meeting.common.pojo.group.GroupDialogDTO;
import com.meeting.common.pojo.member.MemberVO;
import com.meeting.common.pojo.socketio.ChatMessageVO;
import com.meeting.common.util.CommonUtil;
import com.meeting.common.util.SpringContextHolder;
import org.redisson.api.RBucket;
import org.redisson.api.RSet;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * @author: dameizi
 * @description: 会员业务层
 * @dateTime 2019-03-29 16:07
 * @className com.weilaizhe.common.merchant.service.impl.MerchantService
 */
@Service
public class MemberServiceImpl implements IMemberService {

    @Autowired
    private RedissonClient redissonClient;
    @Autowired
    private IMemberDao iMemberDao;
    @Autowired
    private IFriendClassDao iFriendClassDao;
    @Autowired
    private IFriendService iFriendService;
    @Autowired
    private IFriendDao iFriendDao;
    @Autowired
    private IFriendApplyDao iFriendApplyDao;
    @Autowired
    private IGroupDao iGroupDao;
    @Autowired
    private IGroupMemberDao iGroupMemberDao;

    /**
     * @author: dameizi
     * @dateTime: 2019-07-22 18:23
     * @description: 获取登录时间
     * @param: [chatMessageVO]
     * @return: java.lang.Object
     */
    @Override
    public Object getMemberLoginTime(ChatMessageVO chatMessageVO) {
        // 是好友还是群组
        if (CommonConst.DIALOG_TYPE_SINGLE.equals(chatMessageVO.getMessageType())) {
            return iMemberDao.getMemberLoginTime(chatMessageVO);
        } else if (CommonConst.DIALOG_TYPE_CLUSTER.equals(chatMessageVO.getMessageType())){
            return iGroupMemberDao.getGroupCountByDialog(chatMessageVO);
        }
        return MessageFormat.format(CommonConst.ERROR, CommonConst.PARAM_EXCEPTION);
    }

    /**
     * @author: dameizi
     * @dateTime: 2019-04-03 13:29
     * @description: 搜索会员
     * @param: [memberVO]
     * @return: MemberVO
     */
    @Override
    public Object getMember(MemberVO memberVO){
        // 参数空检查
        if (StringUtils.isEmpty(memberVO.getFindType())){
            return MessageFormat.format(CommonConst.ERROR, "找回类型不能为空");
        }
        if (StringUtils.isEmpty(memberVO.getAccount())){
            return MessageFormat.format(CommonConst.ERROR, "账号不能为空");
        }
        // 账号是否存在
        MemberVO member = iMemberDao.getMemberByPassword(memberVO);
        if (member == null){
            return MessageFormat.format(CommonConst.ERROR, "账号不存在");
        }
        // 判断是依据手机号找回还是邮箱找回
        if (CommonConst.RETRIEVE_PASSWORD_TYPE_EMAIL.equals(memberVO.getFindType())){
            if (member.getEmail() == null){
                return MessageFormat.format(CommonConst.ERROR, "账号未设置邮箱，请联系管理员");
            }
        }else if (CommonConst.RETRIEVE_PASSWORD_TYPE_TELEPHONE.equals(memberVO.getFindType())){
            if (member.getTelephone() == null){
                return MessageFormat.format(CommonConst.ERROR, "账号未设置手机，请联系管理员");
            }
        }else{
            return MessageFormat.format(CommonConst.ERROR, "找回类型错误");
        }
        return member;
    }

    /**
     * @author: dameizi
     * @dateTime: 2019-07-06 22:06
     * @description: 修改密码
     * @param: [memberVO]
     * @return: java.lang.Object
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Object changePassword(MemberVO memberVO) {
        // 参数空检查
        String authCode = memberVO.getAuthCode();
        if (StringUtils.isEmpty(authCode)){
            return MessageFormat.format(CommonConst.ERROR, "验证码不能为空");
        }
        if (StringUtils.isEmpty(memberVO.getAccount())){
            return MessageFormat.format(CommonConst.ERROR, "账号不能为空");
        }
        if (StringUtils.isEmpty(memberVO.getPassword())){
            return MessageFormat.format(CommonConst.ERROR, "密码不能为空");
        }
        // 判断是依据手机号找回还是邮箱找回
        if (CommonConst.FIND_TYPE_EMAIL.equals(memberVO.getFindType())){
            if (StringUtils.isEmpty(memberVO.getEmail())){
                return MessageFormat.format(CommonConst.ERROR, "邮箱不能为空");
            }
            RBucket<String> cache = redissonClient.getBucket(MessageFormat.format(RedisKeyConst.MAIL_AUTH_CODE, memberVO.getAccount(), memberVO.getEmail()));
            if (!authCode.equals(cache.get())){
                return MessageFormat.format(CommonConst.ERROR, "验证码错误");
            }
        } else if (CommonConst.FIND_TYPE_TELEPHONE.equals(memberVO.getFindType())){
            if (StringUtils.isEmpty(memberVO.getTelephone())){
                return MessageFormat.format(CommonConst.ERROR, "手机号码不能为空");
            }
            RBucket<String> cache = redissonClient.getBucket(MessageFormat.format(RedisKeyConst.MEMBER_PHONE_SMS, memberVO.getAccount(), memberVO.getTelephone()));
            if (!authCode.equals(cache.get())){
                return MessageFormat.format(CommonConst.ERROR, "验证码错误");
            }
        }else{
            MessageFormat.format(CommonConst.ERROR, "找回类型错误");
        }
        return iMemberDao.changePassword(memberVO);
    }

    /**
     * @author: dameizi
     * @dateTime: 2019-07-06 21:28
     * @description: 根据ID或账号获取
     * @param: [memberVO]
     * @return: com.meeting.common.pojo.member.MemberVO
     */
    @Override
    public MemberVO getMemberById(MemberVO memberVO) {
        return iMemberDao.getMemberById(memberVO);
    }

    /**
     * @author: dameizi
     * @dateTime: 2019-08-02 11:35
     * @description: 会员删除
     * @param: [merchantVO]
     * @return: int
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public int delete(MemberVO memberVO) {
        return iMemberDao.delete(memberVO);
    }

    /**
     * @author: dameizi
     * @dateTime: 2019-07-03 19:30
     * @description: 更新会员信息
     * @param: [memberVO]
     * @return: int
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public int update(MemberVO memberVO) {
        return iMemberDao.update(memberVO);
    }

    /**
     * @author: dameizi
     * @dateTime: 2019-06-18 15:49
     * @description: 更新登录时间
     * @param: [memberVO]
     * @return: int
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public int updateLoginTime(MemberVO memberVO) {
        return iMemberDao.updateLoginTime(memberVO);
    }

    /**
     * @author: dameizi
     * @dateTime: 2019-03-29 16:11
     * @description: 分页查询会员信息
     * @param: [memberVO, pageVO]
     * @return: PageResult<MerchantVO>
     */
    @Override
    public IPage<MemberVO> pageMember(MemberVO memberVO, PageVO pageVO){
        // mybatis plus分页查询插件，第一个参数必须是Page<T>，返回类型必须IPage<T>接收
        Page<MemberVO> page = new Page<MemberVO>(pageVO.getPageNo(),pageVO.getPageSize());
        return iMemberDao.pageMember(page, memberVO);
    }

    /**
     * @author: dameizi
     * @dateTime: 2019-03-30 22:00
     * @description: 列表查询会员信息
     * @param: [memberVO]
     * @return: java.util.List<MemberVO>
     */
    @Override
    public List<MemberVO> listMember(MemberVO memberVO){
        return iMemberDao.listMember(memberVO);
    }

    /**
     * @author: dameizi
     * @dateTime: 2019-07-05 17:55
     * @description:
     * @param: [file,memberVO]
     * @return: int
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Object insert(MultipartFile file, MemberVO memberVO) {
        memberVO.setGender(0);
        memberVO.setIsEnable(1);
        memberVO.setRoleType(1);
        if (file == null){
            //获取配置合法路径
            String path = SpringContextHolder.getApplicationContext().getEnvironment().getProperty("file.linux.avatar.domain.path");
            memberVO.setAvatar(MessageFormat.format("{0}{1}", path, "meeting.png"));
        }
        // 参数校验
        Object x = paramInsertCheck(memberVO);
        if (x != null) {
            return x;
        }
        // 注册会员
        iMemberDao.insert(memberVO);
        MemberVO member = iMemberDao.getMemberById(memberVO);
        // 初始化分组
        FriendClassVO friendClassVO = new FriendClassVO();
        friendClassVO.setIsGrouping(0);
        friendClassVO.setMemberId(member.getMemberId());
        friendClassVO.setNamespace(CommonConst.DEFAULT_CLASS);
        return iFriendClassDao.insert(friendClassVO);
    }

    /**
     * @author: dameizi
     * @dateTime: 2019-07-05 19:29
     * @description: 注册参数检查
     * @param: [memberVO]
     * @return: java.lang.Object
     */
    private Object paramInsertCheck(MemberVO memberVO) {
        if (StringUtils.isEmpty(memberVO.getNickname())){
            return MessageFormat.format(CommonConst.ERROR, "昵称不能为空");
        }
        if (memberVO.getNickname().length() > 16){
            return MessageFormat.format(CommonConst.ERROR, "昵称太长");
        }
        if (StringUtils.isEmpty(memberVO.getAccount())){
            return MessageFormat.format(CommonConst.ERROR, "账号不能为空");
        }
        if (!memberVO.getAccount().matches("[0-9a-zA-Z_]{5,12}")){
            return MessageFormat.format(CommonConst.ERROR, "账号只能数字加字母且5至12位");
        }
        if (StringUtils.isEmpty(memberVO.getPassword())){
            return MessageFormat.format(CommonConst.ERROR, "密码不能为空");
        }
        if (StringUtils.isEmpty(memberVO.getConfirmPassword())){
            return MessageFormat.format(CommonConst.ERROR, "确认密码不能为空");
        }
        if (!memberVO.getPassword().equals(memberVO.getConfirmPassword())){
            return MessageFormat.format(CommonConst.ERROR, "两次密码不一致");
        }
        if (StringUtils.isEmpty(memberVO.getEmail())){
            return MessageFormat.format(CommonConst.ERROR, "邮箱不能为空");
        }
        if (!CommonUtil.checkMail(memberVO.getEmail())){
            return MessageFormat.format(CommonConst.ERROR, "邮箱不合法");
        }
        if (StringUtils.isEmpty(memberVO.getBirthday())){
            return MessageFormat.format(CommonConst.ERROR, "生日不能为空");
        }
        /*
        if (StringUtils.isEmpty(memberVO.getTelephone())){
            return MessageFormat.format(CommonConst.ERROR, "手机号码不能为空");
        }
        String REGEX_MOBILE = "^((17[0-9])|(14[0-9])|(13[0-9])|(15[^4,\\D])|(18[0,5-9]))\\d{8}$";
        if(!Pattern.matches(REGEX_MOBILE, memberVO.getTelephone())){
            return MessageFormat.format(CommonConst.ERROR, "手机号格式不对");
        }*/
        if (iMemberDao.getMemberByAccount(memberVO) > 0){
            return MessageFormat.format(CommonConst.ERROR, "账号已被使用");
        }
        return null;
    }

    /**
     * @author: dameizi
     * @dateTime: 2019-06-28 19:13
     * @description: 获取好友对话列表
     * @param: [memberVO]
     * @return: java.util.TreeSet<com.meeting.common.pojo.member.MemberVO>
     */
    @Override
    public ArrayList<MemberVO> getDialogueList(MemberVO memberVO) {
        // 缓存获取已聊天的好友
        String dialogueSetKey = MessageFormat.format(RedisKeyConst.MEETING_DIALOGUE_SET, memberVO.getAccount());
        RSet<Integer> dialogueSet = redissonClient.getSet(dialogueSetKey);
        // 缓存为空则返回空列表
        if (dialogueSet != null && dialogueSet.size() != 0){
            for (Integer memberId: dialogueSet){
                // 当前账号移除不查询在对话列表
                if (memberId.equals(memberVO.getMemberId())){
                    dialogueSet.remove(memberId);
                    break;
                }
            }
            return iMemberDao.getDialogueList(dialogueSet);
        }else {
            return new ArrayList<>();
        }
    }

    /**
     * @author: dameizi
     * @dateTime: 2019-07-06 15:57
     * @description: 搜索好友
     * @param: [friendVO]
     * @return: com.meeting.common.pojo.member.MemberVO
     */
    @Override
    public MemberVO searchToAdd(FriendVO friendVO) {
        MemberVO memberVO = iMemberDao.searchByAccount(friendVO);
        friendVO.setMemberId(memberVO.getMemberId());
        if (iFriendService.isFriend(friendVO)){
            memberVO.setIsFriend(CommonConst.YES);
        }else{
            memberVO.setIsFriend(CommonConst.NO);
        }
        return memberVO;
    }

    /**
     * @author: dameizi
     * @dateTime: 2019-07-08 13:18
     * @description: 全局账号搜索
     * @param: [friendVO]
     * @return: com.meeting.common.pojo.member.MemberVO
     */
    @Override
    public MemberVO searchByAccount(FriendVO friendVO) {
        return iMemberDao.searchByAccount(friendVO);
    }

    /**
     * @author: dameizi
     * @dateTime: 2019-07-06 20:06
     * @description: 添加好友
     * @param: [friendVO]
     * @return: com.meeting.common.pojo.member.MemberVO
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Object addFriend(FriendApplyVO friendApplyVO) {
        // 先查询是否存在，已存在就更新备注和创建时间，不存在则直接添加
        if (iFriendApplyDao.isFriendApply(friendApplyVO) > 0){
            return iFriendApplyDao.updateSave(friendApplyVO);
        }else{
            // 申请状态（0：申请中 1：已同意 2：已过期）
            friendApplyVO.setStatus(0);
            return iFriendApplyDao.save(friendApplyVO);
        }
    }

    /**
     * @author: dameizi
     * @dateTime: 2019-07-08 20:01
     * @description: 同意添加
     * @param: [memberVO, friendVO]
     * @return: java.lang.Object
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Object agreeAdd(MemberVO memberVO, FriendVO friendVO) {
        if (iFriendApplyDao.getFriendApplyByAdd(memberVO.getMemberId(), friendVO.getTargetId()).getStatus() == 0){
            // 是否是好友，比如隐式删除则更新
            Integer targetId = friendVO.getTargetId();
            friendVO.setMemberId(memberVO.getMemberId());
            if (iFriendDao.isFriend(friendVO)){
                // 更新添加好友
                iFriendDao.updateAdd(friendVO);
                // 更新申请状态
                return updateApplyStatus(memberVO, targetId);
            }else{
                // 初始数据
                friendVO.setStrongReminder(CommonConst.NO);
                friendVO.setChatTop(CommonConst.YES);
                friendVO.setIsDisturb(CommonConst.YES);
                friendVO.setStatus(0);
                // 会员好友数据
                FriendClassVO friendClass = iFriendClassDao.getFriendClassByMemberId(memberVO.getMemberId());
                friendVO.setFriendClassId(friendClass.getFriendClassId());
                iFriendDao.save(friendVO);
                // 目标好友数据
                FriendClassVO friendClass1 = iFriendClassDao.getFriendClassByMemberId(friendVO.getTargetId());
                friendVO.setMemberId(friendVO.getTargetId());
                friendVO.setTargetId(memberVO.getMemberId());
                friendVO.setAccount(memberVO.getAccount());
                friendVO.setAlias(memberVO.getNickname());
                friendVO.setFriendClassId(friendClass1.getFriendClassId());
                iFriendDao.save(friendVO);
                // 更新申请状态
                return updateApplyStatus(memberVO, targetId);
            }
        }else{
            return MessageFormat.format(CommonConst.ERROR, "已经是好友，请勿重复操作");
        }
    }

    /** 更新申请状态 */
    private int updateApplyStatus(MemberVO memberVO, Integer targetId) {
        FriendApplyVO friendApplyVO = new FriendApplyVO();
        friendApplyVO.setMemberId(memberVO.getMemberId());
        friendApplyVO.setTargetId(targetId);
        friendApplyVO.setStatus(1);
        return iFriendApplyDao.updateStatus(friendApplyVO);
    }

    /**
     * @author: dameizi
     * @dateTime: 2019-07-08 18:27
     * @description: 获取新的好友列表
     * @param: [memberVO]
     * @return: java.lang.Object
     */
    @Override
    public Object getNewFriendList(MemberVO memberVO) {
        return iMemberDao.getNewFriendList(memberVO);
    }

    /**
     * @author: dameizi
     * @dateTime: 2019-07-12 12:13
     * @description: 更新头像
     * @param: [memberVO]
     * @return: java.lang.Object
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Object updateAvatar(MemberVO memberVO) {
        return iMemberDao.updateAvatar(memberVO);
    }

    /**
     * @author: dameizi
     * @dateTime: 2019-07-20 19:25
     * @description: 对话信息
     * @param: [dialogVO]
     * @return: java.lang.Object
     */
    @Override
    public Object dialogueInfo(DialogVO dialogVO) {
        if (CommonConst.DIALOG_TYPE_SINGLE.equals(dialogVO.getChatType())){
            return iMemberDao.dialogueInfo(dialogVO);
        } else if (CommonConst.DIALOG_TYPE_CLUSTER.equals(dialogVO.getChatType())){
            GroupDialogDTO groupDialogDTO = iGroupDao.dialogueInfo(dialogVO);
            groupDialogDTO.setListMember(iGroupMemberDao.listMember(dialogVO));
            return groupDialogDTO;
        }
        return MessageFormat.format(CommonConst.ERROR, CommonConst.PARAM_EXCEPTION);
    }

    /**
     * @author: dameizi
     * @dateTime: 2019-06-02 17:54
     * @description: 会员登录
     * @param: [memberVO]
     * @return: MemberVO
     */
    @Override
    public MemberVO login(MemberVO memberVO){
        // 账号密码非空判断
        CommonUtil.paramEmptyVerify(memberVO.getAccount(), CommonConst.ACCOUNT_NOT_EMPTY);
        CommonUtil.paramEmptyVerify(memberVO.getPassword(), CommonConst.PASSWORD_NOT_EMPTY);
        // 密码MD5
        //memberVO.setPassword(CommonUtil.md5Password16(memberVO.getPassword()));
        memberVO.setIsEnable(1);
        return iMemberDao.login(memberVO);
    }

    /**
     * @author: dameizi
     * @dateTime: 2019-04-03 13:44
     * @description: 密码校验
     * @param: [merchantVO]
     * @return: void
     */
    private void passwordVerify(MemberVO merchantVO) {
        // 密码必须是6-18位数字、字母组合，字母区分大小写（MD5加密字符串校验）
        String regex;
        regex = "^([a-zA-Z0-9]{6,18})$";
        String pwd = merchantVO.getPassword();
        if(StringUtils.isEmpty(pwd) || !pwd.matches(regex)) {
            throw new DescribeException(CommonConst.MERCHANT_PASSWORD_VERIFY, 0);
        }
    }

    /**
     * @author: dameizi
     * @dateTime: 2019-03-31 21:03
     * @description: 会员名称、会员账号是否使用
     * @param: [merchantVO]
     * @return: void
     */
    public void parameterIsUse(MemberVO merchantVO){
        // 会员名称不能为空并不能大于16位
        if (StringUtils.isEmpty(merchantVO.getAccount()) || merchantVO.getAccount().length() > 16){
            throw new DescribeException("会员名称非法", 0);
        }
        // 会员编码或会员账号是否已存在
        if (iMemberDao.getCodeOrAccountIsUse(merchantVO) > 0 ){
            throw new DescribeException(CommonConst.CODE_OR_ACCOUNT_IS_USE, -3);
        }
    }

}
