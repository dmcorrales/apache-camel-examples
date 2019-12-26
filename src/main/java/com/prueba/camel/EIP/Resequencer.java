package com.prueba.camel.EIP;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.builder.RouteBuilder;

public class Resequencer extends RouteBuilder {
    @Override
    public void configure() throws Exception {
                from("ftp://213.190.6.37/pruebas?username=u309605935.camel123&password=camel123*")
                 .split()
                .tokenize("\n")
                .streaming()
                .log("${body}")
                .resequence(body())
                        .batch()
                        .log("${body}");
    }
}
