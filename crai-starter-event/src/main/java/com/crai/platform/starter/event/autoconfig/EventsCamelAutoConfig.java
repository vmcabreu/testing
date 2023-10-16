package com.crai.platform.starter.event.autoconfig;

import com.crai.platform.starter.event.bean.EventSink;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import com.crai.platform.starter.event.exception.EventsException;
import com.rabbitmq.client.ConnectionFactory;
import org.apache.camel.CamelContext;
import org.apache.camel.ProducerTemplate;
import org.apache.camel.builder.ThreadPoolBuilder;
import org.apache.camel.impl.DefaultCamelContext;
import org.apache.camel.spring.boot.CamelContextConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.core.task.SimpleAsyncTaskExecutor;
import org.springframework.core.task.TaskExecutor;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;
import org.springframework.scheduling.annotation.EnableAsync;
import com.crai.platform.starter.event.producer.EventTemplate;
import com.crai.platform.starter.event.routes.EndpointUrlBuilder;
import com.crai.platform.starter.event.routes.EventRouteBuilder;

import java.util.concurrent.ExecutorService;

@Configuration
@EnableAsync
public class EventsCamelAutoConfig {


    @Bean
    public EventTemplate eventTemplate(){
        return new EventTemplate();
    }

    @Bean
    @ConditionalOnMissingBean
    ProducerTemplate producer(CamelContext camelContext){
        ProducerTemplate producer=camelContext.createProducerTemplate();
        producer.setThreadedAsyncMode(true);
        return producer;
    }

    @Bean
    @ConditionalOnMissingBean
    public ObjectMapper objectMapper(Jackson2ObjectMapperBuilder mapperBuilder) {
        return mapperBuilder.build().registerModule(new JavaTimeModule());
    }

    @Bean
    @ConditionalOnMissingBean
    public ExecutorService senderExecutorService(CamelContext context) {
        try {
            return new ThreadPoolBuilder(context).poolSize(15).maxPoolSize(40).build("events-sender-pool");
        } catch (Exception e) {
            throw new EventsException("Error creating downstreamExecutorService",e);
        }
    }

    /**
     * Configure asynchronous Spring events for OSV
     * @return
     */
    @Bean
    @ConditionalOnMissingBean
    public TaskExecutor taskExecutor() {
        return new SimpleAsyncTaskExecutor();
    }


    @Bean
    public EventSink eventSink(){
        return new EventSink();
    }

    @Bean
    public EndpointUrlBuilder endpointUrlBuilder(){
        return new EndpointUrlBuilder();
    }

    @Bean
    public EventRouteBuilder eventRouteBuilder(){
        return new EventRouteBuilder();
    }
}

