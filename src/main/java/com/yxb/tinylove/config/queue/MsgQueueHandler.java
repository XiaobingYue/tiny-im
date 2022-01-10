package com.yxb.tinylove.config.queue;

import com.yxb.tinylove.domain.Msg;
import com.yxb.tinylove.service.MsgService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

/**
 * @author yxb
 * @since 2022/1/10
 */

@Component
@Slf4j
public class MsgQueueHandler {

    @Autowired
    private MsgService msgService;

    private volatile boolean isStart;

    private static final BlockingQueue<Msg> MSG_QUEUE = new LinkedBlockingQueue<>(10000);

    public void put(Msg msg) {
        MSG_QUEUE.offer(msg);
    }


    public void start() {
        log.debug("启动消息消费线程...");
        if (!isStart) {
            isStart = true;
            new Thread(() -> {
                while (isStart) {
                    log.debug("开始消费队列中的消息...");
                    try {
                        Msg msg = MSG_QUEUE.take();
                        List<Msg> list = new ArrayList<>();
                        MSG_QUEUE.drainTo(list, 100);
                        list.add(msg);
                        if (!CollectionUtils.isEmpty(list)) {
                            log.debug("本次消费{}条消息，开始入库", list.size());
                            msgService.saveBatch(list);
                            log.debug("入库成功...");
                        }
                    } catch (InterruptedException e) {}

                }
                log.debug("消息消费线程已关闭...");

            }).start();
        } else {
            log.warn("消息消费线程已启动...");
        }

        log.debug("消息消费线程启动成功...");
    }

    public void stop() {
        isStart = false;
    }

}
