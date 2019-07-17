package com.ljc.socialdemo.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by zyb on 2018/5/29
 * Describe:
 */
public class TimeUtil {


    public final static String FORMAT_YEAR = "yyyy";
    public final static String FORMAT_MONTH_DAY = "MM月dd日";

    public final static String FORMAT_DATE = "yyyy-MM-dd";
    public final static String FORMAT_SIM_DATE = "yy-MM-dd";
    public final static String FORMAT_TIME = "HH:mm";
    public final static String FORMAT_MONTH_DAY_TIME = "MM月dd日 hh:mm";
    public final static String FORMAT_MONTH_DAY_TIME_TWO = "MM-dd HH:mm";
    public final static String FORMAT_DATE_TIME = "yyyy-MM-dd HH:mm";
    public final static String FORMAT_DATE1_TIME = "yyyy/MM/dd HH:mm";
    public final static String FORMAT_DATE_TIME_SECOND = "yyyy/MM/dd HH:mm:ss";
    public final static String FORMAT_DATE_TIME_SECOND_TWO = "yyyy-MM-dd HH:mm:ss";
    public final static String FORMAT_DATE_TIME_SECOND_ZONE = "yyyy-MM-dd'T'HH:mm:ss.SSSZ";
    public final static String FORMAT_MONTH = "MM";
    public final static String FORMAT_DAY = "dd";
    public final static String FORMAT_HOUR = "HH";
    public final static String FORMAT_YEAR_MONTH = "yyyy-MM";
    public final static String FORMAT_YEAR_MONTH_TIME = "yyyy年MM月dd日HH:mm";
    public final static String FORMAT_DATE1_MONTH_DAY = "MM/dd";

    private static SimpleDateFormat sdf = new SimpleDateFormat();
    private static final int YEAR = 365 * 24 * 60 * 60;// 年
    private static final int MONTH = 30 * 24 * 60 * 60;// 月
    private static final int DAY = 24 * 60 * 60;// 天
    private static final int HOUR = 60 * 60;// 小时
    private static final int MINUTE = 60;// 分钟

    /**
     * 根据时间戳获取描述性时间，如3分钟前，1天前
     *
     * @param timestamp 时间戳 单位为毫秒
     * @return 时间字符串
     */
    public static String getDescriptionTimeFromTimestamp(long timestamp) {
        long currentTime = System.currentTimeMillis();
        long timeGap = (currentTime - timestamp) / 1000;// 与现在时间相差秒数
        System.out.println("timeGap: " + timeGap);
        String timeStr = null;
        if (timeGap > YEAR) {
            timeStr = timeGap / YEAR + "年前";
        } else if (timeGap > MONTH) {
            timeStr = timeGap / MONTH + "个月前";
        } else if (timeGap > DAY) {// 1天以上
            timeStr = timeGap / DAY + "天前";
        } else if (timeGap > HOUR) {// 1小时-24小时
            timeStr = timeGap / HOUR + "小时前";
        } else if (timeGap > MINUTE) {// 1分钟-59分钟
            timeStr = timeGap / MINUTE + "分钟前";
        } else {// 1秒钟-59秒钟
            timeStr = "刚刚";
        }
        return timeStr;
    }

    /**
     * 获取当前日期的指定格式的字符串
     *
     * @param format 指定的日期时间格式，若为null或""则使用指定的格式"yyyy-MM-dd HH:MM"
     * @return
     */
    public static String getCurrentTime(String format) {
        if (format == null || format.trim().equals("")) {
            sdf.applyPattern(FORMAT_DATE_TIME);
        } else {
            sdf.applyPattern(format);
        }
        return sdf.format(new Date());
    }

    // date类型转换为String类型
    // formatType格式为yyyy-MM-dd HH:mm:ss//yyyy年MM月dd日 HH时mm分ss秒
    // data Date类型的时间
    public static String dateToString(Date data, String formatType) {
        try {
            return new SimpleDateFormat(formatType).format(data);
        } catch (Exception ex) {
            ex.printStackTrace();
            return "";
        }
    }

    // long类型转换为String类型
    // currentTime要转换的long类型的时间
    // formatType要转换的string类型的时间格式
    public static String longToString(long currentTime, String formatType) {
        String strTime = "";
        Date date = longToDate(currentTime, formatType);// long类型转成Date类型
        strTime = dateToString(date, formatType); // date类型转成String
        return strTime;
    }

