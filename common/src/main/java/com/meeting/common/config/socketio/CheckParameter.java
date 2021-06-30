package com.meeting.common.config.socketio;

import com.corundumstudio.socketio.SocketIOClient;
import com.meeting.common.constant.CommonConst;
import com.meeting.common.exception.ResultUtil;
import com.meeting.common.pojo.areacascade.AreaCascadeVO;
import com.meeting.common.pojo.dialog.DialogVO;
import com.meeting.common.pojo.friend.FriendVO;
import com.meeting.common.pojo.friendapply.FriendApplyVO;
import com.meeting.common.pojo.group.GroupVO;
import com.meeting.common.pojo.groupmember.GroupMemberVO;
import com.meeting.common.pojo.member.MemberVO;
import com.meeting.common.pojo.socketio.ChatMessageVO;
import com.meeting.common.pojo.socketio.HistoryMessageVO;
import com.meeting.common.util.CommonUtil;
import org.springframework.util.StringUtils;

/**
 * @author dameizi
 * @description 检查参数
 * @dateTime 2019-07-08 16:24
 * @className com.meeting.common.config.socketio.CheckParameter
 */
public class CheckParameter {

    /** 添加好友 */
    public static boolean addFriend(SocketIOClient client, FriendApplyVO friendApplyVO) {
        if (friendApplyVO == null){
            client.sendEvent("addFriendResult", ResultUtil.error(0,"参数非法"));
            return true;
        }
        if (StringUtils.isEmpty(friendApplyVO.getTargetId())){
            client.sendEvent("addFriendResult", ResultUtil.error(0,"目标ID不能为空"));
            return true;
        }
        return false;
    }

    /** 同意添加 */
    public static boolean agreeAdd(SocketIOClient client, FriendVO friendVO) {
        if (friendVO == null){
            client.sendEvent("agreeAddResult", ResultUtil.error(0,"参数非法"));
            return true;
        }
        if (StringUtils.isEmpty(friendVO.getTargetId())){
            client.sendEvent("agreeAddResult", ResultUtil.error(0,"目标ID不能为空"));
            return true;
        }
        if (StringUtils.isEmpty(friendVO.getAccount())){
            client.sendEvent("agreeAddResult", ResultUtil.error(0,"目标账号不能为空"));
            return true;
        }
        if (StringUtils.isEmpty(friendVO.getAlias())){
            client.sendEvent("agreeAddResult", ResultUtil.error(0,"目标别名不能为空"));
            return true;
        }
        return false;
    }

    /** 删除好友 */
    public static boolean deleteFriend(SocketIOClient client, FriendVO friendVO) {
        if (friendVO == null){
            client.sendEvent("deleteFriendResult", ResultUtil.error(0,"参数非法"));
            return true;
        }
        if (StringUtils.isEmpty(friendVO.getTargetId())){
            client.sendEvent("deleteFriendResult", ResultUtil.error(0,"目标ID不能为空"));
            return true;
        }
        return false;
    }

    /** 地区市县 */
    public static boolean areaCityCounty(SocketIOClient client, AreaCascadeVO areaCascadeVO) {
        if (areaCascadeVO == null){
            client.sendEvent("areaCityCountyResult", ResultUtil.error(-1, "参数非法"));
            return true;
        }
        if (StringUtils.isEmpty(areaCascadeVO.getParentId())){
            client.sendEvent("areaCityCountyResult", ResultUtil.error(-1, "父级ID不能为空"));
            return true;
        }
        return false;
    }

    /** 地区县 */
    public static boolean areaCounty(SocketIOClient client, AreaCascadeVO areaCascadeVO) {
        if (areaCascadeVO == null){
            client.sendEvent("areaCountyResult", ResultUtil.error(-1, "参数非法"));
            return true;
        }
        if (StringUtils.isEmpty(areaCascadeVO.getParentId())){
            client.sendEvent("areaCountyResult", ResultUtil.error(-1, "父级ID不能为空"));
            return true;
        }
        return false;
    }

    /** 拉黑好友 */
    public static boolean blacklistFriend(SocketIOClient client, FriendVO friendVO) {
        if (friendVO == null){
            client.sendEvent("blacklistFriendResult", ResultUtil.error(0,"参数非法"));
            return true;
        }
        if (StringUtils.isEmpty(friendVO.getTargetId())){
            client.sendEvent("blacklistFriendResult", ResultUtil.error(0,"目标ID不能为空"));
            return true;
        }
        return false;
    }

    /** 解除拉黑 */
    public static boolean unblock(SocketIOClient client, FriendVO friendVO) {
        if (friendVO == null){
            client.sendEvent("unblockResult", ResultUtil.error(0,"参数非法"));
            return true;
        }
        if (StringUtils.isEmpty(friendVO.getTargetId())){
            client.sendEvent("unblockResult", ResultUtil.error(0,"目标ID不能为空"));
            return true;
        }
        return false;
    }

