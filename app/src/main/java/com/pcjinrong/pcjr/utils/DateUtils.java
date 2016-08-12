package com.pcjinrong.pcjr.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;


public class DateUtils {
	// ---当前日期的年，月，日，时，分，秒
	public static Calendar now = Calendar.getInstance();

	// 日期格式，年份，例如：2004，2008
	public static final String DATE_FORMAT_YYYY = "yyyy";

	// 日期格式，年份和月份，例如：200707，200808
	public static final String DATE_FORMAT_YYYYMM = "yyyyMM";

	// 日期格式，年份和月份，例如：2007-07，2008-08
	public static final String DATE_FORMAT_YYYY_MM = "yyyy-MM";

	// 日期格式，年月日，例如：20050630，20080808
	public static final String DATE_FORMAT_YYYYMMDD = "yyyyMMdd";

	// 日期格式，年月日，用横杠分开，例如：2006-12-25，2008-08-08
	public static final String DATE_FORMAT_YYYY_MM_DD = "yyyy-MM-dd";

	// 日期格式，年月日时分秒，例如：20001230120000，20080808200808
	public static final String DATE_TIME_FORMAT_YYYYMMDDHHMISS = "yyyyMMddHHmmss";

	// 日期格式，年月日时分秒，年月日用横杠分开，时分秒用冒号分开，
	// 例如：2005-05-10 23：20：00，2008-08-08 20:08:08
	public static final String DATE_TIME_FORMAT_YYYY_MM_DD_HH_MI_SS = "yyyy-MM-dd HH:mm:ss";

	// 日期格式，年月日时分秒毫秒，年月日用横杠分开，时分秒用冒号分开，
	// 例如：2005-05-10 23：20：00，2008-08-08 20:08:08 888
	public static final String DATE_TIME_FORMAT_YYYY_MM_DD_HH_MI_SS_SSS = "yyyy-MM-dd HH:mm:ss SSS";

	// -------------------------------日期类型转换-------------------------------------------
	/**
	 * 字符型日期转化util.Date型日期
	 * 
	 * @Param:p_strDate 字符型日期
	 * @param p_format
	 *            格式:"yyyy-MM-dd" / "yyyy-MM-dd hh:mm:ss"
	 * @Return:java.util.Date util.Date型日期
	 * @Throws: ParseException
	 * @Author: zhuqx
	 * @Date: 2006-10-31
	 */
	public final static Date toUtilDateFromStrDateByFormat(
			String p_strDate, String p_format) throws ParseException {
		Date l_date = null;
		java.text.DateFormat df = new SimpleDateFormat(p_format);
		if (p_strDate != null && (!"".equals(p_strDate)) && p_format != null
				&& (!"".equals(p_format))) {
			l_date = df.parse(p_strDate);
		}
		return l_date;
	}

	/**
	 * 字符型日期转化成sql.Date型日期
	 * 
	 * @param p_strDate
	 *            字符型日期
	 * @return java.sql.Date sql.Date型日期
	 * @throws ParseException
	 * @Author: zhuqx
	 * @Date: 2006-10-31
	 */
	public final static java.sql.Date toSqlDateFromStrDate(String p_strDate,
			String p_format) throws ParseException {
		java.sql.Date returnDate = null;
		java.text.DateFormat sdf = new SimpleDateFormat(p_format);
		if (p_strDate != null && (!"".equals(p_strDate))) {
			returnDate = new java.sql.Date(sdf.parse(p_strDate).getTime());
		}
		return returnDate;
	}

	/**
	 * util.Date型日期转化指定格式的字符串型日期
	 * 
	 * @param p_utilDate
	 *            Date
	 * @param p_format
	 *            String 格式1:"yyyy-MM-dd" 格式2:"yyyy-MM-dd hh:mm:ss EE"
	 *            格式3:"yyyy年MM月dd日 hh:mm:ss EE" 说明: 年-月-日 时:分:秒 星期 注意MM/mm大小写
	 * @return String
	 * @Author: zhuqx
	 * @Date: 2006-10-31
	 */
	public final static String toStrDateFromUtilDateByFormat(
			Date p_utilDate, String p_format) throws ParseException {
		String l_result = "";
		if (p_utilDate != null) {
			SimpleDateFormat sdf = new SimpleDateFormat(p_format);
			l_result = sdf.format(p_utilDate);
		}
		return l_result;
	}

	/**
	 * util.Date型日期转化转化成Calendar日期
	 * 
	 * @param p_utilDate
	 *            Date
	 * @return Calendar
	 * @Author: zhuqx
	 * @Date: 2006-10-31
	 */
	public final static Calendar toCalendarFromUtilDate(
			Date p_utilDate) {
		Calendar c = Calendar.getInstance();
		c.setTime(p_utilDate);
		return c;
	}

