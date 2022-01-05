package com.yxb.tinylove.netty;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

/**
 * @author yxb
 * @since 2021/12/30
 */

@Component
public class NettyServerListener implements ServletContextListener {

    private WsStarter wsStarter;

    @Value("${websocket.port}")
    private int port;

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        wsStarter = new WsStarter();
        wsStarter.start(port);
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        wsStarter.destroy();
    }
}
