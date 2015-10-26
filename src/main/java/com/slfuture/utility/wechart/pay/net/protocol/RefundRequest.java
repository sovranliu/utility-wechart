package com.slfuture.utility.wechart.pay.net.protocol;

import com.slfuture.carrie.base.etc.Serial;
import com.slfuture.carrie.base.text.Text;
import com.slfuture.carrie.base.xml.XMLNode;
import com.slfuture.carrie.base.xml.core.IXMLNode;
import com.slfuture.utility.wechart.utility.Config;
import com.slfuture.utility.wechart.utility.Tool;
import org.apache.log4j.Logger;

/**
 * 退款请求结构体
 */
public class RefundRequest {
    /**
     * 日志对象
     */
    public static Logger logger = Logger.getLogger(RefundRequest.class);


    /**
     * 属性
     */
    public String appId = null;
    public String merchantId = null;
    public String deviceInfo = null;
    public String nonceString = null;
    public String sign = null;
    public String transactionId = null;
    public String tradeNo = null;
    public String refundTradeNo = null;
    // 总金额（分）
    public int totalFee = 0;
    public int refundFee = 0;
    public String refundFeeType = null;


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
        if(!Text.isBlank(transactionId)) {
            item = new XMLNode();
            item.setName("transaction_id");
            item.setValue(transactionId);
            result.children().add(item);
        }
        if(!Text.isBlank(tradeNo)) {
            item = new XMLNode();
            item.setName("out_trade_no");
            item.setValue(tradeNo);
            result.children().add(item);
        }
        if(!Text.isBlank(refundTradeNo)) {
            item = new XMLNode();
            item.setName("out_refund_no");
            item.setValue(refundTradeNo);
            result.children().add(item);
        }
        if(totalFee >= 0) {
            item = new XMLNode();
            item.setName("total_fee");
            item.setValue(String.valueOf(totalFee));
            result.children().add(item);
        }
        if(refundFee >= 0) {
            item = new XMLNode();
            item.setName("refund_fee");
            item.setValue(String.valueOf(refundFee));
            result.children().add(item);
        }
        if(!Text.isBlank(refundFeeType)) {
            item = new XMLNode();
            item.setName("refund_fee_type");
            item.setValue(refundFeeType);
            result.children().add(item);
        }
        if(!Text.isBlank(merchantId)) {
            item = new XMLNode();
            item.setName("op_user_id");
            item.setValue(merchantId);
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
    public static RefundRequest parse(IXMLNode node) {
        if(null == node) {
            return null;
        }
        RefundRequest result = new RefundRequest();
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
            else if("sign".equalsIgnoreCase(item.getName())) {
                result.sign = item.getValue();
            }
            else if("transaction_id".equalsIgnoreCase(item.getName())) {
                result.transactionId = item.getValue();
            }
            else if("out_trade_no".equalsIgnoreCase(item.getName())) {
                result.tradeNo = item.getValue();
            }
            else if("out_refund_no".equalsIgnoreCase(item.getName())) {
                result.refundTradeNo = item.getValue();
            }
            else if("total_fee".equalsIgnoreCase(item.getName())) {
                result.totalFee = Integer.valueOf(item.getValue());
            }
            else if("refund_fee".equalsIgnoreCase(item.getName())) {
                result.refundFee = Integer.valueOf(item.getValue());
            }
            else if("refund_fee_type".equalsIgnoreCase(item.getName())) {
                result.refundFeeType = item.getValue();
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
     * @param refundTradeNo 退款交易流水号
     * @param transactionId 交易号
     * @param totalFee 交易金额（分）
     * @param refundFee 退款金额（分）
     * @return 退款请求结构体
     */
    public static RefundRequest build(String tradeNo, String refundTradeNo, String transactionId, int totalFee, int refundFee) {
        RefundRequest result = new RefundRequest();
        result.appId = Config.appId;
        result.merchantId = Config.merchantId;
        result.deviceInfo = "WEB";
        result.nonceString = Serial.makeRandomString(32).toUpperCase();
        result.transactionId = transactionId;
        result.tradeNo = tradeNo;
        result.refundTradeNo = refundTradeNo;
        result.totalFee = totalFee;
        result.refundFee = refundFee;
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
