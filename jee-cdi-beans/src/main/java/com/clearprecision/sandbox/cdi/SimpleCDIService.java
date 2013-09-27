package com.clearprecision.sandbox.cdi;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.event.Event;
import javax.enterprise.event.Observes;
import javax.inject.Inject;

/**
 * A very simple CDI based service.
 */
@ApplicationScoped
public class SimpleCDIService {

	@Inject
	private Event<Object> responseEvent;

	public void handleMessage(@Observes Object event) {
		System.out.println("Received HelloMessage from Client: " + event);
		responseEvent.fire("event");
	}
}
