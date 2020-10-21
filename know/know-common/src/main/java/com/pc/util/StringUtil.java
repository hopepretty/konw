package com.pc.util;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.jsoup.Jsoup;
import org.jsoup.safety.Whitelist;
import org.springframework.web.util.UriUtils;

import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URL;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.*;
import java.util.regex.Pattern;

/**
 * 字符串操作工具类
 * @author pc
 * @Date 2020/9/7
 **/
public class StringUtil {

    public static final String DEFAULT_CHARSET = "UTF-8";

    private static final Pattern PATTERN = Pattern.compile("[\\u4E00-\\u9FBF]+");

    /**
     *
     * @param str
     * @return
     */
    public static String encoder(String str) {
        String retStr = null;
        try {
            if (str == null) {
                return "";
            }
            retStr = URLEncoder.encode(str, "utf-8");
            retStr = replace(retStr, "+", "%20");
        } catch (UnsupportedEncodingException e) {
            retStr = str;
            e.printStackTrace();
        }
        return retStr;
    }

    /**
     *
     * @param str
     * @return
     */
    public static String decoder(String str) {
        String retStr = null;
        try {
            if (str == null) {
                return "";
            }
            retStr = URLDecoder.decode(str, "utf-8");
        } catch (UnsupportedEncodingException e) {
            retStr = str;
            e.printStackTrace();
        }
        return retStr;
    }

    /**
     * 清除html标签，比如：&lt;a&gt;123456&lt;/a&gt;，被清理成123456
     *
     * @param str
     * @return
     */
    public static String removeHTML(String str) {
        if (isNotEmpty(str)) {
            Whitelist whitelist = new Whitelist();
            str = Jsoup.clean(str, whitelist);
        }
        return str;
    }

    /**
     * 清除回车换行
     *
     * @param str
     * @return
     */
    public static String removeEnter(String str) {
        return StringUtils.replaceEach(str, new String[] {"\n", "\r"}, new String[] {"", ""});
    }

    /**
     * 清除所有空格包含制表符
     *
     * @param str
     * @return
     */
    public static String removeWhitespace(String str) {
        return StringUtils.deleteWhitespace(str);
    }

    /**
     * 删除字符
     *
     * @param str
     *            源字符串
     * @param removeStr
     *            要删除的字符
     * @return
     */
    public static String removeString(String str, String removeStr) {
        return StringUtils.remove(str, removeStr);
    }

    /**
     * 从一个object 转换到 string，如果为null 则返回“”
     *
     * @param object
     * @return
     */
    public static String getString(Object object) {
        return ObjectUtils.toString(object, "");
    }

    /**
     * 从一个object 转换到 string，如果为null 则返回“”
     *
     * @param object
     * @param defString
     * @return
     */
    public static String getString(Object object, String defString) {
        String str = ObjectUtils.toString(object, defString);
        if (StringUtil.isEmpty(str)) {
            str = defString;
        }
        return str;
    }

    /**
     * 截取字符串
     *
     * @param str
     *            源字符串
     * @param start
     *            开始
     * @param end
     *            结束
     * @return
     */
    public static String subString(String str, String start, String end) {
        return StringUtils.substringBetween(str, start, end);
    }

    /**
     * 从一个object 转换到 string并且trim，如果为null 则返回“”
     *
     * @param object
     * @return
     */
    public static String trim(Object object) {
        return ObjectUtils.toString(object, "").trim();
    }

    /**
     * trim字符，比如 0001111000 trim成 1111
     *
     * @param object
     * @param trim
     * @return
     */
    public static String trimString(Object object, String trim) {
        String str = getString(object);
        str = trim(str);
        str = replace(str, " ", "<trim-blank>");
        str = replace(str, trim, " ");
        str = trim(str);
        str = replace(str, " ", trim);
        str = replace(str, "<trim-blank>", " ");
        return str;
    }

