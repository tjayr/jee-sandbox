package com.clearprecision.medcore;

import java.util.logging.Logger;

import javax.annotation.Resource;
import javax.ejb.Remote;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.resource.ResourceException;

import com.clearprecision.medcore.api.MediationCoreInterface;
import com.ericsson.testrar.AcmeConnection;
import com.ericsson.testrar.AcmeConnectionFactory;

@Stateless
@Remote
public class Router implements MediationCoreInterface {

	private static final Logger log = Logger.getLogger("DpsDelegate");

	@Resource(lookup = "java:/eis/AcmeConnectionFactory")
	private AcmeConnectionFactory factory;

	@TransactionAttribute(TransactionAttributeType.SUPPORTS)
	@Override
	public void call() {
		log.info("Calling resource adapter");
		AcmeConnection conn = null;
		try {
			conn = factory.getConnection();
			conn.callMe();
		} catch (ResourceException e) {
			log.severe(e.getMessage());		
			throw new RuntimeException(e.getMessage());
		} finally {
			if(conn != null) {
				conn.close();
			}
		}
	}
}
