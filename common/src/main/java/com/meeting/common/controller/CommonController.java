package com.meeting.common.controller;

import com.alibaba.fastjson.JSONObject;
import com.aliyuncs.CommonRequest;
import com.aliyuncs.CommonResponse;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.exceptions.ServerException;
import com.aliyuncs.http.MethodType;
import com.aliyuncs.profile.DefaultProfile;
import com.meeting.common.annotation.OtherReturn;
import com.meeting.common.areacascade.service.IAreaCascadeService;
import com.meeting.common.constant.CommonConst;
import com.meeting.common.constant.RedisKeyConst;
import com.meeting.common.exception.DescribeException;
import com.meeting.common.exception.ResultUtil;
import com.meeting.common.ipwhite.service.IIpWhiteService;
import com.meeting.common.member.dao.IMemberDao;
import com.meeting.common.member.service.IMemberService;
import com.meeting.common.pojo.ipwhite.IpWhiteVO;
import com.meeting.common.pojo.member.MemberVO;
import com.meeting.common.staticparam.gmail.GmailConfig;
import com.meeting.common.staticparam.sms.SmsConfig;
import com.meeting.common.util.BuildValidateCodeUtil;
import com.meeting.common.util.CommonUtil;
import com.meeting.common.util.DateUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import jodd.mail.Email;
import jodd.mail.MailServer;
import jodd.mail.SendMailSession;
import jodd.mail.SmtpServer;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.redisson.api.RBucket;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.text.MessageFormat;
import java.util.Date;
import java.util.UUID;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

/**
 * @author: dameizi
 * @description: ????????????
 * @dateTime 2019-03-29 13:30
 * @className com.weilaizhe.common.exception.OtherReturn
 */
@RestController
@RequestMapping("/common")
@Api(value="CommonController", tags="CommonController", description = "????????????")
public class CommonController {

    private static Logger logger = LogManager.getLogger(CommonController.class);

    @Autowired
    private RedissonClient redissonClient;
    @Autowired
    private IAreaCascadeService iAreaCascadeService;
    @Autowired
    private IMemberService iMemberService;
    @Autowired
    private IIpWhiteService iIpWhiteService;

    /**
     * @author: dameizi
     * @dateTime: 2019-07-15 12:53
     * @description: ??????IP?????????
     * @param: [request, memberVO]
     * @return: java.lang.Object
     */
    @GetMapping(value = "/isIpWhite")
    @ApiOperation(value="??????IP?????????", notes="??????????????????????????????")
    public Object isIpWhite(HttpServletRequest request, MemberVO memberVO) {
        MemberVO memberVO1 = iMemberService.login(memberVO);
        if (memberVO1 != null || 1 ==memberVO1.getRoleType()){
            IpWhiteVO ipWhiteVO = new IpWhiteVO();
            ipWhiteVO.setIp(CommonUtil.getIpAddr(request));
            if (!iIpWhiteService.isIpWhite(ipWhiteVO)){
                return MessageFormat.format(CommonConst.ERROR, "??????IP??????");
            }
        }
        return "??????IP??????";
    }

    /**
     * @author: dameizi
     * @dateTime: 2019-07-11 13:12
     * @description: ????????????
     * @param: [mail]
     * @return: java.lang.Object
     */
    @GetMapping(value = "/cascadeList")
    @ApiOperation(value="????????????", notes="????????????")
    public Object sendMail(){
        return iAreaCascadeService.cascadeList();
    }

