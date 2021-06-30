package com.meeting.common.util;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.text.MessageFormat;
import java.util.ArrayList;

/**
 * @dateTime 2019-03-27 11:45
 * @author: dameizi
 * @description: Curl调用工具类
 */
@Component
public class CurlUtil {

    /** 系统类型 */
    private static String osName = System.getProperty("os.name").toLowerCase();

    private static Logger logger = LoggerFactory.getLogger(CurlUtil.class);

    /**
     * 调用CRUL命令获取支付宝个人会员订单信息
     * */
    public static  String callAlipayOrder(String cookie,String userid,String token,String startTime,String endTime) throws  Exception{
        String url = "curl -i -s -L  -H \"Host:mbillexprod.alipay.com\" -H \"Origin:https://mbillexprod.alipay.com\" -H \"Referer:https://mbillexprod.alipay.com/enterprise/tradeListQuery.htm\" -H \"User-Agent:Mozilla/5.0 (Windows NT 6.1; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/67.0.3396.99 Safari/537.36\" -b \"{0}\" -d \"queryEntrance=1&billUserId={1}&status=SUCCESS&entityFilterType=0&activeTargetSearchItem=tradeNo&startTime={3}&endTime={4}&pageSize=1&pageNum=1&sortTarget=gmtCreate&order=descend&sortType=0&_input_charset=gbk&ctoken={2}\" \"https://mbillexprod.alipay.com/enterprise/tradeListQuery.json\"";
        String str = MessageFormat.format(url,cookie,userid,token,startTime,endTime);
        ProcessBuilder pb=new ProcessBuilder(partitionCommandLine(str));
        logger.info("curl is {}",str);
        pb.redirectErrorStream(true);
        BufferedInputStream bufferedInputStream =null;
        Process p;
        try {
            p = pb.start();
            bufferedInputStream = new BufferedInputStream(p.getInputStream());
            String result = IOUtils.toString(bufferedInputStream,"utf-8");
            logger.info("alipay result is {}",result);
            p.waitFor();
            p.destroy();
            int start = result.indexOf("{");
            result = result.substring(start);
            return result;
        }finally {
            if(null != bufferedInputStream){
                bufferedInputStream.close();
            }

        }
    }

    /**
     * @author: dameizi
     * @dateTime: 2019-03-28 23:55
     * @description: 调用CURL命令访问外部接口
     * @param: [passKey, url, readType] readType 代表读取Token 还是读取外部数据
     * @return: java.lang.String
     */
    public static String read(String passKey,String url,int readType) throws Exception{
        ProcessBuilder pb=new ProcessBuilder(getCmds(passKey,url,readType));
        pb.redirectErrorStream(true);
        BufferedInputStream bufferedInputStream =null;
        Process p;
        try {
            p = pb.start();
            bufferedInputStream = new BufferedInputStream(p.getInputStream());
            String result = IOUtils.toString(bufferedInputStream,"utf-8");
            p.waitFor();
            p.destroy();
            return result;
        }finally {
            if(null != bufferedInputStream){
                bufferedInputStream.close();
            }

        }
    }

    /**
     * @author: dameizi
     * @dateTime: 2019-03-28 23:56
     * @description: post请求 获取jsonjieguo
     * @param: [param, url]
     * @return: org.json.JSONObject
     */
    public static JSONObject readJson(String param, String url) throws Exception{
        long start = System.currentTimeMillis();
        ProcessBuilder pb=new ProcessBuilder(getCmdsPOSTJson(param,url));
        pb.redirectErrorStream(true);
        BufferedInputStream bufferedRead =null;
        return getJsonObject(pb, bufferedRead);
    }

    /**
     * @author: dameizi
     * @dateTime: 2019-03-28 23:57
     * @description: post请求获取json结果
     * @param: [param, url]
     * @return: org.json.JSONArray
     */
    public static JSONArray readJsonArray(String param, String url) throws Exception{
        long start = System.currentTimeMillis();
        ProcessBuilder pb=new ProcessBuilder(getCmdsPOSTJson(param,url));
        pb.redirectErrorStream(true);
        BufferedInputStream bufferedRead =null;
        return getJsonArray(pb, bufferedRead);
    }

    public static JSONArray readJsonArrayForGET(String param, String url) throws Exception{
        long start = System.currentTimeMillis();
        ProcessBuilder pb=new ProcessBuilder(getCmdsGETJson(param,url));
        pb.redirectErrorStream(true);
        BufferedInputStream bufferedRead =null;
        return getJsonArray(pb, bufferedRead);
    }

    /**
     * @author: dameizi
     * @dateTime: 2019-03-28 23:57
     * @description: GET请求调用接口 返回json
     * @param: [param, url]
     * @return: org.json.JSONObject
     */
    public static JSONObject readGETJson(String param, String url) throws Exception{
        long start = System.currentTimeMillis();
        ProcessBuilder pb=new ProcessBuilder(getCmdsGETJson(param,url));
        pb.redirectErrorStream(true);
        BufferedInputStream bufferedRead =null;
        return getJsonObject(pb, bufferedRead);
    }

