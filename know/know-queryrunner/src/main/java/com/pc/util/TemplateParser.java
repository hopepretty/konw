package com.pc.util;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.beetl.core.Configuration;
import org.beetl.core.GroupTemplate;
import org.beetl.core.Template;
import org.beetl.core.cache.ProgramCacheFactory;
import org.beetl.core.resource.StringTemplateResourceLoader;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.parser.Parser;
import org.jsoup.select.Elements;

import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

/**
 * 模板解析
 * 
 * @author 李杰
 *
 */
public class TemplateParser {
    private static GroupTemplate groupTemplate = null;

    static {
        ProgramCacheFactory.CACHE = "com.hanweb.common.util.TemplateParserCache";
        StringTemplateResourceLoader resourceLoader = new StringTemplateResourceLoader();
        try {
            Configuration cfg = Configuration.defaultConfiguration();
            cfg.setEngine("org.beetl.core.engine.DefaultTemplateEngine");
            cfg.setStatementStart("<!--:");
            cfg.setStatementEnd("-->");
            cfg.setHtmlTagSupport(true);
            cfg.setHtmlTagFlag("h:");
            cfg.setHtmlTagStart("<h:");
            cfg.setHtmlTagEnd("</h:");
            groupTemplate = new GroupTemplate(resourceLoader, cfg);
            groupTemplate.registerFunction("URIEncode", (arg0, arg1) -> {
                String str = null;
                if (ArrayUtils.isNotEmpty(arg0)) {
                    str = (String)arg0[0];
                    str = StringUtil.encoder(str);
                }
                return str;
            });
        } catch (Exception e) {
        }
    }

    public static GroupTemplate getGroupTemplate() {
        return groupTemplate;
    }

    /**
     * 解析模板
     * 
     * @param template
     * @return
     */
    public static String parserTemplate(String template) {
        return parserTemplate(template, null);
    }

    /**
     * 解析模板
     * 
     * @param template
     * @param params
     * @return
     */
    public static String parserTemplate(String template, Map<String, Object> params) {
        String result = null;
        if (StringUtil.isNotEmpty(template)) {
            try {
                Template temp = groupTemplate.getTemplate(template);
                if (params != null) {
                    Set<Entry<String, Object>> p = params.entrySet();
                    for (Entry<String, Object> entry : p) {
                        temp.binding(entry.getKey(), entry.getValue());
                    }
                }
                result = temp.render();
            } catch (Exception e) {
            }
        }
        return result;
    }

    /**
     * 解析模板
     * 
     * @param template
     * @param key
     * @param value
     * @return
     */
    public static String parserTemplate(String template, String key, Object value) {
        String result = null;
        if (StringUtil.isNotEmpty(template) && StringUtil.isNotEmpty(key)) {
            try {
                Template temp = groupTemplate.getTemplate(template);
                temp.binding(key, value);
                result = temp.render();
            } catch (Exception e) {
            }
        }
        return result;
    }

    /**
     * 替换tag
     * @Description	
     * @author 李杰
     * @date 2019年7月24日下午2:21:24
     *
     * @param id    dom的id
     * @param template  模板原文件内容
     * @param content   需要替换上的内容
     * @return
     */
    public static String replaceTag(String id, String template, String content) throws Exception {
        if (StringUtil.isEmpty(id) || StringUtil.isEmpty(template)) {
            return template;
        }
        Element tag = getTagById(id, template);
        if (tag != null) {
            Document document = tag.ownerDocument();
            if (StringUtil.isEmpty(content)) {
                tag.remove();
            } else {
                tag.replaceWith(Jsoup.parse(content, "", Parser.xmlParser()).childNode(0));
            }
            template = document.outerHtml();
        }
        return template;
    }

    /**
     * 获得tag的content，包括标签自己
     * @Description	
     * @author 李杰
     * @date 2019年7月25日下午2:20:56
     *
     * @param id    dom的id
     * @param template  模板内容
     * @return
     * @throws Exception
     */
    public static String getTagContent(String id, String template) throws Exception {
        String content = null;
        if (StringUtil.isEmpty(id) || StringUtil.isEmpty(template)) {
            return template;
        }
        Element tag = getTagById(id, template);
        if (tag != null) {
            content = tag.outerHtml();
        }
        return content;
    }

    /**
     * 获得tag的content，不包括标签自己
     * @Description 
     * @author 李杰
     * @date 2019年7月25日下午2:20:56
     *
     * @param id    dom的id
     * @param template  模板内容
     * @return
     * @throws Exception
     */
    public static String getTagInnerContent(String id, String template) throws Exception {
        String content = null;
        if (StringUtil.isEmpty(id) || StringUtil.isEmpty(template)) {
            return template;
        }
        Element tag = getTagById(id, template);
        if (tag != null) {
            content = tag.html();
        }
        return content;
    }

    /**
     * 获得tag的element
     * @Description	
     * @author 李杰
     * @date 2019年7月25日下午2:23:53
     *
     * @param id
     * @param template
     * @return
     * @throws Exception
     */
    public static Element getTagById(String id, String template) throws Exception {
        Element tag = null;
        Document document = geTemplateDocument(template);
        Elements elements = document.select("#" + id);
        if (CollectionUtils.isNotEmpty(elements)) {
            if (elements.size() == 1) {
                tag = elements.get(0);
            } else {
                throw new Exception("id:" + id + " find not one element");
            }
        }
        return tag;
    }

    /**
     * 获得模板的document对象
     * @Description	
     * @author 李杰
     * @date 2019年7月25日下午2:33:29
     *
     * @param template
     * @return
     */
    public static Document geTemplateDocument(String template) {
        Document document = Jsoup.parse(template, "", Parser.xmlParser());
        document.outputSettings().prettyPrint(false);
        return document;
    }

    /**
     * 模板元素后追加
     * @Description	
     * @author 李杰
     * @date 2019年7月25日下午2:32:42
     *
     * @param select    选择器
     * @param appendString  需要追加的内容
     * @param template  模板内容
     * @return
     */
    public static String append(String select, String appendString, String template) {
        if (StringUtil.isEmpty(select) || StringUtil.isEmpty(appendString) || StringUtil.isEmpty(template)) {
            return template;
        }
        Document document = geTemplateDocument(template);
        document.select(select).append(appendString);
        return document.outerHtml();
    }

    /**
     * 模板元素前追加
     * @Description	
     * @author 李杰
     * @date 2019年7月25日下午2:34:03
     *
     * @param select    选择器
     * @param appendString  需要追加的内容
     * @param template  模板内容
     * @return
     */
    public static String prepend(String select, String appendString, String template) {
        if (StringUtil.isEmpty(select) || StringUtil.isEmpty(appendString) || StringUtil.isEmpty(template)) {
            return template;
        }
        Document document = geTemplateDocument(template);
        document.select(select).prepend(appendString);
        return document.outerHtml();
    }

    /**
     * 删除元素
     * @Description 
     * @author 李杰
     * @date 2019年10月9日下午3:06:18
     *
     * @param template  模板内容
     * @param select  选择器
     * @return
     */
    public static String remove(String template, String select) {
        if (StringUtil.isEmpty(template) || StringUtil.isEmpty(select)) {
            return template;
        }
        Document document = geTemplateDocument(template);
        document.select(select).remove();
        return document.outerHtml();
    }
}
