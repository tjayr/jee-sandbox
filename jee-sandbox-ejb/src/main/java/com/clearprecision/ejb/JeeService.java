package com.clearprecision.ejb;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.interceptor.Interceptors;

import org.slf4j.Logger;

import com.clearprecision.ejb.interceptors.LoggingInterceptor;
import com.clearprecision.ejb.interceptors.ThreadTracker;

@Stateless
@Interceptors({ LoggingInterceptor.class, ThreadTracker.class })
public class JeeService {

	@Inject
	private Logger logger;

	public void invokeService() {
		logger.info("Activate JeeService service");
	}

}
