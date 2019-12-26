package com.prueba.camel.Chapter2;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.camel.component.ActiveMQComponent;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.jms.JmsComponent;
import org.apache.camel.impl.SimpleRegistry;
import org.springframework.jms.JmsException;

import javax.jms.ConnectionFactory;

public class Chapter2ActiveMQProducer extends RouteBuilder {


    @Override
    public void configure() throws Exception {
        from("ftp://213.190.6.37/orders?username=u309605935.camel123&password=camel123*")
                .convertBodyTo(String.class,"UTF-8")
                .to("activemq:prueba")
                .process(new Processor() {
                    @Override
                    public void process(Exchange exchange) throws Exception {
                        System.err.println(exchange.getIn().getHeaders());
                        System.err.println(exchange.getIn().getBody());
                    }
                });
    }
}
