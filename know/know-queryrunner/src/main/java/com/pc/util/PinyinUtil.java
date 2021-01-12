package com.pc.util;

import net.sourceforge.pinyin4j.PinyinHelper;
import org.apache.commons.lang3.ArrayUtils;

/**
 * 拼音工具类
 * 
 * @author 李杰
 * 
 */
public class PinyinUtil {

	/**
	 * 取汉字的首字母
	 *
	 * @param src
	 * @param isCapital
	 *            是否是大写
	 * @return
	 */
	public static char[] getHeadByChar(char src, boolean isCapital) {
		// 如果不是汉字直接返回
		if (src <= 128) {
			return new char[] { src };
		}
		// 获取所有的拼音
		String[] pinyingStr = PinyinHelper.toHanyuPinyinStringArray(src);
		// 创建返回对象
		int polyphoneSize = pinyingStr.length;
		char[] headChars = new char[polyphoneSize];
		int i = 0;
		// 截取首字符
		for (String s : pinyingStr) {
			char headChar = s.charAt(0);
			// 首字母是否大写，默认是小写
			if (isCapital) {
				headChars[i] = Character.toUpperCase(headChar);
			} else {
				headChars[i] = headChar;
			}
			i++;
		}

		return headChars;
	}

	/**
	 * 查找字符串首字母
	 * 
	 * @param src
	 * @param isCapital
	 *            是否大写
	 * @param separator
	 *            分隔符
	 * @return
	 */
	public static String getHeadByString(String src, boolean isCapital, String separator) {
		char[] chars = src.toCharArray();
		String[] headString = new String[chars.length];
		int i = 0;
		for (char ch : chars) {

			char[] chs;
			try {
				chs = getHeadByChar(ch, isCapital);
				StringBuffer sb = new StringBuffer();
				if (null != separator) {
					int j = 1;

					for (char ch1 : chs) {
						sb.append(ch1);
						if (j != chs.length) {
							sb.append(separator);
						}
						j++;
					}
				} else {
					sb.append(chs[0]);
				}
				headString[i] = sb.toString();
			} catch (Exception e) {
				headString[i] = "";
			}
			i++;
		}
		String str = "";
		if (ArrayUtils.isNotEmpty(headString)) {
			str = StringUtil.join(headString, separator);
		}
		return str;
	}
}