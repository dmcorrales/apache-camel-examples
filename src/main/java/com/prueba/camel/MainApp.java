package com.prueba.camel;

import com.mysql.cj.jdbc.MysqlDataSource;
import com.prueba.camel.Chapter1.Chapter1FileCopier;
import com.prueba.camel.Chapter1.Example1;
import com.prueba.camel.Chapter2.Chapter2ActiveMQConsumer;
import com.prueba.camel.Chapter2.Chapter2ActiveMQProducer;
import com.prueba.camel.Databases.MysqlConection;
import com.prueba.camel.EIP.*;
import com.prueba.camel.Quartz.Quartz;
import com.prueba.camel.RestExpose.RestProvider;
import com.prueba.camel.RestExposeSMTP.RestExposeSMTP;
import com.prueba.camel.Zip.TransformZIP;
import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.camel.CamelContext;
import org.apache.camel.Produce;
import org.apache.camel.ProducerTemplate;
import org.apache.camel.component.jms.JmsComponent;
import org.apache.camel.impl.DefaultCamelContext;
import org.apache.camel.impl.DefaultProducerTemplate;
import org.apache.camel.impl.SimpleRegistry;
import org.apache.camel.main.Main;
import org.apache.camel.processor.PollEnricher;
import org.postgresql.ds.PGSimpleDataSource;

import javax.jms.ConnectionFactory;
import java.util.concurrent.TimeUnit;

/**
 * A Camel Application
 */
public class MainApp {

    /**
     * A main() so we can easily run these routing rules in our IDE
     */

    public static void main(String... args) throws Exception {

        //Mysql DataSources
        MysqlDataSource mysqlDataSource = new MysqlDataSource();
        mysqlDataSource.setURL("jdbc:mysql://supportpangea.com:23306/test_db");
        mysqlDataSource.setUser("root");
        mysqlDataSource.setPassword("calabaza");

        PGSimpleDataSource pgSimpleDataSource = new PGSimpleDataSource();
        pgSimpleDataSource.setURL("jdbc:postgresql://supportpangea.com:15432/psg_db");
        pgSimpleDataSource.setUser("root");
        pgSimpleDataSource.setPassword("123456");

        SimpleRegistry simpleRegistry = new SimpleRegistry();
        simpleRegistry.put("mysql",mysqlDataSource);
        simpleRegistry.put("postgres",pgSimpleDataSource);

        CamelContext camelContext = new DefaultCamelContext(simpleRegistry);

        //ActiveMQ sources
        ConnectionFactory connectionFactory = new ActiveMQConnectionFactory("tcp://localhost:61616");
        camelContext.addComponent("activemq", JmsComponent.jmsComponentAutoAcknowledge(connectionFactory));


        camelContext.addRoutes(new WireTap());

        camelContext.start();
        TimeUnit.MINUTES.sleep(1);
        camelContext.stop();



    }

/*   public static void main(String[] args){
       Main main = new Main();
       main.addRouteBuilder(new Resequencer());

       try {
           main.run();
           ProducerTemplate producerTemplate = main.getCamelTemplate();
         //  producerTemplate.sendBody("direct:start",null);

       } catch (Exception e) {
           e.printStackTrace();
       }

   }*/

}

