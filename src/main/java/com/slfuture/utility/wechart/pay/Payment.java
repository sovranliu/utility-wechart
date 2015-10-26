package com.slfuture.utility.wechart.pay;

import com.slfuture.carrie.base.text.Text;
import com.slfuture.carrie.base.type.Table;
import com.slfuture.utility.wechart.pay.net.Communicator;
import com.slfuture.utility.wechart.pay.net.protocol.*;
import com.slfuture.utility.wechart.pay.structure.PayResult;
import com.slfuture.utility.wechart.pay.structure.RefundResult;
import com.slfuture.utility.wechart.pay.structure.TransferResult;
import com.slfuture.utility.wechart.pay.structure.UnifiedOrderResult;
import com.slfuture.utility.wechart.utility.Config;
import com.slfuture.utility.wechart.utility.DB;
import com.slfuture.carrie.base.etc.Serial;
import com.slfuture.carrie.base.time.DateTime;
import com.slfuture.carrie.base.type.List;
import com.slfuture.carrie.base.type.Record;
import com.slfuture.carrie.base.type.core.ICollection;
import com.slfuture.carrie.base.xml.XMLNode;
import com.slfuture.carrie.base.xml.core.IXMLNode;
import com.slfuture.utility.wechart.utility.Tool;
import org.apache.log4j.Logger;

/**
 * 微信支付类
 */
public class Payment {
    /**
     * 日志对象
     */
    public static Logger logger = Logger.getLogger(Payment.class);


    /**
     * 隐藏构造函数
     */
    private Payment() { }

    /**
     * 统一下单
     *
     * @param productId 产品ID
     * @param fee 产品名称
     * @param ip 支付者IP
     * @param openId 用户微信开放ID
     * @return 统一下单结果
     */
    public static UnifiedOrderResult unifiedOrder(int productId, int fee, String openId, String ip) throws Exception {
        logger.info("unifiedOrder(" + productId + ", " + fee + ", " + openId + ", " + ip + ") start");
        Record record = DB.executor().load("SELECT OpenID, TradeNo FROM Pay_Trade WHERE ProductID = " + productId);
        if(null != record) {
            if(openId.equals(record.getString("OpenID"))) {
                logger.info("unifiedOrder(" + productId + ", " + fee + ", " + openId + ", " + ip + ") end repeated");
                return new UnifiedOrderResult(UnifiedOrderResult.CODE_REPEATED);
            }
            else {
                logger.info("unifiedOrder(" + productId + ", " + fee + ", " + openId + ", " + ip + ") end expire");
                return new UnifiedOrderResult(UnifiedOrderResult.CODE_EXPIRE);
            }
        }
        record = DB.executor().load("SELECT TradeNo, PrepayID FROM Pay_Order WHERE ProductID = " + productId + " AND OpenID = '" + openId + "' AND AddTime > DATE_SUB(NOW(), INTERVAL 1 HOUR) ORDER BY ID DESC");
        if(null != record) {
            logger.info("unifiedOrder(" + productId + ", " + fee + ", " + openId + ", " + ip + ") end success over");
            return new UnifiedOrderResult(UnifiedOrderResult.CODE_SUCCESS, record.getString("TradeNo"), record.getString("PrepayID"));
        }
        String tradeNo = generateTradeNo(productId);
        // 网络
        UnifiedOrderRequest unifiedOrderRequest = UnifiedOrderRequest.build(productId, tradeNo, fee, openId, ip);
        logger.info("Prptocol:unifiedOrder send = \n" + unifiedOrderRequest);
        UnifiedOrderResponse unifiedOrderResponse = Communicator.unifiedOrder(unifiedOrderRequest);
        logger.info("Prptocol:unifiedOrder receive = \n" + unifiedOrderResponse);
        DB.executor().alter("INSERT INTO Pay_Order (ProductID, Fee, OpenID, IP, TradeNo, PrepayID, AddTime, UpdateTime) VALUES (" + productId + ", " + fee + ", '" + openId + "', '" + ip + "', '" + tradeNo + "', '" + unifiedOrderResponse.prepayId + "', NOW(), NOW())");
        UnifiedOrderResult result = new UnifiedOrderResult(UnifiedOrderResult.CODE_SUCCESS, tradeNo, unifiedOrderResponse.prepayId);
        Table<String, Object> map = new Table<String, Object>();
        map.put("appId", Config.appId);
        map.put("timeStamp", result.timestamp);
        map.put("nonceStr", result.nonceString);
        map.put("package", "prepay_id=" + result.prepayId);
        map.put("signType", "MD5");
        result.signature = Tool.makeSign(map);
        logger.info("unifiedOrder(" + productId + ", " + fee + ", " + openId + ", " + ip + ") end success");
        return result;
    }

