package com.slfuture.utility.wechart.pay.net.protocol;

import com.slfuture.carrie.base.text.Text;
import com.slfuture.carrie.base.xml.XMLNode;
import com.slfuture.carrie.base.xml.core.IXMLNode;
import org.apache.log4j.Logger;

/**
 * 统一下单反馈结构体
 */
public class UnifiedOrderResponse {
    /**
     * 日志对象
     */
    public static Logger logger = Logger.getLogger(UnifiedOrderResponse.class);


    /**
     * 属性
     */
    public String returnCode = null;
    public String returnMsg = null;
    public String appId = null;
    public String merchantId = null;
    public String deviceInfo = null;
    public String nonceString = null;
    public String sign = null;
    public String resultCode = null;
    public String errCode = null;
    public String errCodeDes = null;
    public String tradeType = null;
    public String prepayId = null;
    public String codeUrl = null;


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
        if(!Text.isBlank(tradeType)) {
            item = new XMLNode();
            item.setName("trade_type");
            item.setValue(tradeType);
            result.children().add(item);
        }
        if(!Text.isBlank(prepayId)) {
            item = new XMLNode();
            item.setName("prepay_id");
            item.setValue(prepayId);
            result.children().add(item);
        }
        if(!Text.isBlank(codeUrl)) {
            item = new XMLNode();
            item.setName("code_url");
            item.setValue(codeUrl);
            result.children().add(item);
        }
        return result;
    }

    /**
     * 解析生成统一下单反馈结构体
     *
     * @param node XML节点
     * @return 统一下单反馈结构体
     */
    public static UnifiedOrderResponse parse(IXMLNode node) {
        if(null == node) {
            return null;
        }
        UnifiedOrderResponse result = new UnifiedOrderResponse();
        for(IXMLNode item : node.children()) {
            if("return_code".equalsIgnoreCase(item.getName())) {
                result.returnCode = item.getValue();
            }
            else if("return_msg".equalsIgnoreCase(item.getName())) {
                result.returnMsg = item.getValue();
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
            else if("result_code".equalsIgnoreCase(item.getName())) {
                result.resultCode = item.getValue();
            }
            else if("err_code".equalsIgnoreCase(item.getName())) {
                result.errCode = item.getValue();
            }
            else if("err_code_des".equalsIgnoreCase(item.getName())) {
                result.errCodeDes = item.getValue();
            }
            else if("trade_type".equalsIgnoreCase(item.getName())) {
                result.tradeType = item.getValue();
            }
            else if("prepay_id".equalsIgnoreCase(item.getName())) {
                result.prepayId = item.getValue();
            }
            else if("code_url".equalsIgnoreCase(item.getName())) {
                result.codeUrl = item.getValue();
            }
            else {
                logger.warn("unknown xml node in unifiedorder response item = " + item.toString() + "\nxml = " + node.toString());
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