    public static String readGETXml2map(String param, String url) throws Exception{
        long start = System.currentTimeMillis();
        ProcessBuilder pb=new ProcessBuilder(getCmdsGETJson(param,url));
        pb.redirectErrorStream(true);
        BufferedInputStream bufferedRead =null;
        return getxml2map(pb, bufferedRead);
    }

    /***
     * curl -s -d 直接参数 url
     * @param param
     * @param url
     * @return
     * @throws Exception
     */
    public static JSONObject readGET(String param, String url) throws Exception{
        ProcessBuilder pb=new ProcessBuilder(getCmdsGET(param,url));
        pb.redirectErrorStream(true);
        BufferedInputStream bufferedRead =null;
        return getJsonObject(pb, bufferedRead);
    }

    /***
     * curl -s  直接参数 url
     * @param param
     * @param url
     * @return
     * @throws Exception
     */
    public static JSONObject readOtherGET(String param, String url) throws Exception{
        ProcessBuilder pb=new ProcessBuilder(getOtherCmdsGET(param,url));
        pb.redirectErrorStream(true);
        BufferedInputStream bufferedRead =null;
        return getJsonObject(pb, bufferedRead);
    }

    /**
     * @author: dameizi
     * @dateTime: 2019-03-28 23:57
     * @description: 读取包含头的POST请求
     * @param: [head, param, url]
     * @return: org.json.JSONObject
     */
    public static JSONObject readPOSTheadJson(String head,String param, String url) throws Exception{
        long start = System.currentTimeMillis();
        ProcessBuilder pb=new ProcessBuilder(getCmdsPOSTheadJson(head,param,url));
        pb.redirectErrorStream(true);
        BufferedInputStream bufferedRead =null;
        return getJsonObject(pb, bufferedRead);
    }

    /**
     * @author: dameizi
     * @dateTime: 2019-03-28 23:58
     * @description: PUT请求方式
     * @param: [head, param, url]
     * @return: org.json.JSONObject
     */
    public static JSONObject readPUTheadJson(String head,String param, String url) throws Exception{
        long start = System.currentTimeMillis();
        ProcessBuilder pb=new ProcessBuilder(getCmdsPUTheadJson(head,param,url));
        pb.redirectErrorStream(true);
        BufferedInputStream bufferedRead =null;
        return getJsonObject(pb, bufferedRead);
    }

    /**
     * @author: dameizi
     * @dateTime: 2019-03-28 23:58
     * @description: 解析调用结果返回
     * @param: [pb, bufferedRead]
     * @return: org.json.JSONObject
     */
    private static JSONObject getJsonObject(ProcessBuilder pb, BufferedInputStream bufferedRead) throws IOException, InterruptedException, JSONException {
        Process p;
        String result =null;
        try {
            p = pb.start();
            bufferedRead = new BufferedInputStream(p.getInputStream());
            result = IOUtils.toString(bufferedRead, "utf-8");
            p.waitFor();
            p.destroy();
            logger.info("获取的结果"+result);
            if(null != result && !("".equals(result))) {
                return JSONObject.parseObject(result);
            }else{
                return null;
            }
        }catch (JSONException e){
            logger.error("错误的报文:{}",result);
            throw e;
        }finally {
            if (null != bufferedRead) {
                bufferedRead.close();
            }
        }
    }

    /**
     * @author: dameizi
     * @dateTime: 2019-03-28 23:58
     * @description: 解析调用结果返回
     * @param: [pb, bufferedRead]
     * @return: org.json.JSONArray
     */
    private static JSONArray getJsonArray(ProcessBuilder pb, BufferedInputStream bufferedRead) throws IOException, InterruptedException, JSONException {
        Process p;
        String result =null;
        try {
            p = pb.start();
            bufferedRead = new BufferedInputStream(p.getInputStream());
            result = IOUtils.toString(bufferedRead, "utf-8");
            p.waitFor();
            p.destroy();
            return JSONArray.parseArray(result);
        }catch (JSONException e){
            logger.error("错误的报文:{}",result);
            throw e;
        }finally {
            if (null != bufferedRead) {
                bufferedRead.close();
            }
        }
    }

    private static String getxml2map(ProcessBuilder pb, BufferedInputStream bufferedRead) throws IOException, InterruptedException, JSONException {
        Process p;
        String result =null;
        try {
            p = pb.start();
            bufferedRead = new BufferedInputStream(p.getInputStream());
            result = IOUtils.toString(bufferedRead, "utf-8");
            p.waitFor();
            p.destroy();
            return result;
        }catch (IOException e){
            logger.error("错误的报文:{}",result);
            throw e;
        }finally {
            if (null != bufferedRead) {
                bufferedRead.close();
            }
        }
    }

    /**
     * @author: dameizi
     * @dateTime: 2019-03-28 23:58
     * @description: 区别获取windows 和linux的命令前缀
     * @param: [passKey, url, readType]
     * @return: java.lang.String[]
     */
    public static String[] getCmds(String passKey,String url,int readType) {
        String token = "token";
        if (readType == 0) {
            token = "passkey";
        }
        String cmd = "curl --compressed -s -H Accept-Encoding:gzip -H {0}:{1} {2}";
        String cmdTemp = MessageFormat.format(cmd, token, passKey, url);
        return getOsCmd(cmdTemp);
    }