    /**
     * 字符串替换
     *
     * @param text
     *            源字符串
     * @param searchString
     *            被替换的字符
     * @param replacement
     *            新字符
     * @return
     */
    public static String replace(String text, String searchString, String replacement) {
        return StringUtils.replace(text, searchString, replacement);
    }

    /**
     * 替换第一个出现的字符串
     *
     * @param text
     *            源字符串
     * @param searchString
     *            被替换的字符
     * @param replacement
     *            新字符
     * @return
     */
    public static String replaceFirst(String text, String searchString, String replacement) {
        return StringUtils.replaceOnce(text, searchString, replacement);
    }

    /**
     * 获得32位唯一吗
     *
     * @return
     */
    public static String getUUIDString() {
        return UUID.randomUUID().toString().replaceAll("-", "");
    }

    /**
     * 把集合装换为字符串
     *
     * @param collection
     *            集合
     * @param separator
     *            分隔符
     * @return
     */
    public static String join(Collection<?> collection, String separator) {
        return StringUtils.join(collection, separator);
    }

    /**
     * 把数组转换为字符串
     *
     * @param array
     *            数组
     * @param separator
     *            分隔符
     * @return
     */
    public static String join(Object[] array, String separator) {
        return StringUtils.join(array, separator);
    }

    /**
     * 删除各种stringbuffer里面的内容
     *
     * @param sb
     */
    public static void cleanStringBuffer(StringBuffer... sb) {
        for (int i = 0; i < sb.length; i++) {
            sb[i].delete(0, sb[i].length());
        }
    }

    /**
     * 搜索指定一个字符串在另一个字符串中出现的次数
     *
     * @param string
     *            源字符串
     * @param sub
     *            要计算的字符串
     * @return
     */
    public static int countMatches(String string, String sub) {
        return StringUtils.countMatches(string, sub);
    }

    /**
     * 判断一个字符串中是否含有指定的字符串
     *
     * @param string
     *            源字符串
     * @param searchStr
     *            是否包含的字符串
     * @return
     */
    public static boolean contains(String string, String searchStr) {
        return StringUtils.contains(string, searchStr);
    }

    /**
     * 返回一个指定重复次数的字符串
     *
     * @param string
     *            源字符串
     * @param frequency
     *            重复次数
     * @return
     */
    public static String repeat(String string, int frequency) {
        return StringUtils.repeat(string, frequency);
    }

    /**
     * 将string数组转换成integer数组
     *
     * @param strArr
     *            string数组
     * @return
     */
    public static Integer[] toIntegerArray(String[] strArr) {
        if (strArr == null) {
            return null;
        }
        Integer[] obj = new Integer[strArr.length];
        for (int i = 0; i < strArr.length; i++) {
            obj[i] = Integer.parseInt(strArr[i]);
        }
        return obj;
    }

    /**
     * 将url前面加上 "/" 符号.
     *
     * @param url
     *            url
     * @param split
     *            分隔符(可以给null)
     * @return
     */
    public static String paresUrl(String url, String split) {
        if (url == null || url.trim().length() == 0) {
            return url;
        }
        url = StringUtils.replace(url, "\\", "/");
        String[] tmp = StringUtils.split(url, split);
        StringBuffer sb = new StringBuffer(0);
        for (int i = 0; i < tmp.length; i++) {
            if (split != null && split.length() > 0) {
                sb.append(split);
            }
            if (!tmp[i].startsWith("/")) {
                sb.append("/");
            }
            sb.append(tmp[i]);
        }
        if (split != null && split.length() > 0) {
            sb.delete(0, 1);
        }
        url = sb.toString();
        cleanStringBuffer(sb);
        return url;
    }

    /**
     * 将path转换成 xxx/xxx/xxx
     *
     * @param path
     *            路径
     * @param fix
     *            是否末尾补全"/"
     */
    public static String convertPath(String path, boolean fix) {
        if (isEmpty(path)) {
            return path;
        }
        path = StringUtils.replace(path, "\\", "/");
        if (fix && !path.endsWith("/")) {
            path = path + "/";
        }
        return path;
    }

