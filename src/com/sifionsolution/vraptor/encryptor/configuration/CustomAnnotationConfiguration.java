package com.sifionsolution.vraptor.encryptor.configuration;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.sifionsolution.vraptor.encryptor.Encryptor;
import com.sifionsolution.vraptor.encryptor.annotation.Encrypt;
import com.sifionsolution.vraptor.encryptor.configuration.map.AnnotationMapping;
import com.sifionsolution.vraptor.encryptor.implementation.Sha512Encryptor;
import com.sifionsolution.vraptor.encryptor.salter.EncryptSalter;
import com.sifionsolution.vraptor.encryptor.salter.implementation.DefaultSalter;

@ApplicationScoped
public class CustomAnnotationConfiguration {

	private List<AnnotationMapping> mappings;

	private static final Logger logger = LoggerFactory.getLogger(CustomAnnotationConfiguration.class);

	@PostConstruct
	public void init() {
		mappings = new ArrayList<AnnotationMapping>();

		mappings.add(new AnnotationMapping(Encrypt.class, Sha512Encryptor.class, DefaultSalter.class));
	}

	/**
	 * Change the default Encryptor and Salter for Encrypt Annotation.
	 * 
	 * @param encryptor
	 * @param salter
	 * @return
	 */
	public CustomAnnotationConfiguration setDefaults(Class<? extends Encryptor> encryptor,
			Class<? extends EncryptSalter> salter) {

		logger.debug("Changing Encrypt defaults: Encryptor => " + encryptor.getCanonicalName() + " Salter => "
				+ salter.getCanonicalName());
		AnnotationMapping map = getEncryptMap();
		map.setEncryptor(encryptor);
		map.setSalter(salter);

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
	public CustomAnnotationConfiguration map(Class<?> annotation, Class<? extends Encryptor> encryptor) {
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
	public CustomAnnotationConfiguration map(Class<?> annotation, Class<? extends Encryptor> encryptor,
			Class<? extends EncryptSalter> salter) {

		if (annotation == Encrypt.class) {
			return setDefaults(encryptor, salter);
		}

		logger.debug("Mapping annotation " + annotation.getCanonicalName() + ": Encryptor => "
				+ encryptor.getCanonicalName() + " Salter => " + salter.getCanonicalName());
		mappings.add(new AnnotationMapping(annotation, encryptor, salter));

		return this;
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

		logger.info("No Mapping found for Annotation: " + clazz.getCanonicalName());

		return null;
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
