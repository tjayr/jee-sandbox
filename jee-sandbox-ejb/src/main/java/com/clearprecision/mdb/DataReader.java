package com.clearprecision.mdb;

import javax.ejb.ActivationConfigProperty;
import javax.ejb.MessageDriven;
import javax.inject.Inject;
import javax.jms.Message;
import javax.jms.MessageListener;

import org.slf4j.Logger;

@MessageDriven(mappedName = "dataReader", activationConfig = {
		@ActivationConfigProperty(propertyName = "acknowledgeMode", propertyValue = "Auto-acknolowedge"),
		@ActivationConfigProperty(propertyName = "destinationType", propertyValue = "javax.jms.Queue") })
public class DataReader implements MessageListener {

	@Inject
	private Logger logger;
	
	@Override
	public void onMessage(Message message) {
		
	}
	
	public void processData(String data) {
		logger.debug("{}", data);
	}

}
