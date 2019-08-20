package util;

import org.apache.commons.lang3.StringUtils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.regex.Pattern;

public class DateUtil{

    /**
     * 将2019-06-03T16:00:00.000Z日期格式转换为2019-06-03 16:00:00格式
     * @param oldDateStr
     * @return
     */
    public static Date transferDateFormat(String oldDateStr) {
        if (StringUtils.isBlank(oldDateStr)){
            return null;
        }
        Date date = null;
        Date date1 = null;
        String dateStr = null;
        try {
            dateStr = oldDateStr.replace("Z", " UTC");//是空格+UTC
            DateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS Z");
            date1 = df.parse(dateStr);
            SimpleDateFormat df1 = new SimpleDateFormat ("EEE MMM dd HH:mm:ss Z yyyy", Locale.UK);
            date = df1.parse(date1.toString());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }
    /**
     * Tue Aug 21 2018 00:00:00 GMT+0800 (中国标准时间) 00:00:00
     */
    public static final  String FORMAT_STRING = "yyyy-MM-dd HH:mm:ss";

    public static final  String[] REPLACE_STRING = new String[]{"GMT+0800", "GMT+08:00"};

    public static final  String SPLIT_STRING = "(中国标准时间)";

    public static Date str2Date(String dateString) {
        try {
            dateString = dateString.split(Pattern.quote(SPLIT_STRING))[0].replace(REPLACE_STRING[0], REPLACE_STRING[1]);
            SimpleDateFormat sf1 = new SimpleDateFormat("E MMM dd yyyy HH:mm:ss z", Locale.US);
            Date date = sf1.parse(dateString);
            return date;
        } catch (Exception e) {
            throw new RuntimeException("时间转化格式错误" + "[dateString=" + dateString + "]" + "[FORMAT_STRING=" + FORMAT_STRING + "]");
        }
    }

}