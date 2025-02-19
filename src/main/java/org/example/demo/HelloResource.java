package org.example.demo;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;

@Path("/hello-world")
public class HelloResource {

    @GET
    @Produces("application/json")
    public Object hello() {
        return new Object(){
            public String message = "Hello, World from a JAX-RS Resource!";
            public int num = 999;
        };
    }
}