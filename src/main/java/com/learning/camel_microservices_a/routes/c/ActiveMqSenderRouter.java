package com.learning.camel_microservices_a.routes.c;

import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Component;

@Component
public class ActiveMqSenderRouter extends RouteBuilder {

    @Override
    public void configure() throws Exception {
        // TODO Auto-generated method stub
        // here we want to put a message in the queue in regular interval of time which
        // is 10 seconds
        // timer

        from("timer:active-mq-timer?period=10000")
                .transform().constant("My message for ActiveMQ")
                .log("${body}")
                .to("activemq:my-activemq-queue");
        // queue
        // throw new UnsupportedOperationException("Unimplemented method 'configure'");
    }

}
