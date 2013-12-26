package com.clearprecision.ejb.interceptors;

import javax.inject.Inject;
import javax.interceptor.AroundInvoke;
import javax.interceptor.InvocationContext;

import org.slf4j.Logger;

public class LoggingInterceptor {

	@Inject
	private Logger logger;

	@AroundInvoke
	public Object adviseThread(InvocationContext context) throws Exception {
		logger.debug("Calling method {} ", context.getMethod().getName());
		return context.proceed();
	}
}
