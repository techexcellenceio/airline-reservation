package com.fsk.airline.reservation.app.configuration;

import org.apache.qpid.server.SystemLauncher;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@ConditionalOnProperty(value = "application.messaging.broker.embedded", havingValue = "true")
@Component
public class QpidEmbeddedComponent implements ApplicationRunner, DisposableBean {

	private final SystemLauncher broker = new SystemLauncher();

	@Override
	public void run(ApplicationArguments args) throws Exception {
		startQpidBroker();
	}

	@Override
	public void destroy() throws Exception {
		broker.shutdown();
	}

	private void startQpidBroker() throws Exception {
		Map<String, Object> attributes = new HashMap<>();
		attributes.put("type", "Memory");
		attributes.put("initialConfigurationLocation", findResourcePath("qpid-config.json"));
		broker.startup(attributes);
	}

	private String findResourcePath(String fileName) {
		return QpidEmbeddedComponent.class.getClassLoader().getResource(fileName).toExternalForm();
	}
}
