package com.meeting.common.controller;

import com.meeting.common.constant.CommonConst;
import com.meeting.common.constant.RedisKeyConst;
import com.meeting.common.dialog.service.IDialogService;
import com.meeting.common.friendmessage.service.IFriendMessageService;
import com.meeting.common.group.service.IGroupService;
import com.meeting.common.groupmessage.service.IGroupMessageService;
import com.meeting.common.member.service.IMemberService;
import com.meeting.common.pojo.friendmessage.FriendMessageVO;
import com.meeting.common.pojo.group.GroupVO;
import com.meeting.common.pojo.groupmessage.GroupMessageVO;
import com.meeting.common.pojo.member.MemberVO;
import com.meeting.common.pojo.socketio.ChatMessageVO;
import com.meeting.common.util.CommonUtil;
import org.redisson.api.RBucket;
import org.redisson.api.RedissonClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.text.MessageFormat;

import static com.meeting.common.init.DaoInit.saveInitFriendMessage;
import static com.meeting.common.init.DaoInit.saveInitGroupMessage;

/**
 * @author dameizi
 * @description 文件上传
 * @dateTime 2019-07-03 21:03
 * @className com.meeting.common.controller
 */
@RestController
@RequestMapping("/file")
public class FileUpload {

    private static final Logger logger = LoggerFactory.getLogger(FileUpload.class);
    /** 系统路径 */
    private static final String LINUX_MESSAGE = "/home/share/meeting2/message/";
    private static final String FILE_DOMAIN_NAME_MESSAGE = "http://file.00pay.vip/message/";
    private static final String LINUX_AVATAR = "/home/share/meeting2/avatar/";
    private static final String FILE_DOMAIN_NAME_AVATAR = "http://file.00pay.vip/avatar/";
    private static final String WINDOWS = "D://log/uploadFile/";
    /** 系统类型 */
    private static String osName = System.getProperty("os.name").toLowerCase();

	@Autowired
    private IFriendMessageService iFriendMessageService;
	@Autowired
    private IGroupMessageService iGroupMessageService;
	@Autowired
    private RedissonClient redissonClient;
	@Autowired
    private IMemberService iMemberService;
	@Autowired
    private IGroupService iGroupService;
	@Autowired
    private IDialogService iDialogService;

    /**
     * @author: dameizi
     * @dateTime: 2019-07-19 11:54
     * @description: 上传群组头像
     * @param: [file, message]
     * @return: java.lang.Object
     */
    @RequestMapping(value = "/uploadGroupAvatar", method = RequestMethod.POST)
    public Object uploadGroupAvatar(@RequestParam(value = "file", required = false) MultipartFile file, ChatMessageVO chatMessageVO) {
        // 参数校验
        String paramResult = paramUploadAvatar(file, chatMessageVO);
        if (paramResult != null) {
            return paramResult;
        }
        if (StringUtils.isEmpty(chatMessageVO.getTargetId())){
            return MessageFormat.format(CommonConst.ERROR, "群组ID不能为空");
        }
        String sessionRedisKey = MessageFormat.format(RedisKeyConst.MEETING_SOCKETIO_ONESELF, chatMessageVO.getClientId());
        RBucket<MemberVO> clientSession = redissonClient.getBucket(sessionRedisKey);
        if (clientSession != null && clientSession.get() != null){
            MemberVO memberVO = clientSession.get();
            // 上传到目录
            String downLoadPath = uploadToDirectory(file, memberVO, "avatar");
            // 更新数据库图片地址
            GroupVO groupVO = new GroupVO();
            groupVO.setMemberId(memberVO.getMemberId());
            groupVO.setGroupsId(chatMessageVO.getTargetId());
            groupVO.setAvatar(downLoadPath);
            return iGroupService.editGroup(groupVO);
        } else {
            return MessageFormat.format(CommonConst.ERROR, "非法请求");
        }
    }

	/**
	 * @author: dameizi
	 * @dateTime: 2019-07-12 11:54
	 * @description: 上传会员头像
	 * @param: [file, message]
	 * @return: java.lang.Object
	 */
    @RequestMapping(value = "/uploadAvatar", method = RequestMethod.POST)
    public Object uploadAvatar(@RequestParam(value = "file", required = false) MultipartFile file, ChatMessageVO chatMessageVO) {
        // 参数校验
        String paramResult = paramUploadAvatar(file, chatMessageVO);
        if (paramResult != null) {
            return paramResult;
        }
        String sessionRedisKey = MessageFormat.format(RedisKeyConst.MEETING_SOCKETIO_ONESELF, chatMessageVO.getClientId());
        RBucket<MemberVO> clientSession = redissonClient.getBucket(sessionRedisKey);
        if (clientSession != null && clientSession.get() != null){
            MemberVO memberVO = clientSession.get();
            // 上传到目录
            String downLoadPath = uploadToDirectory(file, memberVO, "avatar");
            // 更新数据库图片地址
            memberVO.setAvatar(downLoadPath);
            return iMemberService.updateAvatar(memberVO);
        } else {
            return MessageFormat.format(CommonConst.ERROR, "非法请求");
        }
    }

