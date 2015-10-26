package com.slfuture.utility.wechart.authorize;

import com.slfuture.carrie.base.json.JSONObject;
import com.slfuture.carrie.base.json.JSONString;
import com.slfuture.carrie.base.time.DateTime;
import com.slfuture.carrie.utility.net.http.HttpUtil;
import com.slfuture.carrie.utility.net.http.Option;
import com.slfuture.utility.wechart.utility.Config;
import com.slfuture.utility.wechart.utility.XMLHttpsUtil;
import org.apache.log4j.Logger;

/**
 * 访问票据管理类
 */
public class JSTicketAccess {
    /**
     * 获取凭据URL
     */
    private final static String URL_GETTICKET = "https://api.weixin.qq.com/cgi-bin/ticket/getticket?access_token=[ACCESS_TOKEN]&type=jsapi";
    /**
     * 缓存周期
     */
    public final static long PERIOD_CACHE = 3600 * 1000;
    /**
     * 日志对象
     */
    private static Logger logger = Logger.getLogger("pay");
    /**
     * 缓存的票据
     */
    private static String token = null;
    /**
     * 缓存时间
     */
    private static long tick = 0;


    /**
     * 隐藏构造函数
     */
    private JSTicketAccess() { }


    /**
     * 获取票据
     *
     * @return 有效票据
     */
    public static String getTicket() {
        if(DateTime.now().toLong() > PERIOD_CACHE + tick) {
            String newToken = fetchTicket();
            if(null != newToken) {
                token = newToken;
            }
        }
        return token;
    }

    /**
     * 获取票据
     *
     * @return 有效票据
     */
    public synchronized static String fetchTicket() {
        if(DateTime.now().toLong() < PERIOD_CACHE + tick) {
            return token;
        }
        String url = URL_GETTICKET.replace("[ACCESS_TOKEN]", TokenAccess.getToken());
        String result = null;
        try {
            result = XMLHttpsUtil.get(url);
        }
        catch(Exception ex) {
            logger.error("call HttpUtil.send(" + url + ") failed", ex);
            return null;
        }
        JSONObject json = JSONObject.convert(result);
        if(null == json) {
            logger.error("empty response in HttpUtil.send(" + url + ")");
            return null;
        }
        JSONString ticket = (JSONString) json.get("ticket");
        if(null == ticket) {
            logger.error("error response in HttpUtil.send(" + url + "), " + token);
            return null;
        }
        tick = DateTime.now().toLong();
        return ticket.getValue();
    }
}
