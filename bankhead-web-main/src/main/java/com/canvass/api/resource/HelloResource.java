package com.bankhead.api.resource;

import com.bankhead.data.DataStore;
import com.google.inject.Inject;
import com.google.inject.Singleton;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.logging.Logger;

/**
 * Created By: jordancote
 * Created On: 12/30/13
 */
@Singleton
@Path("hello/")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class HelloResource {
    private final DataStore dataStore;
    private final Logger logger;

    @Inject
    public HelloResource(DataStore dataStore, Logger logger) {
        this.dataStore = dataStore;
        this.logger = logger;
    }

    @GET
    @Path("hi")
    public String hello() {
        return "hi!";
    }
}
