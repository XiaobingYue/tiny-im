package com.yxb.tinylove.common.util;

import java.text.SimpleDateFormat;
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
}
