package com.meeting.common.util;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * @author: dameizi
 * @description: IP获取城市
 * @dateTime 2019-03-29 15:21
 * @className com.meeting.common.utils.IpAccessCityUtil
 */
public class IpAccessCityUtil {

    /**
     * @author: dameizi
     * @dateTime: 2019-03-29 15:25
     * @description: 获取地址
     * @param: [content, encodingString]请求的参数 格式为：name=xxx&pwd=xxx
     * @return: java.lang.String
     */
    public String getAddresses(String content, String encodingString) {
        // 这里调用pconline的接口
        String urlStr = "http://ip.taobao.com/service/getIpInfo.php";
        // 从http://whois.pconline.com.cn取得IP所在的省市区信息
        String returnStr = this.getResult(urlStr, content, encodingString);
        if (returnStr != null) {
            // 处理返回的省市区信息
            String[] temp = returnStr.split(",");
            if(temp.length<3){
                //无效IP，局域网测试
                return "无效IP或者局域网";
            }
            StringBuffer address= new StringBuffer();
            String country = (temp[2].split(":"))[1].replaceAll("\"", "");
            String region = (temp[4].split(":"))[1].replaceAll("\"", "");
            String city = (temp[5].split(":"))[1].replaceAll("\"", "");
            if(country!=null){
                address.append(country);
            }
            if(region!=null){
                address.append(",").append(region);
            }
            if(city!=null){
                address.append(",").append(city);
            }
            if(address!=null){
                return address.toString();
            }
            return "局域网";
        }
        return "null";
    }

    /**
     * @author: dameizi
     * @dateTime: 2019-03-29 15:24
     * @description: 请求的地址
     * @param: [urlStr, content, encoding]请求的参数 格式为：name=xxx&pwd=xxx
     * @return: java.lang.String
     */
    private String getResult(String urlStr, String content, String encoding) {
        URL url = null;
        HttpURLConnection connection = null;
        try {
            url = new URL(urlStr);
            // 新建连接实例
            connection = (HttpURLConnection) url.openConnection();
            // 设置连接超时时间，单位毫秒
            connection.setConnectTimeout(2000);
            // 设置读取数据超时时间，单位毫秒
            connection.setReadTimeout(2000);
            // 是否打开输出流 true|false
            connection.setDoOutput(true);
            // 是否打开输入流true|false
            connection.setDoInput(true);
            // 提交方法POST|GET
            connection.setRequestMethod("POST");
            // 是否缓存true|false
            connection.setUseCaches(false);
            // 打开连接端口
            connection.connect();
            DataOutputStream out = new DataOutputStream(connection
            // 打开输出流往对端服务器写数据
            .getOutputStream());
            // 写数据,也就是提交你的表单 name=xxx&pwd=xxx
            out.writeBytes(content);
            // 刷新
            out.flush();
            // 关闭输出流
            out.close();
            BufferedReader reader = new BufferedReader(new InputStreamReader(
                    // 往对端写完数据对端服务器返回数据
                    connection.getInputStream(), encoding));
            // 以BufferedReader流来读取
            StringBuffer buffer = new StringBuffer();
            String line = "";
            while ((line = reader.readLine()) != null) {
                buffer.append(line);
            }
            reader.close();
            return buffer.toString();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (connection != null) {
                //关闭连接
                connection.disconnect();
            }
        }
        return null;
    }

}
