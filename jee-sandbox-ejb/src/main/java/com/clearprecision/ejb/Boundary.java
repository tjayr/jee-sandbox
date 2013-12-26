package com.clearprecision.ejb;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import org.slf4j.Logger;

import com.clearprecision.sandbox.cdi.interfaces.DataStore;

@ApplicationScoped
public class Boundary {

	private static final Logger logger = org.slf4j.LoggerFactory
			.getLogger(Boundary.class);

	@Inject
	DataStore dataStore;

	public String getDataStoreName() {
		String name = dataStore.getName();
		logger.info("Boundary postcontruct, dataStore created with name {}",
				name);
		return name;
	}

}
