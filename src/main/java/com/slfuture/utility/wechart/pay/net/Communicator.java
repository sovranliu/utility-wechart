package com.slfuture.utility.wechart.pay.net;

import com.slfuture.utility.wechart.pay.net.protocol.*;
import com.slfuture.utility.wechart.utility.XMLHttpsUtil;
import com.slfuture.carrie.base.text.Text;
import com.slfuture.carrie.base.xml.XMLNode;

import java.io.IOException;

/**
 * 微信通信类
 */
public class Communicator {
    /**
     * 统一下单
     */
    public final static String URL_UNIFIEDORDER = "https://api.mch.weixin.qq.com/pay/unifiedorder";
    /**
     * 退款
     */
    public final static String URL_REFUND = "https://api.mch.weixin.qq.com/secapi/pay/refund";
    /**
     * 转款
     */
    public final static String URL_TRANSFERS = "https://api.mch.weixin.qq.com/mmpaymkttransfers/promotion/transfers";
    /**
     * 投递红包
     */
    public final static String URL_SENDREDPACKET = "https://api.mch.weixin.qq.com/mmpaymkttransfers/sendredpack";


    /**
     * 隐藏构造函数
     */
    private Communicator() { }

    /**
     * 统一下单
     *
     * @param request 统一下单参数
     * @return 统一下单结果
     */
    public static UnifiedOrderResponse unifiedOrder(UnifiedOrderRequest request) throws IOException {
        String response = XMLHttpsUtil.post(URL_UNIFIEDORDER, request.toString());
        if(Text.isBlank(response)) {
            throw new IOException("receive empty data in unifiedOrder");
        }
        XMLNode xml = XMLNode.convert(response);
        if(null == xml) {
            throw new IOException("parse data failed in unifiedOrder:" + response);
        }
        return UnifiedOrderResponse.parse(xml);
    }

    /**
     * 退款
     *
     * @param request 退款请求
     * @return 退款反馈
     */
    public static RefundResponse refund(RefundRequest request) throws IOException {
        String response = XMLHttpsUtil.post(URL_REFUND, request.toString());
        if(Text.isBlank(response)) {
            throw new IOException("receive empty data in refund");
        }
        XMLNode xml = XMLNode.convert(response);
        if(null == xml) {
            throw new IOException("parse data failed in refund:" + response);
        }
        return RefundResponse.parse(xml);
    }

    /**
     * 转款
     *
     * @param request 转款请求
     * @return 转款反馈
     */
    public static TransferResponse transfer(TransferRequest request) throws IOException {
        String response = XMLHttpsUtil.post(URL_TRANSFERS, request.toString());
        if(Text.isBlank(response)) {
            throw new IOException("receive empty data in transfer");
        }
        XMLNode xml = XMLNode.convert(response);
        if(null == xml) {
            throw new IOException("parse data failed in transfer:" + response);
        }
        return TransferResponse.parse(xml);
    }

    /**
     * 送红包
     *
     * @param request 红包请求
     * @return 红包反馈
     */
    public static RedPacketResponse sendRedPacket(RedPacketRequest request) throws IOException {
        String response = XMLHttpsUtil.post(URL_SENDREDPACKET, request.toString());
        if(Text.isBlank(response)) {
            throw new IOException("receive empty data in sendredpacket");
        }
        XMLNode xml = XMLNode.convert(response);
        if(null == xml) {
            throw new IOException("parse data failed in sendredpacket:" + response);
        }
        return RedPacketResponse.parse(xml);
    }
}
