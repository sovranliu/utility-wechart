package com.slfuture.utility.wechart.pay;

import com.slfuture.utility.wechart.message.Pusher;
import com.slfuture.utility.wechart.utility.DB;
import com.slfuture.utility.wechart.utility.XMLHttpsUtil;

/**
 * 测试类
 */
public class Test {
    /**
     * 主函数
     */
    public static void main(String[] argv) {
        try {
            String c = XMLHttpsUtil.get("https://api.weixin.qq.com/sns/oauth2/access_token?appid=wx0da5d06336e96520&secret=acbb2cee0681eeb129f6b349a327a6c8&code=0210b4cb4efd68b36bd16bda5638b39w&grant_type=authorization_code");
            System.out.println(c);
            DB.executor().insert("INSERT INTO Pay_Receive (OpenID, TradeType, BankType, Fee, FeeType, CashFee, CashFeeType, TransactionID, TradeNo, EndTime, AddTime) VALUES ('notifyPayResponse.openId', 'treT', 'bankType', 10, 'feeType', 5, 'eType', 'transactionId', 'tradeNo', NOW(), NOW())");
            Pusher.push("abc", "123", null, "url");
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
}
