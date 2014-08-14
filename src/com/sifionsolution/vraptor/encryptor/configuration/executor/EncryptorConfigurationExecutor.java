package com.sifionsolution.vraptor.encryptor.configuration.executor;

import javax.annotation.PostConstruct;
import javax.ejb.Startup;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Instance;
import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.sifionsolution.vraptor.encryptor.configuration.EncryptConfiguration;
import com.sifionsolution.vraptor.encryptor.configuration.EncryptorConfiguration;

@Startup
@ApplicationScoped
public class EncryptorConfigurationExecutor {

	@Inject
	private Instance<EncryptorConfiguration> encryptorConfiguration;

	@Inject
	private EncryptConfiguration configuration;

	private static final Logger logger = LoggerFactory.getLogger(EncryptorConfigurationExecutor.class);

	@PostConstruct
	public void init() {
		logger.info("Initializing encryptor configuration");

		if (!encryptorConfiguration.isUnsatisfied()) {
			encryptorConfiguration.get().configure(configuration);
			logger.info("Encyptor configured");
		}
	}
}
