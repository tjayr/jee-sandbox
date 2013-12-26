package com.clearprecision.ejb;

import javax.annotation.PostConstruct;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.inject.Inject;

import org.slf4j.Logger;

@Startup
@Singleton
public class ServiceStarter {
	
	private static final Logger logger = org.slf4j.LoggerFactory.getLogger(Boundary.class);

	@Inject
	Boundary boundary;
	
	@PostConstruct
	void startup() {
		logger.info("Startup bean post contruct");
		logger.info("Boundary returned {}", boundary.getDataStoreName());
	}
	
}
