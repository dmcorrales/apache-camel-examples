package com.prueba.camel.Chapter1;

import com.oracle.xmlns.internal.webservices.jaxws_databinding.JavaWsdlMappingType;
import com.sun.javaws.jnl.XMLFormat;
import org.apache.camel.Exchange;
import org.apache.camel.Predicate;
import org.apache.camel.builder.RouteBuilder;


import java.beans.XMLDecoder;
import java.util.logging.XMLFormatter;

public class Chapter1FileCopier extends RouteBuilder {

    /**
     *  Se realiza una búsqueda recursiva en 'src/data' donde se evalúa si el archivo en formato XML
     *  posee una persona con el atributo de user 'james'.
     * @throws Exception
     */

    @Override
    public void configure() throws Exception {
        from("file:src/data?noop=true")
                .choice()
                .when(xpath("/person[(@user) = 'james']"))
                .to("file:src/data/out")
                .log("Se guardó solo a james.");
    }
}
