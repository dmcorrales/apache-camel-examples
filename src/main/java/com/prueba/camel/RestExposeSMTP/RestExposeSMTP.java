package com.prueba.camel.RestExposeSMTP;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.jackson.JacksonDataFormat;
import org.apache.camel.model.dataformat.JsonLibrary;

public class RestExposeSMTP extends RouteBuilder {
    @Override
    public void configure() throws Exception {

        restConfiguration()
                .component("jetty")
                .host("localhost")
                .port(9590);

        rest("/mail")
                .post("/send").to("direct:sendMail");

        from("direct:sendMail")
                .unmarshal(new JacksonDataFormat(PojoMail.class))
                .process(new Processor() {
                    @Override
                    public void process(Exchange exchange) throws Exception {
                        PojoMail pojoMail = exchange.getIn().getBody(PojoMail.class);
                        exchange.getOut().setHeader("subject", pojoMail.getSubject());
                        exchange.getOut().setHeader("to", pojoMail.getTo());
                        exchange.getOut().setHeader("cc", pojoMail.getCc());
                        exchange.getIn().setBody(pojoMail.getMessage().toString());
                        exchange.getOut().setBody(exchange.getIn().getBody(String.class));
                    }
                })
                .to("smtps://smtp .gmail.com?password=papipopo&username=mcorrales1020@gmail.com&From=mcorrales1020@gmail.com&To="+header("to")+"&Cc="+header("cc")+"")
                .log("mensaje enviado");

    }
}