    /**
     * @author: dameizi
     * @dateTime: 2019-03-28 23:58
     * @description: POST 请求获取json信息
     * @param: [param, url]
     * @return: java.lang.String[]
     */
    public static String[] getCmdsPOSTJson(String param,String url){
        String cmd = "curl -s -L -d {0} {1}";
        param ="\""+param+"\"";
        url = "\""+url+"\"";
        String cmdTemp = MessageFormat.format(cmd,param,url);
        return getOsCmd(cmdTemp);
    }

    /**
     * @author: dameizi
     * @dateTime: 2019-03-28 23:59
     * @description: POST 请求获取json信息
     * @param: [head, param, url]
     * @return: java.lang.String[]
     */
    public static String[] getCmdsPOSTheadJson(String head,String param,String url){
        String cmd = "curl -s -L {0} -d {1} {2}";
        param ="\""+param+"\"";
        url = "\""+url+"\"";
        String cmdTemp = MessageFormat.format(cmd,head,param,url);
        logger.info("getCmdsPOSTheadJson cmdTemp ==> " + cmdTemp);
        return getOsCmd(cmdTemp);
    }

    /**
     * @author: dameizi
     * @dateTime: 2019-03-28 23:59
     * @description: PUT请求
     * @param: [head, param, url]
     * @return: java.lang.String[]
     */
    public static String[] getCmdsPUTheadJson(String head,String param,String url){
        String cmd = "curl -XPUT -s -L {0} -d {1} {2}";
        param ="'"+param+"'";
        url = "'"+url+"'";
        String cmdTemp = MessageFormat.format(cmd,head,param,url);
        logger.info("getCmdsPUTheadJson cmdTemp ==> " + cmdTemp);
        return getOsCmd(cmdTemp);
    }

    /**
     * @author: dameizi
     * @dateTime: 2019-03-28 23:59
     * @description: GET请求
     * @param: [param, url]
     * @return: java.lang.String[]
     */
    public static String[] getCmdsGETJson(String param,String url){
        String cmd = "curl -s -L {0} {1}";
        String cmdTemp = MessageFormat.format(cmd,param,url);
        logger.info("getCmdsGetJson cmdTemp ==> " + cmdTemp);
        return getOsCmd(cmdTemp);
    }

    /***
     * CURL -s -d 形式请求
     * @param param
     * @param url
     * @return
     */
    public static String[] getCmdsGET(String param,String url){
        String cmd = "curl -s -d {0} {1}";
        param ="\""+param+"\"";
        url = "\""+url+"\"";
        String cmdTemp = MessageFormat.format(cmd,param,url);
        logger.info("getCmdsGET cmdTemp ==> " + cmdTemp);
        return getOsCmd(cmdTemp);
    }

    /***
     * CURL -s  形式请求
     * @param param
     * @param url
     * @return
     */
    public static String[] getOtherCmdsGET(String param,String url){
        String cmd = "curl -s {0} {1}";
        param ="\""+param+"\"";
        url = "\""+url+"\"";
        String cmdTemp = MessageFormat.format(cmd,param,url);
        logger.info("getCmdsGET cmdTemp ==> " + cmdTemp);
        return getOsCmd(cmdTemp);
    }

    /**
     * @author: dameizi
     * @dateTime: 2019-03-28 23:58
     * @description: 根据操作系统获取命令前缀
     * @param: [cmdTemp]
     * @return: java.lang.String[]
     */
    public static String[] getOsCmd(String cmdTemp) {
        if(osName.contains("windows")){
            String[] cmds = {"cmd", "/c", cmdTemp};
            return cmds;
        }else{
            return partitionCommandLine(cmdTemp);
        }
    }

    public static String[] partitionCommandLine(final String command) {
        final ArrayList<String> commands = new ArrayList<String>();
        int index = 0;
        StringBuffer buffer = new StringBuffer(command.length());
        boolean isApos = false;
        boolean isQuote = false;
        while (index < command.length()) {
            final char c = command.charAt(index);
            switch (c) {
                case ' ':
                    if (!isQuote && !isApos) {
                        final String arg = buffer.toString();
                        buffer = new StringBuffer(command.length() - index);
                        if (arg.length() > 0) {
                            commands.add(arg);
                        }
                    } else {
                        buffer.append(c);
                    }
                    break;
                case '\'':
                    if (!isQuote) {
                        isApos = !isApos;
                    } else {
                        buffer.append(c);
                    }
                    break;
                case '"':
                    if (!isApos) {
                        isQuote = !isQuote;
                    } else {
                        buffer.append(c);
                    }
                    break;
                default:
                    buffer.append(c);
            }
            index++;
        }
        if (buffer.length() > 0) {
            final String arg = buffer.toString();
            commands.add(arg);
        }
        return commands.toArray(new String[commands.size()]);
    }

}
