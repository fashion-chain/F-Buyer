package com.hottop.core.utils.rabbitmq;

import com.hottop.core.config.RabbitConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import sun.awt.CausedFocusEvent;

import java.util.UUID;

//支付消息生产者
//@Component
public class PayProducer implements RabbitTemplate.ConfirmCallback {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    private RabbitTemplate rabbitTemplate;

    @Autowired
    public PayProducer(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
        rabbitTemplate.setConfirmCallback(this);
    }

    //发送消息
    public void sendMsg(String content) {
        CorrelationData correlationData = new CorrelationData(UUID.randomUUID().toString());
        rabbitTemplate.convertAndSend(RabbitConfig.EXCHANGE_PAY,
                RabbitConfig.ROUTING_KEY_PAY,
                content,
                correlationData);
    }


    //消息成功发送后的回调函数
    @Override
    public void confirm(CorrelationData correlationData, boolean ack, String cause) {
        logger.info("执行回调函数，回调id：{}", correlationData.getId());
        if (ack) {
            logger.info("消息成功消费");
        } else {
            logger.info("消息消费失败，原因：{}", cause);
        }
    }


}
