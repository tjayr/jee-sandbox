package com.clearprecision.sandbox.cdi;

import static com.clearprecision.sandbox.cdi.qualifiers.EnvironmentTypes.PRODUCTION;
import static com.clearprecision.sandbox.cdi.qualifiers.EnvironmentTypes.TEST;

import javax.enterprise.context.RequestScoped;
import javax.enterprise.inject.Produces;

import com.clearprecision.sandbox.cdi.interfaces.DataStore;
import com.clearprecision.sandbox.cdi.qualifiers.DatabaseEnv;

public class DataStoreProducer {

	@Produces
	@RequestScoped
	public DataStore getDataStore(
			@DatabaseEnv(TEST) TestDataStore testDataStore,
			@DatabaseEnv(PRODUCTION) ProductionDataStore productionDataStore) {

		String testEnv = System.getProperty("test.environment.active");
		if (testEnv != null) {
			return testDataStore;
		} else {
			return productionDataStore;
		}
	}

}
