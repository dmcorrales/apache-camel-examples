package com.prueba.camel.Chapter2;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.builder.RouteBuilder;

public class Chapter2ActiveMQConsumer extends RouteBuilder {


    @Override
    public void configure() throws Exception {
        from("activemq:prueba")
                .process(new Processor() {
                    @Override
                    public void process(Exchange exchange) throws Exception {
                        System.out.println("Consumiento cola");
                        System.out.println((exchange.getIn().getBody()));
                    }
                });
    }
}
