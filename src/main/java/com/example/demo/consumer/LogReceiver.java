package com.example.demo.consumer;

import com.example.demo.service.LogService;
import lombok.extern.log4j.Log4j2;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@Log4j2
public class LogReceiver {

    @Autowired
    LogService logService;

    // Consumer 1: 模擬將日誌存入資料庫
    @RabbitListener(queues = "#{fanoutQueueA.name}")
    public void receiveLogForDatabase(String message) {
        log.info("【Consumer A - 資料庫服務】收到日誌: " + message);
        logService.save(message);
    }

    // Consumer 2: 模擬將日誌發送到通知中心
    @RabbitListener(queues = "#{fanoutQueueB.name}")
    public void receiveLogForNotification(String message) {
        // LogReceiver 2 也會收到所有發布到 Exchange 的訊息
        System.out.println("【Consumer B - 通知服務】收到日誌: " + message);
        // 這裡可以實作透過 Redis Pub/Sub 或 WebSocket 發送通知的邏輯
    }

}
