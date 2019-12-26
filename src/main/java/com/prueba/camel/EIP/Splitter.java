package com.prueba.camel.EIP;

import org.apache.camel.builder.RouteBuilder;

public class Splitter extends RouteBuilder {
    @Override
    public void configure() throws Exception {
        from("file:src/data/plain-out?fileName=plain.txt&noop=true")
                .split()
                .tokenize("\n")
                .streaming()
                .log("${body}");

    }
}