	/**
	 * util.Date型日期转化sql.Date(年月日)型日期
	 * 
	 * @Param: p_utilDate util.Date型日期
	 * @Return: java.sql.Date sql.Date型日期
	 * @Author: zhuqx
	 * @Date: 2006-10-31
	 */
	public final static java.sql.Date toSqlDateFromUtilDate(
			Date p_utilDate) {
		java.sql.Date returnDate = null;
		if (p_utilDate != null) {
			returnDate = new java.sql.Date(p_utilDate.getTime());
		}
		return returnDate;
	}

	/**
	 * util.Date型日期转化sql.Time(时分秒)型日期
	 * 
	 * @Param: p_utilDate util.Date型日期
	 * @Return: java.sql.Time sql.Time型日期
	 * @Author: zhuqx
	 * @Date: 2006-10-31
	 */
	public final static java.sql.Time toSqlTimeFromUtilDate(
			Date p_utilDate) {
		java.sql.Time returnDate = null;
		if (p_utilDate != null) {
			returnDate = new java.sql.Time(p_utilDate.getTime());
		}
		return returnDate;
	}

	/**
	 * util.Date型日期转化sql.Date(时分秒)型日期
	 * 
	 * @Param: p_utilDate util.Date型日期
	 * @Return: java.sql.Timestamp sql.Timestamp型日期
	 * @Author: zhuqx
	 * @Date: 2006-10-31
	 */
	public final static java.sql.Timestamp toSqlTimestampFromUtilDate(
			Date p_utilDate) {
		java.sql.Timestamp returnDate = null;
		if (p_utilDate != null) {
			returnDate = new java.sql.Timestamp(p_utilDate.getTime());
		}
		return returnDate;
	}

	/**
	 * sql.Date型日期转化util.Date型日期
	 * 
	 * @Param: sqlDate sql.Date型日期
	 * @Return: java.util.Date util.Date型日期
	 * @Author: zhuqx
	 * @Date: 2006-10-31
	 */
	public final static Date toUtilDateFromSqlDate(
			java.sql.Date p_sqlDate) {
		Date returnDate = null;
		if (p_sqlDate != null) {
			returnDate = new Date(p_sqlDate.getTime());
		}
		return returnDate;
	}

	// -----------------获取指定日期的年份，月份，日份，小时，分，秒，毫秒----------------------------
	/**
	 * 获取指定日期的年份
	 * 
	 * @param p_date
	 *            util.Date日期
	 * @return int 年份
	 * @author zhuqx
	 * @Date: 2006-10-31
	 */
	public final static int getYearOfDate(Date p_date) {
		Calendar c = Calendar.getInstance();
		c.setTime(p_date);
		return c.get(Calendar.YEAR);
	}

	/**
	 * 获取指定日期的月份
	 * 
	 * @param p_date
	 *            util.Date日期
	 * @return int 月份
	 * @author zhuqx
	 * @Date: 2006-10-31
	 */
	public final static int getMonthOfDate(Date p_date) {
		Calendar c = Calendar.getInstance();
		c.setTime(p_date);
		return c.get(Calendar.MONTH) + 1;
	}

	/**
	 * 获取指定日期的日份
	 * 
	 * @param p_date
	 *            util.Date日期
	 * @return int 日份
	 * @author zhuqx
	 * @Date: 2006-10-31
	 */
	public final static int getDayOfDate(Date p_date) {
		Calendar c = Calendar.getInstance();
		c.setTime(p_date);
		return c.get(Calendar.DAY_OF_MONTH);
	}

	/**
	 * 获取指定日期的小时
	 * 
	 * @param p_date
	 *            util.Date日期
	 * @return int 日份
	 * @author zhuqx
	 * @Date: 2006-10-31
	 */
	public final static int getHourOfDate(Date p_date) {
		Calendar c = Calendar.getInstance();
		c.setTime(p_date);
		return c.get(Calendar.HOUR_OF_DAY);
	}

	/**
	 * 获取指定日期的分钟
	 * 
	 * @param p_date
	 *            util.Date日期
	 * @return int 分钟
	 * @author zhuqx
	 * @Date: 2006-10-31
	 */
	public final static int getMinuteOfDate(Date p_date) {
		Calendar c = Calendar.getInstance();
		c.setTime(p_date);
		return c.get(Calendar.MINUTE);
	}

	/**
	 * 获取指定日期的秒钟
	 * 
	 * @param p_date
	 *            util.Date日期
	 * @return int 秒钟
	 * @author zhuqx
	 * @Date: 2006-10-31
	 */
	public final static int getSecondOfDate(Date p_date) {
		Calendar c = Calendar.getInstance();
		c.setTime(p_date);
		return c.get(Calendar.SECOND);
	}

