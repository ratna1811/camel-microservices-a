package com.learning.camel_microservices_a.routes.b;

import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Component;

@Component
public class MyFirstRouter extends RouteBuilder {

    @Override
    public void configure() throws Exception {
        // TODO Auto-generated method stub
        from("file:files/input")
                .log("${body}")
                .to("file:files/output");
    }

}
