package com.clearprecision.sandbox.test;

import java.io.File;

import org.jboss.shrinkwrap.api.Archive;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.spec.EnterpriseArchive;
import org.jboss.shrinkwrap.resolver.api.maven.Maven;
import org.jboss.shrinkwrap.resolver.api.maven.MavenResolvedArtifact;

public class Deployments {

	public static Archive<?> createSandboxEarDeployment() {
		File file = Maven.resolver()
				.resolve("com.clearprecision:jee-sandbox-ear:ear")
				.withoutTransitivity().asSingle(MavenResolvedArtifact.class)
				.asFile();

		EnterpriseArchive ear = ShrinkWrap.createFromZipFile(
				EnterpriseArchive.class, file);
		return ear;
	}

}
