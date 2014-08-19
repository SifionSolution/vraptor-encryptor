package com.sifionsolution.vraptor.encryptor.configuration.executor;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.event.Observes;
import javax.enterprise.inject.Instance;
import javax.inject.Inject;
import javax.servlet.ServletContext;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.sifionsolution.vraptor.encryptor.configuration.Configuration;
import com.sifionsolution.vraptor.encryptor.configuration.EncryptorConfigurator;

@ApplicationScoped
public class EncryptorConfigurationExecutor {

	@Inject
	private Instance<EncryptorConfigurator> encryptorConfiguration;

	@Inject
	private Configuration configuration;

	private static final Logger logger = LoggerFactory.getLogger(EncryptorConfigurationExecutor.class);

	@PostConstruct
	public void init(@Observes ServletContext context) {
		logger.info("Initializing encryptor configuration");

		if (!encryptorConfiguration.isUnsatisfied()) {
			encryptorConfiguration.get().configure(configuration);
			logger.info("Encyptor configured");
		}

		configuration.addDefaultsWhenNull();
	}
}
