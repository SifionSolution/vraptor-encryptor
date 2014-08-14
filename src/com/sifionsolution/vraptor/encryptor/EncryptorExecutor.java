package com.sifionsolution.vraptor.encryptor;

import java.lang.annotation.Annotation;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import br.com.caelum.vraptor.http.Parameter;

import com.sifionsolution.vraptor.encryptor.annotation.Encrypt;
import com.sifionsolution.vraptor.encryptor.configuration.EncryptConfiguration;
import com.sifionsolution.vraptor.encryptor.configuration.map.AnnotationMapping;
import com.sifionsolution.vraptor.encryptor.salter.Salter;

@RequestScoped
public class EncryptorExecutor {

	@Inject
	private EncryptConfiguration configuration;

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
		AnnotationMapping custom = getAnnotationMapping(parameter.getDeclaredAnnotations());

		return useCustomMappingToEncrypt(custom, toEncrypt);
	}

	/**
	 * Determines if should use the Encrypt Mapping Configuration or annotated
	 * salter
	 * 
	 * @return Salter instance
	 */
	private Salter createDefaultSalter() {
		return createSalter(configuration.getDefaultSalter());
	}

	/**
	 * Determines if should use the Encrypt Mapping Configuration or annotated
	 * encryptor
	 * 
	 * @return Encryptor instance
	 */
	private Encryptor createDefaultEncryptor() {
		return createEncryptor(configuration.getDefaultEncryptor());
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
		Salter salter = createSalter(custom.getSalter());

		return encryptor.encrypt(salter.salt(toEncrypt));
	}

	/**
	 * Checks for existing mapping based on the user's declared annotation.
	 * 
	 * @param declaredAnnotations
	 *            Developers declared annotations
	 * @return <code>null</code> if no mapping was found
	 */
	private AnnotationMapping getAnnotationMapping(Annotation[] declaredAnnotations) {
		for (Annotation annotation : declaredAnnotations) {
			AnnotationMapping map = configuration.findMapping(annotation.getClass());

			if (annotation instanceof Encrypt)
				map = new AnnotationMapping((Encrypt) annotation, map);

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
	private Salter createSalter(Class<? extends Salter> clazz) {
		try {
			if (clazz == null || Salter.class.equals(clazz))
				return createDefaultSalter();

			return clazz.newInstance();
		} catch (Exception e) {
			logger.error("Coult not instantiate Encryptor", e);
			logger.info("Returning default Encryptor");
			return createDefaultSalter();
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
			if (clazz == null || Encryptor.class.equals(clazz))
				return createDefaultEncryptor();

			return clazz.newInstance();
		} catch (Exception e) {
			logger.error("Coult not instantiate Encryptor", e);
			logger.info("Returning default Encryptor");
			return createDefaultEncryptor();
		}
	}
}
