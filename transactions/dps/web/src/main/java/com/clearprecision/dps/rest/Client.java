package com.clearprecision.dps.rest;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Produces;
import javax.ws.rs.Path;

import com.clearprecision.dps.DpsDelegate;

@Path("/dps")
public class Client {
	
	@Inject
	private DpsDelegate dps;
	
    @GET
    @Produces("text/plain")
    public String getClichedMessage() {
    	long start = System.nanoTime();
    	dps.process();
    	long end = System.nanoTime();
    	
    	long result = end - start;
    	
        return "Operation took "+result;
    }
}