    /**
     * equals，不会空指针
     *
     * @param str1
     * @param str2
     * @return
     */
    public static boolean equals(String str1, String str2) {
        return StringUtils.equals(str1, str2);
    }

    /**
     * 不区分大小写比较 equalsIgnoreCase，不会空指针
     *
     * @param str1
     * @param str2
     * @return
     */
    public static boolean equalsIgnoreCase(String str1, String str2) {
        return StringUtils.equalsIgnoreCase(str1, str2);
    }

    /**
     * 判断是否为空（自动trim）
     *
     * @param str
     *            原字符串
     * @return
     */
    public static boolean isEmpty(String str) {
        return StringUtils.isBlank(str);
    }

    /**
     * 判断是否为空（自动trim）
     *
     * @param str
     *            原字符串
     * @return
     */
    public static boolean isNotEmpty(String str) {
        return StringUtils.isNotBlank(str);
    }

    /**
     * 拆分字符串为数组
     *
     * @param str
     *            源字符串
     * @param separatorChar
     *            分隔符
     * @return 数组
     */
    public static String[] split(String str, String separatorChar) {
        return StringUtils.splitByWholeSeparator(str, separatorChar);
    }

    /**
     * 将string转换成String list
     *
     * @param str
     * @param separatorChar
     * @return
     */
    public static List<String> toStringList(String str, String separatorChar) {
        String[] strArray = split(str, separatorChar);
        return Arrays.asList(strArray);
    }

    /**
     * 将string转换成String list 默认以,分割
     *
     * @param str
     * @return
     */
    public static List<String> toStringList(String str) {
        return toStringList(str, ",");
    }

    /**
     * 将string转换成Integer list
     *
     * @param str
     * @param separatorChar
     * @return 如果string切分后的字符串不能转换为intger，就会返回null
     */
    public static List<Integer> toIntegerList(String str, String separatorChar) {
        String[] strArray = split(str, separatorChar);
        List<Integer> integers = new ArrayList<Integer>();
        if (strArray != null) {
            for (String string : strArray) {
                if (NumberUtil.isNotNumeric(string)) {
                    return null;
                }
                integers.add(NumberUtil.getInt(string));
            }
        }
        return integers;
    }

    /**
     * 将string转换成Integer list ，默认以,分割
     *
     * @param str
     * @return 如果string切分后的字符串不能转换为intger，就会返回null
     */
    public static List<Integer> toIntegerList(String str) {
        return toIntegerList(str, ",");
    }

    /**
     * 按字节长度截取字符串，默认编码为u8
     *
     * @param str
     *            将要截取的字符串参数
     * @param toCount
     *            截取的字节长度
     * @return 返回截取后的字符串
     */
    public static String limitLen(String str, int toCount) {
        return limitLen(str, toCount, "utf-8");
    }

