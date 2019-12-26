package com.prueba.camel.EIP;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.builder.RouteBuilder;

public class WireTap extends RouteBuilder {
    @Override
    public void configure() throws Exception {
        from("file:src/data/out-ftp?noop=true")
                .wireTap("activemq:myPersonalQueue2")
                .to("file:src/data/outter-ftp");
    }
}
