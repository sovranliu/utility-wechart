package com.slfuture.utility.wechart.utility;

import java.util.*;

import com.slfuture.carrie.base.etc.Serial;
import com.slfuture.carrie.base.text.Text;
import com.slfuture.carrie.base.type.core.ILink;
import com.slfuture.carrie.base.type.core.ITable;
import com.slfuture.carrie.base.xml.core.IXMLNode;

/**
 * 工具类
 */
public class Tool {
    /**
     * 隐藏构造函数
     */
    private Tool() { }

    /**
     * 生成签名
     *
     * @param node 通信投递的XML
     * @return 签名字符串
     */
    public static String makeSign(IXMLNode node) {
        if(null == node) {
            return null;
        }
        TreeMap<String,String> sort = new TreeMap<String,String>();
        for(IXMLNode subNode : node.children()) {
            if(Text.isBlank(subNode.getValue())) {
                continue;
            }
            if("sign".equalsIgnoreCase(subNode.getName())) {
                continue;
            }
            sort.put(subNode.getName(), subNode.getValue());
        }
        StringBuilder builder = null;
        for(Map.Entry<String,String> entry : sort.entrySet()) {
            if(null == builder) {
                builder = new StringBuilder();
            }
            else {
                builder.append("&");
            }
            builder.append(entry.getKey() + "=" + entry.getValue());
        }
        builder.append("&key=" + Config.apiKey);
        return Serial.getMD5String(builder.toString()).toUpperCase();
    }

    /**
     * 生成签名
     *
     * @param map 参数映射
     * @return 签名
     */
    public static String makeSign(ITable<String, Object> map) {
        TreeMap<String,String> sort = new TreeMap<String,String>();
        for(ILink<String, Object> link : map) {
            if(null == link.destination()) {
                continue;
            }
            String value = link.destination().toString();
            if(Text.isBlank(value)) {
                continue;
            }
            if("sign".equalsIgnoreCase(link.origin())) {
                continue;
            }
            sort.put(link.origin(), value);
        }
        StringBuilder builder = null;
        for(Map.Entry<String,String> entry : sort.entrySet()) {
            if(null == builder) {
                builder = new StringBuilder();
            }
            else {
                builder.append("&");
            }
            builder.append(entry.getKey() + "=" + entry.getValue());
        }
        builder.append("&key=" + Config.apiKey);
        return Serial.getMD5String(builder.toString()).toUpperCase();
    }

    /**
     * 生成签名
     *
     * @param keys 参数列表
     * @return 签名
     */
    public static String makeSign(String[] keys) {
        Arrays.sort(keys);
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < keys.length; i++) {
            builder.append(keys[i]);
        }
        return Serial.getSHA1(builder.toString());
    }

    /**
     * 生成签名
     *
     * @param jsTicket JS凭据
     * @param nonceString 随机字符串
     * @param timestamp 时间戳（秒）
     * @param url 调用的URL
     * @return 签名
     */
    public static String makeJSSign(String jsTicket, String nonceString, long timestamp, String url) {
        String string1 = "jsapi_ticket=" + jsTicket + "&noncestr=" + nonceString + "&timestamp=" + timestamp + "&url=" + url;
        return Serial.getSHA1(string1);
    }

    /**
     * 生成签名
     *
     * @param jsTicket JS凭据
     * @param nonceString 随机字符串
     * @param timestamp 时间戳（秒）
     * @param url 调用的URL
     * @return 签名
     */
    public static String makeSign(String jsTicket, String nonceString, long timestamp, String url) {
        String string1 = "jsapi_ticket=" + jsTicket + "&noncestr=" + nonceString + "&timestamp=" + timestamp + "&url=" + url + "&key=" + Config.apiKey;
        return Serial.getSHA1(string1);
    }
}
