package com.superlibrary.ken.utils;

import android.annotation.SuppressLint;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

/**
 * 
 * @author ceyu_wlt;
 * 
 */
@SuppressLint("SimpleDateFormat")
public class DateUtils {

	/**
	 * SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
	 * SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd"); String date =
	 * sdf1.format(new Date(1417172400*1000L)); Log.d("mdate", "date"+date);
	 * 
	 * @return
	 * @throws ParseException
	 */

	// Date或者String转化为时间戳

	// SimpleDateFormat format = new SimpleDateFormat( "yyyy-MM-dd HH:mm:ss" );
	//
	// String time="1970-01-06 11:45:55";
	//
	// Date date = format.parse(time);
	//
	// System.out.print("Format To times:"+date.getTime());

	public static long getTimeStampByTime(String time) throws ParseException {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date date = format.parse(time);
		return date.getTime();
	}

	@SuppressLint("SimpleDateFormat")
	public static String getTimeByTime(String mStr) {
		SimpleDateFormat sdf = new SimpleDateFormat("hh:mm");
		String date = sdf.format(new Date(Integer.valueOf(mStr) * 1000L));
		return date;
	}

	public static String getTimeByCpg_Last(String mStr) {
		SimpleDateFormat sdf = new SimpleDateFormat("dd日HH时mm分",Locale.SIMPLIFIED_CHINESE);
		TimeZone zone = TimeZone.getTimeZone("GMT+8");
		sdf.setTimeZone(zone);
		String date = sdf.format(new Date(Integer.valueOf(mStr) * 1000L));
		return date;
	}

