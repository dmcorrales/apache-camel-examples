package com.prueba.camel.RestExpose;

import jdk.nashorn.internal.parser.JSONParser;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.json.simple.JsonObject;
import org.apache.camel.model.dataformat.JsonLibrary;

public class RestProvider extends RouteBuilder {

    @Override
    public void configure() throws Exception {

        restConfiguration().component("jetty")
                .host("localhost").port("8083");

        rest("/transform")
                .produces("application/json")
                .get("/").to("direct:natty")
                .get("/json").to("direct:transformJson")
                .get("/csv").to("direct:transformCSV");


        from("direct:natty")
                .process(new Processor() {
                    @Override
                    public void process(Exchange exchange) throws Exception {
                        exchange.getIn().setBody("select * from person");
                    }
                })

                .to("jdbc:mysql")
                .setBody(simple( "${body}"));

        from("direct:transformCSV")
                .setBody(constant("select * from person"))
                .to("jdbc:mysql")
                .marshal().csv()
                .setBody(simple("${body}"));

        from("direct:transformJson")
                .process(new Processor() {
                    @Override
                    public void process(Exchange exchange) throws Exception {
                        exchange.getIn().setBody("select * from person");
                    }
                })
                .to("jdbc:mysql")
                .marshal().json(JsonLibrary.Jackson)
                .setBody(simple("${body}"));
    }
}
