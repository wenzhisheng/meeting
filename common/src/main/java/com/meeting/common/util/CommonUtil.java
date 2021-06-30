package com.meeting.common.util;

import com.meeting.common.constant.CommonConst;
import com.meeting.common.exception.DescribeException;
import com.meeting.common.pojo.base.PageVO;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.util.StringUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import java.io.*;
import java.net.InetAddress;
import java.net.URL;
import java.net.URLConnection;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

/**
 * @dateTime 2019-03-26 23:49
 * @author: dameizi
 * @description: 工具类
 */
public class CommonUtil {

    public final static Logger logger = LogManager.getLogger(CommonUtil.class);

    /** 时间差 */
    public static String difftime(Date nowTime, Date oldTime){
        long l = nowTime.getTime() - oldTime.getTime();
        //long day = l/(24*60*60*1000);
        //long hour = (l/(60*60*1000)-day*24);
        long hour = (l/(60*60*1000));
        long min = ((l/(60*1000))-hour*60);
        //long min = ((l/(60*1000))-day*24*60-hour*60);
        //long s = (l/1000-day*24*60*60-hour*60*60-min*60);
        return hour+"小时"+min+"分钟";
    }

    /**
     * @author: dameizi
     * @dateTime: 2019-05-16 14:26
     * @description: 上传文件图片
     * @param: [file]
     * @return: java.lang.String
     */
    public static String uploadFile(MultipartFile file) {
        String path = "D://log/uploadFile/picture";
        String fileName = StringFilter(file.getOriginalFilename());
        String ext = fileName.substring(fileName.lastIndexOf(".")+1);
        if(file.getSize()>1024*1024*5){
            throw new DescribeException("单个文件/图片大小不能超过5M!", 0);
        }
        String name = UUID.randomUUID().toString()+"."+ext;
        String downLoadPath = path+File.separator+name;
        // 根据会员ID新建目录
        File filePath = new File(path);
        if(!filePath.exists()){
            filePath.mkdirs();
        }
        File targetFile = new File(path,name);
        try {
            file.transferTo(targetFile);
        } catch (Exception e) {
            logger.error("上传文件/图片失败，{}", e);
        }
        return downLoadPath;
    }

    /**
     * @author: dameizi
     * @dateTime: 2019-06-29 16:27
     * @description: 获取局域网IP
     * @param: []
     * @return: java.lang.String
     */
    public static String getLocalIP() throws Exception{
        InetAddress addr = (InetAddress) InetAddress.getLocalHost();
        //获取本机IP
        return addr.getHostAddress().toString();
    }

    /**
     * @author: dameizi
     * @dateTime: 2019-05-16 14:12
     * @description: 过滤特殊字符
     * @param: [str]
     * @return: java.lang.String
     */
    public static String StringFilter(String str) throws PatternSyntaxException {
        // 清除掉所有特殊字符和空格
        String regEx="[`~!@#$%^&*()+=|{}':;',\\[\\]<>/?~！@#￥%……&*（）——+|{}【】‘；：”“’。，、？ ]";
        Pattern p = Pattern.compile(regEx);
        Matcher m = p.matcher(str);
        return m.replaceAll("").trim();
    }

    /**
     * @author: dameizi
     * @dateTime: 2019-04-12 16:50
     * @description: 银行卡必须是12-19位数字组成
     * @param: [bankNumber]
     * @return: boolean
     */
    public static boolean bankNumberVerfy(String bankNumber){
        if (bankNumber.matches("[0-9]{12,19}")){
            return true;
        }
        return false;
    }

    /**
     * @author: dameizi
     * @dateTime: 2019-04-12 16:39
     * @description: 姓名必须是2-10个中文字符组成
     * @param: [name]
     * @return: boolean
     */
    public static boolean nameVerfy(String name){
        if (name.matches("[\u4e00-\u9fa5]{2,10}")){
            return true;
        }
        return false;
    }