    /**
     * 按字节长度截取字符串
     *
     * @param str
     *            将要截取的字符串参数
     * @param toCount
     *            截取的字节长度
     * @param charset
     *            字符串编码
     * @return 返回截取后的字符串
     */
    public static String limitLen(String str, int toCount, String charset) {
        int reInt = 0;
        String reStr = "";
        if (str != null && str.length() > 0) {
            if (charset == null || charset.length() == 0) {
                charset = "UTF-8";
            }
            try {
                int nLen = 0;
                nLen = str.getBytes(charset).length;
                if (nLen <= toCount) {
                    reStr = str;
                } else {
                    char[] tempChar = str.toCharArray();
                    for (int kk = 0; kk < tempChar.length; kk++) {
                        String s1 = String.valueOf(tempChar[kk]);
                        byte[] b = s1.getBytes("UTF-8");
                        reInt += b.length;
                        if (reInt >= toCount) {
                            break;
                        }
                        reStr += tempChar[kk];
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return reStr;
    }

    /**
     * 判断字符串是否超过指定长度,超过长度将进行截取,截取后增加后缀字符
     *
     * @param str
     *            要截取的字符串
     * @param toCount
     *            长度
     * @param charset
     *            编码
     * @param strTag
     *            后缀字母
     * @return
     */
    public static String limitLen(String str, int toCount, String charset, String strTag) {
        strTag = getString(strTag);
        int reInt = 0;
        String reStr = "";
        if (str != null && str.length() > 0) {
            if (charset == null || charset.length() == 0) {
                charset = "UTF-8";
            }
            try {
                int nLen = 0;
                nLen = str.getBytes(charset).length;
                if (nLen <= toCount) {
                    reStr = str;
                } else {
                    char[] tempChar = str.toCharArray();
                    for (int kk = 0; kk < tempChar.length; kk++) {
                        String s1 = String.valueOf(tempChar[kk]);
                        byte[] b = s1.getBytes("UTF-8");
                        reInt += b.length;
                        if (reInt >= toCount) {
                            break;
                        }
                        reStr += tempChar[kk];
                    }
                    reStr += strTag;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return reStr;
    }

    /**
     * 获得完整路径
     *
     * @param baseUrl
     * @param url
     * @return
     */
    public static String toFullUrl(String baseUrl, String url) {
        try {
            // 如果是纯域名的后面加 /
            if (StringUtil.isNotEmpty(baseUrl) && !StringUtil.contains(StringUtil.replace(baseUrl, "//", ""), "/")) {
                baseUrl += "/";
            }
            URI base = new URI(baseUrl);
            URI abs = base.resolve(url);// 解析于上述网页的相对URL，得到绝对URI
            URL absURL = abs.toURL();// 转成URL
            url = absURL.toString();
        } catch (Exception e) {
        }
        return url;
    }

    /**
     * 根据字典将 1,2,34,4 转换成 xx,yyy,yyy,vvv
     *
     * @param keys
     *            字典的key
     * @param dic
     *            字典
     * @return
     */
    public static String convertToDicValue(String keys, Map<String, String> dic) {
        String values = null;
        if (StringUtil.isNotEmpty(keys)) {
            String[] keyArray = StringUtil.split(keys, ",");
            List<String> valueList = new ArrayList<String>(2);
            for (int i = 0; i < keyArray.length; i++) {
                String key = keyArray[i];
                String value = dic.get(key);
                if (StringUtil.isNotEmpty(value)) {
                    valueList.add(value);
                }
            }
            values = StringUtil.join(valueList, ",");
        }
        return values;
    }

    /**
     * 根据Unicode编码完美的判断中文汉字和符号
     * @param c
     * @return
     */
    private static boolean isChinese(char c) {
        Character.UnicodeBlock ub = Character.UnicodeBlock.of(c);
        if (ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS
                || ub == Character.UnicodeBlock.CJK_COMPATIBILITY_IDEOGRAPHS
                || ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS_EXTENSION_A
                || ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS_EXTENSION_B
                || ub == Character.UnicodeBlock.CJK_SYMBOLS_AND_PUNCTUATION
                || ub == Character.UnicodeBlock.HALFWIDTH_AND_FULLWIDTH_FORMS
                || ub == Character.UnicodeBlock.GENERAL_PUNCTUATION) {
            return true;
        }
        return false;
    }

    /**
     * 完整的判断中文汉字和符号
     * @param strName
     * @return
     */
    public static boolean isChinese(String strName) {
        if (isEmpty(strName)) {
            return false;
        }
        char[] ch = strName.toCharArray();
        for (int i = 0; i < ch.length; i++) {
            char c = ch[i];
            if (isChinese(c)) {
                return true;
            }
        }
        return false;
    }

    /**
     * 只能判断部分CJK字符（CJK统一汉字）
     * @param str
     * @return
     */
    public static boolean isChineseByREG(String str) {
        if (isEmpty(str)) {
            return false;
        }
        return PATTERN.matcher(str.trim()).find();
    }

    /**
     * 只能判断部分CJK字符（CJK统一汉字）
     * @param str
     * @return
     */
    public static boolean isChineseByName(String str) {
        if (isEmpty(str)) {
            return false;
        }
        // 大小写不同：\\p 表示包含，\\P 表示不包含
        // \\p{Cn} 的意思为 Unicode 中未被定义字符的编码，\\P{Cn} 就表示 Unicode中已经被定义字符的编码
        String reg = "\\p{InCJK Unified Ideographs}&amp;&amp;\\P{Cn}";
        Pattern pattern = Pattern.compile(reg);
        return pattern.matcher(str.trim()).find();
    }

    /**
     * url编码
     *
     * @param url
     * @return
     */
    public static String encodeUrl(String url) {
        return encodeUrl(url, "utf-8");
    }

    /**
     * url编码
     *
     * @param url
     * @param charset
     *            url的编码
     * @return
     */
    public static String encodeUrl(String url, String charset) {
        if (isNotEmpty(url) && isNotEmpty(charset)) {
            try {
                url = UriUtils.encodeQuery(url, charset);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return url;
    }

    /**
     * 将url中的中文变成 utf-8，如果没有返回原url
     *
     * @param url
     * @return
     */
    public static String encodeChineseUrl(String url) {
        return encodeChineseUrl(url, "utf-8");
    }

    /**
     * 将url中的中文变成指定编码，如果没有返回原url
     *
     * @param url
     * @param charset
     * @return
     */
    public static String encodeChineseUrl(String url, String charset) {
        if (isChinese(url) && isNotEmpty(charset)) {
            url = encodeUrl(url, charset);
        }
        return url;
    }

    /**
     * 删除xml中的特殊字符
     *
     * @param content
     * @return
     */
    public static String removeXmlHex(String content) {
        if (isNotEmpty(content)) {
            content = content.replaceAll("[\\x00-\\x08\\x0b-\\x0c\\x0e-\\x1f]", "");
        }
        return content;
    }

    /**
     * 获取长度，其中中文为占3
     *
     * @param str
     * @return
     */
    public static int getRealLength(String str) {
        int m = 0;
        if (StringUtil.isEmpty(str)) {
            return m;
        }
        char arr[] = str.toCharArray();
        for (int i = 0; i < arr.length; i++) {
            char c = arr[i];
            // 中文字符(根据Unicode范围判断),中文字符长度为2
            if ((c >= 0x0391 && c <= 0xFFE5)) {
                m = m + 3;
            } else if ((c >= 0x0000 && c <= 0x00FF)) // 英文字符
            {
                m = m + 1;
            }
        }
        return m;
    }

    /**
     * 将文本分成行
     *
     * @param content
     * @return
     */
    public static List<String> toLines(String content) {
        List<String> lines = null;
        if (StringUtil.isNotEmpty(content)) {
            try {
                lines = IOUtils.readLines(IOUtils.toInputStream(content, "utf-8"), "utf-8");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return lines;
    }

    /**
     * 按照指定长度填充或者裁剪字符串
     * @author 李杰
     * @date 2019年8月15日下午4:09:07
     *
     * @param str   原始字符串
     * @param length    指定长度
     * @param fillStr   填充字符串
     * @return
     */
    public static String fill(String str, int length, String fillStr) {
        if (StringUtil.isNotEmpty(str)) {
            int strLength = str.length();
            int sub = strLength - length;
            if (sub > 0) {
                str = str.substring(0, length);
            } else if (sub < 0) {
                StringBuffer sb = new StringBuffer(str);
                while (sub < 0) {
                    sub++;
                    sb.append(fillStr);
                }
                str = sb.toString();
            }
        }
        return str;
    }

}
