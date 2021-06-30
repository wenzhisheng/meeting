package com.meeting.common.util;

import com.meeting.common.constant.CommonConst;
import com.meeting.common.exception.DescribeException;
import com.meeting.common.pojo.member.MemberVO;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @dateTime 2019-03-27 11:45
 * @author: dameizi
 * @description: 日期工具类
 */
public class DateUtil {

    /** 年月日格式化字符串：yyyy-MM-dd */
    public static final String YYYY_MM_DD = "yyyy年MM月dd日";
    public static final String YYYY_MM_DD_STR = "yyyy-MM-dd";
    public static final String YYYY_MM_DD_HH_MM_SS = "yyyy-MM-dd HH:mm:ss";

    public static String dateToString2(Date time) {
        SimpleDateFormat formatter = new SimpleDateFormat(YYYY_MM_DD_HH_MM_SS);
        return formatter.format(time);
    }

    public static Date stringToDate1(String time) throws ParseException{
        // 定义将日期格式要换成的格式
        SimpleDateFormat formatter = new SimpleDateFormat(YYYY_MM_DD_HH_MM_SS);
        return formatter.parse(time);
    }

    public static Date stringToDate2(String time){
        try {
            //定义将日期格式要换成的格式
            SimpleDateFormat formatter = new SimpleDateFormat(YYYY_MM_DD_HH_MM_SS);
            return formatter.parse(time);
        }catch (ParseException ex){
            throw new DescribeException("日期格式转换错误", -1);
        }
    }

    /**
     * @author: dameizi
     * @dateTime: 2019/3/27 0027 上午 11:52
     * @description: 日期格式转换成yyyy-MM-dd的字符串格式(2018-05-06)
     * @param: [time]
     * @return: java.lang.String
     */
    public static String dateToString(Date time) {
        SimpleDateFormat formatter = new SimpleDateFormat(YYYY_MM_DD_STR);
        return formatter.format(time);
    }

    /**
     * @author: dameizi
     * @dateTime: 2019/3/27 0027 上午 11:52
     * @description: 日期格式转换成yyyy-MM-dd的字符串格式(2018-05-06)
     * @param: [time]
     * @return: java.lang.String
     */
    public static String dateToString3(Date time) {
        SimpleDateFormat formatter = new SimpleDateFormat(YYYY_MM_DD);
        return formatter.format(time);
    }

    /**
     * @author: dameizi
     * @dateTime: 2019/3/27 0027 上午 11:51
     * @description: 字符串转日期格式，如"yyyy-MM-dd"
     * @param: [time, formatterType]
     * @return: java.lang.String
     */
    public static String dateToString(Date time, String formatterType) {
        SimpleDateFormat formatter = new SimpleDateFormat(formatterType); //定义将日期格式要换成的格式
        return formatter.format(time);
    }

    /**
     * @author: dameizi
     * @dateTime: 2019/3/27 0027 上午 11:51
     * @description: 日期转字符串
     * @param: []
     * @return: java.lang.String
     */
    public static String getDate(){
        return DateUtil.dateToString(new Date());
    }

    /**
     * @author: dameizi
     * @dateTime: 2019/3/27 0027 上午 11:50
     * @description: 得到当前时间错时间戳,20180101010000111
     * @param: []
     * @return: java.lang.String
     */
    public static String getDateTime(){
        // 定义将日期格式要换成的格式
        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmssSSS");
        return formatter.format(new Date());
    }
    public static String getTimestamp(){
        // 定义将日期格式要换成的格式
        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");
        return formatter.format(new Date());
    }
    public static String getYyyyMMdd(){
        // 定义将日期格式要换成的格式
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        return formatter.format(new Date());
    }
    public static String getYyyyMMddHHmmss(){
        // 定义将日期格式要换成的格式
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return formatter.format(new Date());
    }

    /**
     * @author: dameizi
     * @dateTime: 2019/3/27 0027 上午 11:48
     * @description: 日期格式，如"yyyy-MM-dd"
     * @param: [time, formatterType]
     * @return: java.util.Date
     */
    public static Date stringToDate(String time, String formatterType){
        try {
            //定义将日期格式要换成的格式
            SimpleDateFormat formatter = new SimpleDateFormat(formatterType);
            return formatter.parse(time);
        }catch (ParseException ex){
            throw new DescribeException("日期格式转换错误", -1);
        }
    }

