package com.fsk.airline.reservation.app.messaging;

import org.apache.qpid.server.SystemLauncher;
import org.junit.jupiter.api.extension.AfterAllCallback;
import org.junit.jupiter.api.extension.BeforeAllCallback;
import org.junit.jupiter.api.extension.ExtensionContext;

import java.util.HashMap;
import java.util.Map;

public class QpidEmbedded implements BeforeAllCallback, AfterAllCallback {

	private final SystemLauncher broker = new SystemLauncher();

	@Override
	public void beforeAll(ExtensionContext context) throws Exception {
		startQpidBroker();
	}

	@Override
	public void afterAll(ExtensionContext context) {
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
