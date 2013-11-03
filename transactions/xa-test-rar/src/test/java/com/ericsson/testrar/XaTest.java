package com.ericsson.testrar;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.logging.Logger;

import javax.ejb.EJB;

import org.apache.commons.httpclient.HttpClient;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.jboss.arquillian.container.test.api.ContainerController;
import org.jboss.arquillian.container.test.api.Deployer;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.container.test.api.OperateOnDeployment;
import org.jboss.arquillian.container.test.api.RunAsClient;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.arquillian.junit.InSequence;
import org.jboss.arquillian.test.api.ArquillianResource;
import org.jboss.shrinkwrap.api.Archive;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(Arquillian.class)
public class XaTest {

	@ArquillianResource
	private ContainerController controller;

	@ArquillianResource
	private Deployer deployer;

	private static final Logger log = Logger.getLogger("XaTest");

	@Deployment(name = "connector-rar", managed = false, testable = false)
	public static Archive<?> createResourceAdapter() {
		return XaTestBuilder.createRar();
	}

	@Deployment(name = "dps-ear", managed = false, testable = true)
	public static Archive<?> createDps() {
		return XaTestBuilder.createDpsEar();
	}

	@Deployment(name = "core-ear", managed = false, testable = true)
	public static Archive<?> createMedCore() {
		return XaTestBuilder.createCoreEar();
	}

	/**
	 * Start executing tests, deploy rar
	 */
	@Test
	@InSequence(1)
	@OperateOnDeployment("connector-rar")
	public void testDeployRar() throws Exception {
		log.info("Deploy rar");
		this.deployer.deploy("connector-rar");
	}

	@Test
	@InSequence(2)
	@OperateOnDeployment("core-ear")
	public void deployCore() throws Exception {
		log.info("Deploy core");
		this.deployer.deploy("core-ear");
	}

	@Test
	@InSequence(3)
	@OperateOnDeployment("dps-ear")
	public void deployDps() throws Exception {
		log.info("Deploy dps");
		this.deployer.deploy("dps-ear");
	}

	@Test
	@InSequence(4)
	@RunAsClient
	public void fireRequest() throws Exception {
		DefaultHttpClient httpClient = new DefaultHttpClient();
		HttpGet getRequest = new HttpGet(
				"http://localhost:8580/dps-web/rest/dps");
		getRequest.addHeader("accept", "text/plain");

		HttpResponse response = httpClient.execute(getRequest);

		if (response.getStatusLine().getStatusCode() != 200) {
			throw new RuntimeException("Failed : HTTP error code : "
					+ response.getStatusLine().getStatusCode());
		}

		BufferedReader br = new BufferedReader(new InputStreamReader(
				(response.getEntity().getContent())));

		String output;
		log.info("Output from Server .... ");
		while ((output = br.readLine()) != null) {
			log.info(output);
		}

		httpClient.getConnectionManager().shutdown();

	}

}