	/**
	 * 获取指定日期的毫秒
	 * 
	 * @param p_date
	 *            util.Date日期
	 * @return long 毫秒
	 * @author zhuqx
	 * @Date: 2006-10-31
	 */
	public final static long getMillisOfDate(Date p_date) {
		Calendar c = Calendar.getInstance();
		c.setTime(p_date);
		return c.getTimeInMillis();
	}

	// -----------------获取当前/系统日期(指定日期格式)-----------------------------------------
	/**
	 * 获取指定日期格式当前日期的字符型日期
	 * 
	 * @param p_format
	 *            日期格式 格式1:"yyyy-MM-dd" 格式2:"yyyy-MM-dd hh:mm:ss EE"
	 *            格式3:"yyyy年MM月dd日 hh:mm:ss EE" 说明: 年-月-日 时:分:秒 星期 注意MM/mm大小写
	 * @return String 当前时间字符串
	 * @author zhuqx
	 * @Date: 2006-10-31
	 */
	public final static String getNowOfDateByFormat(String p_format) {
		Date d = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat(p_format);
		String dateStr = sdf.format(d);
		return dateStr;
	}

	/**
	 * 获取指定日期格式系统日期的字符型日期
	 * 
	 * @param p_format
	 *            日期格式 格式1:"yyyy-MM-dd" 格式2:"yyyy-MM-dd hh:mm:ss EE"
	 *            格式3:"yyyy年MM月dd日 hh:mm:ss EE" 说明: 年-月-日 时:分:秒 星期 注意MM/mm大小写
	 * @return String 系统时间字符串
	 * @author zhuqx
	 * @Date: 2006-10-31
	 */
	public final static String getSystemOfDateByFormat(String p_format) {
		long time = System.currentTimeMillis();
		Date d = new Date(time);
		SimpleDateFormat sdf = new SimpleDateFormat(p_format);
		String dateStr = sdf.format(d);
		return dateStr;
	}

	/**
	 * 获取字符日期一个月的天数
	 * 
	 * @param p_date
	 * @return 天数
	 * @author zhuqx
	 */
	public final static long getDayOfMonth(Date p_date) throws ParseException {
		int year = getYearOfDate(p_date);
		int month = getMonthOfDate(p_date) - 1;
		int day = getDayOfDate(p_date);
		int hour = getHourOfDate(p_date);
		int minute = getMinuteOfDate(p_date);
		int second = getSecondOfDate(p_date);
		Calendar l_calendar = new GregorianCalendar(year, month, day, hour,
				minute, second);
		return l_calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
	}

	// -----------------获取指定月份的第一天,最后一天
	// ---------------------------------------------------------------------------
	/**
	 * 获取指定月份的第一天
	 * 
	 * @param p_strDate
	 *            指定月份
	 * @param p_format
	 *            日期格式
	 * @return String 时间字符串
	 * @author zhuqx
	 * @Date: 2006-10-31
	 */
	public final static String getDateOfMonthBegin(String p_strDate,
			String p_format) throws ParseException {
		Date date = toUtilDateFromStrDateByFormat(p_strDate, p_format);
		return toStrDateFromUtilDateByFormat(date, p_format) + "-01";
	}

	/**
	 * 获取指定月份的最后一天
	 * 
	 * @param p_strDate
	 *            指定月份
	 * @param p_format
	 *            日期格式
	 * @return String 时间字符串
	 * @author zhuqx
	 * @Date: 2006-10-31
	 */
	public final static String getDateOfMonthEnd(String p_strDate,
			String p_format) throws ParseException {
		Date date = toUtilDateFromStrDateByFormat(
				getDateOfMonthBegin(p_strDate, p_format), p_format);
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(Calendar.MONTH, 1);
		calendar.add(Calendar.DAY_OF_YEAR, -1);
		return toStrDateFromUtilDateByFormat(calendar.getTime(),
				DATE_FORMAT_YYYY_MM_DD);
	}

	/**
	 * 是否开始日期在结束日期之前(不包括相等)
	 * 
	 * @param p_startDate
	 * @param p_endDate
	 * @return boolean 在结束日期前:ture;否则：false
	 * @author zhuqx
	 * @Date: 2006-10-31
	 */
	public final static boolean isStartDateBeforeEndDate(Date p_startDate,
			Date p_endDate) {
		long l_startTime = getMillisOfDate(p_startDate);
		long l_endTime = getMillisOfDate(p_endDate);
		return (l_startTime - l_endTime < (long) 0) ? true : false;
	}

	/**
	 * 获取2个字符日期的天数差
	 * 
	 * @param p_startDate
	 * @param p_endDate
	 * @return 天数差
	 * @author zhuqx
	 * @Date: 2006-10-31
	 */
	public final static long getDaysOfTowDiffDate(String p_startDate,
			String p_endDate) throws ParseException {

		Date l_startDate = toUtilDateFromStrDateByFormat(p_startDate,
				"yyyy-MM-dd");
		Date l_endDate = toUtilDateFromStrDateByFormat(p_endDate, "yyyy-MM-dd");
		long l_startTime = getMillisOfDate(l_startDate);
		long l_endTime = getMillisOfDate(l_endDate);
		long betweenDays = (long) ((l_endTime - l_startTime) / (1000 * 60 * 60 * 24));
		return betweenDays;
	}

