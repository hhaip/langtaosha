package cn.lts.common.util;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.Date;

/**
 * author: huanghaiping
 * created on 16-7-13.
 */
public class TimeUtils {

	public final static int dayHours = 24;

	public final static int KTV_END_TIME_STANDARD = 7;

	/**返回给定localDateTime,年月日时分秒14位不含符号的时间串
	 * 为空则返回当前时间的串
	 * */
	public static String fullDateStr(LocalDateTime localDateTime) {
		if (localDateTime == null) {
			localDateTime = LocalDateTime.now();
		}
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");
		return localDateTime.format(formatter);
	}

	/**返回给定localDateTime,年月日8位不含符号的时间串
	 * 为空则返回当前时间的串
	 * */
	public static String getyyyyMMdd(LocalDateTime localDateTime) {
		if (localDateTime == null) {
			localDateTime = LocalDateTime.now();
		}
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");
		return localDateTime.format(formatter);
	}

	/**
	 * 两个时间间隔 
	 * unit 通过单位来具体获得间隔单位
	 * @param firstTime
	 * @param secondTime
	 * @param unit  @ChronoUnit.SECONDS 秒  @ChronoUnit.MINUTES 分
	 * @return
	 */
	public static long getTwoTimeInterval(LocalDateTime firstTime, LocalDateTime secondTime, ChronoUnit unit) {
		return firstTime.until(secondTime, unit);
	}

	/**
	 * 获得给定时间所在周，周一0点
	 * */
	public static LocalDateTime getWeekdayZero(LocalDateTime localDateTime) {
		if (localDateTime == null) {
			localDateTime = LocalDateTime.now();
		}
		DayOfWeek df = localDateTime.getDayOfWeek();
		int weekIndex = df.getValue();
		localDateTime = localDateTime.plusDays(-(weekIndex - 1));
		return LocalDateTime.of(localDateTime.getYear(), localDateTime.getMonth(), localDateTime.getDayOfMonth(), 0, 0, 0, 0);
	}

	/**
	 * 获得给定时间所在月，月度一号0点
	 * */
	public static LocalDateTime getMonthdayZero(LocalDateTime localDateTime) {
		if (localDateTime == null) {
			localDateTime = LocalDateTime.now();
		}
		return LocalDateTime.of(localDateTime.getYear(), localDateTime.getMonth(), 1, 0, 0, 0, 0);
	}

	/**
	 * 将一个整型日期转为MM.dd格式
	 * */
	public static String getMMddStr(int time, String symbol) {
		String str = time + "";
		if (str.length() < 8) {
			return "";
		}
		return str.substring(4, 6) + symbol + str.substring(6, 8);
	}

	/**返回给定localDateTime,年月日8位的时间串yyyy-MM-dd
	 * 为空则返回当前时间的串
	 * */
	public static String getyyyy_MM_dd(LocalDateTime localDateTime) {
		if (localDateTime == null) {
			localDateTime = LocalDateTime.now();
		}
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		return localDateTime.format(formatter);
	}

	/**返回完整的时间字符串*/
	public static String getFullStrTime(LocalDateTime localDateTime) {
		if (localDateTime == null) {
			localDateTime = LocalDateTime.now();
		}
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
		return localDateTime.format(formatter);
	}

	public static String getTimeStrByFormate(LocalDateTime localDateTime, String formate) {
		if (localDateTime == null) {
			localDateTime = LocalDateTime.now();
		}
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern(formate);
		return localDateTime.format(formatter);
	}

	/**
	 * 判断时间是否存在重叠
	 * @date 2015年12月11日 下午1:59:19
	 * @author chengyadong
	 * @param startTime1
	 * @param startTime2
	 * @param endTime1
	 * @param endTime2
	 * @return
	 */
	public static boolean isOverlap(LocalDateTime startTime1, LocalDateTime startTime2, LocalDateTime endTime1, LocalDateTime endTime2) {
		if (startTime1.isAfter(startTime2)) {
			return startTime1.isBefore(endTime2);
		} else if (startTime2.isAfter(startTime1)) {
			return startTime2.isBefore(endTime1);
		}
		return true;
	}

	public static LocalDateTime convertTime(int hourIndex) {
		LocalDateTime initTime = LocalDateTime.of(LocalDate.now(), LocalTime.MIN);
		LocalDateTime now = LocalDateTime.now();
		if (now.isBefore(initTime.withHour(KTV_END_TIME_STANDARD))) {
			//时间为凌晨,起始时间为前天0点
			initTime = initTime.minusDays(1L);
		}
		return initTime.plusHours(hourIndex);
	}

	public static LocalDateTime convertTime(LocalTime time) {
		int hourIndex = time.getHour();
		if (hourIndex <= KTV_END_TIME_STANDARD) {
			hourIndex += 24;
		}
		return convertTime(hourIndex).withMinute(time.getMinute()).withSecond(time.getSecond()).withNano(time.getNano());
	}

	public static boolean isNeedConvert(LocalDateTime now) {
		LocalDateTime initTime = LocalDateTime.of(LocalDate.now(), LocalTime.MIN);
		return now.isBefore(initTime.withHour(KTV_END_TIME_STANDARD));
	}

	public static boolean isNeedConvert() {
		return isNeedConvert(LocalDateTime.now());
	}

	public static LocalDateTime[] initTime(LocalTime startTime, LocalTime endTime) {
		LocalDateTime startDateTime = convertTime(startTime);
		LocalDateTime endDateTime = convertTime(endTime);
		if (endTime.isBefore(startTime)) {
			endDateTime = endDateTime.plusDays(1L);
		}
		LocalDateTime[] array = { startDateTime, endDateTime };
		return array;
	}

	public static int getHourIndex(LocalTime time) {
		return time.getHour() <= KTV_END_TIME_STANDARD ? time.getHour() + 24 : time.getHour();
	}

	public static Date toDate(LocalDate localDate) {
		return Date.from(localDate.atStartOfDay().atZone(ZoneId.systemDefault()).toInstant());
	}

}
