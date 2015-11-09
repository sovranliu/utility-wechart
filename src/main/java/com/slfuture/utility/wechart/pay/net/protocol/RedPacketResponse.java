package com.slfuture.utility.wechart.pay.net.protocol;

import com.slfuture.carrie.base.text.Text;
import com.slfuture.carrie.base.xml.XMLNode;
import com.slfuture.carrie.base.xml.core.IXMLNode;
import org.apache.log4j.Logger;

/**
 * 红包反馈结构体
 */
public class RedPacketResponse {
    /**
     * 日志对象
     */
    public static Logger logger = Logger.getLogger(RedPacketResponse.class);


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
    public String tradeNo = null;
    public String openId = null;
    public int amount = 0;
    public String sign = null;
    public String sendTime = null;
    public String orderlist = null;


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
        if(!Text.isBlank(tradeNo)) {
            item = new XMLNode();
            item.setName("mch_billno");
            item.setValue(tradeNo);
            result.children().add(item);
        }
        if(!Text.isBlank(openId)) {
            item = new XMLNode();
            item.setName("re_openid");
            item.setValue(tradeNo);
            result.children().add(item);
        }
        if(amount >= 0) {
            item = new XMLNode();
            item.setName("total_amount");
            item.setValue(String.valueOf(amount));
            result.children().add(item);
        }
        if(!Text.isBlank(sendTime)) {
            item = new XMLNode();
            item.setName("send_time");
            item.setValue(sendTime);
            result.children().add(item);
        }
        if(!Text.isBlank(orderlist)) {
            item = new XMLNode();
            item.setName("send_listid");
            item.setValue(orderlist);
            result.children().add(item);
        }
        if(!Text.isBlank(sign)) {
            item = new XMLNode();
            item.setName("sign");
            item.setValue(sign);
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
    public static RedPacketResponse parse(IXMLNode node) {
        if(null == node) {
            return null;
        }
        RedPacketResponse result = new RedPacketResponse();
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
            else if("wxappid".equalsIgnoreCase(item.getName())) {
                result.appId = item.getValue();
            }
            else if("mch_id".equalsIgnoreCase(item.getName())) {
                result.merchantId = item.getValue();
            }
            else if("sign".equalsIgnoreCase(item.getName())) {
                result.sign = item.getValue();
            }
            else if("mch_billno".equalsIgnoreCase(item.getName())) {
                result.tradeNo = item.getValue();
            }
            else if("re_openid".equalsIgnoreCase(item.getName())) {
                result.openId = item.getValue();
            }
            else if("total_amount".equalsIgnoreCase(item.getName())) {
                result.amount = Integer.valueOf(item.getValue());
            }
            else if("send_time".equalsIgnoreCase(item.getName())) {
                result.sendTime = item.getValue();
            }
            else if("send_listid".equalsIgnoreCase(item.getName())) {
                result.orderlist = item.getValue();
            }
            else {
                logger.warn("unknown xml node in refund response item = " + item.toString() + "\nxml = " + node.toString());
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
