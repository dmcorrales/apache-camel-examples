package com.prueba.camel.EIP;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.processor.aggregate.AggregationStrategy;

import java.io.PrintStream;

public class Aggregator extends RouteBuilder {
    @Override
    public void configure() throws Exception {
        from("direct:start")
        .from("ftp://213.190.6.37/pruebas?username=u309605935.camel123&password=camel123*")
                .setHeader("myId",simple("AgreggatorID"))
                .log("Sending ${body} with correlation key ${header.myId}")
                .aggregate(header("myId"),new AggregationStrategy() {
                    @Override
                    public Exchange aggregate(Exchange oldExchange, Exchange newExchange) {
                        System.out.println(oldExchange);
                        System.out.println(newExchange);
                        if (oldExchange == null) {
                            return newExchange;
                        }
                        String oldBody = oldExchange.getIn()
                                .getBody(String.class);
                        String newBody = newExchange.getIn()
                                .getBody(String.class);
                        String body = oldBody +"\n\n"+ newBody;
                        oldExchange.getIn().setBody(body);
                        return oldExchange;
                    }
                })
                .completionSize(10)
                .completionTimeout(1000L)
                .to("direct:aggregate");

        from("direct:aggregate")
                .log("Sending out: ${body}");
    }
}
