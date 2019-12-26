package com.prueba.camel.Zip;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.dataformat.ZipFileDataFormat;
import org.apache.camel.processor.aggregate.zipfile.ZipAggregationStrategy;

import java.util.Iterator;
import java.util.zip.ZipFile;

public class TransformZIP extends RouteBuilder {
    @Override
    public void configure() throws Exception {

        ZipFileDataFormat zipFile = new ZipFileDataFormat();
        zipFile.setUsingIterator(true);

        from("ftp://213.190.6.37/api/app?username=u309605935&password=IdeashoppersFTP2018")
                .aggregate(new ZipAggregationStrategy())
                .constant(true)
                .completionFromBatchConsumer()
                .eagerCheckCompletion()
                .setHeader(Exchange.FILE_NAME, constant("secret.zip"))
                .to("file:src/data/zip");

        from("direct:unzipFiles")
                .from("file:src/data/zip?noop=true")
                .unmarshal(zipFile)
                .split(body(Iterator.class))
                .streaming()
                    .to("file:src/data/unzip")
                    .log("done");
}
}
