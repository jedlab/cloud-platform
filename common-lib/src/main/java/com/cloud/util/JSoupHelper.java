package com.cloud.util;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.jedlab.framework.util.StringUtil;

public class JSoupHelper
{

    /**
     * <p>clean up html tags</p>
     * @param html
     * @return
     */
    public static String html2text(String html)
    {
        if(StringUtil.isEmpty(html))
            return "";
        return Jsoup.parse(html).text();
    }
    
    /**
     * 
     * @param html
     * @return cleaned text paragraph from html
     */
    public static String findParagraph(String html)
    {
        if(StringUtil.isEmpty(html))
            return "";
        Elements paragraphs = Jsoup.parse(html).select("p");
        StringBuilder sb = new StringBuilder();
        for (Element element : paragraphs)
        {
            if(element.hasText())
                sb.append(element.text()).append(" ");
        }
        String text = sb.toString();
        if(StringUtil.isEmpty(text))
            return "";
        if(text.length() <= 200)
            return text;
        return text.substring(0, 200);
    }
    
   
    
}
