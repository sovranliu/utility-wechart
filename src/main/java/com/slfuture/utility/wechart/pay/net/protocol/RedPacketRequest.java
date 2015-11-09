package com.slfuture.utility.wechart.pay.net.protocol;

import com.slfuture.carrie.base.etc.Serial;
import com.slfuture.carrie.base.text.Text;
import com.slfuture.carrie.base.xml.XMLNode;
import com.slfuture.carrie.base.xml.core.IXMLNode;
import com.slfuture.utility.wechart.utility.Config;
import com.slfuture.utility.wechart.utility.Tool;
import org.apache.log4j.Logger;

/**
 * 红包请求结构体
 */
public class RedPacketRequest {
    /**
     * 日志对象
     */
    public static Logger logger = Logger.getLogger(RedPacketRequest.class);


    /**
     * 属性
     */
    public String appId = null;
    public String merchantId = null;
    public String nonceString = null;
    public String sign = null;
    public String tradeNo = null;
    public String merchantName = null;
    public String openId = null;
    public int amount = 0;
    public int count = 0;
    public String wishing = null;
    public String createIP = null;
    public String gameName = null;
    public String remark = null;


    /**
     * 转换为XML节点对象
     *
     * @return XML节点对象
     */
    public IXMLNode toXML() {
        XMLNode result = new XMLNode();
        result.setName("xml");
        XMLNode item = null;
        //
        if(!Text.isBlank(appId)) {
            item = new XMLNode();
            item.setName("wxappid");
            item.setValue(appId);
            result.children().add(item);
        }
        if(!Text.isBlank(merchantId)) {
            item = new XMLNode();
            item.setName("mch_id");
            item.setValue(merchantId);
            result.children().add(item);
        }
        if(!Text.isBlank(nonceString)) {
            item = new XMLNode();
            item.setName("nonce_str");
            item.setValue(nonceString);
            result.children().add(item);
        }
        if(!Text.isBlank(tradeNo)) {
            item = new XMLNode();
            item.setName("mch_billno");
            item.setValue(tradeNo);
            result.children().add(item);
        }
        if(!Text.isBlank(merchantName)) {
            item = new XMLNode();
            item.setName("send_name");
            item.setValue(merchantName);
            result.children().add(item);
        }
        if(!Text.isBlank(openId)) {
            item = new XMLNode();
            item.setName("re_openid");
            item.setValue(openId);
            result.children().add(item);
        }
        if(amount >= 0) {
            item = new XMLNode();
            item.setName("total_amount");
            item.setValue(String.valueOf(amount));
            result.children().add(item);
        }
        if(count >= 0) {
            item = new XMLNode();
            item.setName("total_num");
            item.setValue(String.valueOf(count));
            result.children().add(item);
        }
        if(!Text.isBlank(wishing)) {
            item = new XMLNode();
            item.setName("wishing");
            item.setValue(wishing);
            result.children().add(item);
        }
        if(!Text.isBlank(createIP)) {
            item = new XMLNode();
            item.setName("client_ip");
            item.setValue(createIP);
            result.children().add(item);
        }
        if(!Text.isBlank(gameName)) {
            item = new XMLNode();
            item.setName("act_name");
            item.setValue(gameName);
            result.children().add(item);
        }
        if(!Text.isBlank(remark)) {
            item = new XMLNode();
            item.setName("remark");
            item.setValue(remark);
            result.children().add(item);
        }
        sign = Tool.makeSign(result);
        if(!Text.isBlank(sign)) {
            item = new XMLNode();
            item.setName("sign");
            item.setValue(sign);
            result.children().add(item);
        }
        return result;
    }

    /**
     * 解析生成退款请求结构体
     *
     * @param node XML节点
     * @return 退款请求结构体
     */
    public static RedPacketRequest parse(IXMLNode node) {
        if(null == node) {
            return null;
        }
        RedPacketRequest result = new RedPacketRequest();
        for(IXMLNode item : node.children()) {
            if("appid".equalsIgnoreCase(item.getName())) {
                result.appId = item.getValue();
            }
            else if("mch_id".equalsIgnoreCase(item.getName())) {
                result.merchantId = item.getValue();
            }
            else if("nonce_str".equalsIgnoreCase(item.getName())) {
                result.nonceString = item.getValue();
            }
            else if("sign".equalsIgnoreCase(item.getName())) {
                result.sign = item.getValue();
            }
            else if("mch_billno".equalsIgnoreCase(item.getName())) {
                result.tradeNo = item.getValue();
            }
            else if("send_name".equalsIgnoreCase(item.getName())) {
                result.merchantName = item.getValue();
            }
            else if("re_openid".equalsIgnoreCase(item.getName())) {
                result.openId = item.getValue();
            }
            else if("total_amount".equalsIgnoreCase(item.getName())) {
                result.amount = Integer.valueOf(item.getValue());
            }
            else if("total_num".equalsIgnoreCase(item.getName())) {
                result.count = Integer.valueOf(item.getValue());
            }
            else if("wishing".equalsIgnoreCase(item.getName())) {
                result.wishing = item.getValue();
            }
            else if("client_ip".equalsIgnoreCase(item.getName())) {
                result.createIP = item.getValue();
            }
            else if("act_name".equalsIgnoreCase(item.getName())) {
                result.gameName = item.getValue();
            }
            else if("remark".equalsIgnoreCase(item.getName())) {
                result.remark = item.getValue();
            }
            else {
                logger.warn("unknown xml node in refund request item = " + item.toString() + "\nxml = " + node.toString());
            }
        }
        return result;
    }

    /**
     * 构建退款请求结构体
     *
     * @param tradeNo 交易流水号
     * @param openId 接收红包的用户
     * @param amount 红包金额
     * @param ip 用户IP
     * @return 退款请求结构体
     */
    public static RedPacketRequest build(String tradeNo, String openId, int amount, String ip) {
        RedPacketRequest result = new RedPacketRequest();
        result.appId = Config.appId;
        result.merchantId = Config.merchantId;
        result.nonceString = Serial.makeRandomString(32).toUpperCase();
        result.tradeNo = tradeNo;
        result.merchantName = "环旗网络科技";
        result.openId = openId;
        result.amount = amount;
        result.count = 1;
        result.wishing = "恭喜发财";
        result.createIP = ip;
        result.gameName = "游戏通关红包";
        result.remark = "你这么厉害你朋友圈知道么？";
        return result;
    }

    /**
     * 转换为字符串
     *
     * @return 字符串
     */
    @Override
    public String toString() {
        return toXML().toString();
    }
}
