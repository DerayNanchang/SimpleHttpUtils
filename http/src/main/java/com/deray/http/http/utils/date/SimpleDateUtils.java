package com.deray.http.http.utils.date;

import android.text.TextUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by Deray on 2017/9/13.
 */

public class SimpleDateUtils {
    // 24 小時制
    public final static String DF_YYYY_MM_DD_HH_MM = "yyyy-MM-dd HH:mm";
    // 12 小時制
    public final static String DF_YYYY_MM_DD_hh_MM = "yyyy-MM-dd hh:mm";
    // 精確到日
    public final static String DF_YYYY_MM_DD = "yyyy-MM-dd";
    // 精确到秒
    public final static String DF_YYYY_MM_DD_HH_MM_SS = "yyyy-MM-dd HH:mm:ss";


    // 24 小時制
    public final static String DF_YYYY_MM_DD_HH_MM2 = "yyyy/MM/dd HH:mm";
    // 12 小時制
    public final static String DF_YYYY_MM_DD_hh_MM2 = "yyyy/MM/dd hh:mm";
    // 精確到日
    public final static String DF_YYYY_MM_DD2 = "yyyy/MM/dd";
    // 精确到秒
    public final static String DF_YYYY_MM_DD_HH_MM_SS2 = "yyyy/MM/dd HH:mm:ss";

    public final static String[] WEEK_EEE = {"星期日", "星期一", "星期二", "星期三", "星期四", "星期五", "星期六"};
    public final static String[] WEEK_EE = {"周日", "周一", "周二", "周三", "周四", "周五", "周六"};

    public static final int THE_DAY_AFTER_TOMORROW = 5;
    public static final int TOMORROW = 4;
    public static final int TODAY = 3;
    public static final int YESTERDAY = 2;
    public static final int THE_DAY_BEFORE_YESTERDAY = 1;
    private static Calendar calendar;

    public static SimpleDateFormat get_MMDateFormat(String format) {
        return new SimpleDateFormat(format);
    }


    public static long getTimeMillis(String time) {
        if (!TextUtils.isEmpty(time)) {
            try {
                SimpleDateFormat dateFormat = new SimpleDateFormat(DF_YYYY_MM_DD);
                Date date = dateFormat.parse(time);
                Calendar calendar = Calendar.getInstance();
                calendar.setTime(date);
                return calendar.getTimeInMillis();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return 0;
    }

    /**
     * find calendar by date
     *
     * @param date
     * @return
     */
    public static Calendar getCalendarDate(Date date) {
        Calendar calendar = null;
        if (date != null) {
            calendar = Calendar.getInstance();
            calendar.setTime(date);
        }
        return calendar;
    }


    /**
     * 轉化成日期
     *
     * @param source
     * @param format
     * @return
     */
    public static Date getDate(String source, String format) {
        Date date = null;
        if (!TextUtils.isEmpty(source) && !TextUtils.isEmpty(format)) {
            try {
                date = new SimpleDateFormat(source)
                        .parse(format);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        return date;
    }

    /**
     * 轉化成String
     *
     * @param cource
     * @param format
     * @return
     */
    public static String getDateStr(String cource, Date format) {
        if (!TextUtils.isEmpty(cource) && format != null) {
            return new SimpleDateFormat(cource).format(format);
        }
        return null;
    }

    public static String getCurrentDateStr() {
        return SimpleDateUtils.getDateStr(DF_YYYY_MM_DD_HH_MM, new Date());
    }


    public static String getWeekDay(String[] cource, Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        int e = cal.get(Calendar.DAY_OF_WEEK) - 1;
        if (e < 0) {
            e = 0;
        }
        return cource[e];
    }

    /**
     * find current date by weekDay
     *
     * @return
     */
    public static String getWeekDay() {
        Calendar cal = Calendar.getInstance();
        cal.setTime(new Date());
        int e = cal.get(Calendar.DAY_OF_WEEK) - 1;
        if (e < 0) {
            e = 0;
        }
        return WEEK_EE[e];
    }

    /**
     * 如果在1分钟之内发布的显示"刚刚" 如果在1个小时之内发布的显示"XX分钟之前" 如果在1天之内发布的显示"XX小时之前"
     * 如果在今年的1天之外的只显示“月-日”，例如“05-03” 如果不是今年的显示“年-月-日”，例如“2014-03-11”
     *
     * @param time
     * @return
     */
    public static String getTranslateTime(String time) {
        SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
        long timeMilliseconds = 0;
        // 在主页面中设置当天时间
        Date nowTime = new Date();
        String currDate = sdf1.format(nowTime);
        long currentMilliseconds = nowTime.getTime();// 当前日期的毫秒值
        Date date = null;
        try {
            // 将给定的字符串中的日期提取出来
            date = sdf1.parse(time);
        } catch (Exception e) {
            e.printStackTrace();
            return time;
        }
        if (date != null) {
            timeMilliseconds = date.getTime();
        }

        long timeDifferent = currentMilliseconds - timeMilliseconds;


        if (timeDifferent < 60000) {// 一分钟之内

            return "刚刚";
        }
        if (timeDifferent < 3600000) {// 一小时之内
            long longMinute = timeDifferent / 60000;
            int minute = (int) (longMinute % 100);
            return minute + "分钟之前";
        }
        long l = 24 * 60 * 60 * 1000; // 每天的毫秒数
        if (timeDifferent < l) {// 小于一天
            long longHour = timeDifferent / 3600000;
            int hour = (int) (longHour % 100);
            return hour + "小时之前";
        }
        if (timeDifferent >= l) {
            String currYear = currDate.substring(0, 4);
            String year = time.substring(0, 4);
            if (!year.equals(currYear)) {
                return time.substring(0, 10);
            }
            return time.substring(5, 10);
        }
        return time;
    }





    /**
     * 根据当天时间 00:00 获取 Calendar
     * @return
     */
    public static Calendar getCalendar() {
        // 备忘录预定时间大于当天 00:00
        calendar = Calendar.getInstance();
        calendar.setTime(getTodayZeroDate());
        calendar.add(calendar.DATE, 1);
        return calendar;
    }

    /**
     * 获取当天时间 00 ：00
     *
     * @return
     */
    public static Date getTodayZeroDate() {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        return calendar.getTime();
    }

    public static Date getTodayaddOne(Calendar calendar){
        calendar.add(calendar.DATE, 1);
        return calendar.getTime();
    }


    /**
     * 获取明天
     *
     * @param calendar
     * @return
     */
    public static Date getTomorrow(Calendar calendar) {
        calendar.add(calendar.DATE, 2);
        return calendar.getTime();
    }

    /**
     * 获取未来一周
     *
     * @param calendal
     * @return
     */
    public static Date getWeek(Calendar calendal) {
        calendal.add(calendal.WEEK_OF_YEAR, 2);
        return calendal.getTime();
    }

    /**
     * 未来一个月
     * @param calendal
     * @return
     */
    public static Date getMonth(Calendar calendal) {
        calendal.add(calendal.DAY_OF_MONTH, 2);
        return calendal.getTime();
    }
}
