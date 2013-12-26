package com.clearprecision.producers;

import javax.enterprise.inject.Produces;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Slf4JLoggerProducer {

	@Produces
	private Logger getLogger(Class clazz) {
		return LoggerFactory.getLogger(clazz);
	}

}
