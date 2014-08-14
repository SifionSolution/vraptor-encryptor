package com.sifionsolution.vraptor.encryptor.configuration;

import java.util.HashSet;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.sifionsolution.vraptor.encryptor.Encryptor;
import com.sifionsolution.vraptor.encryptor.annotation.Encrypt;
import com.sifionsolution.vraptor.encryptor.configuration.map.AnnotationMapping;
import com.sifionsolution.vraptor.encryptor.implementation.Sha512Encryptor;
import com.sifionsolution.vraptor.encryptor.salter.Salter;
import com.sifionsolution.vraptor.encryptor.salter.implementation.DefaultSalter;

public class EncryptorConfiguration {
	private static final Logger logger = LoggerFactory.getLogger(EncryptorConfiguration.class);
	private static final EncryptorConfiguration instance = new EncryptorConfiguration();

	private final Set<AnnotationMapping> mappings = new HashSet<>();

	private EncryptorConfiguration() {
		map(Encrypt.class, Sha512Encryptor.class, DefaultSalter.class);
	}

	public static EncryptorConfiguration getInstance() {
		return instance;
	}

	/**
	 * Change the default Encryptor and Salter for Encrypt Annotation.
	 * 
	 * @param encryptor
	 * @param salter
	 * @return
	 */
	public EncryptorConfiguration setDefault(Class<? extends Encryptor> encryptor, Class<? extends Salter> salter) {
		logger.debug("Changing Encrypt defaults: Encryptor => " + encryptor.getCanonicalName() + " Salter => "
				+ salter.getCanonicalName());

		map(Encrypt.class, encryptor, salter);

		return this;
	}

	/**
	 * Mapping default Encryptor on a specific annotation class.
	 * <p>
	 * Note: Default Salter will be used
	 * 
	 * @param annotation
	 * @param encryptor
	 * @return
	 */
	public EncryptorConfiguration map(Class<?> annotation, Class<? extends Encryptor> encryptor) {
		AnnotationMapping map = getEncryptMap();

		return map(annotation, encryptor, map.getSalter());
	}

	/**
	 * Mapping the default Encryptor and Salter on a specific annotation class
	 * 
	 * @param annotation
	 * @param encryptor
	 * @param salter
	 * @return
	 */
	public EncryptorConfiguration map(Class<?> annotation, Class<? extends Encryptor> encryptor,
			Class<? extends Salter> salter) {
		
		// TODO What if developer send null parameters?

		logger.debug("Mapping annotation " + annotation.getCanonicalName() + ": Encryptor => "
				+ encryptor.getCanonicalName() + " Salter => " + salter.getCanonicalName());

		replaceIfExists(new AnnotationMapping(annotation, encryptor, salter));

		return this;
	}

	private void replaceIfExists(AnnotationMapping annotationMapping) {
		AnnotationMapping old = findMapping(annotationMapping);

		if (old != null)
			mappings.remove(old);

		mappings.add(annotationMapping);
	}

	private AnnotationMapping findMapping(AnnotationMapping annotationMapping) {
		return findMapping(annotationMapping.getAnnotation());
	}

	/**
	 * Find the AnnotationMapping refering to desired annotation class.
	 * 
	 * @param clazz
	 *            - Annotation to be found
	 * @return <code>null</code> if no AnnotationMapping was found
	 */
	public AnnotationMapping findMapping(Class<?> clazz) {
		for (AnnotationMapping map : mappings) {
			if (clazz.equals(map.getAnnotation()))
				return map;
		}

		logger.info("No Mapping found for Annotation: " + clazz.getCanonicalName());

		return null;
	}

	/**
	 * Default configured Salter
	 * 
	 * @return
	 */
	public Class<? extends Salter> getDefaultSalter() {
		return getEncryptMap().getSalter();
	}

	/**
	 * Default configured Encryptor
	 * 
	 * @return
	 */
	public Class<? extends Encryptor> getDefaultEncryptor() {
		return getEncryptMap().getEncryptor();
	}

	/**
	 * Shortcut for <code>findingMaping(Encrypt.class)</code>
	 * 
	 * @return <code>null</code> if Encrypt Map was not found
	 */
	private AnnotationMapping getEncryptMap() {
		return findMapping(Encrypt.class);
	}
}
