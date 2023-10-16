package com.crai.platform.starter.event.util;
import lombok.experimental.UtilityClass;
import org.apache.camel.Exchange;
import org.apache.camel.ExchangePattern;

@UtilityClass
public class CamelUtil {
    protected static final String RABBITMQ_HEADER_PREFIX="rabbitmq.";

    public static void discardResponse(Exchange exchange){
        exchange.setPattern(ExchangePattern.InOnly);
        exchange.getIn().setBody(null);
    }

    public static String toRabbitMQ(String header){
        return RABBITMQ_HEADER_PREFIX+header;
    }
    public static String fromRabbitMQ(String queueHeader){
        if (queueHeader==null){
            return null;
        }
        return queueHeader.replace(RABBITMQ_HEADER_PREFIX,"");
    }
}
