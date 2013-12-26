package com.clearprecision.mdb;

import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import javax.inject.Inject;
import javax.interceptor.AroundInvoke;
import javax.interceptor.InvocationContext;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.ObjectMessage;
import javax.jms.TextMessage;

import org.slf4j.Logger;

public class PayloadExtractor {

	@Inject
	private Logger logger;

	@AroundInvoke
	public void extract(InvocationContext ctx) throws Exception {
		Object mdb = ctx.getTarget();
		Method method = ctx.getMethod();
		if ("onMessage".equals(method.getName())) {
			Message message = (Message) ctx.getParameters()[0];
			invokeMethodWithsingleParameter(message, mdb);
		}
	}

	private void invokeMethodWithsingleParameter(Message message, Object mdb) {
		if (message instanceof ObjectMessage) {
			try {
				ObjectMessage om = (ObjectMessage) message;
				Serializable payload = om.getObject();
				invokeConsume(mdb, message, payload);
			} catch (JMSException e) {
				e.printStackTrace();
			}
		} else if (message instanceof TextMessage) {
			try {
				TextMessage tm = (TextMessage) message;
				invokeConsume(mdb, message, tm.getText());
			} catch (JMSException e) {
				e.printStackTrace();
			}
		} else {
			raiseError(message);
		}
	}

	private void invokeConsume(Object mdb, Message message, Object payload) {
		try {
			mdb.getClass().getMethod("processData", payload.getClass())
					.invoke(mdb, payload);
		} catch (IllegalAccessException | IllegalArgumentException
				| InvocationTargetException | NoSuchMethodException
				| SecurityException e) {
			raiseError(message);
		}
	}

	private void raiseError(Message message) {
		logger.error("Error occurred processing message [{}]", message);
	}

}
