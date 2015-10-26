package com.slfuture.utility.wechart.pay.net.protocol;

import com.slfuture.carrie.base.etc.Serial;
import com.slfuture.carrie.base.text.Text;
import com.slfuture.carrie.base.time.DateTime;
import com.slfuture.carrie.base.time.Duration;
import com.slfuture.carrie.base.xml.XMLNode;
import com.slfuture.carrie.base.xml.core.IXMLNode;

import com.slfuture.utility.wechart.utility.Config;
import com.slfuture.utility.wechart.utility.Tool;
import org.apache.log4j.Logger;

/**
 * 统一下单请求结构体
 */
public class UnifiedOrderRequest {
    /**
     * 日志对象
     */
    public static Logger logger = Logger.getLogger(UnifiedOrderRequest.class);


    /**
     * 属性
     */
    public String appId = null;
    public String merchantId = null;
    public String deviceInfo = null;
    public String nonceString = null;
    public String body = null;
    public String detail = null;
    public String attach = null;
    public String tradeNo = null;
    public String feeType = null;
    // 总金额（分）
    public int totalFee = 0;
    public String createIP = null;
    public String startTime = null;
    public String expireTime = null;
    public String goodsTag = null;
    public String notifyUrl = null;
    public String tradeType = null;
    public String productId = null;
    public String limitPay = null;
    public String openId = null;
    public String sign = null;


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
            item.setName("appid");
            item.setValue(appId);
            result.children().add(item);
        }
        if(!Text.isBlank(merchantId)) {
            item = new XMLNode();
            item.setName("mch_id");
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
        if(!Text.isBlank(body)) {
            item = new XMLNode();
            item.setName("body");
            item.setValue(body);
            result.children().add(item);
        }
        if(!Text.isBlank(detail)) {
            item = new XMLNode();
            item.setName("detail");
            item.setValue(detail);
            result.children().add(item);
        }
        if(!Text.isBlank(attach)) {
            item = new XMLNode();
            item.setName("attach");
            item.setValue(attach);
            result.children().add(item);
        }
        if(!Text.isBlank(tradeNo)) {
            item = new XMLNode();
            item.setName("out_trade_no");
            item.setValue(tradeNo);
            result.children().add(item);
        }
        if(!Text.isBlank(feeType)) {
            item = new XMLNode();
            item.setName("fee_type");
            item.setValue(feeType);
            result.children().add(item);
        }
        if(totalFee >= 0) {
            item = new XMLNode();
            item.setName("total_fee");
            item.setValue(String.valueOf(totalFee));
            result.children().add(item);
        }
        if(!Text.isBlank(createIP)) {
            item = new XMLNode();
            item.setName("spbill_create_ip");
            item.setValue(createIP);
            result.children().add(item);
        }
        if(!Text.isBlank(startTime)) {
            item = new XMLNode();
            item.setName("time_start");
            item.setValue(startTime);
            result.children().add(item);
        }
        if(!Text.isBlank(expireTime)) {
            item = new XMLNode();
            item.setName("time_expire");
            item.setValue(expireTime);
            result.children().add(item);
        }
        if(!Text.isBlank(goodsTag)) {
            item = new XMLNode();
            item.setName("goods_tag");
            item.setValue(goodsTag);
            result.children().add(item);
        }
        if(!Text.isBlank(notifyUrl)) {
            item = new XMLNode();
            item.setName("notify_url");
            item.setValue(notifyUrl);
            result.children().add(item);
        }
        if(!Text.isBlank(tradeType)) {
            item = new XMLNode();
            item.setName("trade_type");
            item.setValue(tradeType);
            result.children().add(item);
        }
        if(!Text.isBlank(productId)) {
            item = new XMLNode();
            item.setName("product_id");
            item.setValue(productId);
            result.children().add(item);
        }
        if(!Text.isBlank(limitPay)) {
            item = new XMLNode();
            item.setName("limit_pay");
            item.setValue(limitPay);
            result.children().add(item);
        }
        if(!Text.isBlank(openId)) {
            item = new XMLNode();
            item.setName("openid");
            item.setValue(openId);
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
     * 解析生成统一下单请求结构体
     *
     * @param node XML节点
     * @return 统一下单请求结构体
     */
    public static UnifiedOrderRequest parse(IXMLNode node) {
        if(null == node) {
            return null;
        }
        UnifiedOrderRequest result = new UnifiedOrderRequest();
        for(IXMLNode item : node.children()) {
            if("appid".equalsIgnoreCase(item.getName())) {
                result.appId = item.getValue();
            }
            else if("mch_id".equalsIgnoreCase(item.getName())) {
                result.merchantId = item.getValue();
            }
            else if("device_info".equalsIgnoreCase(item.getName())) {
                result.deviceInfo = item.getValue();
            }
            else if("nonce_str".equalsIgnoreCase(item.getName())) {
                result.nonceString = item.getValue();
            }
            else if("body".equalsIgnoreCase(item.getName())) {
                result.body = item.getValue();
            }
            else if("detail".equalsIgnoreCase(item.getName())) {
                result.detail = item.getValue();
            }
            else if("attach".equalsIgnoreCase(item.getName())) {
                result.attach = item.getValue();
            }
            else if("out_trade_no".equalsIgnoreCase(item.getName())) {
                result.tradeNo = item.getValue();
            }
            else if("fee_type".equalsIgnoreCase(item.getName())) {
                result.feeType = item.getValue();
            }
            else if("total_fee".equalsIgnoreCase(item.getName())) {
                result.totalFee = Integer.valueOf(item.getValue());
            }
            else if("spbill_create_ip".equalsIgnoreCase(item.getName())) {
                result.createIP = item.getValue();
            }
            else if("time_start".equalsIgnoreCase(item.getName())) {
                result.startTime = item.getValue();
            }
            else if("time_expire".equalsIgnoreCase(item.getName())) {
                result.expireTime = item.getValue();
            }
            else if("goods_tag".equalsIgnoreCase(item.getName())) {
                result.goodsTag = item.getValue();
            }
            else if("notify_url".equalsIgnoreCase(item.getName())) {
                result.notifyUrl = item.getValue();
            }
            else if("trade_type".equalsIgnoreCase(item.getName())) {
                result.tradeType = item.getValue();
            }
            else if("product_id".equalsIgnoreCase(item.getName())) {
                result.productId = item.getValue();
            }
            else if("limit_pay".equalsIgnoreCase(item.getName())) {
                result.limitPay = item.getValue();
            }
            else if("openid".equalsIgnoreCase(item.getName())) {
                result.openId = item.getValue();
            }
            else if("sign".equalsIgnoreCase(item.getName())) {
                result.sign = item.getValue();
            }
            else {
                logger.warn("unknown xml node in unifiedorder request item = " + item.toString() + "\nxml = " + node.toString());
            }
        }
        return result;
    }

    /**
     * 构建统一下单请求结构体
     *
     * @param productId 商品ID
     * @param tradeNo 交易流水号
     * @param fee 交易金额（分）
     * @param openId 用户开放ID
     * @param ip 发起者IP
     * @return 统一下单请求结构体
     */
    public static UnifiedOrderRequest build(int productId, String tradeNo, int fee, String openId, String ip) {
        UnifiedOrderRequest result = new UnifiedOrderRequest();
        result.appId = Config.appId;
        result.merchantId = Config.merchantId;
        result.deviceInfo = "WEB";
        result.nonceString = Serial.makeRandomString(32).toUpperCase();
        result.body = "“女神的晚餐”线下活动费用";
        result.attach = String.valueOf(productId);
        result.tradeNo = tradeNo;
        result.totalFee = fee;
        result.createIP = ip;
        DateTime startTime = DateTime.now();
        result.startTime = startTime.toString("yyyyMMddHHmmss");
        startTime = startTime.add(Duration.createHours(2));
        result.expireTime = startTime.toString("yyyyMMddHHmmss");
        result.notifyUrl = Config.notiyUrl;
        result.tradeType = "JSAPI";
        result.limitPay = "no_credit";
        result.openId = openId;
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
