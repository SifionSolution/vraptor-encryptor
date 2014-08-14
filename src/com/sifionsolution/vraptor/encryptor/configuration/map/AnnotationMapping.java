package com.sifionsolution.vraptor.encryptor.configuration.map;

import static com.sifionsolution.commons.StringAdapter.getNullSafe;

import java.lang.annotation.Annotation;

import com.sifionsolution.vraptor.encryptor.Encryptor;
import com.sifionsolution.vraptor.encryptor.salter.Salter;

public class AnnotationMapping {
	private final Class<? extends Annotation> annotation;
	private Class<? extends Encryptor> encryptor;
	private Class<? extends Salter> salter;

	// TODO private Class<?> executor;

	public AnnotationMapping(Class<? extends Annotation> annotation, Class<? extends Encryptor> encryptor,
			Class<? extends Salter> salter) {
		if (annotation == null)
			throw new NullPointerException("The annotation class cannot be null.");

		this.annotation = annotation;
		this.encryptor = encryptor;
		this.salter = salter;
	}

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

	public void addDefaultsWhenNull(AnnotationMapping defaultMap) {
		if (salter == null)
			salter = defaultMap.salter;

		if (encryptor == null)
			encryptor = defaultMap.encryptor;
	}

}