    /**
     * 收款通知
     *
     * @param content 收到的确认信息
     * @return 收款结果，0：成功，1：重复通知，2：收款重复，-1：数据异常，-2：字段异常，-3：内部异常
     */
    public static PayResult onPay(String content) {
        logger.info("notifyPay:\n" + content);
        NotifyPayResponse notifyPayResponse = NotifyPayResponse.parse(XMLNode.convert(content));
        if(null == notifyPayResponse) {
            logger.error("parse failed in notifyPay(" + content + ")");
            return null;
        }
        if(!Text.isNumber(notifyPayResponse.attach)) {
            logger.error("attach error in notifyPay(" + content + ")");
            return null;
        }
        Record record = DB.executor().load("SELECT ID FROM Pay_Receive WHERE ProductID = " + notifyPayResponse.attach + " AND OpenID = '" + notifyPayResponse.openId + "'");
        if(null != record) {
            logger.warn("receive repeated notify :" + notifyPayResponse.tradeNo);
            return new PayResult(PayResult.CODE_SUCCESS, Integer.valueOf(notifyPayResponse.attach), notifyPayResponse.openId, notifyPayResponse.totalFee);
        }
        String endTimeString = "null";
        if(null != notifyPayResponse.endTime) {
            endTimeString = "'" + notifyPayResponse.endTime.toString() + "'";
        }
        Long result = DB.executor().insert("INSERT INTO Pay_Receive (ProductID, OpenID, TradeType, BankType, Fee, FeeType, CashFee, CashFeeType, TransactionID, TradeNo, EndTime, AddTime) VALUES (" + notifyPayResponse.attach + ", '" + notifyPayResponse.openId + "', '" + notifyPayResponse.tradeType + "', '" + notifyPayResponse.bankType + "', " + notifyPayResponse.totalFee + ", '" + notifyPayResponse.feeType + "', " + notifyPayResponse.cashFee + ", '" + notifyPayResponse.cashFeeType + "', '" + notifyPayResponse.transactionId + "', '" + notifyPayResponse.tradeNo + "', " + endTimeString + ", NOW())");
        if(null != result && -2 == result) {
            logger.warn("receive repeated notify in db :" + notifyPayResponse.tradeNo);
            return new PayResult(PayResult.CODE_SUCCESS, Integer.valueOf(notifyPayResponse.attach), notifyPayResponse.openId, notifyPayResponse.totalFee);
        }
        //
        record = DB.executor().load("SELECT * FROM Pay_Trade WHERE ProductID = " + notifyPayResponse.attach);
        if(null != record) {
            return new PayResult(PayResult.CODE_REPEAT, Integer.valueOf(notifyPayResponse.attach), notifyPayResponse.openId, notifyPayResponse.totalFee);
        }
        result = DB.executor().insert("INSERT INTO Pay_Trade (ProductID, Fee, OpenID, Status, TradeNo, AddTime, UpdateTime) VALUES (" + notifyPayResponse.attach + ", " + notifyPayResponse.totalFee + ", '" + notifyPayResponse.openId + "', 1, '" + notifyPayResponse.tradeNo + "', NOW(), NOW())");
        if(null != result && -2 == result) {
            return new PayResult(PayResult.CODE_REPEAT, Integer.valueOf(notifyPayResponse.attach), notifyPayResponse.openId, notifyPayResponse.totalFee);
        }
        else if(result > 0) {
            return new PayResult(PayResult.CODE_SUCCESS, Integer.valueOf(notifyPayResponse.attach), notifyPayResponse.openId, notifyPayResponse.totalFee);
        }
        return new PayResult(PayResult.CODE_ERROR, Integer.valueOf(notifyPayResponse.attach), notifyPayResponse.openId, notifyPayResponse.totalFee);
    }

