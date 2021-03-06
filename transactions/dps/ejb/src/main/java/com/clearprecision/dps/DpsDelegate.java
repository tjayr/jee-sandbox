/*
 * JBoss, Home of Professional Open Source
 * Copyright 2013, Red Hat, Inc. and/or its affiliates, and individual
 * contributors by the @authors tag. See the copyright.txt in the
 * distribution for a full listing of individual contributors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.clearprecision.dps;

import java.util.Hashtable;
import java.util.logging.Logger;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import com.clearprecision.medcore.api.MediationCoreInterface;

@Stateless
public class DpsDelegate {

	private static final Logger log = Logger.getLogger("DpsDelegate");

	//@EJB(lookup = "java:jboss/exported/medcore-ear-0.0.1-SNAPSHOT/medcore-ejb/Router!com.clearprecision.medcore.api.MediationCoreInterface")
	//private MediationCoreInterface router;

	@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
	public void process() {
		MediationCoreInterface router;
		try {
			router = lookupRemoteEJB();
			router.call();
		} catch (NamingException e) {
			log.severe(e.toString());
		}
	}

	private static MediationCoreInterface lookupRemoteEJB()
			throws NamingException {

		final Hashtable jndiProperties = new Hashtable();
		jndiProperties.put(Context.URL_PKG_PREFIXES,
				"org.jboss.ejb.client.naming");
		final Context context = new InitialContext(jndiProperties);
		log.info("Looking EJB via JNDI ");

		return (MediationCoreInterface) context
				.lookup("ejb:medcore-ear-0.0.1-SNAPSHOT/medcore-ejb/Router!com.clearprecision.medcore.api.MediationCoreInterface");		
	}

}