    /**
     * @author: dameizi
     * @dateTime: 2019-07-08 13:22
     * @description: ????????????
     * @param: [mail]
     * @return: java.lang.Object
     */
    @GetMapping(value = "/sendMail")
    @ApiOperation(value="????????????", notes="?????????????????????")
    public Object sendMail(String mail, String account){
        // ????????????
        if (StringUtils.isEmpty(mail) || StringUtils.isEmpty(account) || !CommonUtil.checkMail(mail)){
            return MessageFormat.format(CommonConst.ERROR, CommonConst.PARAM_EXCEPTION);
        }
        // ????????????????????????????????????15???????????????
        RBucket<String> bucket = redissonClient.getBucket(MessageFormat.format(RedisKeyConst.MAIL_AUTH_CODE, account, mail));
        if (bucket != null && bucket.get() != null){
            return MessageFormat.format(CommonConst.ERROR, "?????????15????????????,??????????????????");
        }
        // jodd mail ????????????
        String subject = "49????????????--??????????????????";
        String content = String.valueOf(CommonUtil.buildVerifyCode());
        String htmlMessage = "<html><META http-equiv=Content-Type content=\"text/html; charset=utf-8\"><body>" +
                "<div style=\"background:#fff;border:1px solid #ccc;margin:2%;padding:0 30px\">\n" +
                "\t<div style=\"line-height:40px;height:40px\">&nbsp;</div>\n" +
                "\t<p style=\"margin:0;padding:0;font-size:14px;line-height:30px;color:#333;font-family:arial,sans-serif;font-weight:bold\">??????????????????</p>\n" +
                "\t<div style=\"line-height:20px;height:20px\">&nbsp;</div>\n" +
                "\t<p style=\"margin:0;padding:0;line-height:30px;font-size:14px;color:#333;font-family:'??????',arial,sans-serif\">????????????????????????49???????????????????????????????????????????????????????????????????????????</p>\n" +
                "\t<p style=\"margin:0;padding:0;line-height:30px;font-size:14px;color:#333;font-family:'??????',arial,sans-serif\">\n" +
                "\t\t<b style=\"font-size:18px;color:#f90\">\n" +
                "\t\t\t<span style=\"border-bottom:1px dashed #ccc;z-index:1\" t=\"7\" onclick=\"return false;\" data=\"{0}\">{0}</span>\n" +
                "\t\t</b>\n" +
                "\t\t<span style=\"margin:0;padding:0;margin-left:10px;line-height:30px;font-size:14px;color:#979797;font-family:'??????',arial,sans-serif\">(??????????????????????????????????????????1????????????????????????)</span>\n" +
                "\t</p>\n" +
                "\t<div style=\"line-height:80px;height:80px\">&nbsp;</div>\n" +
                "\t<p style=\"margin:0;padding:0;line-height:30px;font-size:14px;color:#333;font-family:'??????',arial,sans-serif\">49????????????</p>\n" +
                "\t<p style=\"margin:0;padding:0;line-height:30px;font-size:14px;color:#333;font-family:'??????',arial,sans-serif\">{1}</p>\n" +
                "\t<div style=\"line-height:20px;height:20px\">&nbsp;</div>\n" +
                "\t</div>\n" +
                "</div>" +
                "</body></html>";
        // ??????????????????
        SmtpServer smtpServer = MailServer.create()
                .ssl(GmailConfig.ssl)
                .host(GmailConfig.host)
                .auth(GmailConfig.auth, GmailConfig.password)
                .buildSmtpMailServer();
        // ??????????????????
        Email email = Email.create()
                .from(GmailConfig.auth)
                .to(mail)
                .subject(subject)
                //.textMessage("")
                .htmlMessage(MessageFormat.format(htmlMessage, content, DateUtil.dateToString3(new Date())));
        SendMailSession session = smtpServer.createSession();
        session.open();
        session.sendMail(email);
        session.close();
        // ????????????????????????????????????15???????????????
        RBucket<String> cache = redissonClient.getBucket(MessageFormat.format(RedisKeyConst.MAIL_AUTH_CODE, account, mail));
        cache.set(content);
        cache.expire(15, TimeUnit.MINUTES);
        return content;
    }

    /**
     * @author: dameizi
     * @dateTime: 2019-05-29 0:43
     * @description: ?????????????????????
     * @param: [PhoneNumbers]
     * @return: java.lang.Object
     */
    @OtherReturn
    @GetMapping(value = "/sendSms")
    @ApiOperation(value="?????????????????????", notes="???????????????Authorization???????????????")
    public Object sendSms(String PhoneNumbers, String account){
        // ????????????
        if (StringUtils.isEmpty(PhoneNumbers) || StringUtils.isEmpty(account)){
            return ResultUtil.error(-1, CommonConst.PARAM_EXCEPTION);
        }else{
            CommonUtil.checkMobileNumber(PhoneNumbers);
        }
        // ????????????????????????????????????15???????????????
        RBucket<String> bucket = redissonClient.getBucket(MessageFormat.format(RedisKeyConst.MEMBER_PHONE_SMS, account, PhoneNumbers));
        if (bucket != null && bucket.get() != null){
            return ResultUtil.error(-1,"??????15????????????,??????????????????");
        }
        // ???????????????
        JSONObject jsonObject = new JSONObject();
        String centent = String.valueOf(CommonUtil.buildVerifyCode());
        jsonObject.put(SmsConfig.code, centent);
        DefaultProfile profile = DefaultProfile.getProfile(SmsConfig.RegionId, SmsConfig.AccessKeyID, SmsConfig.AccessKeySecret);
        IAcsClient client = new DefaultAcsClient(profile);
        CommonRequest request = new CommonRequest();
        //request.setProtocol(ProtocolType.HTTPS);
        request.setMethod(MethodType.POST);
        request.setDomain(SmsConfig.Domain);
        request.setVersion(SmsConfig.Version);
        request.setAction(SmsConfig.Action);
        request.putQueryParameter(SmsConfig.REGIONID, SmsConfig.RegionId);
        request.putQueryParameter(SmsConfig.PHONENUMBERS, PhoneNumbers);
        request.putQueryParameter(SmsConfig.SIGNNAME, SmsConfig.SignName);
        request.putQueryParameter(SmsConfig.TEMPLATECODE, SmsConfig.TemplateCode);
        request.putQueryParameter(SmsConfig.TEMPLATEPARAM, JSONObject.toJSONString(jsonObject));
        CommonResponse response;
        try {
            response = client.getCommonResponse(request);
        } catch (ServerException e) {
            logger.error("??????????????????{}", e);
            return ResultUtil.error(-1, CommonConst.SMS_SENG_FAIL);
        } catch (ClientException e) {
            logger.error("??????????????????{}", e);
            return ResultUtil.error(-1, CommonConst.SMS_SENG_FAIL);
        }
        Object message = JSONObject.parseObject(response.getData()).get("Message");
        if (CommonConst.OK.equals(message)){
            // ????????????????????????????????????15???????????????
            RBucket<String> cache = redissonClient.getBucket(MessageFormat.format(RedisKeyConst.MEMBER_PHONE_SMS, account, PhoneNumbers));
            cache.set(centent);
            cache.expire(15, TimeUnit.MINUTES);
            return ResultUtil.success(message);
        }
        logger.error("????????????????????????{}", message);
        return ResultUtil.error(-1, CommonConst.SMS_SENG_FAIL);
    }