	/**
	 * 获取2个字符日期的小时差
	 *
	 * @param p_startDate
	 * @param p_endDate
	 * @return 小时差
	 * @author zhuqx
	 * @Date: 2006-10-31
	 */
	public final static long getHoursOfTowDiffDate(Date p_startDate,
												   Date p_endDate){

		long l_startTime = getMillisOfDate(p_startDate);
		long l_endTime = getMillisOfDate(p_endDate);
		long betweenHours = (long) ((l_endTime - l_startTime) / (1000 * 60 * 60));
		return betweenHours;
	}


	/**
	 * 获取时间差 毫秒数
	 * @param p_startDate
	 * @param p_endDate
     * @return
     */
	public final static long getMinusMillisOfDate(Date p_startDate,
												   Date p_endDate){

		long l_startTime = getMillisOfDate(p_startDate);
		long l_endTime = getMillisOfDate(p_endDate);
		long between = (long) ((l_endTime - l_startTime));
		return between;
	}
	/**
	 * 获取2个字符日期的周数差
	 * 
	 * @param p_startDate
	 * @param p_endDate
	 * @return 周数差
	 * @author zhuqx
	 * @Date: 2006-10-31
	 */
	public final static long getWeeksOfTowDiffDate(String p_startDate,
			String p_endDate) throws ParseException {
		return getDaysOfTowDiffDate(p_startDate, p_endDate) / 7;
	}

	/**
	 * 获取2个字符日期的月数差
	 * 
	 * @param p_startDate
	 * @param p_endDate
	 * @return 月数差
	 * @author zhuqx
	 * @Date: 2006-10-31
	 */
	public final static long getMonthsOfTowDiffDate(String p_startDate,
			String p_endDate) throws ParseException {
		return getDaysOfTowDiffDate(p_startDate, p_endDate) / 30;
	}

	/**
	 * 获取2个字符日期的年数差
	 * 
	 * @param p_startDate
	 * @param p_endDate
	 * @return 年数差
	 * @author zhuqx
	 * @Date: 2006-10-31
	 */
	public final static long getYearsOfTowDiffDate(String p_startDate,
			String p_endDate) throws ParseException {
		return getDaysOfTowDiffDate(p_startDate, p_endDate) / 365;
	}

	/**
	 * 在给定的日期基础上添加年，月，日、时，分，秒 例如要再2006－10－21（uitl日期）添加3个月，并且格式化为yyyy-MM-dd格式，
	 * 这里调用的方式为 addDate(2006－10－21,3,Calendar.MONTH,"yyyy-MM-dd")
	 * 
	 * @param p_startDate
	 *            给定的日期
	 * @param p_count
	 *            时间的数量
	 * @param p_field
	 *            添加的域
	 * @param p_format
	 *            时间转化格式，例如：yyyy-MM-dd hh:mm:ss 或者yyyy-mm-dd等
	 * @return 添加后格式化的时间
	 * @Date: 2006-10-31
	 */
	public final static String addDate(Date p_startDate, int p_count,
			int p_field, String p_format) throws ParseException {

		// 年，月，日、时，分，秒
		int l_year = getYearOfDate(p_startDate);
		int l_month = getMonthOfDate(p_startDate) - 1;
		int l_day = getDayOfDate(p_startDate);
		int l_hour = getHourOfDate(p_startDate);
		int l_minute = getMinuteOfDate(p_startDate);
		int l_second = getSecondOfDate(p_startDate);
		Calendar l_calendar = new GregorianCalendar(l_year, l_month, l_day,
				l_hour, l_minute, l_second);
		l_calendar.add(p_field, p_count);
		return toStrDateFromUtilDateByFormat(l_calendar.getTime(), p_format);
	}

	/**
	 * 判断给定日期是不是润年
	 * 
	 * @param p_date
	 *            给定日期
	 * @return boolean 如果给定的年份为闰年，则返回 true；否则返回 false。
	 * @Date: 2006-10-31
	 */
	public final static boolean isLeapYear(Date p_date) {
		int l_year = getYearOfDate(p_date);
		GregorianCalendar l_calendar = new GregorianCalendar();
		return l_calendar.isLeapYear(l_year);
	}

	/**
	 * 字符串转换为日期
	 * 
	 * @author dingyongli
	 * @param
	 *            strDate：日期的字符串形式
	 * @param
	 *            format：转换格式
	 * @return String
	 * @throws
	 */
	public final static Date strToDate(String strDate, String format) {
		SimpleDateFormat dateFormat = new SimpleDateFormat(format);
		Date date = null;
		try {
			date = dateFormat.parse(strDate);
		} catch (ParseException e) {
			e.printStackTrace();
			// System.out.println(e.getMessage());
		}
		return date;
	}

