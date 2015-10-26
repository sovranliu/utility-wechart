package com.slfuture.utility.wechart.pay.net.protocol;

import com.slfuture.carrie.base.text.Text;
import com.slfuture.carrie.base.xml.XMLNode;
import com.slfuture.carrie.base.xml.core.IXMLNode;
import org.apache.log4j.Logger;

/**
 * 退款反馈结构体
 */
public class RefundResponse {
    /**
     * 日志对象
     */
    public static Logger logger = Logger.getLogger(RefundResponse.class);


    /**
     * 属性
     */
    public String returnCode = null;
    public String returnMsg = null;
    public String resultCode = null;
    public String errCode = null;
    public String errCodeDes = null;
    public String appId = null;
    public String merchantId = null;
    public String deviceInfo = null;
    public String nonceString = null;
    public String sign = null;
    public String transactionId = null;
    public String tradeNo = null;
    public String refundTradeNo = null;
    public String refundId = null;
    public String refundChannel = null;
    // 总金额（分）
    public int refundFee = 0;
    public int totalFee = 0;
    public String feeType = null;
    public int cashFee = 0;
    public int cashRefundFee = 0;
    public int couponRefundFee = 0;
    public int couponRefundCount = 0;
    public String couponRefundId = null;


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
        if(!Text.isBlank(returnCode)) {
            item = new XMLNode();
            item.setName("return_code");
            item.setValue(returnCode);
            result.children().add(item);
        }
        if(!Text.isBlank(returnMsg)) {
            item = new XMLNode();
            item.setName("return_msg");
            item.setValue(returnMsg);
            result.children().add(item);
        }
        if(!Text.isBlank(resultCode)) {
            item = new XMLNode();
            item.setName("result_code");
            item.setValue(resultCode);
            result.children().add(item);
        }
        if(!Text.isBlank(errCode)) {
            item = new XMLNode();
            item.setName("err_code");
            item.setValue(errCode);
            result.children().add(item);
        }
        if(!Text.isBlank(errCodeDes)) {
            item = new XMLNode();
            item.setName("err_code_des");
            item.setValue(errCodeDes);
            result.children().add(item);
        }
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
        if(!Text.isBlank(sign)) {
            item = new XMLNode();
            item.setName("sign");
            item.setValue(sign);
            result.children().add(item);
        }
        if(!Text.isBlank(transactionId )) {
            item = new XMLNode();
            item.setName("trade_type");
            item.setValue(transactionId );
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
        if(!Text.isBlank(refundId)) {
            item = new XMLNode();
            item.setName("refund_id");
            item.setValue(refundId);
            result.children().add(item);
        }
        if(!Text.isBlank(refundChannel)) {
            item = new XMLNode();
            item.setName("refund_channel");
            item.setValue(refundChannel);
            result.children().add(item);
        }
        if(true) {
            item = new XMLNode();
            item.setName("refund_fee");
            item.setValue(String.valueOf(refundFee));
            result.children().add(item);
        }
        if(true) {
            item = new XMLNode();
            item.setName("total_fee");
            item.setValue(String.valueOf(totalFee));
            result.children().add(item);
        }
        if(!Text.isBlank(feeType)) {
            item = new XMLNode();
            item.setName("fee_type");
            item.setValue(feeType);
            result.children().add(item);
        }
        if(true) {
            item = new XMLNode();
            item.setName("cash_fee");
            item.setValue(String.valueOf(cashFee));
            result.children().add(item);
        }
        if(cashRefundFee >= 0) {
            item = new XMLNode();
            item.setName("cash_refund_fee");
            item.setValue(String.valueOf(cashRefundFee));
            result.children().add(item);
        }
        if(couponRefundFee >= 0) {
            item = new XMLNode();
            item.setName("coupon_refund_fee");
            item.setValue(String.valueOf(couponRefundFee));
            result.children().add(item);
        }
        if(couponRefundCount >= 0) {
            item = new XMLNode();
            item.setName("coupon_refund_count");
            item.setValue(String.valueOf(couponRefundCount));
            result.children().add(item);
        }
        if(!Text.isBlank(couponRefundId)) {
            item = new XMLNode();
            item.setName("coupon_refund_id");
            item.setValue(String.valueOf(couponRefundId));
            result.children().add(item);
        }
        return result;
    }

    /**
     * 解析生成退款反馈结构体
     *
     * @param node XML节点
     * @return 退款反馈结构体
     */
    public static RefundResponse parse(IXMLNode node) {
        if(null == node) {
            return null;
        }
        RefundResponse result = new RefundResponse();
        for(IXMLNode item : node.children()) {
            if("return_code".equalsIgnoreCase(item.getName())) {
                result.returnCode = item.getValue();
            }
            else if("return_msg".equalsIgnoreCase(item.getName())) {
                result.returnMsg = item.getValue();
            }
            else if("result_code".equalsIgnoreCase(item.getName())) {
                result.resultCode = item.getValue();
            }
            else if("err_code".equalsIgnoreCase(item.getName())) {
                result.errCode = item.getValue();
            }
            else if("err_code_des".equalsIgnoreCase(item.getName())) {
                result.errCodeDes = item.getValue();
            }
            else if("appid".equalsIgnoreCase(item.getName())) {
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
            else if("refund_id".equalsIgnoreCase(item.getName())) {
                result.refundId = item.getValue();
            }
            else if("refund_channel".equalsIgnoreCase(item.getName())) {
                result.refundChannel = item.getValue();
            }
            else if("refund_fee".equalsIgnoreCase(item.getName())) {
                result.refundFee = Integer.valueOf(item.getValue());
            }
            else if("total_fee".equalsIgnoreCase(item.getName())) {
                result.totalFee = Integer.valueOf(item.getValue());
            }
            else if ("fee_type".equalsIgnoreCase(item.getName())) {
                result.feeType = item.getValue();
            }
            else if("cash_fee".equalsIgnoreCase(item.getName())) {
                result.cashFee = Integer.valueOf(item.getValue());
            }
            else if("cash_refund_fee".equalsIgnoreCase(item.getName())) {
                result.cashRefundFee = Integer.valueOf(item.getValue());
            }
            else if("coupon_refund_fee".equalsIgnoreCase(item.getName())) {
                result.couponRefundFee = Integer.valueOf(item.getValue());
            }
            else if("coupon_refund_count".equalsIgnoreCase(item.getName())) {
                result.couponRefundCount = Integer.valueOf(item.getValue());
            }
            else if("coupon_refund_id".equalsIgnoreCase(item.getName())) {
                result.couponRefundId = item.getValue();
            }
            else if("no_credit".equalsIgnoreCase(item.getName())) {

            }
            else {
                logger.warn("unknown xml node in refund response item = " + item.toString() + "\nxml = " + node.toString());
            }
        }
        return result;
    }

    /**
     * 构建退款反馈结构体
     *
     * @param tradeNo 交易流水号
     * @param refundTradeNo 退款交易流水号
     * @param totalFee 交易金额（分）
     * @param refundFee 退款金额（分）
     * @return 退款反馈结构体
     */
    public static RefundResponse build(String tradeNo, String refundTradeNo, int totalFee, int refundFee) {
        RefundResponse result = new RefundResponse();
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
