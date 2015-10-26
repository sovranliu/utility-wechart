package com.slfuture.utility.wechart.js;

import com.slfuture.carrie.base.etc.Serial;
import com.slfuture.carrie.base.time.DateTime;
import com.slfuture.utility.wechart.authorize.JSTicketAccess;
import com.slfuture.utility.wechart.js.structure.JSConfig;
import com.slfuture.utility.wechart.utility.Config;
import com.slfuture.utility.wechart.utility.Tool;

/**
 * JS 帮助类
 */
public class JSHelper {
    /**
     * 隐藏构造函数
     */
    private JSHelper() { }

    /**
     * 获取JS配置对象
     *
     * @param url 调用URL
     * @return JS配置对象
     */
    public static JSConfig fetchConfig(String url) {
        long tick = DateTime.now().toLong();
        JSConfig result = new JSConfig();
        result.appId = Config.appId;
        result.timestamp = String.valueOf(tick);
        result.url = url;
        result.jsTicket = JSTicketAccess.getTicket();
        result.nonceString = Serial.makeRandomString(32).toUpperCase();
        result.signature = Tool.makeJSSign(result.jsTicket, result.nonceString, tick, result.url);
        return result;
    }
}
