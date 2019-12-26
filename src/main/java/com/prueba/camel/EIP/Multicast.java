package com.prueba.camel.EIP;

import com.prueba.camel.MyRouteBuilder;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.builder.RouteBuilder;

public class Multicast extends RouteBuilder {

    @Override
    public void configure() throws Exception {
        from("direct:start")
                .process(new Processor() {
                    @Override
                    public void process(Exchange exchange) throws Exception {
                        exchange.getOut().setBody("Body non modified");
                    }
                })
                .multicast()
                .to("direct:cast1")
                .to("direct:cast2")
                .log("${body}");

        from("direct:cast1")
                .process(new Processor() {
                    @Override
                    public void process(Exchange exchange) throws Exception {
                        exchange.getOut().setBody("Body yes modified");
                    }
                })
                .log("${body}");

        from("direct:cast2")
                .process(new Processor() {
                    @Override
                    public void process(Exchange exchange) throws Exception {
                        exchange.getOut().setBody("Body yes modified x 2 ");
                    }
                })
                .log("${body}");




    }
}
