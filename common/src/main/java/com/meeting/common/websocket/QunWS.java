//package com.meeting.common.websocket;
//
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.ResponseBody;
//import org.springframework.web.bind.annotation.RestController;
//
//@RestController
//@RequestMapping("/qun")
//public class QunWS {
//
//	//获取群成员列表(只含会员名)
//	@ResponseBody
// 	@GetMapping(value = "/getSimpleMemberByGroupId")
//	public Object getSimpleMemberByGroupId(int id){
//		return null;
//	}
//
//	//获取我的群组列表(加入的)
//	@ResponseBody
//	@RequestMapping(value = "/getGroupByUserId")
//	public Object getGroupByUserId(int userId){
//		return null;
//	}
//
//	//记录发送的消息
//	@ResponseBody
//	@GetMapping(value = "/saveMessage")
//	public Object saveMessage(Integer userId, Integer groupId, String content) {
// 		return null;
//	}
//
//	//获取群成员信息 for sns
//	@ResponseBody
//	@GetMapping(value = "/getByGroupId")
//	public Object getByGroupId(int id){
//
//		return null;
//	}
//}