	/**
	 * 字符串转换为日期时间
	 * 
	 * @author dingyongli
	 * @param
	 *            strDateTime：日期时间的字符串形式
	 * @param
	 *            fromat：转换格式
	 * @return String
	 * @throws
	 */
	public final static Date strToDateTime(String strDateTime, String fromat) {
		SimpleDateFormat dateTimeFormat = new SimpleDateFormat(fromat);
		Date dateTime = null;
		try {
			dateTime = dateTimeFormat.parse(strDateTime);
		} catch (ParseException e) {
			e.printStackTrace();
			// System.out.println(e.getMessage());
		}
		return dateTime;
	}

	/**
	 * 日期转换为字符串
	 * 
	 * @author dingyongli
	 * @param
	 *            date：需要转换的日期
	 * @param
	 *            format：转换格式
	 * @return String
	 * @throws
	 */
	public final static String dateToStr(Date date, String format) {
		SimpleDateFormat dateFormat = new SimpleDateFormat(format);
		return dateFormat.format(date);
	}

	/**
	 * 日期时间转换为字符串
	 * 
	 * @author dingyongli
	 * @param
	 *            date：需要转换的日期
	 * @param
	 *            format：转换格式
	 * @return String
	 * @throws
	 */
	public final static String dateTimeToStr(Date date, String format) {
		SimpleDateFormat dateTimeFormat = new SimpleDateFormat(format);
		return dateTimeFormat.format(date);
	}

	/**
	 * 得到当天的最后时间,today是字符串类型"yyyy-mm-dd", 返回是日期类型"yyyy-mm-dd 23:59:59"
	 * 
	 * @author dingyongli
	 * @param
	 *            today
	 * @return Date
	 * @throws
	 */
	public final static Date getTodayLastTime(String today) {
		String todayLastTime = today + " 23:59:59";
		return strToDateTime(todayLastTime,
				DateUtils.DATE_TIME_FORMAT_YYYY_MM_DD_HH_MI_SS);
	}

	// 取得系统当前时间,格式为yyyy-mm-dd
	public final static String getCurrentDate() {
		Calendar rightNow = Calendar.getInstance();
		int year = rightNow.get(Calendar.YEAR);
		int month = rightNow.get(Calendar.MONTH) + 1;
		int day = rightNow.get(Calendar.DATE);
		return year + "-" + month + "-" + day;
	}

	// 取得系统当前时间,格式为yyyy年mm月dd日
	public final static String getCurrentDate1() {
		Calendar rightNow = Calendar.getInstance();
		int year = rightNow.get(Calendar.YEAR);
		int month = rightNow.get(Calendar.MONTH) + 1;
		int day = rightNow.get(Calendar.DATE);
		return year + "年" + month + "月" + day + "日";
	}

	// 取得系统当前时间前n个月的相对应的一天
	public final static String getNMonthBeforeCurrentDay(int n) {
		Calendar c = Calendar.getInstance();
		c.add(Calendar.MONTH, -n);
		return "" + c.get(Calendar.YEAR) + "-" + (c.get(Calendar.MONTH) + 1)
				+ "-" + c.get(Calendar.DATE);

	}

	// 取得系统当前时间后n个月的相对应的一天
	public final static String getNMonthAfterCurrentDay(int n) {
		Calendar c = Calendar.getInstance();
		c.add(Calendar.MONTH, n);
		return "" + c.get(Calendar.YEAR) + "-" + (c.get(Calendar.MONTH) + 1)
				+ "-" + c.get(Calendar.DATE);

	}

	// 取得系统当前时间前n天,格式为yyyy-mm-dd
	public final static String getNDayBeforeCurrentDate(int n) {
		Calendar c = Calendar.getInstance();
		c.add(Calendar.DAY_OF_MONTH, -n);
		return "" + c.get(Calendar.YEAR) + "-" + (c.get(Calendar.MONTH) + 1)
				+ "-" + c.get(Calendar.DATE);
	}

	// 取得系统当前时间后n天,格式为yyyy-mm-dd
	public final static String getNDayAfterCurrentDate(int n) {
		Calendar c = Calendar.getInstance();
		c.add(Calendar.DAY_OF_MONTH, n);
		return "" + c.get(Calendar.YEAR) + "-" + (c.get(Calendar.MONTH) + 1)
				+ "-" + c.get(Calendar.DATE);
	}

