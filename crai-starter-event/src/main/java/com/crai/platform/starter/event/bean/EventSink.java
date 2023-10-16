package com.crai.platform.starter.event.bean;

import lombok.extern.slf4j.Slf4j;
import org.apache.camel.Body;
import org.apache.camel.Exchange;
import org.apache.camel.Handler;
import com.crai.platform.starter.event.util.CamelUtil;

@Slf4j
public class EventSink {

    /**
     * Discard exchange event. Logs the discarded message
     * @param msg
     * @param exchange
     */
    public void discard(@Body Object msg, Exchange exchange){
        if (msg != null) {
            log.debug("**** Discarding  message  {} ", msg);
        }
        CamelUtil.discardResponse(exchange);
    }

    /**
     * Same as discardMessage but never logs sinked events.
     * @param exchange
     */
    @Handler
    public void sink(Exchange exchange){
        CamelUtil.discardResponse(exchange);
    }
}
