package com.sifionsolution.vraptor.encryptor.configuration;

import java.lang.annotation.Annotation;
import java.util.HashSet;
import java.util.Set;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.sifionsolution.vraptor.encryptor.Encryptor;
import com.sifionsolution.vraptor.encryptor.annotation.Encrypt;
import com.sifionsolution.vraptor.encryptor.configuration.map.AnnotationMapping;
import com.sifionsolution.vraptor.encryptor.implementation.Sha512Encryptor;
import com.sifionsolution.vraptor.encryptor.salter.Salter;
import com.sifionsolution.vraptor.encryptor.salter.implementation.DefaultSalter;

@ApplicationScoped
public class EncryptConfiguration {

	private Set<AnnotationMapping> mappings = new HashSet<>();

	private static final Logger logger = LoggerFactory.getLogger(EncryptConfiguration.class);

	private Class<? extends Encryptor> defaultEncryptor;
	private Class<? extends Salter> defaultSalter;

	@PostConstruct
	public void init() {
		defaultEncryptor = Sha512Encryptor.class;
		defaultSalter = DefaultSalter.class;
	}

	/**
	 * Change the default Encryptor and Salter for Encrypt Annotation.
	 * 
	 * @param encryptor
	 * @param salter
	 * @return
	 */
	public EncryptConfiguration setDefaults(Class<? extends Encryptor> encryptor, Class<? extends Salter> salter) {
		logger.info("Changing default encryptor and salter");

		StringBuilder builder = new StringBuilder();

		if (encryptor != null) {
			builder.append("Changed Encryptor => ").append(defaultEncryptor.getName()).append(" to => ")
					.append(encryptor.getName());
			defaultEncryptor = encryptor;
		}

		if (salter != null) {
			builder.append("Changed Salter => ").append(defaultSalter.getName()).append(" to => ")
					.append(salter.getName());
			defaultSalter = salter;
		}

		logger.debug(builder.toString());

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
	public EncryptConfiguration map(Class<? extends Annotation> annotation, Class<? extends Encryptor> encryptor) {
		return map(annotation, encryptor, null);
	}

	/**
	 * Mapping the default Encryptor and Salter on a specific annotation class
	 * 
	 * @param annotation
	 * @param encryptor
	 * @param salter
	 * @return
	 */
	public EncryptConfiguration map(Class<? extends Annotation> annotation, Class<? extends Encryptor> encryptor,
			Class<? extends Salter> salter) {

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
			if (clazz == map.getAnnotation())
				return map;
		}

		logger.info("No Mapping found for Annotation: " + clazz.getName());

		return null;
	}

	public boolean contains(Annotation annotation) {
		return mappings.contains(new AnnotationMapping(annotation));
	}

	/**
	 * Default configured Salter
	 * 
	 * @return
	 */
	public Class<? extends Salter> getDefaultSalter() {
		return defaultSalter;
	}

	/**
	 * Default configured Encryptor
	 * 
	 * @return
	 */
	public Class<? extends Encryptor> getDefaultEncryptor() {
		return defaultEncryptor;
	}

	public void addDefaultsWhenNull() {
		mapDefault();

		for (AnnotationMapping map : mappings)
			map.addDefaultsWhenNotConfigured(defaultEncryptor, defaultSalter);
	}

	private void mapDefault() {
		map(Encrypt.class, defaultEncryptor, defaultSalter);
	}
}