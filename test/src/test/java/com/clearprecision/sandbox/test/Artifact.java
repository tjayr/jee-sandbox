package com.clearprecision.sandbox.test;

import java.io.File;

import org.jboss.shrinkwrap.resolver.api.maven.Maven;
import org.jboss.shrinkwrap.resolver.api.maven.MavenResolvedArtifact;

public class Artifact {
	
	public static File getArtifact(String gav) {
		MavenResolvedArtifact artifact = Maven.resolver().resolve(gav)
				.withoutTransitivity().asSingle(MavenResolvedArtifact.class);

		return artifact.asFile();
	}
}
