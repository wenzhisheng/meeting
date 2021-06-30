//package com.meeting.common.websocket;
//
//import com.meeting.common.pojo.friendmessage.FriendMessageVO;
//import com.meeting.common.pojo.member.MemberVO;
//import org.redisson.api.RBucket;
//import org.redisson.api.RedissonClient;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.ui.Model;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.ResponseBody;
//import org.springframework.web.bind.annotation.RestController;
//
//import javax.servlet.http.HttpSession;
//
//@RestController
//@RequestMapping("/friend")
//public class FriendWS {
//
//	@Autowired
//	private RedissonClient redissonClient;
//
//	//记录发送的消息
//	@ResponseBody
//	@RequestMapping(value = "/saveMessage")
//	public Object saveMessage(FriendMessageVO friendMessageVO) {
//		return null;
//	}
//
//
//	//查询历史消息页面
//	@RequestMapping(value = "/getHistoryMessagePage")
//	public Object getHistoryMessagePage(HttpSession session, Integer id, String type, Integer pageNum, Integer pageSize, Model model) {
//		MemberVO memberVO = (MemberVO) session.getAttribute("user");
//		if(pageNum==null){
//			pageNum=1;
//		}
//		if(pageSize==null){
//			pageSize=10;
//		}
//		int fromUserId = memberVO.getMemberId();
//		String str = getHistoryMsg(type,id,fromUserId,pageNum,pageSize);
//		model.addAttribute("jsonStr","["+str+"]");
//		model.addAttribute("toId",id);
//		model.addAttribute("type",type);
//		return "chatLog";
//	}
//
//	//查询历史消息接口
//	@ResponseBody
//	@RequestMapping(value = "/getHistoryMessage",produces="text/html;charset=UTF-8")
//	public String getHistoryMessage() {
//		return null;
//	}
//
//	private String getHistoryMsg(String type, Integer id, Integer fromUserId, Integer pageNum, Integer pageSize){
//		/*if(type.equals("friend")){
//			IPage<FriendMessageVO> list = friendMessageService.getHistoryMessage(fromUserId,id,pageNum,pageSize);
//			JSONArray jsonArray= JSONArray.fromObject(list);
//			return jsonArray.get(0).toString();
//		}else if(type.equals("group")){
//			IPage<GroupMessageVO> list = groupMessageService.getHistoryMessage(id,pageNum,pageSize);
//			JSONArray jsonArray= JSONArray.fromObject(list);
//			return jsonArray.get(0).toString();
//		}*/
//		return null;
//	}
//
//	@ResponseBody
//	@RequestMapping(value = "/updateOnLineStatus", produces = "text/plain; charset=utf-8")
//	public String updateOnLineStatus(int userId, String status) {
//        try{
//			RBucket<Object> bucket = redissonClient.getBucket(userId + "_status");
//			bucket.set(status);
//            return "1";
//        }catch(Exception e){
//            e.printStackTrace();
//        }
//    	return "0";
//	}
//
//	@RequestMapping(value = "/msgBoxPage", produces = "text/plain; charset=utf-8")
//	public String msgBoxPage(HttpSession session, Model model) {
//
//		return null;
//	}
//
//}
