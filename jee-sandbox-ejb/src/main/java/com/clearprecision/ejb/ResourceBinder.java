package com.clearprecision.ejb;

import javax.annotation.PostConstruct;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.naming.InitialContext;
import javax.naming.NamingException;

@Singleton
@Startup
public class ResourceBinder {

	private static final String APP_VERSION = "1.0";

	@PostConstruct
	public void bindResources() {
		InitialContext ctx;
		try {
			ctx = new InitialContext();
			ctx.rebind("APPVERSION", APP_VERSION);
		} catch (NamingException e) {
			e.printStackTrace();
		}
	}

}
