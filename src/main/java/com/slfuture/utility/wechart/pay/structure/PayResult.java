package com.slfuture.utility.wechart.pay.structure;

/**
 * 统一下单结果类
 */
public class PayResult {
    /**
     * 结果代码：成功
     */
    public final static int CODE_SUCCESS = 0;
    /**
     * 结果代码：收款重复
     */
    public final static int CODE_REPEAT = 1;
    /**
     * 结果代码：内部异常
     */
    public final static int CODE_ERROR = -1;


    /**
     * 结果代码
     */
    public int code = 0;
    /**
     * 产品ID
     */
    public int productId = 0;
    /**
     * 用户开放ID
     */
    public String openId = null;
    /**
     * 支付总金额
     */
    public int amount;


    /**
     * 构造函数
     */
    public PayResult() { }

    /**
     * 构造函数
     *
     * @param code 结果代码
     * @param productId 产品ID
     * @param openId 用户开放ID
     * @param amount 支付金额
     */
    public PayResult(int code, int productId, String openId, int amount) {
        this.code = code;
        this.productId = productId;
        this.openId = openId;
        this.amount = amount;
    }
}
