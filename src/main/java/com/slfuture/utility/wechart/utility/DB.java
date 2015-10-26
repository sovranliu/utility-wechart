package com.slfuture.utility.wechart.utility;

import com.slfuture.carrie.base.text.Text;
import com.slfuture.carrie.base.xml.XMLNode;
import com.slfuture.carrie.utility.config.Configuration;
import com.slfuture.carrie.utility.config.xml.RootConfig;
import com.slfuture.carrie.utility.db.DBConnectionPoolConfig;
import com.slfuture.carrie.utility.db.DBExecutor;

import org.apache.log4j.Logger;

import java.io.FileReader;
import java.io.InputStream;
import java.util.ResourceBundle;

/**
 * 数据库
 */
public class DB {
    /**
     * 数据库执行器
     */
    private static DBExecutor executor = null;
    /**
     * 日志对象
     */
    private static Logger logger = Logger.getLogger("pay");


    /**
     * 隐藏构造函数
     */
    private DB() { }

    /**
     * 获取执行器
     *
     * @return 执行器
     */
    public static DBExecutor executor() {
        if(null == executor) {
            synchronized (DB.class) {
                if(null == executor) {
                    RootConfig rootConfig = null;
                    if(System.getProperty("os.name").toLowerCase().contains("windows")) {
                        rootConfig = RootConfig.convert(XMLNode.convert(Text.loadStream(DB.class.getResourceAsStream("/config/pay/dbtest.xml"), "UTF-8")));
                    }
                    else {
                        rootConfig = RootConfig.convert(XMLNode.convert(Text.loadStream(DB.class.getResourceAsStream("/config/pay/db.xml"), "UTF-8")));
                    }

                    if(null == rootConfig) {
                        logger.error("db.xml file not exist");
                        return null;
                    }
                    DBConnectionPoolConfig conf = new DBConnectionPoolConfig();
                    if(!conf.load(rootConfig)) {
                        logger.error("db.xml parse failed");
                        return null;
                    }
                    executor = new DBExecutor();
                    if(!executor.initialize(conf)) {
                        logger.error("db.xml init failed");
                        executor = null;
                    }
                    return executor;
                }
            }
        }
        return executor;
    }
}
