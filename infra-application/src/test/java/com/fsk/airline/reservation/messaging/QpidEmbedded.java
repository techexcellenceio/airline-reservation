package com.fsk.airline.reservation.messaging;

import org.apache.qpid.server.SystemLauncher;
import org.junit.jupiter.api.extension.AfterEachCallback;
import org.junit.jupiter.api.extension.BeforeEachCallback;
import org.junit.jupiter.api.extension.ExtensionContext;

import java.util.HashMap;
import java.util.Map;

public class QpidEmbedded implements BeforeEachCallback, AfterEachCallback {

	private final SystemLauncher broker = new SystemLauncher();

	@Override
	public void beforeEach(ExtensionContext context) throws Exception {
		startQpidBroker();
	}

	@Override
	public void afterEach(ExtensionContext context) {
		broker.shutdown();
	}

	private void startQpidBroker() throws Exception {
		Map<String, Object> attributes = new HashMap<>();
		attributes.put("type", "Memory");
		attributes.put("initialConfigurationLocation", findResourcePath("qpid-config.json"));
		broker.startup(attributes);
	}

	private String findResourcePath(String fileName) {
		return QpidEmbedded.class.getClassLoader().getResource(fileName).toExternalForm();
	}
}
