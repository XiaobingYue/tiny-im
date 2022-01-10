package com.yxb.tinylove.netty;

import com.yxb.tinylove.config.queue.MsgQueueHandler;
import org.springframework.beans.factory.annotation.Autowired;
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

    @Autowired
    private WsStarter wsStarter;

    @Value("${websocket.port}")
    private int port;

    @Autowired
    private MsgQueueHandler msgQueueHandler;

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        wsStarter.start(port);
        msgQueueHandler.start();
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        wsStarter.destroy();
        msgQueueHandler.stop();
    }
}
