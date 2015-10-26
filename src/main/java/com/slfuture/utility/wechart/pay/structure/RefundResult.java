package com.slfuture.utility.wechart.pay.structure;

/**
 * 退款结果类
 */
public class RefundResult {
    /**
     * 结果代码：成功
     */
    public final static int CODE_SUCCESS = 0;
    /**
     * 结果代码：查不到收款
     */
    public final static int CODE_NORECEIVE = -1;
    /**
     * 结果代码：内部异常
     */
    public final static int CODE_ERROR = -2;


    /**
     * 结果代码
     */
    public int code = 0;
    /**
     * 交易流水号
     */
    public String tradeNo = null;
    /**
     * 退款交易流水号
     */
    public String refundTradeNo = null;


    /**
     * 构造函数
     */
    public RefundResult() { }

    /**
     * 构造函数
     *
     * @param code 结果代码
     * @param tradeNo 交易流水号
     * @param refundTradeNo 交易流水号
     */
    public RefundResult(int code, String tradeNo, String refundTradeNo) {
        this.code = code;
        this.tradeNo = tradeNo;
        this.refundTradeNo = refundTradeNo;
    }
}
