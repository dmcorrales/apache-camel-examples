package com.prueba.camel.Databases;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.builder.RouteBuilder;

public class PostgresConection extends RouteBuilder {
    @Override
    public void configure() throws Exception {
        from("direct:start")
                .to("jdbc:postgres")
                .log("${body}");
    }
}
