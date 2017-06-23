package com.superlibrary.ken.utils;

import android.annotation.SuppressLint;
import android.text.TextUtils;

import com.superlibrary.ken.common.Link;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;



public class TextUtil {
	
	@SuppressLint("SimpleDateFormat") public static SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

	/**
	 * 验证手机号合法性
	 * 
	 * @param number /^(0|86|17951)?(13[0-9]|15[012356789]|17[678]|18[0-9]|14[57])[0-9]{8}$/
	 * @return
	 */

	public static boolean verifyPhoneNumber(String number) {

		L.d("verifyPhoneNumber", "--number--"+number);
//		return Pattern.matches("^1\\d{10}", number);
		return Pattern.matches("^(0|86|17951)?(13[0-9]|15[012356789]|17[678]|18[0-9]|14[57])[0-9]{8}$", number);

	}
	
	 /**
	  * 验证邮箱的合法性
	  * @param email
	  * @return
	  */
	public static boolean verifyEmailString(String email){
		L.d("verifyEmailString", "--email--"+email);
		return Pattern.matches("^([a-z0-9A-Z]+[-|_|\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$", email);
	}
	
	/**
     * 验证身份证号是否符合规则
     * @param text 身份证号
     * @return
     */
	public static boolean personIdValidation(String text) {
        String regx = "[0-9]{17}x";
        String regx1 = "[0-9]{17}X";
        String reg1 = "[0-9]{15}";
        String regex = "[0-9]{18}";
        return text.matches(regx) || text.matches(reg1) || text.matches(regex) || text.matches(regx1);
  }
	
	
	/**
	 * 去掉小数点的00
	 * @param number
	 * @return
	 */

	public static String toInteger(String number) {

		
		if(number!= null){
			return  number.replaceAll("\\.0+", "");
		}
		
		return null;

	}

	/**
	 * url校验器
	 * @param rawUrl
	 * @return
	 */
	public static String urlBuidler(String rawUrl ){
		
		if(TextUtils.isEmpty(rawUrl)){
			return Link.URL_BOOT;
		}else if(!rawUrl.startsWith("http")){
			rawUrl = Link.URL_BOOT + rawUrl;
		}
		return rawUrl;
	}

	/**
	 * 提取数字
	 * 
	 * @param target
	 * @return
	 */
	public static String extractNumberFromStr(String target) {
		String regEx = "[^0-9]";
		Pattern p = Pattern.compile(regEx);
		Matcher m = p.matcher(target);
		String result = m.replaceAll("").trim();
		return result;
	}

	public static String extractFirstNumberFromStr(String target) {
		String regEx = "^[0-9]{1,3}";
		Pattern p = Pattern.compile(regEx);
		Matcher m = p.matcher(target);
//		Log.d("]extractFirstNumberFromStr]", m.find() + "");
		String result = m.group(0);
//		Log.d("]extractFirstNumberFromStr]", m.find() + ", result:" + result);
		return result;
	}

	/** 
     * 半角转换为全角 
     *  
     * @param input 
     * @return 
     */  
    public static String ToDBC(String input) {  
        char[] c = input.toCharArray();  
        for (int i = 0; i < c.length; i++) {  
            if (c[i] == 12288) {  
                c[i] = (char) 32;  
                continue;  
            }  
            if (c[i] > 65280 && c[i] < 65375)  
                c[i] = (char) (c[i] - 65248);  
        }  
        return new String(c);  
    }  
	
	@SuppressLint("DefaultLocale") public static String toTime(int time) {
		time /= 1000;
		int minute = time / 60;
		int hour = minute / 60;
		int second = time % 60;
		minute %= 60;
		return String.format("%02d:%02d:%02d", hour, minute, second);
	}

	public static String getNewDate() {
		return format.format(new Date());
	}

	/**
	 * 获取预约的时间
	 * @return
	 */
	@SuppressLint("SimpleDateFormat") public static String getReserveDate() {


		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm");

		return format.format(getReserveCalendar().getTime());

	}
	public static Calendar getReserveCalendar(){
		Calendar calendar = Calendar.getInstance();
		int hour = calendar.get(Calendar.HOUR_OF_DAY);
		calendar.set(Calendar.HOUR_OF_DAY, hour + 1);
		calendar.set(Calendar.MINUTE, 0);
		return calendar;
	}

