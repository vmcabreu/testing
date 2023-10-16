package com.crai.platform.starter.event.routes;


import com.crai.platform.starter.event.exception.EventsException;
import org.apache.camel.builder.RouteBuilder;

public abstract class BaseRouteBuilder extends RouteBuilder {

  @Override
  public void configure() throws Exception {

    onException(EventsException.class,
      IllegalStateException.class)
        .handled(true);

    /*
     * onException(CTINotFoundException.class)
     * .to("log:not.found.exception?level=ERROR&showException=true&showCaughtException=true&showStackTrace=true")
     * .handled(true);
     */

    // By default, retry resources 3 times and return error
    // as a route reply
    onException(EventsException.class)
      .maximumRedeliveries(3)
      .redeliveryDelay(5000);
  }

}
