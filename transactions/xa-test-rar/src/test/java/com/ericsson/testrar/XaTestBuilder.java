package com.ericsson.testrar;

import java.io.File;
import java.util.UUID;

import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.spec.EnterpriseArchive;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.jboss.shrinkwrap.api.spec.ResourceAdapterArchive;
import org.jboss.shrinkwrap.resolver.api.DependencyResolvers;
import org.jboss.shrinkwrap.resolver.api.maven.MavenDependencyResolver;

public class XaTestBuilder {

	public static ResourceAdapterArchive createRar() {

		// final File archiveFile =
		// resolveArtifactWithoutDependencies("com.clearprecision:testrar:rar");
		// if (archiveFile == null) {
		// throw new IllegalStateException("Unable to resolve artifact ");
		// }
		// final ResourceAdapterArchive rar = ShrinkWrap.createFromZipFile(
		// ResourceAdapterArchive.class, archiveFile);
		// return rar;
		//
		// public static ResourceAdapterArchive createRar() {
		ResourceAdapterArchive raa = ShrinkWrap.create(
				ResourceAdapterArchive.class, "XaConnectorTestCase.rar");
		JavaArchive ja = ShrinkWrap.create(JavaArchive.class, UUID.randomUUID()
				.toString() + ".jar");
		ja.addClasses(AcmeResourceAdapter.class,
				AcmeManagedConnectionFactory.class,
				AcmeManagedConnection.class, AcmeConnectionFactoryImpl.class,
				AcmeConnectionImpl.class);
		raa.addAsLibrary(ja);

		raa.addAsManifestResource("META-INF/ironjacamar.xml", "ironjacamar.xml");
		raa.addAsManifestResource("test-manifest.mf", "MANIFEST.MF");

		return raa;

	}

	public static EnterpriseArchive createCoreEar() {

		final File archiveFile = resolveArtifactWithoutDependencies("com.clearprecision:medcore-ear:ear");
		if (archiveFile == null) {
			throw new IllegalStateException("Unable to resolve artifact ");
		}
		final EnterpriseArchive archive = ShrinkWrap.createFromZipFile(
				EnterpriseArchive.class, archiveFile);

		return archive;
	}

	public static EnterpriseArchive createDpsEar() {
		final File archiveFile = resolveArtifactWithoutDependencies("com.clearprecision:dps-ear:ear");
		if (archiveFile == null) {
			throw new IllegalStateException("Unable to resolve dps artifact ");
		}
		final EnterpriseArchive archive = ShrinkWrap.createFromZipFile(
				EnterpriseArchive.class, archiveFile);

		return archive;
	}

	/**
	 * Maven resolver that will try to resolve dependencies using pom.xml of the
	 * project where this class is located.
	 * 
	 * @return MavenDependencyResolver
	 */
	public static MavenDependencyResolver getMavenResolver() {
		return DependencyResolvers.use(MavenDependencyResolver.class)
				.loadMetadataFromPom("pom.xml");

	}

	/**
	 * Resolve artifacts without dependencies
	 * 
	 * @param artifactCoordinates
	 * @return
	 */
	public static File resolveArtifactWithoutDependencies(
			final String artifactCoordinates) {
		final File[] artifacts = getMavenResolver()
				.artifact(artifactCoordinates).exclusion("*").resolveAsFiles();
		if (artifacts == null) {
			throw new IllegalStateException("Artifact with coordinates "
					+ artifactCoordinates + " was not resolved");
		}
		if (artifacts.length != 1) {
			throw new IllegalStateException(
					"Resolved more then one artifact with coordinates "
							+ artifactCoordinates);
		}
		return artifacts[0];
	}

}