	/**
	 * @author: dameizi
	 * @dateTime: 2019-05-14 20:10
	 * @description: 上传图片文件
	 * @param: [file, userId, request]
	 * @return: java.lang.String
	 */
	@RequestMapping(value = "/upload", method = RequestMethod.POST)
	public Object uploadFile(@RequestParam(value = "file", required = false) MultipartFile file, ChatMessageVO chatMessageVO) {
	    // 参数校验
        String paramResult = paramVerify(file, chatMessageVO);
        if (paramResult != null) {
            return paramResult;
        }
        if (StringUtils.isEmpty(chatMessageVO.getMessageType())){
            return MessageFormat.format(CommonConst.ERROR, "消息类型不能为空");
        }
        String sessionRedisKey = MessageFormat.format(RedisKeyConst.MEETING_SOCKETIO_ONESELF, chatMessageVO.getClientId());
        RBucket<MemberVO> clientSession = redissonClient.getBucket(sessionRedisKey);
        if (clientSession != null && clientSession.get() != null){
            MemberVO memberVO = clientSession.get();
            // 上传到目录
            String downLoadPath = uploadToDirectory(file, memberVO, "message");
            // 发送文件消息
            return sendFileMessage(chatMessageVO, memberVO, downLoadPath);
        }else{
            return MessageFormat.format(CommonConst.ERROR, "非法请求");
        }
	}

    /** 上传到目录 */
    private String uploadToDirectory(MultipartFile file, MemberVO memberVO, String type){
        // 上传到指定目录
        String path;
//        if(osName.contains("windows")){
//            path = MessageFormat.format("{0}{1}{2}", WINDOWS, memberVO.getAccount());
//        } else {
            // 消息还是头像
            if ("message".equals(type)){
                path = MessageFormat.format("{0}{1}", LINUX_MESSAGE, memberVO.getAccount());
            } else {
                path = MessageFormat.format("{0}{1}", LINUX_AVATAR, memberVO.getAccount());
            }
//        }
        // 根据用户账号新建目录
        File filePath = new File(path);
        if(!filePath.exists()){
            filePath.mkdirs();
        }
        // 文件名称是否存在
        String fileName = CommonUtil.StringFilter(file.getOriginalFilename());;
        // 下载路径
        String downLoadPath = MessageFormat.format("{0}{1}{2}", path, File.separator, fileName);
        File filePath2 = new File(downLoadPath);
        if (filePath2.exists()){
            String suffix = fileName.substring(fileName.lastIndexOf("."));
            fileName = fileName.replaceAll(suffix, 0+suffix);
            //downLoadPath = MessageFormat.format("{0}{1}{2}", path, File.separator, fileName);
        }
        File targetFile = new File(path, fileName);
        try {
            file.transferTo(targetFile);
        } catch (Exception e) {
            logger.error("发送文件失败，{}", e);
        }
        //return downLoadPath;
        // 消息还是头像
        if ("avatar".equals(type)){
            return MessageFormat.format("{0}{1}{2}{3}", FILE_DOMAIN_NAME_AVATAR, memberVO.getAccount(), File.separator, fileName);
        } else {
            return MessageFormat.format("{0}{1}{2}{3}", FILE_DOMAIN_NAME_MESSAGE, memberVO.getAccount(), File.separator, fileName);
        }
    }

	/** 发送文件消息 */
	private Object sendFileMessage(ChatMessageVO chatMessageVO, MemberVO memberVO, String downLoadPath){
        // 生成消息唯一标识，设置文件路径
        chatMessageVO.setMsgid(CommonUtil.generateOrderNo(memberVO.getMemberId()));
        chatMessageVO.setContent(downLoadPath);
        chatMessageVO.setMemberId(memberVO.getMemberId());
        if (CommonConst.DIALOG_TYPE_SINGLE.equals(chatMessageVO.getMessageType())){
            chatMessageVO.setMemberAccount(memberVO.getAccount());
            chatMessageVO.setMemberAlias(memberVO.getNickname());
            chatMessageVO.setMemberAvatar(memberVO.getAvatar());
            // 保存单聊记录
            saveFriendMessage(chatMessageVO, memberVO);
        } else if (CommonConst.DIALOG_TYPE_CLUSTER.equals(chatMessageVO.getMessageType())) {
            // 保存群聊记录
            saveGroupMessage(chatMessageVO, memberVO);
        }
        // 保存对话
        iDialogService.save(chatMessageVO);
        // 最新30条聊天记录
        return iFriendMessageService.listMessage(chatMessageVO);
    }

