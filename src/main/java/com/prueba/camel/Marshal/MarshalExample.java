package com.prueba.camel.Chapter3;

import org.apache.camel.Exchange;
import org.apache.camel.Expression;
import org.apache.camel.Processor;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.dataformat.csv.CsvDataFormat;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;

public class MarshalExample extends RouteBuilder {
    @Override
    public void configure() throws Exception {
        from("file:src/data/csv?fileName=example1.csv&noop=true")
               .unmarshal().csv().process(new Processor() {
            @Override
            public void process(Exchange exchange) throws Exception {
                System.out.println(exchange.getIn().getBody(String.class));
            }
        });
    }
}
