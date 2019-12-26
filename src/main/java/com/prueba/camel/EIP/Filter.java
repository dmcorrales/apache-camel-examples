package com.prueba.camel.EIP;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.builder.RouteBuilder;

public class Filter extends RouteBuilder {
    @Override
    public void configure() throws Exception {
        from("direct:start")
                .process(new Processor() {
                    @Override
                    public void process(Exchange exchange) throws Exception {
                        exchange.getOut().setHeader("data","example");
                    }
                })
                .filter(header("data").isEqualTo("example1"))
                .log("si equals");
    }
}