    /** 保存单聊记录 */
    private void saveFriendMessage(ChatMessageVO chatMessageVO, MemberVO memberVO) {
        FriendMessageVO friendMessageVO = saveInitFriendMessage(chatMessageVO, memberVO);
        friendMessageVO.setMessageType("file");
        iFriendMessageService.save(friendMessageVO);
    }

    /** 保存群聊记录 */
    private void saveGroupMessage(ChatMessageVO chatMessageVO, MemberVO memberVO) {
        GroupMessageVO groupMessageVO = saveInitGroupMessage(chatMessageVO, memberVO);
        groupMessageVO.setMessageType("file");
        iGroupMessageService.save(groupMessageVO);
    }

    /** 上传头像 */
    private String paramUploadAvatar(MultipartFile file, ChatMessageVO chatMessageVO) {
        String x = paramCheck(file, chatMessageVO);
        if (x != null) {
            return x;
        }
        return null;
    }

    /** 参数检查 */
    private String paramCheck(MultipartFile file, ChatMessageVO chatMessageVO) {
        if (file == null){
            return MessageFormat.format(CommonConst.ERROR, "文件不能为空");
        }
        if (file.getSize() > 1024*1024*10){
            return MessageFormat.format(CommonConst.ERROR, "发送文件超出限制");
        }
        if (chatMessageVO == null){
            return MessageFormat.format(CommonConst.ERROR, "参数非法");
        }
        if (StringUtils.isEmpty(chatMessageVO.getClientId())){
            return MessageFormat.format(CommonConst.ERROR, "客户端ID不能为空");
        }
        return null;
    }

    /** 发送文件 */
    private String paramVerify(MultipartFile file, ChatMessageVO chatMessageVO) {
        String x = paramCheck(file, chatMessageVO);
        if (x != null) {
            return x;
        }
        if (StringUtils.isEmpty(chatMessageVO.getTargetId())){
            return MessageFormat.format(CommonConst.ERROR, "接收者ID不能为空");
        }
        if (StringUtils.isEmpty(chatMessageVO.getAccount())){
            return MessageFormat.format(CommonConst.ERROR, "接收者账号不能为空");
        }
        if (StringUtils.isEmpty(chatMessageVO.getAvatar())){
            return MessageFormat.format(CommonConst.ERROR, "接收者头像图片不能为空");
        }
        return null;
    }

    /**
	 * @author: dameizi
	 * @dateTime: 2019-05-14 20:11
	 * @description: 下载图片文件
	 * @param: [downLoadPath, fileName, response]
	 * @return: void
	 */
	@RequestMapping(value = "/downLoad", method = RequestMethod.GET)
	public void downLoadFile(HttpServletResponse response, String downLoadPath) {
        String fileName = downLoadPath.substring(downLoadPath.lastIndexOf(File.separator) + 1);
        BufferedInputStream bis = null;
		BufferedOutputStream bos = null;
		try {
			response.setCharacterEncoding("UTF-8");
			long fileLength = new File(downLoadPath).length();
			response.setHeader("Content-disposition", "attachment; filename="+
                    new String(fileName.getBytes("gbk"),"iso-8859-1"));
			response.setContentType("application/x-download;");
			response.setHeader("Content-Length", String.valueOf(fileLength));
			bis = new BufferedInputStream(new FileInputStream(downLoadPath));
			bos = new BufferedOutputStream(response.getOutputStream());
			byte[] buff = new byte[2048];
			int bytesRead;
			while (-1 != (bytesRead = bis.read(buff, 0, buff.length))) {
				bos.write(buff, 0, bytesRead);
			}
			bos.flush();
		} catch (Exception e) {
		    logger.error("文件下载失败{}", e);
		} finally {
            try {
                if (bis != null) {
                    bis.close();
                }
                if (bos != null) {
                    bos.close();
                }
            } catch (IOException e) {
                logger.error("文件下载失败{}", e);
            }
		}
	}

}