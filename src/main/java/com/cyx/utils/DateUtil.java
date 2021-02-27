package com.cyx.utils;

import com.sun.tools.internal.xjc.reader.xmlschema.bindinfo.BIConversion;
import com.sun.xml.internal.rngom.digested.DPattern;
import org.springframework.core.convert.converter.Converter;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @Description
 * @date 2021/2/25
 */
public class DateUtil {
    public static String dateToString(Date date, String pattern) {
        DateFormat dateFormat = new SimpleDateFormat(pattern);
        return dateFormat.format(date);
    }

    public static Date stringToDate(String date, String pattern) throws ParseException {
        DateFormat dateFormat = new SimpleDateFormat(pattern);
        return dateFormat.parse(date);
    }
}
