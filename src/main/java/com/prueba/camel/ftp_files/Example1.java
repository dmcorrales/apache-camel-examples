package com.prueba.camel.Chapter1;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.builder.RouteBuilder;

public class Example1 extends RouteBuilder {
    @Override
    public void configure() throws Exception {
        from("direct:start").process(new Processor() {
            @Override
            public void process(Exchange exchange) throws Exception {

                System.out.println(exchange.getIn().getBody());
            }
        });
    }
}
