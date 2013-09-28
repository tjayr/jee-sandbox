package com.clearprecision.sandbox.cdi;

import static org.junit.Assert.*;

import javax.inject.Inject;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.clearprecision.sandbox.cdi.interfaces.DataStore;
import com.clearprecision.sandbox.cdi.qualifiers.Production;

@RunWith(Arquillian.class)
public class DataStoreProducerTestProduction {

	@Deployment
	public static JavaArchive createDeployment() {
		return ShrinkWrap.create(JavaArchive.class)
				.addClass(DataStoreProducer.class)
				.addClass(ProductionDataStore.class)
				.addClass(TestDataStore.class).addClass(DataStore.class)
				.addClass(Production.class).addClass(Test.class)
				.addAsManifestResource(EmptyAsset.INSTANCE, "beans.xml");
	}

	@Inject
	@Production
	DataStore dataStore;

	@Test
	public void productionEnv() {
		assertEquals("Production Data Store", dataStore.getName());
	}

}
