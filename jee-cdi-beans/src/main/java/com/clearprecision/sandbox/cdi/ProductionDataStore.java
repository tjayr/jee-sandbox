package com.clearprecision.sandbox.cdi;

import com.clearprecision.sandbox.cdi.interfaces.DataStore;

public class ProductionDataStore implements DataStore {

	@Override
	public String getName() {
		return "Production Data Store";
	}

	@Override
	public String getVersion() {
		return "1.0";
	}

}
