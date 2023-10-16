package com.crai.platform.starter.event.routes;

import com.crai.platform.starter.event.autoconfig.EventsProperties;
import org.apache.commons.lang3.NotImplementedException;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.tuple.Pair;
import org.springframework.beans.factory.annotation.Autowired;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
public class EndpointUrlBuilder {

  //  static final String URL_PREFIX="rabbitmq:";
  static final String URL_PREFIX="spring-rabbitmq:";
  //  static final String[] QUERY_PARAMS = {"addresses", "autoDelete","autoDeclare", "automaticRecoveryEnabled", "deadLetterExchange", "deadLetterExchangeType", "deadLetterQueue",
//    "deadLetterRoutingKey", "declare", "durable", "exchangeType", "exclusive", "hostname", "passive", "portNumber", "queue", "routingKey", "skipDlqDeclare", "skipExchangeDeclare",
//    "skipQueueBind", "skipQueueDeclare", "vhost", "autoAck", "bridgeErrorHandler", "concurrentConsumers","maxConcurrentConsumers", "consumerTag", "exclusiveConsumer", "prefetchCount", "prefetchEnabled",
//    "prefetchGlobal", "prefetchSize", "reQueue", "exchangePattern", "threadPoolSize",  "allowCustomHeaders",
//    "allowNullHeaders", "bridgeEndpoint", "channelPoolMaxSize", "channelPoolMaxWait", "guaranteedDeliveries", "immediate", "lazyStartProducer", "mandatory",
//    "publisherAcknowledgements", "publisherAcknowledgementsTimeout", "allowMessageBodySerialization",
//    "connectionTimeout", "networkRecoveryInterval", "requestedChannelMax", "requestedFrameMax", "requestedHeartbeat", "requestTimeout", "requestTimeoutCheckerInterval",
//    "topologyRecoveryEnabled", "transferException", "password", "sslProtocol", "username","queues"};
  static final String[] QUERY_PARAMS = {"connectionFactory", "disableReplyTo", "routingKey", "testConnectionOnStartup", "acknowledgeMode",
          "asyncConsumer", "autoDeclare", "autoStartup", "deadLetterExchange", "deadLetterExchangeType", "deadLetterQueue", "deadLetterRoutingKey",
          "exchangeType", "exclusive", "maximumRetryAttempts", "noLocal", "queues", "rejectAndDontRequeue", "retryDelay", "bridgeErrorHandler",
          "concurrentConsumers", "exceptionHandler", "exchangePattern", "maxConcurrentConsumers", "messageListenerContainerType", "prefetchCount",
          "retry", "allowNullBody", "confirm", "confirmTimeout", "replyTimeout", "usePublisherConnection", "lazyStartProducer",
          "messageConverter", "messagePropertiesConverter", "synchronous"};
  static final String[] QUERY_PARAMS_MAP = { "args", "clientProperties", "additionalHeaders", "additionalProperties"};

  List<String> queryAttributes= Collections.unmodifiableList(Arrays.asList(QUERY_PARAMS));
  //List<String> queryMapAttributes= Collections.unmodifiableList(Arrays.asList(QUERY_PARAMS));

  @Autowired
  EventsProperties eventsProperties;

  public String buildUrl(EventsProperties.EventDef eventDef) {

    if (eventDef.getExchangeName()==null){
      throw new IllegalArgumentException("Exchange name not declared");
    }
    if (ObjectUtils.anyNotNull(eventDef.getAdditionalHeaders(),eventDef.getAdditionalProperties(),eventDef.getClientProperties())){
      throw new NotImplementedException("Event map properties (clientProperties, additionalHeaders and additionalProperties) are currently unsupported");
    }

    Map<String,Object> paramMap=buildParamsMap(eventDef);
    paramMap.putAll(eventDef.getArgs());
    if (paramMap.isEmpty()){
      return URL_PREFIX+eventDef.getExchangeName();
    }else {
      return URL_PREFIX+eventDef.getExchangeName()+"?"+toQueryParams(paramMap);
    }

  }

  /*
   * Ejemplo para la construcción de una url dinámica.
   * Comentado de momento por si hace falta más adelante.
   *
  public String buildUrlDynamic(String body) {
	EventDto event = null;
    try {
		event = new ObjectMapper().readValue(body, EventDto.class);
	} catch (JsonMappingException e) {
		e.printStackTrace();
	} catch (JsonProcessingException e) {
		e.printStackTrace();
	}
	  if (!invoked) {
		invoked = true;
        EventsProperties.EventDef eventDef=eventsProperties.getSenders().get("senderHandler");

	    if (eventDef.getExchangeName()==null){
	      throw new IllegalArgumentException("Exchange name not declared");
	    }
	    if (ObjectUtils.anyNotNull(eventDef.getAdditionalHeaders(),eventDef.getAdditionalProperties(),eventDef.getClientProperties())){
	      throw new NotImplementedException("Event map properties (clientProperties, additionalHeaders and additionalProperties) are currently unsupported");
	    }

	    Map<String,Object> paramMap=buildParamsMap(eventDef);
	    paramMap.putAll(eventDef.getArgs());
        //paramMap.put("routingKey", "crai.notification2.4");
	    if (!StringUtils.isEmpty(event.getName())) {
           paramMap.put("routingKey", "*.notificaciones." + event.getName() + ".*");
	    } else {
           paramMap.put("routingKey", "*.notificaciones.*");
	    }
	    if (paramMap.isEmpty()){
	      return URL_PREFIX+eventDef.getExchangeName();
	    }else {
	      return URL_PREFIX+eventDef.getExchangeName()+"?"+toQueryParams(paramMap);
	    }
      } else {
	     invoked = false;
		 return null;
	  }
  }*/


  public String buildSenderUrl(EventsProperties.EventDef eventDef, String user) {
    return buildUrl(eventDef);
  }
  public String buildProducerUrl(EventsProperties.EventDef eventDef, String user) {
    return buildUrl(eventDef);
  }

  private Map<String,Object> buildParamsMap(EventsProperties.EventDef eventDef){
    return queryAttributes.stream().map(param -> Pair.of(param,eventDef.getValue(param)))
            .filter(pair -> pair.getValue()!=null)
            .collect(Collectors.toMap(Pair::getKey, Pair::getValue));
  }

  public String toQueryParams(Map<String,Object> params){
    return params.entrySet().stream()
            .map( entry -> entry.getKey()+"="+entry.getValue() )
            .collect(Collectors.joining("&"));
  }
}
