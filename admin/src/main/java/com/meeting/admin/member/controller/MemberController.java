package com.meeting.admin.member.controller;

import com.meeting.common.member.service.IMemberService;
import com.meeting.common.pojo.base.PageVO;
import com.meeting.common.pojo.member.MemberVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author: dameizi
 * @description: 会员管理
 * @dateTime 2019-04-05 23:03
 * @className MemberController
 */
@RestController
@RequestMapping("/member")
@Api(value = "MemberController", tags = "Member", description = "会员管理")
public class MemberController {

    @Autowired
    private IMemberService iMemberService;

    /**
     * @author: dameizi
     * @dateTime: 2019-03-30 22:00
     * @description: 列表查询会员信息
     * @param: [merchant]
     * @return: java.lang.Object
     */
    @ResponseBody
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    @ApiOperation(value="列表查询会员",notes="无需参数")
    public Object listMember(MemberVO memberVO){
        return iMemberService.listMember(memberVO);
    }

    /**
     * @author: dameizi
     * @dateTime: 2019-03-29 16:11
     * @description: 分页查询会员信息
     * @param: [memberVO, pageVO]
     * @return: java.lang.Object
     */
    @ResponseBody
    @RequestMapping(value = "/page", method = RequestMethod.GET)
    @ApiOperation(value="分页查询会员信息",notes="分页查询会员信息")
    public Object pageMerchant(MemberVO memberVO, PageVO pageVO){
        return iMemberService.pageMember(memberVO, pageVO);
    }

    /**
     * @author: dameizi
     * @dateTime: 2019-04-02 23:12
     * @description: 会员删除
     * @param: [merchant]
     * @return: java.lang.Object
     */
    @RequestMapping(value = "/delete",method = RequestMethod.GET)
    @ApiOperation(value="根据id删除会员信息",notes="根据id删除会员信息：主键，会员id必填")
    public Object deleteMerchantById(@RequestBody MemberVO merchantVO){
        return iMemberService.delete(merchantVO);
    }

    /**
     * @author: dameizi
     * @dateTime: 2019-05-22 23:07
     * @description: 会员配置
     * @param: [merchant]
     * @return: java.lang.Object
     */
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    @ApiOperation(value="更新会员信息", notes="必填参数：ID")
    public Object updateMerchant(@RequestBody MemberVO merchant){
        return iMemberService.update(merchant);
    }


}
