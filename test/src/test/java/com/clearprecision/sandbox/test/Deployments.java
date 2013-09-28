package com.clearprecision.sandbox.test;

import java.io.File;

import org.jboss.shrinkwrap.api.Archive;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.spec.EnterpriseArchive;
import org.jboss.shrinkwrap.resolver.api.maven.Maven;
import org.jboss.shrinkwrap.resolver.api.maven.MavenResolvedArtifact;

public class Deployments {

	public static Archive<?> createSandboxEarDeployment() {
		System.setProperty("maven.repo.local", "/home/tony/.m2");
		File pom = new File("/home/tony/git-projects/jee-sandbox-ear/test/pom.xml");
		File file = Maven.resolver().offline().loadPomFromFile(pom).importTestDependencies()
				.resolve("com.clearprecision:jee-sandbox-ear:ear:1.0.1-SNAPSHOT")				
				.withoutTransitivity().asSingle(MavenResolvedArtifact.class)
				.asFile();

		EnterpriseArchive ear = ShrinkWrap.createFromZipFile(
				EnterpriseArchive.class, file);
		return ear;
	}
	
	public static void main(String[] args) {
		Deployments.createSandboxEarDeployment();
	}

}
