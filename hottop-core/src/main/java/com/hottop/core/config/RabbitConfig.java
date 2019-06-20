package com.hottop.core.config;

import com.rabbitmq.client.AMQP;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

import java.io.IOException;

// rabbit mq 配置类
//@Configuration
public class RabbitConfig {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    //========= pay交换机 ==========
    public final static String EXCHANGE_PAY = "EXCHANGE_PAY";

    //========== pay队列 ===========
    public final static String QUEUE_PAY = "QUEUE_PAY";
    public final static String QUEUE_PurchaseOrder = "QUEUE_PurchaseOrder";//采购单队列

    //======= pay routing key ======
    public final static String ROUTING_KEY_PAY = "ROUTING_KEY_PAY";

    @Value("${spring.rabbitmq.host}")
    private String host;

    @Value("${spring.rabbitmq.port}")
    private Integer port;

    @Value("${spring.rabbitmq.username}")
    private String username;

    @Value("${spring.rabbitmq.password}")
    private String password;

    @Bean
    public ConnectionFactory connectionFactory() {
        CachingConnectionFactory connectionFactory = new CachingConnectionFactory(host,port);
        connectionFactory.setUsername(username);
        connectionFactory.setPassword(password);
        connectionFactory.setVirtualHost("/");
        connectionFactory.setPublisherConfirms(true);
        return connectionFactory;
    }

    @Bean
    //必须是prototype类型
    @Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
    public RabbitTemplate rabbitTemplate() {
        RabbitTemplate template = new RabbitTemplate(connectionFactory());
        return template;
    }

    //pay queue
    @Bean
    public Queue payQueue() {
        // true表示持久化
        return new Queue(QUEUE_PAY, true);
    }

    //pay exchange
    @Bean
    public DirectExchange payExchange() {
        return new DirectExchange(EXCHANGE_PAY);
    }

    //绑定pay exchange queue
    @Bean
    public Binding binding() {
        return BindingBuilder.bind(payQueue()).to(payExchange())
                .with(ROUTING_KEY_PAY);
    }


}