    // string类型转换为date类型
    // strTime要转换的string类型的时间，formatType要转换的格式yyyy-MM-dd HH:mm:ss//yyyy年MM月dd日
    // HH时mm分ss秒，
    // strTime的时间格式必须要与formatType的时间格式相同
    public static Date stringToDate(String strTime, String formatType) {
        SimpleDateFormat formatter = new SimpleDateFormat(formatType);
        Date date = null;
        try {
            date = formatter.parse(strTime);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }

    public static Date stringToDate(String strTime) {

        strTime = strTime.replace("Z", "UTC");
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ");
        Date dt = null;
        try {
            dt = sdf.parse(strTime);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return dt;
    }

    public static long serStrToLong(String strTime) {
        return dateToLong(stringToDate(strTime));
    }

    // long转换为Date类型
    // currentTime要转换的long类型的时间
    // formatType要转换的时间格式yyyy-MM-dd HH:mm:ss//yyyy年MM月dd日 HH时mm分ss秒
    public static Date longToDate(long currentTime, String formatType) {
        Date dateOld = new Date(currentTime); // 根据long类型的毫秒数生命一个date类型的时间
        String sDateTime = dateToString(dateOld, formatType); // 把date类型的时间转换为string
        Date date = stringToDate(sDateTime, formatType); // 把String类型转换为Date类型
        return date;
    }

    // string类型转换为long类型
    // strTime要转换的String类型的时间
    // formatType时间格式
    // strTime的时间格式和formatType的时间格式必须相同
    public static long stringToLong(String strTime, String formatType) {
        Date date = stringToDate(strTime, formatType); // String类型转成date类型
        if (date == null) {
            return 0;
        } else {
            long currentTime = dateToLong(date); // date类型转成long类型
            return currentTime;
        }
    }

    // date类型转换为long类型
    // date要转换的date类型的时间
    public static long dateToLong(Date date) {
        if (null != date) {
            return date.getTime();
        } else {
            return 0;
        }
    }

    public static String getTime(long time) {
        SimpleDateFormat format = new SimpleDateFormat("yy-MM-dd HH:mm");
        return format.format(new Date(time));
    }

    public static String getFormatTime(long time) {
        SimpleDateFormat format = new SimpleDateFormat("MM-dd");
        return format.format(new Date(time));
    }

    public static String getHourAndMin(long time) {
        SimpleDateFormat format = new SimpleDateFormat("hh:mm");
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(time);

        if (calendar.get(Calendar.AM_PM) == 0) {
            return "上午 " + format.format(new Date(time));

        } else if (calendar.get(Calendar.AM_PM) == 1) {
            return "下午 " + format.format(new Date(time));
        }
        return "";
    }

    public static String getDayForYear(Date date, int temp) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        String time = format.format(date);
        if (temp >= 0) {
            return time.substring(5);
        }
        return time;
    }

    public static String getDayForWeek(Date date) {
        String week = "星期";
        final Calendar calendar = Calendar.getInstance();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        String time = format.format(date);
        try {
            calendar.setTime(format.parse(time));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        switch (calendar.get(Calendar.DAY_OF_WEEK)) {
            case 1:
                week += "天 ";
                break;
            case 2:
                week += "一 ";
                break;
            case 3:
                week += "二 ";
                break;
            case 4:
                week += "三 ";
                break;
            case 5:
                week += "四 ";
                break;
            case 6:
                week += "五 ";
                break;
            case 7:
                week += "六 ";
                break;
            default:
                break;
        }
        return week;
    }

    public static String getHour(long timesamp) {
        SimpleDateFormat format = new SimpleDateFormat("HH:mm");
        return format.format(new Date(timesamp));
    }

    public static String getChatTime(String serverDate) {
        return getChatTime(dateToLong(stringToDate(serverDate)));
    }

    public static String getChatTimeByServerTime(String serverTime) {
        String resultTime = "";
        try {
            resultTime = getChatTime(dateToLong(stringToDate(serverTime)));
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return resultTime;
    }

    public static String getChatTime(long timesamp) {
        String result = "";
        SimpleDateFormat sdf = new SimpleDateFormat("DD");
        Date today = new Date(System.currentTimeMillis());
        Date otherDay = new Date(timesamp);
        int temp = Integer.parseInt(sdf.format(today)) - Integer.parseInt(sdf.format(otherDay));

        if (otherDay.getYear() < today.getYear()) {
            temp = -1;
        }
        switch (temp) {
            case 0:
                result = getHourAndMin(timesamp);
                break;
            case 1:
                result = "昨天 " + getHourAndMin(timesamp);
                break;
            case 2:
                result = getDayForWeek(otherDay) + getHourAndMin(timesamp);
                break;
            case 3:
                result = getDayForWeek(otherDay) + getHourAndMin(timesamp);
                break;
            case 4:
                result = getDayForWeek(otherDay) + getHourAndMin(timesamp);
                break;
            case 5:
                result = getDayForWeek(otherDay) + getHourAndMin(timesamp);
                break;
            case 6:
                result = getDayForWeek(otherDay) + getHourAndMin(timesamp);
                break;
            default:
                result = getDayForYear(otherDay, -1) + " " + getHourAndMin(timesamp);
                break;
        }

        return result;
    }


}
