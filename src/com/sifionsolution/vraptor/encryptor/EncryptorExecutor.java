package com.sifionsolution.vraptor.encryptor;

import java.lang.annotation.Annotation;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import br.com.caelum.vraptor.http.Parameter;

import com.sifionsolution.vraptor.encryptor.annotation.Encrypt;
import com.sifionsolution.vraptor.encryptor.configuration.CustomAnnotationConfiguration;
import com.sifionsolution.vraptor.encryptor.configuration.map.AnnotationMapping;
import com.sifionsolution.vraptor.encryptor.implementation.Sha512Encryptor;
import com.sifionsolution.vraptor.encryptor.salter.EncryptSalter;
import com.sifionsolution.vraptor.encryptor.salter.implementation.DefaultSalter;

@RequestScoped
public class EncryptorExecutor {

	@Inject
	private CustomAnnotationConfiguration configuration;

	private static final Logger logger = LoggerFactory.getLogger(EncryptorExecutor.class);

	/**
	 * Encrypts user parameter selecting the intended strategy
	 * 
	 * @param parameter
	 *            Intercepted parameter to encrypt
	 * @param toEncrypt
	 *            String to be encrypted
	 * @return Encoded String
	 */
	public String encrypt(Parameter parameter, String toEncrypt) {
		AnnotationMapping custom = findCustomAnnotationMapping(parameter.getDeclaredAnnotations());

		if (custom != null) {
			return useCustomMappingToEncrypt(custom, toEncrypt);
		}

		return defaultEncrypt(parameter.getAnnotation(Encrypt.class), toEncrypt);
	}

	/**
	 * Encrypts using default encrypting methods
	 * 
	 * @param annotation
	 * @param toEncrypt
	 * @return
	 */
	private String defaultEncrypt(Encrypt annotation, String toEncrypt) {
		// TODO will be encapsulated by EncryptStrategy feature
		Encryptor encryptor = extractEncryptor(annotation);
		EncryptSalter salter = extractSalter(annotation);

		return encryptor.encrypt(salter.salt(toEncrypt));
	}

	/**
	 * Determines if should use the Encrypt Mapping Configuration or annotated
	 * salter
	 * 
	 * @param annotation
	 * @return Salter instance
	 */
	private EncryptSalter extractSalter(Encrypt annotation) {
		if (annotation.salter() != EncryptSalter.class) {
			return createSalter(annotation.salter());
		}

		return createSalter(configuration.getEncryptDefaultSalter());
	}

	/**
	 * Determines if should use the Encrypt Mapping Configuration or annotated
	 * encryptor
	 * 
	 * @param annotation
	 * @return Encryptor instance
	 */
	private Encryptor extractEncryptor(Encrypt annotation) {
		if (annotation.value() != Encryptor.class) {
			return createEncryptor(annotation.value());
		}

		return createEncryptor(configuration.getEncryptDefaultEncryptor());
	}

	/**
	 * Encrypts using custom mapping.
	 * 
	 * @param custom
	 *            Mapping to be used for encryption
	 * @param toEncrypt
	 *            String to be encrypted
	 * @return Encoded String
	 */
	private String useCustomMappingToEncrypt(AnnotationMapping custom, String toEncrypt) {
		// TODO will be encapsulated by EncryptStrategy feature

		Encryptor encryptor = createEncryptor(custom.getEncryptor());
		EncryptSalter salter = createSalter(custom.getSalter());

		return encryptor.encrypt(salter.salt(toEncrypt));
	}

	/**
	 * Checks for existing mapping based on the user's declared annotation.
	 * 
	 * @param declaredAnnotations
	 *            Developers declared annotations
	 * @return <code>null</code> if no mapping was found
	 */
	private AnnotationMapping findCustomAnnotationMapping(Annotation[] declaredAnnotations) {
		for (Annotation annotation : declaredAnnotations) {
			AnnotationMapping map = configuration.findMapping(annotation.getClass());

			if (map != null)
				return map;
		}

		logger.debug("No custom annotations used");
		return null;
	}

	/**
	 * Utility for instantiating the Salter
	 * 
	 * @param clazz
	 * @return Salter instance
	 */
	private EncryptSalter createSalter(Class<? extends EncryptSalter> clazz) {
		try {
			return clazz.newInstance();
		} catch (Exception e) {
			logger.error("Coult not instantiate Salter", e);
			logger.info("Returning default Salter");
			return new DefaultSalter();
		}
	}

	/**
	 * Utility for instantiating the Encryptor
	 * 
	 * @param clazz
	 * @return Encryptor instance
	 */
	private Encryptor createEncryptor(Class<? extends Encryptor> clazz) {
		try {
			return clazz.newInstance();
		} catch (Exception e) {
			logger.error("Coult not instantiate Encryptor", e);
			logger.info("Returning default Encryptor");
			return new Sha512Encryptor();
		}
	}

}
