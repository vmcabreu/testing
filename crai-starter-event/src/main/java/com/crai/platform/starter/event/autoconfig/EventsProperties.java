package com.crai.platform.starter.event.autoconfig;


import com.rabbitmq.client.ConnectionFactory;
import lombok.Data;
import org.apache.camel.ExchangePattern;
import org.apache.camel.spi.ExceptionHandler;
import org.apache.commons.beanutils.BeanUtils;
import org.springframework.amqp.core.AcknowledgeMode;
import org.springframework.amqp.rabbit.support.MessagePropertiesConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.retry.interceptor.RetryOperationsInterceptor;

import javax.net.ssl.TrustManager;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;

@Data
@ConfigurationProperties(prefix = "app.events")
public class EventsProperties {

  Boolean enabled = true;
  String scanBasePackage = "com.crai.platform";

  Map<String,EventDef> receivers=new HashMap<>();
  Map<String,EventDef> senders=new HashMap<>();

  @Data
  public static final class EventDef {
    //@NotNull
//    String exchange;

    String exchangeName;

//    String autoDeclare;

    String eventType;
    /** The connection factory to be use. A connection factory must be configured either on the component or endpoint.**/
    ConnectionFactory connectionFactory;
    /** Specifies whether Camel ignores the ReplyTo header in messages.
     If true, Camel does not send a reply back to the destination specified in the ReplyTo header.
     You can use this option if you want Camel to consume from a route and you do not want Camel to automatically send back a reply
     message because another component in your code handles the reply message.
     You can also use this option if you want to use Camel as a proxy between different message
     brokers and you want to route message from one system to another.
     **/
    Boolean disableReplyTo;
    /** The value of a routing key to use. Default is empty which is not helpful when using the default (or any direct) exchange, but fine if the exchange is a headers exchange for instance. **/
    String routingKey;
    /**Specifies whether to test the connection on startup. This ensures that when Camel starts that all the JMS consumers have a valid connection to the JMS broker. If a connection cannot be granted then Camel throws an exception on startup. This ensures that Camel is not started with failed connections. The JMS producers is tested as well.**/
    Boolean testConnectionOnStartup;
    AcknowledgeMode acknowledgeMode;
    Boolean asyncConsumer;
    Boolean autoDeclare;
    Boolean autoStartup;
    String deadLetterExchange;
    String deadLetterExchangeType;
    String deadLetterQueue;
    String deadLetterRoutingKey;
    String exchangeType;
    Boolean exclusive;
    Integer maximumRetryAttempts;
    Boolean noLocal;
    String queues;
    Boolean rejectAndDontRequeue;
    Integer retryDelay;
    Boolean bridgeErrorHandler;
    Integer concurrentConsumers;
    ExceptionHandler exceptionHandler;
    ExchangePattern exchangePattern;
    Integer maxConcurrentConsumers;
    String messageListenerContainerType;
    Integer prefetchCount;
    RetryOperationsInterceptor retry;
    Boolean allowNullBody;
    String confirm;
    Long confirmTimeout;
    Long replyTimeout;
    Long usePublisherConnection;
    Boolean lazyStartProducer;
    Map<String,String> args;
    MessageConverter messageConverter;
    MessagePropertiesConverter messagePropertiesConverter;
    Boolean synchronous;

    // not exist in spring-rabbitmq

    Map<String,String> additionalProperties;
    Map<String,String> clientProperties;
    Map<String,String> additionalHeaders;

    public Object getValue(String param) {
      try {
        return BeanUtils.getProperty(this, param);
      } catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
        return null;
      }
    }
  }
}
