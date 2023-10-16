package com.crai.platform.event.webbroker.routes;

import org.springframework.stereotype.Component;

@Component
public class RabbitReceiverRoute{}
//public class RabbitReceiverRoute extends RouteBuilder {
//    @Override
//    public void configure() throws Exception {
//        from("spring-rabbitmq:crai-events?queues=test-queue").id("routerIdMiaSoloMia").log("before send to direct");
//    }

