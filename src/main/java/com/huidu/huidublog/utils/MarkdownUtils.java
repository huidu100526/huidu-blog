package com.huidu.huidublog.utils;

import org.commonmark.Extension;
import org.commonmark.ext.gfm.tables.TableBlock;
import org.commonmark.ext.gfm.tables.TablesExtension;
import org.commonmark.ext.heading.anchor.HeadingAnchorExtension;
import org.commonmark.node.Link;
import org.commonmark.node.Node;
import org.commonmark.parser.Parser;
import org.commonmark.renderer.html.AttributeProvider;
import org.commonmark.renderer.html.HtmlRenderer;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @auther huidu
 * @create 2020/2/24 11:12
 * @Description: Markdown 转 html 工具类
 */
public class MarkdownUtils {
    /**
     * 将markdown格式转换成HTML格式方法
     */
    public static String markdownToHtml(String markdown) {
        Parser parser = Parser.builder().build();
        Node document = parser.parse(markdown);
        HtmlRenderer renderer = HtmlRenderer.builder().build();
        return renderer.render(document);
    }

    /**
     * 增加扩展[标题锚点，表格生成]
     * Markdown转换成HTML
     */
    public static String markdownToHtmlExtensions(String markdown) {
        // 给h标题生成id，使markdown能够解析
        Set<Extension> headingAnchorExtensions = Collections.singleton(HeadingAnchorExtension.create());
        // 将table转换HTML
        List<Extension> tableExtension = Collections.singletonList(TablesExtension.create());
        Parser parser = Parser.builder().extensions(tableExtension).build();
        Node document = parser.parse(markdown);
        HtmlRenderer renderer = HtmlRenderer.builder()
                .extensions(headingAnchorExtensions)
                .extensions(tableExtension)
                .attributeProviderFactory(context -> new CustomAttributeProvider())
                .build();
        return renderer.render(document);
    }

    /**
     * 处理标签的属性，点击文章中连接打开新标签进行跳转
     */
    static class CustomAttributeProvider implements AttributeProvider {
        @Override
        public void setAttributes(Node node, String tagName, Map<String, String> attributes) {
            //改变a标签的target属性为_blank
            if (node instanceof Link) {
                attributes.put("target", "_blank");
            }
            if (node instanceof TableBlock) {
                attributes.put("class", "ui celled table");
            }
        }
    }
}
