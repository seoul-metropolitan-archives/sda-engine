/*
 * Copyright 2002-2012 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.bgf.shbank.utils;

import com.bgf.shbank.core.message.CommonInfo;
import org.apache.commons.lang3.StringUtils;

import java.sql.Timestamp;
import java.time.*;
import java.time.temporal.ChronoUnit;
import java.util.Date;


/**
 * Created by tw.jang on 16. 2. 1.
 */
public final class DateUtils {
	
	private DateUtils() { }
	
	public static final int HOURS_24 = 24;
	
	public static final int MINUTES_60 = 60;
	
	public static final int SECONDS_60 = 60;

	public static final int MILLI_SECONDS_1000 = 1000;
	
	private static final int UNIT_HEX = 16;

	/** Date pattern */
	public static final String DATE_PATTERN_DASH = "yyyy-MM-dd";

	/** Time pattern */
	public static final String TIME_PATTERN = "HH:mm";

	/** Date Time pattern */
	public static final String DATE_TIME_PATTERN = "yyyy-MM-dd HH:mm:ss";

	/** Date Time pattern */
	public static final String KOR_DATE_TIME_PATTERN = "yyyy년MM월dd일 HH시mm분ss초";

	/** Date Time pattern */
	public static final String KOR_DATE_PATTERN = "yyyy년MM월dd일";

	/** Date HMS pattern */
	public static final String DATE_HMS_PATTERN = "yyyyMMddHHmmss";

	/** Time stamp pattern */
	public static final String TIMESTAMP_PATTERN = "yyyy-MM-dd HH:mm:ss.SSS";

	/** year pattern (yyyy)*/
    public static final String YEAR_PATTERN = "yyyy";

    /** month pattern (MM) */
    public static final String MONTH_PATTERN = "MM";

    /** day pattern (dd) */
    public static final String DAY_PATTERN = "dd";

    /** date pattern (yyyyMMdd) */
    public static final String DATE_PATTERN = "yyyyMMdd";

    /** hour, minute, second pattern (HHmmss) */
    public static final String TIME_HMS_PATTERN = "HHmmss";

    /** hour, minute, second pattern (HH:mm:ss) */
    public static final String TIME_HMS_PATTERN_COLONE = "HH:mm:ss";
    

	/**
	 * 현재 날짜, 시간을 조회하여 문자열 형태로 반환한다.<br>
	 *
	 * @return (yyyy-MM-dd HH:mm:ss) 포맷으로 구성된 현재 날짜와 시간
	 */
	public static String getNow() {
		return getNow(DATE_TIME_PATTERN);
	}

	/**
	 * 현재 날짜, 시간을 조회을 조회하고, pattern 형태의 포맷으로 문자열을 반환한다.<br><br>
	 *
	 * DateUtils.getNow("yyyy년 MM월 dd일 hh시 mm분 ss초") = "2012년 04월 12일 20시 41분 50초"<br>
	 * DateUtils.getNow("yyyy-MM-dd HH:mm:ss") = "2012-04-12 20:41:50"
	 *
	 * @param pattern 날짜 및 시간에 대한 포맷
	 * @return patter 포맷 형태로 구성된 현재 날짜와 시간
	 */
	public static String getNow(String pattern) {
		LocalDateTime dateTime = LocalDateTime.now();
		java.time.format.DateTimeFormatter formatter = java.time.format.DateTimeFormatter.ofPattern(pattern);
		return dateTime.format(formatter);
	}

	/**
	 * 입력받은 두 날짜 사이의 일자를 계산한다.<br>
	 * -startDate와 endDate는 patter의 포맷형식을 꼭 따라야 한다.<br><br>
	 *
	 * DateUtils.getDays("2010-11-24", "2010-12-30", "yyyy-MM-dd") = 36
	 *
	 * @param startDate 시작일
	 * @param endDate 종료일
	 * @param pattern 날짜 포맷
	 * @return 두 날짜 사이의 일자
	 */
	public static int getDays(String startDate, String endDate, String pattern) {

		java.time.format.DateTimeFormatter formatter = java.time.format.DateTimeFormatter.ofPattern(pattern);

		LocalDate localStartDate = LocalDate.parse(startDate, formatter);
		LocalDate localEndDate = LocalDate.parse(endDate, formatter);

		return (int) ChronoUnit.DAYS.between(localStartDate, localEndDate);
	}

