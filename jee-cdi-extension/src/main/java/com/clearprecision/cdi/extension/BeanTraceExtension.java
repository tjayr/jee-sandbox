package com.clearprecision.cdi.extension;

import javax.enterprise.event.Observes;
import javax.enterprise.inject.spi.AfterBeanDiscovery;
import javax.enterprise.inject.spi.BeforeBeanDiscovery;
import javax.enterprise.inject.spi.Extension;
import javax.enterprise.inject.spi.ProcessAnnotatedType;
import javax.enterprise.inject.spi.ProcessBean;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class BeanTraceExtension implements Extension {
	
	private static final Logger logger = LoggerFactory
			.getLogger(BeanTraceExtension.class);
	
	
	public BeanTraceExtension() {
		logger.info("Creating bean trace extension");
	}
	
	void beforeBeanDiscovery(@Observes BeforeBeanDiscovery bbd) {
		logger.info("beginning the scanning process");		
	}
	

	<T> void processAnnotatedType(@Observes ProcessAnnotatedType<T> pat) {
		logger.info("scanning type: "
				+ pat.getAnnotatedType().getJavaClass().getName());			
	}

	void afterBeanDiscovery(@Observes AfterBeanDiscovery abd) {
		logger.info("finished the scanning process");		
	}
	
	
	<T> void processBean(@Observes ProcessBean<T> bean) {
		logger.info("Extension found bean with name {}", bean.getBean().getName());
	}
	
}
