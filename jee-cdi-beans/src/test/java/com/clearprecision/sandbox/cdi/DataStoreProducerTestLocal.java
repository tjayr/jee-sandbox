package com.clearprecision.sandbox.cdi;

import static org.junit.Assert.assertEquals;

import javax.inject.Inject;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.clearprecision.sandbox.cdi.interfaces.DataStore;
import com.clearprecision.sandbox.cdi.qualifiers.DatabaseEnv;
import com.clearprecision.sandbox.cdi.qualifiers.EnvironmentTypes;

@RunWith(Arquillian.class)
public class DataStoreProducerTestLocal {

	@Deployment
	public static JavaArchive createDeployment() {
		return ShrinkWrap.create(JavaArchive.class)
				.addClass(DataStoreProducer.class)
				.addClass(ProductionDataStore.class)
				.addClass(TestDataStore.class).addClass(DataStore.class)
				.addClass(DatabaseEnv.class).addClass(EnvironmentTypes.class)
				.addAsManifestResource(EmptyAsset.INSTANCE, "beans.xml");
	}

	@BeforeClass
	public static void setup() {
		System.setProperty("test.environment.active", "true");
	}

	@Inject
	DataStore dataStore;

	@Test
	public void testEnv() {
		assertEquals("Test Data Store", dataStore.getName());
	}

}