    /**
     * 退款
     *
     * @param productId 产品ID
     * @param openId 用户开放ID
     * @param fee 退款金额
     * @return 退款结果
     */
    public static RefundResult refund(int productId, String openId, int fee) throws Exception {
        Record record = DB.executor().load("SELECT ID, TradeNo, TransactionID FROM Pay_Receive WHERE ProductID = " + productId + " AND OpenID = '" + openId + "'");
        if(null == record) {
            logger.error("refund without receive :" + productId + ", " + openId + ", " + fee);
            return new RefundResult(RefundResult.CODE_NORECEIVE, null, null);
        }
        String tradeNo = record.getString("TradeNo");
        String transactionId = record.getString("TransactionID");
        record = DB.executor().load("SELECT ID, TradeNo, RefundNo FROM Pay_Refund WHERE ProductID = " + productId + " AND OpenID = '" + openId + "'");
        if(null != record) {
            logger.error("refund repeat :" + productId + ", " + openId + ", " + fee);
            return new RefundResult(RefundResult.CODE_SUCCESS, record.getString("TradeNo"), record.getString("RefundNo"));
        }
        String sql = "UPDATE Pay_Trade SET Status = 2 WHERE Status = 1 AND ProductID = " + productId + " AND OpenID = '" + openId + "'";
        if(1 != DB.executor().alter(sql)) {
            logger.error("refund repeat :" + productId + ", " + openId + ", " + fee + ", Pay_Trade status not right");
            return new RefundResult(RefundResult.CODE_ERROR, record.getString("TradeNo"), record.getString("RefundNo"));
        }
        //
        RefundResult result = new RefundResult();
        // 网络
        String refundTradeNo = generateTradeNo(productId);
        RefundRequest refundRequest = RefundRequest.build(tradeNo, refundTradeNo, transactionId, fee, fee);
        logger.info("Prptocol:refund send = \n" + refundRequest);
        RefundResponse refundResponse = Communicator.refund(refundRequest);
        logger.info("Prptocol:refund receive = \n" + refundResponse);
        Long index = DB.executor().insert("INSERT INTO Pay_Refund (ProductID, OpenID, Fee, TradeNo, RefundNo, AddTime, UpdateTime) VALUES (" + productId + ", '" + openId + "', " + fee + ", '" + tradeNo + "', '" + refundTradeNo + "', NOW(), NOW())");
        if(null != index && -2 == index) {
            logger.warn("refund repeated notify in db : productId = " + productId + ", openId = " + openId);
            return new RefundResult(PayResult.CODE_SUCCESS, refundResponse.tradeNo, refundResponse.refundTradeNo);
        }
        result.code = RefundResult.CODE_SUCCESS;
        result.tradeNo = refundResponse.tradeNo;
        result.refundTradeNo = refundResponse.refundTradeNo;
        return result;
    }

