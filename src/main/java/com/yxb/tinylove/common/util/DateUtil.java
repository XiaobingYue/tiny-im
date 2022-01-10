package com.yxb.tinylove.common.util;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Calendar;
import java.util.Date;

/**
 * @author yxb
 * @since 2022.1.5
 */
public class DateUtil {

    public static final String PATTERN_M2S = "MM月dd日 HH:mm";

    public static String format(Date date, String pattern) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
        return simpleDateFormat.format(date);
    }

    /**
     * 根据给定的日期得到过去某日的日期
     *
     * @param date 给定的日期
     * @param day  向前推的天数
     * @return 过去某日日期
     */
    public static Date getLastOnceDate(Date date, int day) {
        LocalDate localDate = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        localDate = localDate.minusDays(day);
        return Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
    }
}