	/**
	 * 입력받은 두 날짜 사이의 일자를 계산한다.<br><br>
	 *
	 * DateUtils.getDays("2010-11-24", "2010-12-30") = 36
	 *
	 * @param startDate (yyyy-MM-dd) 포맷 형태의 시작일
	 * @param endDate (yyyy-MM-dd) 포맷 형태의 종료일
	 * @return 두 날짜 사이의 일자
	 */
	public static int getDays(String startDate, String endDate) {
		return getDays(startDate, endDate, DATE_PATTERN_DASH);
	}

	public static int getDays(LocalDate startDate, LocalDate endDate) {
		return (int) ChronoUnit.DAYS.between(startDate, endDate);
	}

	public static int getDays(LocalDateTime startDateTime, LocalDateTime endDateTime) {
		return (int) ChronoUnit.DAYS.between(startDateTime, endDateTime);
	}

	/**
	 * 입력받은 두 일자가 같은지 여부를 확인한다.<br><br>
	 *
	 * DateUtils.equals(new LocalDate(1292252400000l), new LocalDate(1292252400000l)) = true
	 *
	 * @param date1 LocalDate형의 일자
	 * @param date2 LocalDate형의 일자
	 * @return 입력받은 두 일자가 같으면 true를 그렇지않으면 false를 반환.
	 */
	public static boolean equals(LocalDate date1, LocalDate date2) {
		return date1.isEqual(date2);
	}

	/**
	 * 입력받은 두 일자가 같은지 여부를 확인한다.<br><br>
	 *
	 * DateUtils.equals(new Date(1292252400000l), "2010-12-14") = true
	 *
	 * @param date LocalDate형의 일자
	 * @param dateStr (yyyy-MM-dd) 포맷형태의 일자
	 * @return 일치하면 true를 그렇지않으면 false를 반환
	 */
	public static boolean equals(LocalDate date, String dateStr) {
		return equals(date, dateStr, DATE_PATTERN_DASH);
	}

	/**
	 * 입력받은 두 일자가 같은지 여부를 확인한다.<br><br>
	 *
	 * DateUtils.equals(new LocalDate(1292252400000l), "2010/12/14", "yyyy/MM/dd") = true
	 *
	 * @param date Date형의 일자
	 * @param dateStr 포맷형태의 일자
	 * @param pattern 날짜 포맷
	 * @return 입력받은 두 일자가 같으면 true를 그렇지않으면 false를 반환.
	 */
	public static boolean equals(LocalDate date, String dateStr, String pattern) {

		java.time.format.DateTimeFormatter formatter = java.time.format.DateTimeFormatter.ofPattern(pattern);
		LocalDate parsedDate = LocalDate.parse(dateStr, formatter);

		return equals(date, parsedDate);
	}

    /**
     * 입력된 일자를 기준으로 해당년도가 윤년인지 여부를 반환한다.
     * 
     * @param  inputDate (yyyy-MM-dd) 형식의 일자
     * @return 윤년이면 true를 그렇지 않으면 false를 반환
     */
	public static boolean isLeapYear(String inputDate) {
		return isLeapYear(Integer.parseInt(inputDate.substring(0, 4)));
	}
    
    /**
     * 정수형태로 입력된 년도를 기준으로 해당년도가 윤년인지 여부를 반환한다.
     * 
     * @param year 년도
     * @return year이 윤년이면 true를 그렇지 않으면 false를 반환
     */
    public static boolean isLeapYear(int year) {
    	return LocalDate.of(year, 1, 1).isLeapYear();
    }
    
	public static LocalDateTime convertToDateTime(String dateTime, String pattern) {

		if (StringUtils.isEmpty(dateTime)) {
			return null;
		}

		if (dateTime.length() == 8) {
			return DateUtils.convertToDate(dateTime, "yyyyMMdd").atTime(0, 0, 0);
		}

		if (dateTime.length() == 10) {
			return DateUtils.convertToDate(dateTime).atTime(0, 0, 0);
		}

		java.time.format.DateTimeFormatter formatter = java.time.format.DateTimeFormatter.ofPattern(pattern);
		return LocalDateTime.parse(dateTime, formatter);
	}

	public static LocalDateTime convertToDateTime(String dateTime) {
		return convertToDateTime(dateTime, DATE_TIME_PATTERN);
	}

	public static LocalDateTime convertToDateTime(long timeMillis) {
		Date date = new Date(timeMillis);
		return date.toInstant().atZone(ZoneId.of("Asia/Seoul")).toLocalDateTime();
	}

	public static Timestamp convertToTimestamp(String dateTime, String pattern) {
		return Timestamp.valueOf(convertToDateTime(dateTime, pattern));
	}

	public static Timestamp convertToTimestamp(String dateTime) {
		return Timestamp.valueOf(convertToDateTime(dateTime, DATE_TIME_PATTERN));
	}

