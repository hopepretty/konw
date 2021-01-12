package com.pc.newtime;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

/**
 * @author pc
 * @Date 2020/9/10
 **/
public class LocalDateTimeTest {

    public static void main(String[] args) {
//		test();

		test1();
    }

    public static void test() {
		//本地时间，不受时区限制 （也可以定义时区 通过ZoneId）
		LocalDateTime localDateTime = LocalDateTime.now();
		System.out.println(localDateTime);
		//当前月份中的天数
		System.out.println(localDateTime.getDayOfMonth());
		//当前年份中的天数
		System.out.println(localDateTime.getDayOfYear());
		//日期转化
		System.out.println(localDateTime.toLocalDate());
		//构造相应时间
		System.out.println(localDateTime.withYear(1996).withDayOfMonth(14).withMonth(3));

		//获取当天或者某天的起止时间字符串
		String beginDate = LocalDateTime.of(LocalDate.now(), LocalTime.MIN).format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
		String endDate = LocalDateTime.of(LocalDate.now(), LocalTime.MAX).format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
		System.out.println(beginDate);
		System.out.println(endDate);
	}

	/**
	 * 项目开发阶段，有一个关于下单发货的需求：如果今天下午 3 点前进行下单，那么发货时间是明天，如果今天下午 3 点后进行下单，
	 * 那么发货时间是后天，如果被确定的时间是周日，那么在此时间上再加 1 天为发货时间。
	 */
	public static void test1() {
		//临界时间设置在当天下午3点整
		LocalDateTime nodeTime = LocalDateTime.now().withHour(15).withMinute(0).withSecond(0);
		//判断订单创建时间
		LocalDateTime orderCreateTime = LocalDateTime.now().withHour(16);

		System.out.println(orderCreateTime.isBefore(nodeTime) ? 0 : 1);
	}

}