    /**
     * @author: dameizi
     * @dateTime: 2019-04-02 14:39
     * @description: ????????????
     * @param: []
     * @return: java.lang.Object
     */
    @CrossOrigin
    @OtherReturn
    @ResponseBody
    @GetMapping("/generateSecretKey")
    @ApiOperation(value="????????????",notes="???????????????Authorization")
    public Object generateSecretKey(){
        // ????????????udid???MD5????????????32????????????
        return CommonUtil.md5Password(UUID.randomUUID().toString());
    }

    /**
     * @author: dameizi
     * @dateTime: 2019-03-29 14:24
     * @description: ???????????????
     * @param: [request, response]
     * @return: VerifyCodeVO
     */
    @OtherReturn
    @ApiOperation("???????????????")
    @RequestMapping(value = "/getVerifyCode")
    public Object getVerify(HttpServletRequest request, HttpServletResponse response) {
        try {
            //??????????????????,???????????????????????????????????????
            response.setContentType("image/jpeg");
            //????????????????????????????????????????????????????????????
            response.setHeader("Pragma", "No-cache");
            response.setHeader("Cache-Control", "no-cache");
            response.setDateHeader("Expire", 0);
            response.setHeader(CommonConst.AUTHORIZATION, request.getSession().getId());
            BuildValidateCodeUtil buildValidateCode = new BuildValidateCodeUtil();
            //???????????????????????????
            return buildValidateCode.getRandcode(request, response);
        } catch (Exception e) {
            logger.error("?????????????????????", e);
        }
        return null;
    }

    /**
     * @author: dameizi
     * @dateTime: 2019-05-21 15:15
     * @description: ????????????
     * @param: [file, request]
     * @return: java.lang.String
     */
    @OtherReturn
    @ResponseBody
    @ApiOperation(value = "????????????", notes = "???????????????file, token")
    @RequestMapping(value = "/uploadFile", produces = "text/plain; charset=utf-8", method = RequestMethod.POST)
    public Object uploadFile(@RequestParam(value = "file") MultipartFile file) {
        String path = "D://log/upload/picture/";
        String fileName = CommonUtil.StringFilter(file.getOriginalFilename());
        String suffix = fileName.substring(fileName.lastIndexOf(".")+1);
        if(file.getSize()>1024*1024*5){
            throw new DescribeException("????????????/????????????????????????5M!", 0);
        }
        String name = UUID.randomUUID().toString()+"." + suffix;
        String downLoadPath = MessageFormat.format("{0}{1}{2}",path, File.separator , name);
        // ????????????ID????????????
        File filePath = new File(path);
        if(!filePath.exists()){
            filePath.mkdirs();
        }
        try {
            // ??????springboot??????????????????
            File targetFile = new File(path, name);
            file.transferTo(targetFile);
        } catch (Exception e) {
            logger.error("??????????????????::{}", e);
            throw new DescribeException("??????????????????", -1);
        }
        return downLoadPath;
    }

    @ResponseBody
    @PostMapping(value = "/uploadFile")
    public String uploadFile(@RequestParam(value = "file", required = false) MultipartFile file, int userId, HttpServletRequest request ) {
        return null;
    }

    @RequestMapping("/downLoadFile")
    public void downLoadFile(String downLoadPath, String fileName, HttpServletResponse response) {
        BufferedInputStream bis = null;
        BufferedOutputStream bos = null;
        try {
            response.setCharacterEncoding("UTF-8");
            long fileLength = new File(downLoadPath).length();
            response.setHeader("Content-disposition", "attachment; filename="+new String(fileName.getBytes("gbk"),"iso-8859-1"));
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
        } finally {
            if (bis != null) {
                try {
                    bis.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (bos != null) {
                try {
                    bos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    // ??????????????????
    public static String StringFilter(String str) throws PatternSyntaxException {
        // ????????????????????????????????????
        String regEx="[`~!@#$%^&*()+=|{}':;',\\[\\]<>/?~???@#???%??????&*????????????+|{}???????????????????????????????????? ]";
        Pattern p = Pattern.compile(regEx);
        Matcher m = p.matcher(str);
        return m.replaceAll("").trim();
    }

}
