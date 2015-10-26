package com.slfuture.utility.wechart.certificate;

import com.slfuture.utility.wechart.utility.Config;
import com.slfuture.utility.wechart.utility.Tool;

/**
 * 认证帮助类
 */
public class Helper {
    /**
     * 身份验证
     *
     * @param signature 签名
     * @param timestamp 时间戳
     * @param nonce 随机字符串
     * @return 是否验证成功
     */
    public static boolean check(String signature, String timestamp, String nonce) {
        String [] keys = new String[3];
        keys[0] = Config.token;
        keys[1] = timestamp;
        keys[2] = nonce;
        if(signature.equalsIgnoreCase(Tool.makeSign(keys))) {
            return true;
        }
        else {
            return false;
        }
    }
}