	@SuppressLint("SimpleDateFormat")
	public static String getTimeByDate(String mStr) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String date = sdf.format(new Date(Integer.valueOf(mStr) * 1000L));
		return date;
	}
	@SuppressLint("SimpleDateFormat")
	public static String getTimeByDateTime(String mStr) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		String date = sdf.format(new Date(Integer.valueOf(mStr) * 1000L));
		return date;
	}

	public static String getTimeByDate2_China(String mStr) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd日");
		String date = sdf.format(new Date(Integer.valueOf(mStr) * 1000L));
		return date;
	}

	@SuppressLint("SimpleDateFormat")
	public static String getTimeByDate_China(String mStr) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy年M月d日");
		String date = sdf.format(new Date(Integer.valueOf(mStr) * 1000L));
		return date;
	}

	public static String getTimeByTime_China(String mStr) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy年M月d日 HH:mm");
		String date = sdf.format(new Date(Integer.valueOf(mStr) * 1000L));
		return date;
	}

	public static String getTimeByDay(String mStr) {
		SimpleDateFormat sdf = new SimpleDateFormat("dd");
		String date = sdf.format(new Date(Integer.valueOf(mStr) * 1000L));
		return date;
	}

	public static String getTimeByDay_E8(String mStr) {
		SimpleDateFormat sdf = new SimpleDateFormat("dd",
				Locale.SIMPLIFIED_CHINESE);
		TimeZone zone = TimeZone.getTimeZone("GMT+8");
		sdf.setTimeZone(zone);
		String date = sdf.format(new Date(Integer.valueOf(mStr) * 1000L));
		return date;
	}

	public static String getTimeByHour(String mStr) {
		SimpleDateFormat sdf = new SimpleDateFormat("HH");
		String date = sdf.format(new Date(Integer.valueOf(mStr) * 1000L));
		return date;
	}

	public static String getTimeByHour_E8(String mStr) {
		SimpleDateFormat sdf = new SimpleDateFormat("HH",
				Locale.SIMPLIFIED_CHINESE);
		TimeZone zone = TimeZone.getTimeZone("GMT+8");
		sdf.setTimeZone(zone);
		String date = sdf.format(new Date(Integer.valueOf(mStr) * 1000L));
		return date;
	}

	public static String getTimeByMin(String mStr) {
		SimpleDateFormat sdf = new SimpleDateFormat("mm");
		String date = sdf.format(new Date(Integer.valueOf(mStr) * 1000L));
		return date;
	}

	public static String getTimeByMin_E8(String mStr) {
		SimpleDateFormat sdf = new SimpleDateFormat("mm",
				Locale.SIMPLIFIED_CHINESE);
		TimeZone zone = TimeZone.getTimeZone("GMT+8");
		sdf.setTimeZone(zone);
		String date = sdf.format(new Date(Integer.valueOf(mStr) * 1000L));
		return date;
	}
	
	/**
	 * 格式化时间 如：12小时前
	 * 
	 * @param timestr
	 *            秒
	 * @param range
	 *            秒0 分1 时2 日3 月4 年5
	 * @return
	 */
	public static String fmttoCN(String timestr, int range) {
		String timeText = null;
		if (null == timestr || "".equals(timestr)) {
			return "";
		}
		long time = new Long(timestr);
		Date dt = new Date();
		long nowSec = dt.getTime() / 1000;
		long timediff = nowSec - time;

		if (timediff < 60) {
			// 小与1分钟显示 ‘刚刚’
			timeText = "刚刚";
			if (range == 0)
				return timeText;
		} else if (timediff >= 60 && timediff < 60 * 60) {
			// 小于1小时 显示‘分钟’
			timeText = String.valueOf((int) timediff / 60) + "分钟前";
			if (range < 1)
				return stemp2timeStr(timestr);
			;
		} else if (timediff >= 60 * 60 && timediff < 24 * 60 * 60) {
			// 小于24小时，则显示‘时’
			timeText = String.valueOf((int) timediff / (60 * 60)) + "小时前";
			if (range < 2)
				return stemp2StimeStr(timestr);
			;
		} else if (timediff >= 24 * 60 * 60 && timediff < 30 * 24 * 60 * 60) {
			// 小于1个月，则显示‘天’
			timeText = String.valueOf((int) timediff / (24 * 60 * 60)) + "天前";
			if (range < 3)
				return stemp2SdateStimeStr(timestr);
		} else if (timediff >= 30 * 24 * 60 * 60
				&& timediff < 12 * 30 * 24 * 60 * 60) {
			// 小于1年，则显示‘月’
			timeText = String.valueOf((int) timediff / (30 * 24 * 60 * 60))
					+ "个月前";
			if (range < 4)
				return stemp2SdateStimeStr(timestr);
		} else if (timediff >= 12 * 30 * 24 * 60 * 60) {
			// 大于1年显示‘年’
			timeText = String
					.valueOf((int) timediff / (12 * 30 * 24 * 60 * 60)) + "年前";
			if (range < 5)
				return stemp2dateStr(timestr);
		}

		return timeText;

	}
	
	/**
	 * 将时间戳转为三段时间串
	 * 
	 * @param cc_time
	 * @return "HH:mm:ss"
	 */
	public static String stemp2timeStr(String cc_time) {
		String re_StrTime = null;

		SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss", Locale.CHINESE);
		// 例如：cc_time=1291778220
		long lcc_time = Long.valueOf(cc_time);
		re_StrTime = sdf.format(new Date(lcc_time * 1000L));

		return re_StrTime;

	}
	
	/**
	 * 将时间戳转为时分时间串
	 * 
	 * @param cc_time
	 * @return "HH:mm:ss"
	 */
	public static String stemp2StimeStr(String cc_time) {
		String re_StrTime = null;

		SimpleDateFormat sdf = new SimpleDateFormat("HH:mm", Locale.CHINESE);
		// 例如：cc_time=1291778220
		long lcc_time = Long.valueOf(cc_time);
		re_StrTime = sdf.format(new Date(lcc_time * 1000L));

		return re_StrTime;

	}
	

	/**
	 * 将时间戳转为中文四段日期时间串
	 * 
	 * @param cc_time
	 * @return "MM月dd日 HH:mm"
	 */
	public static String stemp2SdateStimeStr(String cc_time) {
		String re_StrTime = null;

		SimpleDateFormat sdf = new SimpleDateFormat("MM月dd日 HH:mm",
				Locale.CHINESE);
		// 例如：cc_time=1291778220
		long lcc_time = Long.valueOf(cc_time);
		re_StrTime = sdf.format(new Date(lcc_time * 1000L));

		return re_StrTime;

	}
	
	
	/**
	 * 将时间戳转为中文三段日期串
	 * 
	 * @param cc_time
	 * @return "yyyy年MM月dd日"
	 */
	public static String stemp2dateStr(String cc_time) {
		String re_StrTime = null;

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd日",
				Locale.CHINESE);
		// 例如：cc_time=1291778220
		long lcc_time = Long.valueOf(cc_time);
		re_StrTime = sdf.format(new Date(lcc_time * 1000L));

		return re_StrTime;

	}
	
	/**
     * 根据时间返回Calendar
     * 
     * @param dateStr
     * @return
     */
    public static Calendar calendarConver(String dateStr) {
//    	Log.e("jsonData", "time:"+dateStr);
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Calendar calendar = Calendar.getInstance();
        try {
            Date date = dateFormat.parse(dateStr);
            calendar.setTime(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return calendar;
    }


	public static String getCurrentTime(Date date){
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
		return df.format(date);// new Date()为获取当前系统时间
	}

	public static String getCurrentYue(Date date){
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM");//设置日期格式
		return df.format(date);// new Date()为获取当前系统时间
	}

	public static String getCurrentYue_1(Date date){
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM");//设置日期格式
		return df.format(getMonth_1(date));// new Date()为获取当前系统时间
	}
	public static String getCurrentYue_2(Date date){
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM");//设置日期格式
		return df.format(getMonth_2(date));// new Date()为获取当前系统时间
	}


	/**
	 * 获取上个月date
	 * @param date
	 * @return
	 */
	private static Date getMonth_1(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(Calendar.MONTH, -1);
		int index = calendar.get(Calendar.DAY_OF_MONTH);
		calendar.add(Calendar.DATE, (1 - index));
		return calendar.getTime();
	}

	/**
	 * 获取上上个月date
	 * @param date
	 * @return
	 */
	private static Date getMonth_2(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(Calendar.MONTH, -2);
		int index = calendar.get(Calendar.DAY_OF_MONTH);
		calendar.add(Calendar.DATE, (2 - index));
		return calendar.getTime();
	}


}
