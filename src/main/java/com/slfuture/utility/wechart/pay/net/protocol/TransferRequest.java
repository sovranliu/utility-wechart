package com.slfuture.utility.wechart.pay.net.protocol;

import com.slfuture.carrie.base.etc.Serial;
import com.slfuture.carrie.base.text.Text;
import com.slfuture.carrie.base.xml.XMLNode;
import com.slfuture.carrie.base.xml.core.IXMLNode;
import com.slfuture.utility.wechart.utility.Config;
import com.slfuture.utility.wechart.utility.Tool;
import org.apache.log4j.Logger;

/**
 * 转款请求结构体
 */
public class TransferRequest {
    /**
     * 日志对象
     */
    public static Logger logger = Logger.getLogger(TransferRequest.class);


    /**
     * 属性
     */
    public String appId = null;
    public String merchantId = null;
    public String deviceInfo = null;
    public String nonceString = null;
    public String sign = null;
    public String tradeNo = null;
    public String openId = null;
    public String checkName = null;
    public String userName = null;
    // 总金额（分）
    public int amount = 0;
    public String description = null;
    public String createIP = null;


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
            item.setName("mch_appid");
            item.setValue(appId);
            result.children().add(item);
        }
        if(!Text.isBlank(merchantId)) {
            item = new XMLNode();
            item.setName("mchid");
            item.setValue(merchantId);
            result.children().add(item);
        }
        if(!Text.isBlank(deviceInfo)) {
            item = new XMLNode();
            item.setName("device_info");
            item.setValue(deviceInfo);
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
            item.setName("partner_trade_no");
            item.setValue(tradeNo);
            result.children().add(item);
        }
        if(!Text.isBlank(openId)) {
            item = new XMLNode();
            item.setName("openid");
            item.setValue(openId);
            result.children().add(item);
        }
        if(!Text.isBlank(checkName)) {
            item = new XMLNode();
            item.setName("check_name");
            item.setValue(checkName);
            result.children().add(item);
        }
        if(!Text.isBlank(userName)) {
            item = new XMLNode();
            item.setName("re_user_name");
            item.setValue(userName);
            result.children().add(item);
        }
        if(amount >= 0) {
            item = new XMLNode();
            item.setName("amount");
            item.setValue(String.valueOf(amount));
            result.children().add(item);
        }
        if(!Text.isBlank(description)) {
            item = new XMLNode();
            item.setName("desc");
            item.setValue(description);
            result.children().add(item);
        }
        if(!Text.isBlank(createIP)) {
            item = new XMLNode();
            item.setName("spbill_create_ip");
            item.setValue(createIP);
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
     * 解析生成转款请求结构体
     *
     * @param node XML节点
     * @return 转款请求结构体
     */
    public static TransferRequest parse(IXMLNode node) {
        if(null == node) {
            return null;
        }
        TransferRequest result = new TransferRequest();
        for(IXMLNode item : node.children()) {
            if("mch_appid".equalsIgnoreCase(item.getName())) {
                result.appId = item.getValue();
            }
            else if("mchid".equalsIgnoreCase(item.getName())) {
                result.merchantId = item.getValue();
            }
            else if("device_info".equalsIgnoreCase(item.getName())) {
                result.deviceInfo = item.getValue();
            }
            else if("nonce_str".equalsIgnoreCase(item.getName())) {
                result.nonceString = item.getValue();
            }
            else if("sign".equalsIgnoreCase(item.getName())) {
                result.sign = item.getValue();
            }
            else if("partner_trade_no".equalsIgnoreCase(item.getName())) {
                result.tradeNo = item.getValue();
            }
            else if("openid".equalsIgnoreCase(item.getName())) {
                result.openId = item.getValue();
            }
            else if("check_name".equalsIgnoreCase(item.getName())) {
                result.checkName = item.getValue();
            }
            else if("re_user_name".equalsIgnoreCase(item.getName())) {
                result.userName = item.getValue();
            }
            else if("amount".equalsIgnoreCase(item.getName())) {
                result.amount = Integer.valueOf(item.getValue());
            }
            else if("desc".equalsIgnoreCase(item.getName())) {
                result.description = item.getValue();
            }
            else if("spbill_create_ip".equalsIgnoreCase(item.getName())) {
                result.createIP = item.getValue();
            }
            else {
                logger.warn("unknown xml node in transfer request:" + item.toString() + "\nxml = " + node.toString());
            }
        }
        return result;
    }

    /**
     * 构建转款请求结构体
     *
     * @param tradeNo 交易流水号
     * @param openId 用户微信开放ID
     * @param userName 用户名称
     * @param amount 交易金额
     * @param description 描述
     * @param ip 发起者IP
     * @return 转款请求结构体
     */
    public static TransferRequest build(String tradeNo, String openId, String userName, int amount, String description, String ip) {
        TransferRequest result = new TransferRequest();
        result.appId = Config.appId;
        result.merchantId = Config.merchantId;
        result.deviceInfo = "WEB";
        result.nonceString = Serial.makeRandomString(32).toUpperCase();
        result.tradeNo = tradeNo;
        result.openId = openId;
        result.checkName = "FORCE_CHECK";
        result.userName = userName;
        result.amount = amount;
        result.description = description;
        result.amount = amount;
        result.createIP = ip;
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