    /** 编辑会员 */
    public static boolean editMember(SocketIOClient client, MemberVO memberVO) {
        if (memberVO == null){
            client.sendEvent("editMemberResult", ResultUtil.error(0,"参数非法"));
            return true;
        }
        if (StringUtils.isEmpty(memberVO.getNickname())){
            client.sendEvent("editMemberResult", ResultUtil.error(0,"昵称不能为空"));
            return true;
        }
        if (memberVO.getNickname().length() > 16){
            client.sendEvent("editMemberResult", ResultUtil.error(0,"昵称太长"));
            return true;
        }
        if (StringUtils.isEmpty(memberVO.getBirthday())){
            client.sendEvent("editMemberResult", ResultUtil.error(0,"生日不能为空"));
            return true;
        }
        if (StringUtils.isEmpty(memberVO.getGender())){
            client.sendEvent("editMemberResult", ResultUtil.error(0,"性别不能为空"));
            return true;
        }
        if (StringUtils.isEmpty(memberVO.getRegion())){
            client.sendEvent("editMemberResult", ResultUtil.error(0,"地区不能为空"));
            return true;
        }
        if (StringUtils.isEmpty(memberVO.getMotto())){
            client.sendEvent("editMemberResult", ResultUtil.error(0,"个性签名不能为空"));
            return true;
        }
        if (StringUtils.isEmpty(memberVO.getEmail())){
            client.sendEvent("editMemberResult", ResultUtil.error(0,"邮箱不能为空"));
            return true;
        }
        if (!CommonUtil.checkMail(memberVO.getEmail())){
            client.sendEvent("editMemberResult", ResultUtil.error(0,"邮箱不合法"));
            return true;
        }
        if (StringUtils.isEmpty(memberVO.getTelephone())){
            client.sendEvent("editMemberResult", ResultUtil.error(0,"手机号码不能为空"));
            return true;
        }
        if (!CommonUtil.checkTelephone(memberVO.getTelephone())){
            client.sendEvent("editMemberResult", ResultUtil.error(0,"手机号码不合法"));
            return true;
        }
        return false;
    }

    /** 删除对话 */
    public static boolean deleteDialogue(SocketIOClient client, DialogVO dialogVO) {
        if (dialogVO == null){
            client.sendEvent("deleteDialogueResult", ResultUtil.error(0,"参数非法"));
            return true;
        }
        if (StringUtils.isEmpty(dialogVO.getTargetId())){
            client.sendEvent("deleteDialogueResult", ResultUtil.error(0,"目标ID不能为空"));
            return true;
        }
        if (StringUtils.isEmpty(dialogVO.getAccount())){
            client.sendEvent("deleteDialogueResult", ResultUtil.error(0,"目标账号不能为空"));
            return true;
        }
        if (StringUtils.isEmpty(dialogVO.getChatType())){
            client.sendEvent("deleteDialogueResult", ResultUtil.error(0,"对话类型不能为空"));
            return true;
        }
        return false;
    }

    /** 对话信息 */
    public static boolean dialogueInfo(SocketIOClient client, DialogVO dialogVO) {
        if (dialogVO == null){
            client.sendEvent("dialogueInfoResult", ResultUtil.error(0,"参数非法"));
            return true;
        }
        if (StringUtils.isEmpty(dialogVO.getTargetId())){
            client.sendEvent("dialogueInfoResult", ResultUtil.error(0,"目标ID不能为空"));
            return true;
        }
        if (StringUtils.isEmpty(dialogVO.getChatType())){
            client.sendEvent("dialogueInfoResult", ResultUtil.error(0,"对话类型不能为空"));
            return true;
        }
        return false;
    }

    /** 读取消息 */
    public static boolean readMessage(SocketIOClient client, DialogVO dialogVO) {
        if (dialogVO == null){
            client.sendEvent("readMessageResult", ResultUtil.error(0,"参数非法"));
            return true;
        }
        if (StringUtils.isEmpty(dialogVO.getTargetId())){
            client.sendEvent("readMessageResult", ResultUtil.error(0,"目标ID不能为空"));
            return true;
        }
        if (StringUtils.isEmpty(dialogVO.getChatType())){
            client.sendEvent("readMessageResult", ResultUtil.error(0,"对话类型不能为空"));
            return true;
        }
        return false;
    }

