package com.example.demo.config;

import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {

    // 1. 宣告 Fanout Exchange (廣播交換機)
    public static final String FANOUT_EXCHANGE_NAME = "fanout-log-exchange";

    @Bean
    public FanoutExchange fanoutExchange() {
        return new FanoutExchange(FANOUT_EXCHANGE_NAME);
    }

    // 2. 宣告第一個 Queue (消費者 1 使用)
    @Bean
    public Queue fanoutQueueA() {
        return new AnonymousQueue(); // 使用 AnonymousQueue 讓 RabbitMQ 自動生成名稱
    }

    // 3. 宣告第二個 Queue (消費者 2 使用)
    @Bean
    public Queue fanoutQueueB() {
        return new AnonymousQueue();
    }

    // 4. 綁定 Queue A 到 Exchange
    @Bean
    public Binding bindingA(FanoutExchange fanoutExchange, Queue fanoutQueueA) {
        // Fanout 模式下，Routing Key 為空字串
        return BindingBuilder.bind(fanoutQueueA).to(fanoutExchange);
    }

    // 5. 綁定 Queue B 到 Exchange
    @Bean
    public Binding bindingB(FanoutExchange fanoutExchange, Queue fanoutQueueB) {
        return BindingBuilder.bind(fanoutQueueB).to(fanoutExchange);
    }
}
