package com.ericsson.testrar;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

@Stateless
public class MockDPSClient {
	
	@EJB(lookup="java:jboss/exported/med-core/core-ejb/MockMediationCore!com.ericsson.testrar.MediatioCoreInterface")
	MediatioCoreInterface router;
	
	
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void callRouter() {
		router.call();
	}
	
}
