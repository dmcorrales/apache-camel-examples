package com.prueba.camel.EIP;

import org.apache.camel.Exchange;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.processor.aggregate.AggregationStrategy;

public class PollEnrich extends RouteBuilder {
    @Override
    public void configure() throws Exception {
        from("direct:start")
                .to("http://vapedraza.com/me/")
        .pollEnrich("http://dmcorrales.me", new AggregationStrategy() {
            @Override
            public Exchange aggregate(Exchange oldExchange, Exchange newExchange) {
                System.out.println(oldExchange.getIn().getBody(String.class));
                System.out.println(newExchange.getIn().getBody(String.class));
                return oldExchange;
            }
        })
        ;
    }
}
