package com.sifionsolution.vraptor.encryptor.configuration.map;

import static com.sifionsolution.commons.StringAdapter.getNullSafe;

import java.lang.annotation.Annotation;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.sifionsolution.vraptor.encryptor.Encryptor;
import com.sifionsolution.vraptor.encryptor.annotation.Encrypt;
import com.sifionsolution.vraptor.encryptor.salter.Salter;

public class AnnotationMapping {
	private final Class<? extends Annotation> annotation;
	private Class<? extends Encryptor> encryptor;
	private Class<? extends Salter> salter;

	// TODO private Class<?> executor;

	private static final Logger logger = LoggerFactory.getLogger(AnnotationMapping.class);

	public AnnotationMapping(Encrypt encryptAnnotation, Class<? extends Encryptor> defaultEncryptor,
			Class<? extends Salter> defaultSalter) {
		this(encryptAnnotation.getClass(), encryptAnnotation.value(), encryptAnnotation.salter());
		addDefaultsWhenNotConfigured(defaultEncryptor, defaultSalter);
	}

	public AnnotationMapping(Class<? extends Annotation> annotation, Class<? extends Encryptor> encryptor,
			Class<? extends Salter> salter) {
		if (annotation == null)
			throw new NullPointerException("The annotation class cannot be null.");

		this.annotation = annotation;
		this.encryptor = encryptor;
		this.salter = salter;
	}

	public AnnotationMapping(Annotation annotation) {
		this(annotation.getClass(), null, null);
	}

	@Override
	public String toString() {
		if (annotation == null)
			return "";

		return "[AnnotationMapping for '" + getNullSafe(annotation.getName()) + "']";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + toString().hashCode();
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		AnnotationMapping other = (AnnotationMapping) obj;

		return toString().equals(other.toString());
	}

	public Class<? extends Annotation> getAnnotation() {
		return annotation;
	}

	public Class<? extends Encryptor> getEncryptor() {
		return encryptor;
	}

	public Class<? extends Salter> getSalter() {
		return salter;
	}

	public void addDefaultsWhenNotConfigured(Class<? extends Encryptor> defaultEncryptor,
			Class<? extends Salter> defaultSalter) {
		logger.debug("Checking configuration used by Annotated values ");

		if (salter == Salter.class) {
			logger.debug("No Salter configured. Using default: " + defaultSalter.getName());

			salter = defaultSalter;
		}

		if (encryptor == Encryptor.class) {
			logger.debug("No Encryptor configured. Using default: " + defaultEncryptor.getName());

			encryptor = defaultEncryptor;
		}
	}

}
