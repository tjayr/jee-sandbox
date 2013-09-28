package com.clearprecision.sandbox.cdi;

import javax.enterprise.context.RequestScoped;
import javax.enterprise.inject.New;
import javax.enterprise.inject.Produces;

import com.clearprecision.sandbox.cdi.interfaces.DataStore;
import com.clearprecision.sandbox.cdi.qualifiers.Production;

public class DataStoreProducer {

	@Produces
	@RequestScoped
	@Production
	public DataStore getDataStore(@New TestDataStore testDataStore,
			@New ProductionDataStore productionDataStore) {

		String testEnv = System.getProperty("test.environment.active");
		if (testEnv != null) {
			return testDataStore;
		} else {
			return productionDataStore;
		}
	}

}
