package com.slfuture.utility.wechart.pay.structure;

import com.slfuture.carrie.base.etc.Serial;
import com.slfuture.carrie.base.time.DateTime;
import com.slfuture.carrie.base.type.Table;
import com.slfuture.utility.wechart.utility.Config;
import com.slfuture.utility.wechart.utility.Tool;

/**
 * 统一下单结果类
 */
public class UnifiedOrderResult {
    /**
     * 结果代码：成功
     */
    public final static int CODE_SUCCESS = 0;
    /**
     * 结果代码：重复支付
     */
    public final static int CODE_REPEATED = -1;
    /**
     * 结果代码：过期
     */
    public final static int CODE_EXPIRE = -2;


    /**
     * 结果代码
     */
    public int code = 0;
    /**
     * 订单号
     */
    public String tradeNo = null;
    /**
     * 预支付交易会话标识
     */
    public String prepayId = null;
    /**
     * 随机字符串
     */
    public String nonceString = null;
    /**
     * 签名方式
     */
    public String signType = "MD5";
    /**
     * 时间戳
     */
    public long timestamp = 0;
    /**
     * 签名
     */
    public String signature = null;


    /**
     * 构造函数
     */
    public UnifiedOrderResult() { }

    /**
     * 构造函数
     *
     * @param code 结果代码
     */
    public UnifiedOrderResult(int code) {
        this.code = code;
    }

    /**
     * 构造函数
     *
     * @param code 结果代码
     * @param tradeNo 交易流水号
     * @param prepayId 预支付ID
     */
    public UnifiedOrderResult(int code, String tradeNo, String prepayId) {
        this.code = code;
        this.tradeNo = tradeNo;
        this.prepayId = prepayId;
        //
        this.nonceString = Serial.makeRandomString(32).toUpperCase();
        this.timestamp = DateTime.now().toLong();
        Table<String, Object> signTable = new Table<String, Object>();
        signTable.put("appId", Config.appId);
        signTable.put("timeStamp", this.timestamp);
        signTable.put("nonceStr", this.nonceString);
        signTable.put("package", "prepay_id=" + this.prepayId);
        signTable.put("signType", signType);
        this.signature = Tool.makeSign(signTable);
    }
}
