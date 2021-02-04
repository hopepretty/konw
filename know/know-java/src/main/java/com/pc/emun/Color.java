package com.pc.emun;

import org.apache.commons.lang3.StringUtils;

/**
 * 颜色
 *
 * @author pc
 * @Date 2021/2/4
 **/
public enum Color {

	/**
	 *
	 */
	REA("红色", "red"),
	GREEN("绿色", "green"),

	; //注意，枚举类出现属性方法需要在这里加个分号
	private String name;

	private String value;

	Color(String name, String value) {
		this.name = name;
		this.value = value;
	}

	/**
	 * 从枚举类种获取数据
	 *
	 * 当然也可以使用枚举类自己的valueof()方法判断是否存在相应的枚举类，不存在的话会抛异常的
	 *
	 * @param name
	 * @return
	 */
	public static String getValue(String name) {
		if (StringUtils.isEmpty(name)) {
			return null;
		}
		Color[] values = Color.values();
		for (Color value : values) {
			if (value.name.equals(name)) {
				return value.value;
			}
		}
		return null;
	}

	public static void main(String[] args) {
		System.out.println(Color.getValue("xx"));
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}
}
