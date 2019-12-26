package com.prueba.camel.Databases;

import com.mysql.cj.jdbc.MysqlDataSource;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.builder.RouteBuilder;

public class MysqlConection extends RouteBuilder {
    @Override
    public void configure() throws Exception {

        from("direct:start")
                .to("jdbc:mysql")
                .process(new Processor() {
                    @Override
                    public void process(Exchange exchange) throws Exception {
                        System.out.println(exchange.getIn().getBody());
                    }
                });
    }
}
