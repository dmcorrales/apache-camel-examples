package com.prueba.camel.EIP;

import org.apache.camel.builder.RouteBuilder;

public class Throttler extends RouteBuilder {
    @Override
    public void configure() throws Exception {
        from("direct:start")
                .throttle(1)
                .timePeriodMillis(1000)
                .log("${body}");
    }
}