	public static Timestamp convertToTimestamp(long timeMillis) {
		return Timestamp.valueOf(convertToDateTime(timeMillis));
	}

	public static LocalDate convertToDate(String date, String pattern) {
		java.time.format.DateTimeFormatter formatter = java.time.format.DateTimeFormatter.ofPattern(pattern);
		return LocalDate.parse(date, formatter);
	}

	public static LocalDate convertToDate(String date) {
		return convertToDate(date, DATE_PATTERN_DASH);
	}

	public static String convertToString(LocalDateTime dateTime, String pattern) {
		java.time.format.DateTimeFormatter formatter = java.time.format.DateTimeFormatter.ofPattern(pattern);
		return dateTime.format(formatter);
	}

	public static String convertToString(LocalDateTime dateTime) {
		java.time.format.DateTimeFormatter formatter = java.time.format.DateTimeFormatter.ofPattern(DATE_TIME_PATTERN);
		return dateTime.format(formatter);
	}

	public static String convertToString(LocalDate date, String pattern) {
		java.time.format.DateTimeFormatter formatter = java.time.format.DateTimeFormatter.ofPattern(pattern);
		return date.format(formatter);
	}

	public static String convertToString(LocalDate date) {
		java.time.format.DateTimeFormatter formatter = java.time.format.DateTimeFormatter.ofPattern(DATE_PATTERN_DASH);
		return date.format(formatter);
	}

	public static String convertToString(LocalTime time, String pattern) {
		java.time.format.DateTimeFormatter formatter = java.time.format.DateTimeFormatter.ofPattern(pattern);
		return time.format(formatter);
	}

	public static String convertToString(LocalTime time) {
		java.time.format.DateTimeFormatter formatter = java.time.format.DateTimeFormatter.ofPattern(TIME_HMS_PATTERN_COLONE);
		return time.format(formatter);
	}

	public static String convertToStrKorDateTime(LocalDateTime dateTime) {
		java.time.format.DateTimeFormatter formatter = java.time.format.DateTimeFormatter.ofPattern(KOR_DATE_TIME_PATTERN);
		return dateTime.format(formatter);
	}

	public static String convertToStrKorDateTime(String dateTimeStr) {
		return convertToStrKorDateTime(dateTimeStr, DATE_TIME_PATTERN);
	}

	public static String convertToStrKorDateTime(String dateTimeStr, String pattern) {
		LocalDateTime dateTime = convertToDateTime(dateTimeStr, pattern);
		java.time.format.DateTimeFormatter formatter = java.time.format.DateTimeFormatter.ofPattern(KOR_DATE_TIME_PATTERN);
		return dateTime.format(formatter);
	}

	public static String convertToStrKorDate(LocalDateTime dateTime) {
		java.time.format.DateTimeFormatter formatter = java.time.format.DateTimeFormatter.ofPattern(KOR_DATE_PATTERN);
		return dateTime.format(formatter);
	}

	public static String convertToStrKorDate(String dateStr) {
		return convertToStrKorDate(dateStr, DATE_PATTERN_DASH);
	}

	public static String convertToStrKorDate(String dateStr, String pattern) {
		LocalDateTime dateTime = convertToDateTime(dateStr, pattern);
		java.time.format.DateTimeFormatter formatter = java.time.format.DateTimeFormatter.ofPattern(KOR_DATE_PATTERN);
		return dateTime.format(formatter);
	}

	public static long convertToTimeMillis(LocalDateTime dateTime) {
		ZonedDateTime zdt = dateTime.atZone(ZoneId.of("Asia/Seoul"));
		return zdt.toInstant().toEpochMilli();
	}

	public static void setCurrentDateTime(CommonInfo commonInfo) {

		LocalDateTime now = LocalDateTime.now();

		commonInfo.setCreateDate(DateUtils.convertToString(now, "yyyyMMdd"));
		commonInfo.setCreateTime(DateUtils.convertToString(now, "HHmmss"));
	}

/*	public static void main(String[] args) {
		System.out.println(convertToStrKorDateTime("2016-09-04 12:22:22"));
		System.out.println(convertToStrKorDateTime(LocalDateTime.now()));
		System.out.println(convertToStrKorDate(LocalDateTime.now()));
		System.out.println(convertToStrKorDate("2016-09-04 12:22:22", "yyyy-MM-dd HH:mm:ss"));
		System.out.println(convertToDateTime(System.currentTimeMillis()));
		System.out.println(convertToDateTime(convertToTimeMillis(LocalDateTime.now())));
	}*/

}
