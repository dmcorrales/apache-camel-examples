package com.prueba.camel.Quartz;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.builder.RouteBuilder;

import java.util.Date;

public class Quartz extends RouteBuilder {
    @Override
    public void configure() throws Exception {
        from("quartz2://myGroup/myTimerName?cron=0+0/1+*+1/1+*+?")
                .to("direct:insertData")
                .to("direct:sendEmail");

        from("direct:insertData")
                .process(new Processor() {
                    @Override
                    public void process(Exchange exchange) throws Exception {
                        exchange.getIn().setBody("insert into person(name) values ('Daniel Corrales')");
                    }
                })
                .to("jdbc:mysql");

        from("direct:sendEmail")
                .process(new Processor() {
                    @Override
                    public void process(Exchange exchange) throws Exception {
                        exchange.getOut().setHeader("to","vapedraza1706@gmail.com");
                        exchange.getOut().setHeader("cc","vapedraza1706@gmail.com");
                        exchange.getOut().setBody("La integración se ha realizado correctamente." + new Date());
                    }
                })
                .to("smtps://smtp.gmail.com?password=papipopo&username=mcorrales1020@gmail.com&From=mcorrales1020@gmail.com&To="+header("to")+"&Cc="+header("cc")+"");
    }
}
