package com.yxb.tinylove.netty;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

/**
 * @author yxb
 * @since 2021/12/30
 */
public class NettyServerListener implements ServletContextListener {

    private WsStarter wsStarter;

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        wsStarter = new WsStarter();
        wsStarter.start();
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        wsStarter.destroy();
    }
}
