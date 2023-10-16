package com.crai.platform.starter.event.routes;

public class EventHeaders {

    ////Request Route Headers
    public static final String ACTION_METHOD="CamelBeanMethodName"; //Camel method auto binding header

    ////Response & Event Route Headers
//    public static final String ROUTINGKEY="rabbitmq.ROUTING_KEY";
    public static final String ROUTINGKEY="spring-rabbitmq.ROUTING_KEY";

    private EventHeaders(){}
}
