package com.slfuture.utility.wechart.pay.structure;

import com.slfuture.carrie.base.time.DateTime;
import com.slfuture.carrie.base.type.core.IList;

/**
 * 退款结果类
 */
public class TransferResult {
    /**
     * 结果代码：成功
     */
    public final static int CODE_SUCCESS = 0;
    /**
     * 结果代码：姓名不匹配
     */
    public final static int CODE_NAME_MISSMATCH = -1;
    /**
     * 结果代码：账户余额不足
     */
    public final static int CODE_NOTENOUGH = -2;
    /**
     * 结果代码：内部错误
     */
    public final static int CODE_ERROR = -3;


    /**
     * 结果代码
     */
    public int code = 0;
    /**
     * 转款微信订单号列表
     */
    public IList<String> paymentNoList = null;
    /**
     * 支付时间
     */
    public DateTime paymentTime = null;


    /**
     * 构造函数
     */
    public TransferResult() { }

    /**
     * 构造函数
     *
     * @param code 结果代码
     */
    public TransferResult(int code) {
        this.code = code;
    }

    /**
     * 构造函数
     *
     * @param code 结果代码
     * @param paymentNoList 转款微信订单号列表
     * @param paymentTime 支付时间
     */
    public TransferResult(int code, IList<String> paymentNoList, DateTime paymentTime) {
        this.code = code;
        this.paymentNoList = paymentNoList;
        this.paymentTime = paymentTime;
    }
}
