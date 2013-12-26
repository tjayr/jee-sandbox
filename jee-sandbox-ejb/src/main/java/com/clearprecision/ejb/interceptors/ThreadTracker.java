package com.clearprecision.ejb.interceptors;

import javax.interceptor.AroundInvoke;
import javax.interceptor.InvocationContext;

public class ThreadTracker {

	@AroundInvoke
	public Object adviseThread(InvocationContext context) throws Exception {
		String tName = Thread.currentThread().getName();
		String callingBeanName = context.getTarget().getClass().getName();
		StringBuilder builder = new StringBuilder();
		builder.append(tName);
		builder.append("#");
		builder.append(callingBeanName);
		try {
			Thread.currentThread().setName(builder.toString());
			return context.proceed();
		} finally {
			Thread.currentThread().setName(tName);
		}

	}

}