    /** 创建群组 */
    public static boolean buildGroup(SocketIOClient client, GroupVO groupVO) {
        if (groupVO == null){
            client.sendEvent("buildGroupResult", ResultUtil.error(0,"参数非法"));
            return true;
        }
        if (StringUtils.isEmpty(groupVO.getNickname())){
            client.sendEvent("buildGroupResult", ResultUtil.error(0,"群组昵称不能为空"));
            return true;
        }
        if (groupVO.getNickname().length() > 16){
            client.sendEvent("buildGroupResult", ResultUtil.error(0,"群组昵称太长"));
            return true;
        }
        if (groupVO.getMemberIds().length == 0){
            client.sendEvent("buildGroupResult", ResultUtil.error(0,"群成员ID不能为空"));
            return true;
        }
        return false;
    }

    /** 查找群成员 */
    public static boolean groupMember(SocketIOClient client, GroupMemberVO groupMemberVO) {
        if (groupMemberVO == null){
            client.sendEvent("groupMemberResult", ResultUtil.error(0,"参数非法"));
            return true;
        }
        if (StringUtils.isEmpty(groupMemberVO.getGroupId())){
            client.sendEvent("groupMemberResult", ResultUtil.error(0,"群组ID不能为空"));
            return true;
        }
        return false;
    }

    /** 获取添加群组成员 */
    public static boolean getAddGroupMember(SocketIOClient client, GroupMemberVO groupMemberVO) {
        if (groupMemberVO == null){
            client.sendEvent("getAddGroupMemberResult", ResultUtil.error(0,"参数非法"));
            return true;
        }
        if (StringUtils.isEmpty(groupMemberVO.getGroupId())){
            client.sendEvent("getAddGroupMemberResult", ResultUtil.error(0,"群组ID不能为空"));
            return true;
        }
        return false;
    }

    /** 获取移除群组成员 */
    public static boolean getRemoveGroupMember(SocketIOClient client, GroupMemberVO groupMemberVO) {
        if (groupMemberVO == null){
            client.sendEvent("getRemoveGroupMember", ResultUtil.error(0,"参数非法"));
            return true;
        }
        if (StringUtils.isEmpty(groupMemberVO.getGroupId())){
            client.sendEvent("getRemoveGroupMember", ResultUtil.error(0,"群组ID不能为空"));
            return true;
        }
        return false;
    }

    /** 添加群组成员 */
    public static boolean addGroupMember(SocketIOClient client, GroupMemberVO groupMemberVO) {
        if (groupMemberVO == null){
            client.sendEvent("addGroupMemberResult", ResultUtil.error(0,"参数非法"));
            return true;
        }
        if (StringUtils.isEmpty(groupMemberVO.getGroupId())){
            client.sendEvent("addGroupMemberResult", ResultUtil.error(0,"群组ID不能为空"));
            return true;
        }
        if (groupMemberVO.getMemberIds().length == 0){
            client.sendEvent("addGroupMemberResult", ResultUtil.error(0,"群成员ID数组不能为空"));
            return true;
        }
        return false;
    }

    /** 退出群组 */
    public static boolean quitGroup(SocketIOClient client, GroupMemberVO groupMemberVO) {
        if (groupMemberVO == null){
            client.sendEvent("quitGroupResult", ResultUtil.error(0,"参数非法"));
            return true;
        }
        if (StringUtils.isEmpty(groupMemberVO.getGroupId())){
            client.sendEvent("quitGroupResult", ResultUtil.error(0,"群组ID不能为空"));
            return true;
        }
        return false;
    }

    /** 解散群组 */
    public static boolean disbandGroup(SocketIOClient client, GroupMemberVO groupMemberVO) {
        if (groupMemberVO == null){
            client.sendEvent("disbandGroupResult", ResultUtil.error(0,"参数非法"));
            return true;
        }
        if (StringUtils.isEmpty(groupMemberVO.getGroupId())){
            client.sendEvent("disbandGroupResult", ResultUtil.error(0,"群组ID不能为空"));
            return true;
        }
        return false;
    }

    /** 移除成员 */
    public static boolean removeGroupMemeber(SocketIOClient client, GroupMemberVO groupMemberVO) {
        if (groupMemberVO == null){
            client.sendEvent("removeGroupMemberResult", ResultUtil.error(0,"参数非法"));
            return true;
        }
        if (StringUtils.isEmpty(groupMemberVO.getGroupId())){
            client.sendEvent("removeGroupMemberResult", ResultUtil.error(0,"群组ID不能为空"));
            return true;
        }
        if (groupMemberVO.getMemberIds().length == 0){
            client.sendEvent("removeGroupMemberResult", ResultUtil.error(0,"成员ID数组不能为空"));
            return true;
        }
        return false;
    }

