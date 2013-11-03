package com.ericsson.testrar;

import javax.annotation.Resource;
import javax.ejb.EJB;
import javax.ejb.Remote;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.resource.ResourceException;

@Stateless
@Remote(MediatioCoreInterface.class)
public class MockMediationCore implements MediatioCoreInterface {

	@Resource(mappedName = "java:/eis/AcmeConnectionFactory")
	private AcmeConnectionFactory connectionFactory1;

	@TransactionAttribute(TransactionAttributeType.SUPPORTS)
	@Override
	public void call() {
		try {
			connectionFactory1.getConnection().callMe();
		} catch (ResourceException e) {
			e.printStackTrace();
		}
	}

}