    /**
     * @author: dameizi
     * @dateTime: 2019/3/27 0027 上午 11:48
     * @description: 根据本月获取上一个月的分表键
     * @param: [currentYearAndMonth]
     * @return: java.lang.String
     */
    public static String generateLastMonth(String currentYearAndMonth) {
        StringBuffer lastMonth = new StringBuffer();
        Integer year = Integer.valueOf(currentYearAndMonth.substring(0, 4));
        Integer month = Integer.valueOf(currentYearAndMonth.substring(4));
        if(month == 1) { // 如果是1月份，年份减去1
            lastMonth.append(year-1).append(12);
        } else if(month <= 10) { // 如果月份减一单数，月份前补0，月份减去1
            lastMonth.append(year).append(0).append(month-1);
        } else {// 如果月份是数，月份前补0，月份减去1
            lastMonth.append(year).append(month-1);
        }
        return lastMonth.toString();
    }

    /**
     * @author: dameizi
     * @dateTime: 2019/3/27 0027 上午 11:48
     * @description: 生成最近两个月份的分表键
     * @param: []
     * @return: java.util.Set<java.lang.String>
     */
    public static Set<String> generateCurrentTableKeys() {
        // 当前月份
        String currentMonth = DateUtil.dateToString(new Date(), CommonConst.DATE_FORMAT_YYYYMM);
        Set<String> tableSet = new HashSet<String>();
        // 上个月
        tableSet.add(generateLastMonth(currentMonth));
        // 本月
        tableSet.add(currentMonth);
        return tableSet;
    }

    /**
     * @author: dameizi
     * @dateTime: 2019/3/27 0027 上午 11:47
     * @description: 查询开始月份到结束月份直接的所有月份，包含开始月份和结束月份
     * @param: [startMonth, endMonth]
     * @return: java.util.Set<java.lang.String>
     */
    public static Set<String> findAllMonth(String startMonth, String endMonth) {
        // TreeSet 有序排列
        Set<String> tableSet = new TreeSet<String>();
        if(startMonth.equals(endMonth)) { // 同一个月份
            tableSet.add(startMonth);
        } else if(Integer.valueOf(startMonth) > Integer.valueOf(endMonth)){
            throw new DescribeException("开始月份不能大于结束月份。", 0);
        } else {
            String lastMonth = endMonth;
            for(int i=0; i<12; i++) { // 最多只算12个月份
                tableSet.add(lastMonth);
                // 上个月份等于结束月份，则结束循环
                if(startMonth.equals(lastMonth)) {
                    break;
                }
                lastMonth = generateLastMonth(lastMonth);
            }
        }
        return tableSet;
    }

    /**
     * @author: dameizi
     * @dateTime: 2019/3/27 0027 上午 11:47
     * @description: 获取会员所有的分库键从会员的创建月份-当前月份
     * @param: [merchantVO]
     * @return: java.util.Set<java.lang.String>
     */
    public static Set<String> getMerchantAllTableKey(MemberVO merchantVO){
        if (merchantVO!=null && merchantVO.getGmtCreate() != null){
            return startAndEnd(merchantVO.getGmtCreate(),new Date(),null);
        }else {
            return null;
        }
    }

    /**
     * @author: dameizi
     * @dateTime: 2019/3/27 0027 上午 11:47
     * @description: 返回分页tableSet
     * @param: [startDate, endDate, tableSet]
     * @return: java.util.Set<java.lang.String>
     */
    public static Set<String> startAndEnd(Date startDate,Date endDate,Set<String> tableSet){
        if(startDate != null && endDate != null){
            tableSet = findAllMonth(dateToString(startDate, "yyyyMM"),
                    dateToString(endDate, "yyyyMM"));
        }
        return tableSet;
    }

    /**
     * @author: dameizi
     * @dateTime: 2019/3/27 0027 上午 11:46
     * @description: 将时间处理为凌晨0点0分0秒
     * @param: [time]
     * @return: java.util.Date
     */
    public static Date handleDay(Date time) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(time);
        // 初始化时 分 秒 毫秒为0
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        return calendar.getTime();
    }

    /**
     * @author: dameizi
     * @dateTime: 2019/3/27 0027 上午 11:46
     * @description: 得到两个时间差
     * @param: [endDate, nowDate]
     * @return: java.lang.Long
     */
    public static Long getDatePoor(Date endDate, Date nowDate) {
        long nm = 1000 * 60;
        // 获得两个时间的毫秒时间差异
        long diff = endDate.getTime() - nowDate.getTime();
        // 计算差多少分钟
        long min = diff / nm;
        return min;
    }

}