    /**
     * 退款
     *
     * @param productId 产品ID
     * @param name 用户姓名
     * @param openId 用户开放ID
     * @param amount 交易金额
     * @return 退款结果
     */
    public static TransferResult transfer(int productId, String name, String openId, int amount, String description, String ip) throws Exception {
        logger.info("Payment.transfer("  + productId + ", " + name + ", " + openId + ", " + amount + ") executed");
        Record record = DB.executor().load("SELECT TradeNo, Status FROM Pay_Trade WHERE ProductID = " + productId);
        if(null == record) {
            logger.error("transfer with no trade in product:" + productId);
            return new TransferResult(TransferResult.CODE_ERROR);
        }
        if(1 != record.getInteger("Status")) {
            logger.error("transfer with trade in status " + record.getInteger("Status") + " in product:" + productId + ", openId:" + openId);
            return new TransferResult(TransferResult.CODE_ERROR);
        }
        String tradeNo = record.getString("TradeNo");
        String sql = "UPDATE Pay_Trade SET Status = 3 WHERE Status = 1 AND ProductID = " + productId;
        if(1 != DB.executor().alter(sql)) {
            logger.error("transfer repeat :" + productId + ", " + name + ", " + openId + ", " + amount + ", " + description + ", Pay_Trade status not right");
            return new TransferResult(TransferResult.CODE_ERROR);
        }
        ICollection<Record> set = DB.executor().select("SELECT ID FROM Pay_Transfer WHERE Status IN (0, 1) AND ProductID = " + productId);
        if(set.size() > 0) {
            logger.error("multi transfer in product:" + productId);
            return new TransferResult(TransferResult.CODE_ERROR);
        }
        TransferResult result = new TransferResult();
        int part = 0;
        while(true) {
            int fee = 0;
            if(amount > 20000) {
                fee = 20000;
                amount -= 20000;
            }
            else if(0 == amount) {
                result.code = RefundResult.CODE_SUCCESS;
                break;
            }
            else {
                fee = amount;
                amount = 0;
            }
            part++;
            String transferTradeNo = generateTradeNo(productId);
            Long transferId = DB.executor().insert("INSERT INTO Pay_Transfer (ProductID, Part, TradeNo, OpenID, Name, Amount, Status, ErrCode, PaymentNo, AddTime, UpdateTime) VALUES (" + productId + ", " + part + ", '" + transferTradeNo + "', '"  + openId + "', '" + name + "', " + fee + ", 0, NULL, NULL, NOW(), NOW())");
            if(null == transferId || 0 == transferId) {
                logger.error("insert Pay_Transfer failed");
                return new TransferResult(TransferResult.CODE_ERROR);
            }
            // 网络
            TransferRequest transferRequest = TransferRequest.build(tradeNo, openId, name, fee, description, ip);
            logger.info("Prptocol:transfer send = \n" + transferRequest);
            TransferResponse transferResponse = Communicator.transfer(transferRequest);
            logger.info("Prptocol:transfer receive = \n" + transferResponse);
            if("SUCCESS".equalsIgnoreCase(transferResponse.resultCode) && "SUCCESS".equalsIgnoreCase(transferResponse.resultCode)) {
                DB.executor().alter("UPDATE Pay_Transfer SET Status = 1, PaymentNo = '" + transferResponse.paymentNo + "', UpdateTime = NOW() WHERE TradeNo = '" + transferTradeNo + "'");
                if(null == result.paymentNoList) {
                    result.paymentNoList = new List<String>();
                }
                result.paymentNoList.add(transferResponse.paymentNo);
                result.paymentTime = transferResponse.paymentTime;
            }
            else {
                if("SYSTEMERROR".equalsIgnoreCase(transferResponse.errCode)) {
                    transferResponse = Communicator.transfer(transferRequest);
                    logger.info("Prptocol:transfer receive = \n" + transferResponse);
                    if("SUCCESS".equalsIgnoreCase(transferResponse.resultCode) && "SUCCESS".equalsIgnoreCase(transferResponse.resultCode)) {
                        DB.executor().alter("UPDATE Pay_Transfer SET Status = 1, PaymentNo = '" + transferResponse.paymentNo + "', UpdateTime = NOW() WHERE TradeNo = '" + transferTradeNo + "'");
                    }
                    else {
                        DB.executor().alter("UPDATE Pay_Transfer SET Status = 2, ErrCode = '" + transferResponse.errCode + "', UpdateTime = NOW() WHERE TradeNo = '" + transferTradeNo + "'");
                        result.code = TransferResult.CODE_ERROR;
                        break;
                    }
                }
                else if("NAME_MISMATCH".equalsIgnoreCase(transferResponse.errCode)) {
                    DB.executor().alter("UPDATE Pay_Transfer SET Status = 2, ErrCode = '" + transferResponse.errCode + "', UpdateTime = NOW() WHERE TradeNo = '" + transferTradeNo + "'");
                    result.code = TransferResult.CODE_NAME_MISSMATCH;
                    break;
                }
                else if("CODE_NOTENOUGH".equalsIgnoreCase(transferResponse.errCode)) {
                    DB.executor().alter("UPDATE Pay_Transfer SET Status = 2, ErrCode = '" + transferResponse.errCode + "', UpdateTime = NOW() WHERE TradeNo = '" + transferTradeNo + "'");
                    result.code = TransferResult.CODE_NOTENOUGH;
                    break;
                }
                else {
                    DB.executor().alter("UPDATE Pay_Transfer SET Status = 2, ErrCode = '" + transferResponse.errCode + "', UpdateTime = NOW() WHERE TradeNo = '" + transferTradeNo + "'");
                    result.code = TransferResult.CODE_ERROR;
                    break;
                }
            }
        }
        return result;
    }

    /**
     * 生成交易流水号
     *
     * @param productId 产品ID
     * @return 交易流水号
     */
    private static synchronized String generateTradeNo(int productId) {
        String result = String.valueOf(productId);
        result += DateTime.now().toLong();
        result += Serial.makeLoopInteger();
        result += Serial.makeRandomString(32);
        return result.substring(0, 32);
    }
}
