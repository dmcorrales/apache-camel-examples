package com.prueba.camel.Chapter1;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.builder.RouteBuilder;

public class Chapter1FtpConsumer extends RouteBuilder {

    /**
     *  Conexión a FTP de manera directa, donde los archivos pasan a ser almacenados en 'src/data/ftp'.
     * @throws Exception
     */
    @Override
    public void configure() throws Exception {
        from("ftp://213.190.6.37/api/app?username=u309605935&password=IdeashoppersFTP2018")
                .process(new Processor() {
                    @Override
                    public void process(Exchange exchange) throws Exception {
                        exchange.getOut().setBody(exchange.getIn().getBody());
                        exchange.getOut().setHeader("CamelFileName",exchange.getIn().getHeader("CamelFileName"));
                    }
                }).to("direct:saveFiles");

        /**
         *  Los ficheros encontrados en la conexión FTP pasan a ser almacenados.
         */
        from("direct:saveFiles")
                .to("file:src/data/ftp")
                .process(new Processor() {
                    @Override
                    public void process(Exchange exchange) throws Exception {
                        System.out.println(exchange.getIn().getBody());
                    }
                });
    }
}
