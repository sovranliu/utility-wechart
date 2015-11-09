package com.slfuture.utility.wechart.authorize;

import com.slfuture.carrie.base.json.JSONNumber;
import com.slfuture.carrie.base.json.JSONObject;
import com.slfuture.utility.wechart.utility.XMLHttpsUtil;
import org.apache.log4j.Logger;

/**
 * 访问票据管理类
 */
public class UserInfoAccess {
    /**
     * 获取用户信息URL
     */
    private final static String URL_USERINFO = "https://api.weixin.qq.com/cgi-bin/user/info?access_token=[ACCESS_TOKEN]&openid=[OPENID]&lang=zh_CN";
    /**
     * 日志对象
     */
    private static Logger logger = Logger.getLogger("pay");


    /**
     * 隐藏构造函数
     */
    private UserInfoAccess() { }


    /**
     * 判断是否关注公众号
     *
     * @param openId 用户开放ID
     * @return 有效票据
     */
    public synchronized static boolean isFans(String openId) {
        String url = URL_USERINFO.replace("[ACCESS_TOKEN]", TokenAccess.getToken()).replace("[OPENID]", openId);
        String result = null;
        try {
            result = XMLHttpsUtil.get(url);
        }
        catch(Exception ex) {
            logger.error("call HttpUtil.send(" + url + ") failed", ex);
            return true;
        }
        JSONObject json = JSONObject.convert(result);
        if(null == json) {
            logger.error("empty response in HttpUtil.send(" + url + ")");
            return true;
        }
        JSONNumber subscribe = (JSONNumber) json.get("subscribe");
        if(null == subscribe) {
            logger.error("error response in HttpUtil.send(" + url + "), openId = " + openId);
            return true;
        }
        if(0 == subscribe.intValue()) {
            return false;
        }
        return true;
    }
}
