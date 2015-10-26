package com.slfuture.utility.wechart.pay.net.protocol;

import com.slfuture.carrie.base.text.Text;
import com.slfuture.carrie.base.time.DateTime;
import com.slfuture.carrie.base.xml.XMLNode;
import com.slfuture.carrie.base.xml.core.IXMLNode;
import org.apache.log4j.Logger;

import java.text.ParseException;

/**
 * 退款反馈结构体
 */
public class TransferResponse {
    /**
     * 日志对象
     */
    public static Logger logger = Logger.getLogger(TransferResponse.class);


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
    public String tradeNo = null;
    public String paymentNo = null;
    public DateTime paymentTime = null;


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
        if(!Text.isBlank(sign)) {
            item = new XMLNode();
            item.setName("sign");
            item.setValue(sign);
            result.children().add(item);
        }
        if(!Text.isBlank(tradeNo)) {
            item = new XMLNode();
            item.setName("partner_trade_no");
            item.setValue(tradeNo);
            result.children().add(item);
        }
        if(!Text.isBlank(paymentNo)) {
            item = new XMLNode();
            item.setName("payment_no");
            item.setValue(paymentNo);
            result.children().add(item);
        }
        if(null != paymentTime) {
            item = new XMLNode();
            item.setName("payment_time");
            item.setValue(paymentTime.toString());
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
    public static TransferResponse parse(IXMLNode node) {
        if(null == node) {
            return null;
        }
        TransferResponse result = new TransferResponse();
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
            else if("mch_appid".equalsIgnoreCase(item.getName())) {
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
            else if("payment_no".equalsIgnoreCase(item.getName())) {
                result.paymentNo = item.getValue();
            }
            else if("payment_time".equalsIgnoreCase(item.getName())) {
                try {
                    result.paymentTime = DateTime.parse(item.getValue());
                }
                catch (ParseException e) {
                    logger.error("call DateTime.parse(" + item.getValue() + ") failed", e);
                }
            }
            else {
                logger.warn("unknown xml node in transfer response item = " + item.toString() + "\nxml = " + node.toString());
            }
        }
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
