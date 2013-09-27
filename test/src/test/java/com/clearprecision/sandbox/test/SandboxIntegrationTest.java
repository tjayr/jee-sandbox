package com.clearprecision.sandbox.test;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.Archive;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(Arquillian.class)
public class SandboxIntegrationTest {

	@Deployment
	public static Archive<?> createDeployment() {
		return Deployments.createSandboxEarDeployment();
	}

	@Test
	public void testDeploy() {
		System.out.println("test ear deployment ok");
	}
}