    /**
     * @author: dameizi
     * @dateTime: 2019-04-05 17:50
     * @description: 分页参数校验
     * @param: [pageVO]
     * @return: void
     */
    public static void pageParamVerify(PageVO pageVO){
        if (pageVO == null){
            throw new DescribeException(CommonConst.PAGE_PARAM_NOT_EMPTY, 0);
        }else{
            if (pageVO.getPageNo() == 0 && pageVO.getPageSize() == 0){
                throw new DescribeException(CommonConst.PAGE_PARAM_NOT_EMPTY, 0);
            }
        }
    }

    /**
     * @author: dameizi
     * @dateTime: 2019-04-03 19:53
     * @description: 随机生成6位数字
     * @param: []
     * @return: int
     */
    public static int buildVerifyCode(){
        return (int) ((Math.random() * 10) * 100000);
    }

    /**
     * @author: dameizi
     * @dateTime: 2019/3/27 0027 上午 0:01
     * @description: 生成32位md5码秘钥
     * @param: [password]
     * @return: java.lang.String
     */
    public static String md5Password(String password) {
        try {
            // 得到一个信息摘要器
            MessageDigest digest = MessageDigest.getInstance("md5");
            byte[] result = digest.digest(password.getBytes());
            StringBuffer buffer = new StringBuffer();
            // 把每一个byte 做一个与运算 0xff;
            for (byte b : result) {
                // 与运算，加盐
                int number = b & 0xff;
                String str = Integer.toHexString(number);
                if (str.length() == 1) {
                    buffer.append("0");
                }
                buffer.append(str);
            }
            // 标准的md5加密后的结果
            return buffer.toString();
        } catch (NoSuchAlgorithmException e) {
            logger.error("MD5加密失败：{},", e);
            return "";
        }
    }