	// ---------------------------------------------------------------------
	// 取过去一个时间对应的系统当年的一天
	public final static String getCurrentDateAfterPastDate(String sPastDate) {
		if (sPastDate != null && !sPastDate.equals("")) {
			Date date = switchStringToDate(sPastDate);
			Calendar c = Calendar.getInstance();
			c.setTime(date);
			int iPastYear = c.get(Calendar.YEAR);
			Calendar c1 = Calendar.getInstance();
			int iCurrentYear = c1.get(Calendar.YEAR);
			c.add(Calendar.YEAR, iCurrentYear - iPastYear);
			return "" + c.get(Calendar.YEAR) + "-"
					+ (c.get(Calendar.MONTH) + 1) + "-" + c.get(Calendar.DATE);
		} else {
			return null;
		}
	}

	// -----------------------------------------------------------------
	// 将一个日期字符串转化成日期
	public final static Date switchStringToDate(String sDate) {
		Date date = null;
		try {
			SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
			date = df.parse(sDate);

		} catch (Exception e) {
			System.out.println("日期转换失败:" + e.getMessage());
		}
		return date;
	}

	// 输入两个字符串型的日期，比较两者的大小
	public final static boolean compareTwoDateBigOrSmall(String fromDate,
			String toDate) {
		Date dateFrom = switchStringToDate(fromDate);
		Date dateTo = switchStringToDate(toDate);
		if (dateFrom.before(dateTo)) {
			return true;
		} else {
			return false;
		}
	}

	// 将一个日期字符串转化成Calendar
	public final static Calendar switchStringToCalendar(String sDate) {
		Date date = switchStringToDate(sDate);
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		return c;
	}

	// 将一个日期转化成Calendar
	public final static Calendar switchStringToCalendar(Date date) {
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		return c;
	}

	// 取得某个时间前n个月的相对应的一天
	public final static String getNMonthBeforeOneDay(String sDate, int n) {
		Calendar c = switchStringToCalendar(sDate);
		c.add(Calendar.MONTH, -n);
		return "" + c.get(Calendar.YEAR) + "-" + (c.get(Calendar.MONTH) + 1)
				+ "-" + c.get(Calendar.DATE);

	}

	// 取得某个时间后n个月的相对应的一天
	public final static String getNMonthAfterOneDay(String sDate, int n) {
		Calendar c = switchStringToCalendar(sDate);
		c.add(Calendar.MONTH, n);
		return "" + c.get(Calendar.YEAR) + "-" + (c.get(Calendar.MONTH) + 1)
				+ "-" + c.get(Calendar.DATE);

	}

	// 取得某个时间前n天,格式为yyyy-mm-dd
	public final static String getNDayBeforeOneDate(String sDate, int n) {
		Calendar c = switchStringToCalendar(sDate);
		c.add(Calendar.DAY_OF_MONTH, -n);
		return "" + c.get(Calendar.YEAR) + "-" + (c.get(Calendar.MONTH) + 1)
				+ "-" + c.get(Calendar.DATE);
	}

	// 取得某个时间后n天,格式为yyyy-mm-dd
	public final static String getNDayAfterOneDate(String sDate, int n) {
		Calendar c = switchStringToCalendar(sDate);
		c.add(Calendar.DAY_OF_MONTH, n);
		return "" + c.get(Calendar.YEAR) + "-" + (c.get(Calendar.MONTH) + 1)
				+ "-" + c.get(Calendar.DATE);
	}

	// 判断系统当前时间是不是润年
	public final static boolean isRunNian() {
		Calendar rightNow = Calendar.getInstance();
		int year = rightNow.get(Calendar.YEAR);
		if (0 == year % 4 && (year % 100 != 0 || year % 400 == 0)) {
			return true;
		} else {
			return false;
		}
	}

	// 获取当前月份的前一月份,格式为:yyyy-MM, 如2008-06前一月份为2008-05
	public final static String getPriorMonth(String curMonth) {
		String priorMonth = getNMonthBeforeOneDay(curMonth + "-01", 1);
		String year = priorMonth.substring(0, 4);
		String month = priorMonth.substring(priorMonth.indexOf("-") + 1,
				priorMonth.lastIndexOf("-"));
		month = month.length() == 1 ? "0" + month : month;
		return year + "-" + month;
	}

	// 获取当前月份的前一月份,格式为:yyyy-MM, 如2008-06前一月份为2008-05
	public final static String getNextMonth(String curMonth) {
		String priorMonth = getNMonthAfterOneDay(curMonth + "-01", 1);
		String year = priorMonth.substring(0, 4);
		String month = priorMonth.substring(priorMonth.indexOf("-") + 1,
				priorMonth.lastIndexOf("-"));
		month = month.length() == 1 ? "0" + month : month;
		return year + "-" + month;
	}