	@SuppressLint("SimpleDateFormat") public static int getDateToNow(String date) {
		int day = 0;
		try {
			SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd");
			long now = new Date().getTime();
			long date_l = f.parse(date).getTime();
			if (date_l > now) {
				day = (int) ((date_l - now) / 1000 / 60 / 60 / 24);
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return day;
	}

	public static int getMinutes(String date1, String date2) {
		if (date1 == null || "".equals(date1) || date2 == null || "".equals(date2)) {
			return 0;
		}
		int day = 0;
		try {
			long date_1 = format.parse(date1).getTime();
			long date_2 = format.parse(date2).getTime();
			long time = 0;
			if (date_1 > date_2) {
				time = date_1 - date_2;
			} else {
				time = date_2 - date_1;
			}
			day = (int) (time / 1000 / 60);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return day;
	}

	/**
	 * 
	 * 将日期时间串转为时间戳 "yyyy-MM-dd HH:mm:ss"
	 * 
	 * @param timeStr
	 * @return
	 */
	public static String str2stemp(String timeStr) {
		String timeStemp = null;
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.CHINESE);
		Date d = null;

		try {
			d = sdf.parse(timeStr);
		} catch (java.text.ParseException e) {
			e.printStackTrace();
		}
		long l = d.getTime();
		String str = String.valueOf(l);
		timeStemp = str.substring(0, 10);

		return timeStemp;
	}

	/**
	 * 将日期时间串转为时间戳 "yyyy年MM月dd日HH时mm分ss秒"
	 * 
	 * @param timeStr
	 * @return
	 */
	public static String str2stemp2(String timeStr) {
		String timeStemp = null;
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd日HH时mm分ss秒", Locale.CHINESE);
		Date d = null;

		try {
			d = sdf.parse(timeStr);
		} catch (java.text.ParseException e) {
			e.printStackTrace();
		}
		long l = d.getTime();
		String str = String.valueOf(l);
		timeStemp = str.substring(0, 10);

		return timeStemp;
	}

	/**
	 * 将时间戳转为日期时间串
	 * 
	 * @param cc_time
	 * @return "yyyy-MM-dd HH:mm:ss"
	 */
	public static String stemp2str(String cc_time) {
		String re_StrTime = null;

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.CHINESE);
		// 例如：cc_time=1291778220
		long lcc_time = Long.valueOf(cc_time);
		re_StrTime = sdf.format(new Date(lcc_time * 1000L));

		return re_StrTime;

	}

	/**
	 * 将时间戳转为日期时间串
	 * 
	 * @param millsTime
	 * @return yyyy-MM-dd
	 */
	public static String formatDatestr(long millsTime) {
		String re_StrTime = null;

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.CHINESE);

		re_StrTime = sdf.format(new Date(millsTime));
		return re_StrTime;

	}

	/**
	 * 将时间戳转为中文日期时间串
	 * 
	 * @param cc_time
	 * @return "yyyy年MM月dd日HH时mm分ss秒"
	 */
	public static String stemp2str2(String cc_time) {
		String re_StrTime = null;

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd日HH时mm分ss秒", Locale.CHINESE);
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

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd日", Locale.CHINESE);
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

		SimpleDateFormat sdf = new SimpleDateFormat("MM月dd日 HH:mm", Locale.CHINESE);
		// 例如：cc_time=1291778220
		long lcc_time = Long.valueOf(cc_time);
		re_StrTime = sdf.format(new Date(lcc_time * 1000L));

		return re_StrTime;
	}

	/**
	 * 将时间戳转为中文两段日期串
	 * 
	 * @param cc_time
	 * @return "MM月dd日"
	 */
	public static String stemp2SdateStr(String cc_time) {
		String re_StrTime = null;

		SimpleDateFormat sdf = new SimpleDateFormat("MM月dd日", Locale.CHINESE);
		// 例如：cc_time=1291778220
		long lcc_time = Long.valueOf(cc_time);
		re_StrTime = sdf.format(new Date(lcc_time * 1000L));

		return re_StrTime;

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
	 * 格式化时间 如：12小时前
	 * 
	 * @param timestr
	 *            秒
	 * @param range
	 *            秒0 分1 时2 日3 月4 年5
	 * @return
	 */
	@SuppressLint("UseValueOf") public static String fmttoCN(String timestr, int range) {
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
		} else if (timediff >= 30 * 24 * 60 * 60 && timediff < 12 * 30 * 24 * 60 * 60) {
			// 小于1年，则显示‘月’
			timeText = String.valueOf((int) timediff / (30 * 24 * 60 * 60)) + "个月前";
			if (range < 4)
				return stemp2SdateStimeStr(timestr);
		} else if (timediff >= 12 * 30 * 24 * 60 * 60) {
			// 大于1年显示‘年’
			timeText = String.valueOf((int) timediff / (12 * 30 * 24 * 60 * 60)) + "年前";
			if (range < 5)
				return stemp2dateStr(timestr);
		}
		return timeText;

	}

}