    /**
     * @author: dameizi
     * @dateTime: 2019/3/27 0027 上午 0:02
     * @description: 16位加密
     * @param: [encryptStr]
     * @return: java.lang.String
     */
    public static String md5Password16(String encryptStr) {
        MessageDigest md5;
        try {
            md5 = MessageDigest.getInstance("MD5");
            byte[] md5Bytes = md5.digest(encryptStr.getBytes());
            StringBuffer hexValue = new StringBuffer();
            for (int i = 0; i < md5Bytes.length; i++) {
                int val = ((int) md5Bytes[i]) & 0xff;
                if (val < 16){
                    hexValue.append("0");
                }
                hexValue.append(Integer.toHexString(val));
            }
            encryptStr = hexValue.toString();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return encryptStr.substring(8,24);
    }

    /**
     * @author: dameizi
     * @dateTime: 2019-05-23 17:11
     * @description: 得到浏览器cookies
     * @param: []
     * @return: java.lang.String
     */
    public static String getToken() {
        //获取到当前线程绑定的请求对象
        HttpServletRequest req = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        String token ;
        token = req.getHeader(CommonConst.AUTHORIZATION);
        return token;
    }

    /**
     * @author: dameizi
     * @dateTime: 2019/3/27 0027 上午 0:03
     * @description: 得到nginx代理的IP
     * @param: [request]
     * @return: java.lang.String
     */
    public static String getIp(HttpServletRequest request)  {
        String ipAddress = request.getHeader("X-Real-IP");
        return  ipAddress;
    }

    /**
     * @author: dameizi
     * @dateTime: 2019/3/27 0027 上午 0:03
     * @description: 将xml格式的字符串解析成相应的是实体
     * @param: [xmlStr, cls]
     * @return: java.lang.Object
     */
    public static Object XMLStringToBean(String xmlStr, Class cls) throws JAXBException {
        JAXBContext context = JAXBContext.newInstance(cls);
        Unmarshaller unmarshaller = context.createUnmarshaller();
        return unmarshaller.unmarshal(new StringReader(xmlStr));
    }

    /**
     * @author: dameizi
     * @dateTime: 2019/3/27 0027 上午 0:03
     * @description: 将对象实体解析成xml格式的字符串
     * @param: [obj]
     * @return: java.lang.String
     */
    public static String beanToXMLString(Object obj) throws JAXBException {
        JAXBContext context = JAXBContext.newInstance(obj.getClass());
        Marshaller marshaller = context.createMarshaller();
        StringWriter stringWriter = new StringWriter();
        marshaller.marshal(obj, stringWriter);
        return stringWriter.toString();
    }

    /**
     * @author: dameizi
     * @dateTime: 2019/3/27 0027 上午 0:03
     * @description: 全局查找替换str的字符串
     * @param: [str, replaceStr, rep]
     * @return: java.lang.String
     */
    public static String replaceAllStr(String str,String replaceStr,String rep){
        //去掉空格符合换行符
        Pattern pattern = Pattern.compile(rep);
        Matcher matcher = pattern.matcher(str);
        String result = matcher.replaceAll(replaceStr);
        return result;
    }

    /**
     * @author: dameizi
     * @dateTime: 2019/3/27 0027 上午 0:04
     * @description: 参数非空校验
     * @param: [param, error]
     * @return: void
     */
    public static void paramEmptyVerify(String param, String error) {
        if(StringUtils.isEmpty(param)) {
            throw new DescribeException(error, 0);
        }
    }

    /**
     * @author: dameizi
     * @dateTime: 2019-05-23 19:59
     * @description: 判断Integer是否为空
     * @param: [param, error]
     * @return: void
     */
    public static void integerEmptyVerify(Integer param, String error) {
        if(param == null) {
            throw new DescribeException(error, 0);
        }
    }

    /**
     * @author: dameizi
     * @dateTime: 2019-07-05 15:35
     * @description: 群号（15位时间戳:190709212835492）
     * @param: []
     * @return: java.lang.String
     */
    public static String generateGroupNo(){
        SimpleDateFormat formatter = new SimpleDateFormat("yyMMddHHmmssSSS");
        return formatter.format(new Date());
    }

    /**
     * @author: dameizi
     * @dateTime: 2019/3/27 0027 上午 11:31
     * @description: 获取订单号月份
     * @param: [orderNo]
     * @return: java.lang.String
     */
    public static String getSuffix(String orderNo){
        CommonUtil.paramEmptyVerify(orderNo, "订单号不能为空");
        // 正则校验订单号是否正确（R开头：存款  W开头：取款）
        if(!orderNo.matches("^[C,D,K,R,W]\\d+-\\d{18,}$")) {
            throw new DescribeException("订单号错误", 0);
        }
        String suffix = orderNo.substring(orderNo.length()-17, orderNo.length()-11);
        return suffix;
    }

    /**
     * @author: dameizi
     * @dateTime: 2019/3/27 0027 上午 11:31
     * @description: 星号替换银行卡号
     * @param: [rankCode]
     * @return: java.lang.String
     */
    public static String rankCodeReplace(String rankCode){
        if(StringUtils.isEmpty(rankCode)) {
            return rankCode;
        }
        // 将银行卡号中间部分替换成星号
        StringBuffer stringBuffer = new StringBuffer(rankCode);
        return stringBuffer.replace(4, stringBuffer.length()-4, "********").toString();
    }

    /**
     * @author: dameizi
     * @dateTime: 2019/3/27 11:32
     * @description: 生成订单号（17位时间戳 + 2位随机数 + 3位数会员ID + 4位随机数)
     * @param: [prefix, companyId, userId]
     * @return: java.lang.String
     */
    public static String generateOrderNo(Integer userId) {
        StringBuilder orderNumber = new StringBuilder();
        orderNumber.append(DateUtil.getDateTime())
                .append(String.valueOf(Math.random()).substring(2,4))
                .append(addZeroForNum(String.valueOf(userId), 3))
                .append(String.valueOf(Math.random()).substring(2,6));
        return orderNumber.toString();
    }

    /**
     * @author: dameizi
     * @dateTime: 2019-04-08 19:39
     * @description: 生成会员编码
     * @param: []
     * @return: java.lang.String
     */
    public static String generateMerchantCode(){
        StringBuilder orderNumber = new StringBuilder();
        orderNumber.append(DateUtil.getDateTime())
                .append(String.valueOf(Math.random()).substring(2,5));
        return orderNumber.toString();
    }

    /**
     * @author: dameizi
     * @dateTime: 2019/3/27 0027 上午 11:33
     * @description: 随机类名
     * @param: []
     * @return: java.lang.String
     */
    public static String getClassName(){
        return  "com.common.util.A"+UUID.randomUUID().toString().replace("-","");
    }

    /**
     * @author: dameizi
     * @dateTime: 2019-04-07 16:14
     * @description: 没有五位数补充0代替，超过五位数截取后五位
     * @param: [str, strLength]
     * @return: java.lang.String
     */
    public static String addZeroForNum(String str, int strLength) {
        int strLen = str.length();
        if (strLen < strLength) {
            while (strLen < strLength) {
                StringBuffer sb = new StringBuffer();
                // 左补0
                sb.append("0").append(str);
                //右补0
                // sb.append(str).append("0");
                str = sb.toString();
                strLen = str.length();
            }
        }
        if(strLen > strLength) {
            str = str.substring(str.length()-5);
        }
        return str;
    }

    /**
     * @author: dameizi
     * @dateTime: 2019-03-29 15:28
     * @description: 生成随机字符串
     * @param: []
     * @return: java.lang.String
     */
    public static String generateNonceStr() {
        return UUID.randomUUID().toString().replaceAll("-", "").substring(0, 32);
    }

    /**
     * @author: dameizi
     * @dateTime: 2019/3/27 0027 上午 11:35
     * @description: 得到范围内的随机数
     * @param: [minVal, maxVal]
     * @return: java.lang.Integer
     */
    public static Integer createRandomKey(Integer minVal, Integer maxVal) {
        Integer v  = new Random().nextInt(maxVal);
        if(v <= minVal) {
            v = v +minVal;
        }
        return v;
    }

    /**
     * @author: dameizi
     * @dateTime: 2019/3/27 0027 上午 11:35
     * @description: 指定URL发送POST方法的请求
     * @param: [url, param]
     * @return: java.lang.String
     */
    public static String sendPost(String url, String param) {
        PrintWriter out = null;
        BufferedReader in = null;
        String result = "";
        try {
            URL realUrl = new URL(url);
            // 打开和URL之间的连接
            URLConnection conn = realUrl.openConnection();
            // 设置通用的请求属性
            conn.setConnectTimeout(10000);
            conn.setReadTimeout(10000);
            conn.setRequestProperty("accept", "*/*");
            conn.setRequestProperty( "connection", "Keep-Alive");
            conn.setRequestProperty("user-agent",
                    "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
            // 发送POST请求必须设置如下两行
            conn.setDoOutput(true);
            conn.setDoInput(true);
            conn.setRequestProperty("Content-Type", "application/json");
            // 获取URLConnection对象对应的输出流
            out = new PrintWriter(conn.getOutputStream());
            // 发送请求参数
            out.print(param);
            // flush输出流的缓冲
            out.flush();
            // 定义BufferedReader输入流来读取URL的响应
            in = new BufferedReader(
                    new InputStreamReader(conn.getInputStream()));
            String line;
            while ((line = in.readLine()) != null) {
                result += line;
            }
        } catch (Exception e) {
            logger.error("发送 POST 请求出现异常" + e);
        }
        //使用finally块来关闭输出流、输入流
        finally{
            try{
                if(out!=null){
                    out.close();
                }
                if(in!=null){
                    in.close();
                }
            }
            catch(IOException ex){
                logger.error("IO流异常" + ex);
            }
        }
        return result;
    }

    /**
     * 根据订单失效时间生产mq延时等级
     * */
    public static int getMqDelayLevel(int  delayTime){
        //  messageDelayLevel=1s 5s 10s 30s 1m 2m 3m 4m 5m 6m 7m 8m 9m 10m 20m 30m 1h 2h
        if(delayTime >=30 && delayTime <= 60){
            return 4;
        } else if(delayTime >=60 && delayTime < 120){
            return 5;
        } else if(delayTime >=120 && delayTime < 180){
            return 6;
        }else if(delayTime >=180 && delayTime < 240){
            return 7;
        }else if(delayTime >=240 && delayTime < 300){
            return 8;
        }else if(delayTime >=300 && delayTime < 360){
            return 9;
        }else{
            return 6;
        }
    }

    /**
     * @author: dameizi
     * @dateTime: 2019/3/27 0027 上午 11:37
     * @description: 校验手机号码
     * @param: [mobileVerification]
     * @return: void
     */
    public static void checkMobileNumber(String mobileVerification){
        String REGEX_MOBILE = "^((17[0-9])|(14[0-9])|(13[0-9])|(15[^4,\\D])|(18[0,5-9]))\\d{8}$";
        if(null == mobileVerification){
            throw new DescribeException("手机号不能为空",0);
        }
        if(!Pattern.matches(REGEX_MOBILE, mobileVerification)){
            throw new DescribeException("手机号格式不对",0);
        }
    }

    /**
     * @author: dameizi
     * @dateTime: 2019-07-09 20:32
     * @description: 检测邮箱
     * @param: [mail]
     * @return: boolean
     */
    public static boolean checkMail(String mail){
        String regex = "^\\w+@(\\w+\\.)+\\w+$";
        if(!Pattern.matches(regex, mail)){
            return false;
        }
        return true;
    }

    /**
     * @author: dameizi
     * @dateTime: 2019-07-09 20:34
     * @description: 检测手机号码
     * @param: [telephone]
     * @return: boolean
     */
    public static boolean checkTelephone(String telephone){
        String regex = "^((17[0-9])|(14[0-9])|(13[0-9])|(15[^4,\\D])|(18[0,5-9]))\\d{8}$";
        if(!Pattern.matches(regex, telephone)){
            return false;
        }
        return true;
    }

    /**
     * @author: dameizi
     * @dateTime: 2019-05-21 22:25
     * @description: 多IP判断
     * @param: [ipAllowed]
     * @return: void
     */
    public static void ipAllowed(String ipAllowed) {
        if (ipAllowed!=null && !CommonConst.NULL_CHAR.equals(ipAllowed)){
            String[] ips = ipAllowed.split("\\|");
            for (int i =0;i<ips.length;i++){
                if (!CommonUtil.ipCheck(ips[i])){
                    throw new DescribeException(CommonConst.IP_ILLEGAL, 0);
                }
            }
        }
    }

    /**
     * @author: dameizi
     * @dateTime: 2019/3/27 0027 上午 11:37
     * @description: 获取登录IP地址
     * @param: [request]
     * @return: java.lang.String
     */
    public static String getIpAddr(HttpServletRequest request) {
        String ip = request.getHeader("x-forwarded-for");
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        if (ip.equals("0:0:0:0:0:0:0:1")) {
            ip = "127.0.0.1";
        }
        return ip;
    }

    /**
     * @author: dameizi
     * @dateTime: 2019/3/27 0027 上午 11:38
     * @description: IP合法性校验
     * @param: [text]
     * @return: boolean
     */
    public static boolean ipCheck(String text) {
        if (text != null && !text.isEmpty()) {
            // 定义正则表达式
            String regex = "^(1\\d{2}|2[0-4]\\d|25[0-5]|[1-9]\\d|[1-9])\\."+
                    "(1\\d{2}|2[0-4]\\d|25[0-5]|[1-9]\\d|\\d)\\."+
                    "(1\\d{2}|2[0-4]\\d|25[0-5]|[1-9]\\d|\\d)\\."+
                    "(1\\d{2}|2[0-4]\\d|25[0-5]|[1-9]\\d|\\d)$";
            // 判断ip地址是否与正则表达式匹配
            if (text.matches(regex)) {
                // 返回判断信息
                return true;
            } else {
                // 返回判断信息
                return false;
            }
        }
        return false;
    }

    /**
     * @author: dameizi
     * @dateTime: 2019-05-29 15:09
     * @description: 简单密码
     * @param: [merchantVO]
     * @return: void
     */
    public static void passwordEasy(String passwordEasy) {
        // 六位数数字密码校验
        String regex = "^[0-9]{6}$";
        if(StringUtils.isEmpty(passwordEasy) || !passwordEasy.matches(regex)) {
            throw new DescribeException(CommonConst.PASSWORD_EASY, 0);
        }
    }

    /**
     * @author: dameizi
     * @dateTime: 2019/3/27 0027 上午 11:38
     * @description: 会员名称长度不能超过800字符
     * @param: [goodsName]
     * @return: void
     */
    public static void checkGoodsNameLength(String goodsName){
        if(goodsName.length()>800){
            throw new DescribeException("商品名称长度过长，仅限800字符", 0);
        }
    }
}
