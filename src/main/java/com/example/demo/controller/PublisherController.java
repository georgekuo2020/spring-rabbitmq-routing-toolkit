package com.example.demo.controller;

import com.example.demo.config.RabbitMQConfig;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@Tag(name = "發佈者 - 測試")
@RestController
@RequestMapping("/publisher/v1")
public class PublisherController {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @PostMapping("/broadcast/log")
    public ResponseEntity<String> publishLog() {

        String randomString = UUID.randomUUID().toString().replace("-", "");

        // 發布訊息到 Fanout Exchange
        rabbitTemplate.convertAndSend(
                RabbitMQConfig.FANOUT_EXCHANGE_NAME, // Exchange 名稱
                "", // Fanout 模式下，Routing Key 為空字串
                randomString
        );

        return ResponseEntity.ok("Log published successfully: " + randomString);
    }
}
