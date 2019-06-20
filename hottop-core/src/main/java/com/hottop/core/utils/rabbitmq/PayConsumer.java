package com.hottop.core.utils.rabbitmq;

import com.google.gson.reflect.TypeToken;
import com.hottop.core.config.BaseConfiguration;
import com.hottop.core.config.RabbitConfig;
import com.hottop.core.model.merchant.MerchantTrade;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

//支付消息 消费者
//@Component
//@RabbitListener(queues = RabbitConfig.QUEUE_PAY)
public class PayConsumer {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    @RabbitHandler
    public void process(String content) {
        logger.info("支付消息消费者，接收到消息:{}", content);
        //修改订单的状态
        MerchantTrade merchantTrade = BaseConfiguration.generalGson().fromJson(content, new TypeToken<MerchantTrade>(){}.getType());
        //先不用rabbitmq
    }
}