	/*
	 * 判断星期几返回加多少天可以到下个星期一(只限星期六、星期日)
	 */
	public final static int getDays(String time) {
		int day = 0;
		SimpleDateFormat formatYMD = new SimpleDateFormat("yyyy-MM-dd");
		SimpleDateFormat formatD = new SimpleDateFormat("E");
		Date d = null;
		String dayOfweek = "";
		try {
			d = formatYMD.parse(time);
			dayOfweek = formatD.format(d);
			if (dayOfweek.equals("星期六")
					|| dayOfweek.equalsIgnoreCase("SATURDAY")) {
				day = 2;
			} else if (dayOfweek.equals("星期日")
					|| dayOfweek.equalsIgnoreCase("SUNDAY")) {
				day = 1;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return day;
	}

	/*
	 * 将日期加上相应的天数
	 */
	public final static String getTheTime(String time, int num) {
		String rtnTime = "";
		SimpleDateFormat myFormatter = new SimpleDateFormat("yyyy-MM-dd");
		try {
			Date date = myFormatter.parse(time);
			long myTime = (date.getTime() / 1000) + 60 * 60 * 24 * num;
			date.setTime(myTime * 1000);
			rtnTime = myFormatter.format(date);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return rtnTime;
	}

	public final static Date getDate(String strNowDate) throws Exception {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return sdf.parse(strNowDate);
	}

	/*
	 * 取得系统日期!
	 */
	public final static String getDate() {
		SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd");
		return f.format(new Date());
	}

	/*
	 * 取得前一天的日期
	 */
	public final static String getYesterday(String time) {
		SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd");
		Date myDate = new Date();
		try {
			myDate = f.parse(time);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		long myTime = (myDate.getTime() / 1000) - 60 * 60 * 24;
		myDate.setTime(myTime * 1000);
		return f.format(myDate);
	}

	/*
	 * 取得下一天的日期
	 */
	public final static String getNextDay(String time) {
		SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd");
		Date myDate = new Date();
		try {
			myDate = f.parse(time);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		long myTime = (myDate.getTime() / 1000) + 60 * 60 * 24;
		myDate.setTime(myTime * 1000);
		return f.format(myDate);
	}

	/*
	 * 取得系统日期时间！
	 */
	public final static String getDateTime() {
		SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return f.format(new Date());
	}

	/*
	 * 取得系统日期时间！
	 */
	public final static String getDateTimeString() {
		SimpleDateFormat f = new SimpleDateFormat("yyyyMMddHHmmss");
		return f.format(new Date());
	}

	/*
	 * 取得系统日期时间！
	 */
	public final static String getTimeString() {
		SimpleDateFormat f = new SimpleDateFormat("yyyyMMdd");
		return f.format(new Date());
	}

	/*
	 * 取得系统时间！
	 */
	public final static String getTime() {
		SimpleDateFormat f = new SimpleDateFormat("HH:mm:ss");
		return f.format(new Date());
	}

	/*
	 * 取得当月月头
	 */
	public final static String getStartMonth() {
		Calendar cal = Calendar.getInstance();
		int min = cal.getActualMinimum(Calendar.DATE);
		cal.set(Calendar.DAY_OF_MONTH, min);
		SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd");
		return f.format(cal.getTime());
	}

	/*
	 * 取得当月月尾
	 */
	public final static String getEndMonth() {
		Calendar cal = Calendar.getInstance();
		int max = cal.getActualMaximum(Calendar.DATE);
		cal.set(Calendar.DAY_OF_MONTH, max);
		SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd");
		return f.format(cal.getTime());
	}

	// 得到三分钟前的时间
	public final static String getTheTime() throws Exception {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date date1 = format.parse(getDateTime());
		long Time = (date1.getTime() / 1000) - 60 * 3;
		date1.setTime(Time * 1000);
		String mydate1 = format.format(date1);
		return mydate1;
	}

	/**
	 * 得到系统年月
	 * 
	 * @return：2007-03
	 */
	public final static String yearMonth() {
		return dateToStr(new Date(), DateUtils.DATE_FORMAT_YYYY_MM);
	}

	/**
	 * 得到系统年月日
	 * 
	 * @return：2007-03
	 */
	public final static String yearMonthDay() {
		return dateToStr(new Date(), DateUtils.DATE_FORMAT_YYYY_MM_DD);
	}

	/**
	 * 取得当前日期的字符串，返回八位数的时间串值，格式如：20070320
	 * 
	 * @return
	 */
	public final static String getSysDate() {
		return dateToStr(new Date(), DateUtils.DATE_FORMAT_YYYYMMDD);
	}

	/**
	 * 得到系统日期时间
	 * 
	 * @return String (YYYY-MM-DD HH:mm:ss) 不够位数补0
	 */
	public final static String getDateTimeAll() {
		return dateToStr(new Date(),
				DateUtils.DATE_TIME_FORMAT_YYYY_MM_DD_HH_MI_SS);
	}

	public final static String gettime(Date date) {
		return dateToStr(date, DateUtils.DATE_TIME_FORMAT_YYYY_MM_DD_HH_MI_SS);
	}

	public final static String getYearMonth() {
		return DateUtils.dateToStr(new Date(), DateUtils.DATE_FORMAT_YYYYMM);
	}

	/**
	 * 取得当前时间的年分
	 */
	public final static int getSysYear() {
		Calendar calendar = null;
		calendar = Calendar.getInstance();
		Date sDate = new Date();
		calendar.setTime(sDate);
		int iYear = calendar.get(Calendar.YEAR);
		return iYear;
	}

	/**
	 * 当前时间的月分
	 */
	public final static int getSysMonth() {
		Calendar calendar = null;
		calendar = Calendar.getInstance();
		Date sDate = new Date();
		calendar.setTime(sDate);
		int iMonth = calendar.get(Calendar.MONTH) + 1;
		return iMonth;
	}

	/**
	 * 取得从数据库中读到的时间串的前10位，即精确到天，把时分秒给截掉
	 * 
	 * @param date
	 * @return
	 */
	public final static String formatDate(String date) {
		String str = "";
		if (date == null || date.equals("")) {
			str = "";
		} else if (date.length() >= 10) {
			str = date.substring(0, 10);
		} else {
			str = date;
		}
		return str;
	}

	/**
	 * 根据年月获得上个月的字段2007-05
	 * 
	 * @param year
	 * @param month
	 * @return
	 */
	public final static String getBeforMonth(String year, String month) {
		String beforstr = "";
		int intyear = 0, intmonth = 0;
		try {
			intyear = Integer.parseInt(year);
			intmonth = Integer.parseInt(month);
			if (intmonth != 1) {
				intmonth = intmonth - 1;
			} else {
				intmonth = 12;
				intyear = (intyear - 1);
			}
			if (("" + intmonth).length() != 2) {
				beforstr = (intyear + "") + "-0" + intmonth;
			} else {
				beforstr = (intyear + "") + "-" + intmonth;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return beforstr;
	}

	/**
	 * 获得下个月的年月
	 * 
	 * @param year
	 * @param month
	 * @return
	 */
	public final static String getAfterMonth(String year, String month) {
		String beforstr = "";
		int intyear = 0, intmonth = 0;
		try {
			intyear = Integer.parseInt(year);
			intmonth = Integer.parseInt(month);
			if (intmonth != 12) {
				intmonth = intmonth + 1;
			} else {
				intmonth = 1;
				intyear = (intyear + 1);
			}
			if (("" + intmonth).length() != 2) {
				beforstr = (intyear + "") + "-0" + intmonth;
			} else {
				beforstr = (intyear + "") + "-" + intmonth;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return beforstr;
	}

	/**
	 * 获得系统日期昨天的日期 格式为yyyyMMdd
	 * 
	 * @return
	 */
	public final static String getYesterday() {
		SimpleDateFormat f = new SimpleDateFormat("yyyyMMdd");
		Date myDate = new Date();
		long myTime = (myDate.getTime() / 1000) - 60 * 60 * 24;
		myDate.setTime(myTime * 1000);
		return f.format(myDate);
	}

	/**
	 * 获取指定日期的季度
	 * 
	 * @param p_date
	 *            util.Date日期
	 * @return int 季度
	 * @author LINJINHAI
	 * @date 2013-12-26 15:32:05
	 */
	public final static int getQuarterOfDate(Date p_date) {
		Calendar c = Calendar.getInstance();
		// c.setTime(p_date);
		int month = c.get(Calendar.MONTH) + 1;
		int quarter = 0;
		if (month == 1 || month == 2 || month == 3) {
			quarter = 1;
		} else if (month == 4 || month == 5 || month == 6) {
			quarter = 2;
		} else if (month == 7 || month == 8 || month == 9) {
			quarter = 3;
		} else if (month == 10 || month == 11 || month == 12) {
			quarter = 4;
		}
		return quarter;
	}

	/**
	 * 把毫秒转化成日期
	 * @param dateFormat(日期格式，例如：MM/ dd/yyyy HH:mm:ss)
	 * @param millSec(毫秒数)
	 * @return
	 */
	public static String transferLongToDate(String dateFormat,Long millSec){
		SimpleDateFormat sdf = new SimpleDateFormat(dateFormat);
		Date date= new Date(millSec);
		return sdf.format(date);
	}

	/**
	 * 测试
	 * 
	 * @param args
	 */
	public static void main(String[] args) throws Exception {
		// System.out.println(getPriorMonth("2008-01"));
		// System.out.println(getPriorMonth("2008-06"));
		// System.out.println(getPriorMonth("2008-12"));
		//
		// System.out.println(getNextMonth("2008-01"));
		// System.out.println(getNextMonth("2008-06"));
		// System.out.println(getNextMonth("2008-12"));

		// System.out.println(DateUtil.getEndMonth());

		System.out.println(DateUtils.transferLongToDate("yyyy-MM-dd",1463933863L));
	}
}