package com.clearprecision.sandbox.cdi;

import com.clearprecision.sandbox.cdi.interfaces.DataStore;

public class TestDataStore implements DataStore {

	@Override
	public String getName() {
		return "Test Data Store";
	}

	@Override
	public String getVersion() {
		return "1.0";
	}

}