    /** 编辑群组 */
    public static boolean editGroup(SocketIOClient client, GroupVO groupVO) {
        if (groupVO == null){
            client.sendEvent("editGroupResult", ResultUtil.error(0,"参数非法"));
            return true;
        }
        if (StringUtils.isEmpty(groupVO.getGroupsId())){
            client.sendEvent("editGroupResult", ResultUtil.error(0,"群组ID不能为空"));
            return true;
        }
        if (StringUtils.isEmpty(groupVO.getNickname())){
            client.sendEvent("editGroupResult", ResultUtil.error(0,"群组昵称不能为空"));
            return true;
        }
        if (groupVO.getNickname().length() > 16){
            client.sendEvent("editGroupResult", ResultUtil.error(0,"群组昵称太长"));
            return true;
        }
        if (StringUtils.isEmpty(groupVO.getAnnouncement())){
            client.sendEvent("editGroupResult", ResultUtil.error(0,"群组公告不能为空"));
            return true;
        }
        if (groupVO.getAnnouncement().length() > 600){
            client.sendEvent("editGroupResult", ResultUtil.error(0,"群组公告太长"));
            return true;
        }
        return false;
    }

    /** 发送聊天基础 */
    public static boolean sendChatBase(SocketIOClient client, ChatMessageVO chatMessageVO) {
        if (chatMessageVO == null){
            client.sendEvent("listMessageResult", ResultUtil.error(0,"参数非法"));
            return true;
        }
        if (StringUtils.isEmpty(chatMessageVO.getMessageType())){
            client.sendEvent("listMessageResult", ResultUtil.error(0,"消息类型不能为空"));
            return true;
        }
        return false;
    }

    /** 发送聊天基础派生一 */
    private static boolean sendChatBaseOne(SocketIOClient client, ChatMessageVO chatMessageVO) {
        if (StringUtils.isEmpty(chatMessageVO.getTargetId())){
            client.sendEvent("listMessageResult", ResultUtil.error(0,"接收者ID不能为空"));
            return true;
        }
        if (StringUtils.isEmpty(chatMessageVO.getAccount())){
            client.sendEvent("listMessageResult", ResultUtil.error(0,"接收者账号不能为空"));
            return true;
        }
        return false;
    }

    /** 发送聊天基础派生二 */
    private static boolean sendChatBaseTwo(SocketIOClient client, ChatMessageVO chatMessageVO) {
        if (StringUtils.isEmpty(chatMessageVO.getTargetId())){
            client.sendEvent("listMessageResult", ResultUtil.error(0,"接收者ID不能为空"));
            return true;
        }
        return false;
    }

    /** 最新30消息记录 */
    public static boolean listMessage(SocketIOClient client, ChatMessageVO chatMessageVO) {
        if (sendChatBase(client, chatMessageVO)) {
            return true;
        }
        if (sendChatBaseOne(client, chatMessageVO)) {
            return true;
        }
        return false;
    }

    /** 发送消息 */
    public static boolean sendMsg(SocketIOClient client, ChatMessageVO chatMessageVO) {
        if (sendChatBase(client, chatMessageVO)) {
            return true;
        }
        if (sendChatBaseOne(client, chatMessageVO)) {
            return true;
        }
        if (StringUtils.isEmpty(chatMessageVO.getAvatar())){
            client.sendEvent("listMessageResult", ResultUtil.error(0,"接收者头像图片不能为空"));
            return true;
        }
        if (StringUtils.isEmpty(chatMessageVO.getContent())){
            client.sendEvent("listMessageResult", ResultUtil.error(0,"消息内容不能为空"));
            return true;
        }
        if (chatMessageVO.getContent().length() > CommonConst.MAX_SINGLE_TEXT_CONTENT_LENGTH){
            client.sendEvent("listMessageResult", ResultUtil.error(0,"消息内容超越限制"));
            return true;
        }
        return false;
    }

    /** 读取消息 */
    public static boolean readMsg(SocketIOClient client, ChatMessageVO chatMessageVO) {
        if (sendChatBase(client, chatMessageVO)) {
            return true;
        }
        if (sendChatBaseTwo(client, chatMessageVO)) {
            return true;
        }
        return false;
    }

    /** 删除消息 */
    public static boolean deleteMsg(SocketIOClient client, ChatMessageVO chatMessageVO) {
        if (sendChatBase(client, chatMessageVO)) {
            return true;
        }
        if (sendChatBaseTwo(client, chatMessageVO)) {
            return true;
        }
        if (StringUtils.isEmpty(chatMessageVO.getMsgid())){
            client.sendEvent("listMessageResult", ResultUtil.error(0,"消息标识不能为空"));
            return true;
        }
        return false;
    }

    /** 历史消息 */
    public static boolean historyMessage(SocketIOClient client, HistoryMessageVO message) {
        if (message == null){
            client.sendEvent("historyMessageResult", ResultUtil.error(0,"参数非法"));
            return true;
        }
        if (StringUtils.isEmpty(message.getReceiveId())){
            client.sendEvent("historyMessageResult", ResultUtil.error(0,"接收者账号不能为空"));
            return true;
        }
        return false;
    }

}
