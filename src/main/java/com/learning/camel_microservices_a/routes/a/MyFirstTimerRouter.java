package com.learning.camel_microservices_a.routes.a;

import java.time.LocalDateTime;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.builder.RouteBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class MyFirstTimerRouter extends RouteBuilder {
    @Autowired
    private GetCurrentTimeBean getCurrentTimeBean;

    @Autowired
    private SimpleLoggingProcessingComponent simpleLoggingProcessingComponent;

    @Override
    public void configure() throws Exception {
        // TODO Auto-generated method stub
        // timer
        // transformation - to make some changes in the message that comes in
        // log

        // from states - starting point of the route - listening on the timer endpoint
        // In below we are listening to timer and sending message to the log
        // we are picking up message using from, transforming message as constant and
        // sending it to log
        from("timer:first-timer")
                .log("${body}") // prints null
                .transform().constant("My Constant Message")
                .log("${body}") // prints My Constant Message
                // .transform().constant("Time now is " + LocalDateTime.now())
                // .bean("getCurrentTimeBean")
                .bean(getCurrentTimeBean, "getCurrentTime")
                .log("${body}") // prints Time now is LacalDateTime.now() value
                .bean(simpleLoggingProcessingComponent)
                .process(new SimpleLoggingProcessor())
                .log("${body}")
                .to("log:first-timer");
        // throw new UnsupportedOperationException("Unimplemented method 'configure'");
    }

}

@Component
class GetCurrentTimeBean {
    public String getCurrentTime() {
        return "Time now is " + LocalDateTime.now();
    }
}

@Component
class SimpleLoggingProcessingComponent {
    private Logger LOGGER = LoggerFactory.getLogger(SimpleLoggingProcessingComponent.class);

    public void process(String message) {
        LOGGER.info("SimpleLoggingProcessingComponent {}", message);
    }
}

class SimpleLoggingProcessor implements Processor {
    private Logger LOGGER = LoggerFactory.getLogger(SimpleLoggingProcessor.class);

    @Override
    public void process(Exchange exchange) throws Exception {
        LOGGER.info("SimpleLoggingProcessor {}", exchange.getMessage().getBody());
    }
}