package com.pc.util;

import org.apache.commons.io.IOUtils;

import java.io.IOException;
import java.io.InputStream;

/**
 * 从类路径下获取资源文件
 * @author pc
 * @Date 2020/9/7
 **/
public class ClassPathUtil {

    /**
     * 流
     *
     * @param path
     *            根目录后面的路径(以"/"开头)
     * @return
     */
    public static InputStream getContentAsStream(String path) {
        path = StringUtil.convertPath(path, false);
        InputStream inputStream = null;
        if (StringUtil.isEmpty(path)) {
            return inputStream;
        }
        if (!path.startsWith("/")) {
            path = "/" + path;
        }
        try {
            inputStream = ClassPathUtil.class.getResourceAsStream(path);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return inputStream;
    }

    /**
     * 读取文件内容
     *
     * @param path
     *            根目录后面的路径(以"/"开头)
     * @return
     */
    public static String getContentAsString(String path) {
        path = StringUtil.convertPath(path, false);
        String content = null;
        InputStream inputStream = null;
        if (StringUtil.isEmpty(path)) {
            return content;
        }
        if (!path.startsWith("/")) {
            path = "/" + path;
        }
        inputStream = getContentAsStream(path);
        if (inputStream != null) {
            try {
                content = IOUtils.toString(inputStream, "UTF-8");
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return content;
    }

}
