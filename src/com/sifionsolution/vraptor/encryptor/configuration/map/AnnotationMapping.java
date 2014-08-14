package com.sifionsolution.vraptor.encryptor.configuration.map;

import static com.sifionsolution.commons.StringAdapter.getNullSafe;

import com.sifionsolution.vraptor.encryptor.Encryptor;
import com.sifionsolution.vraptor.encryptor.salter.Salter;

public class AnnotationMapping {
	private final Class<?> annotation;
	private final Class<? extends Encryptor> encryptor;
	private final Class<? extends Salter> salter;

	// TODO private Class<?> executor;

	public AnnotationMapping(Class<?> annotation, Class<? extends Encryptor> encryptor, Class<? extends Salter> salter) {
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

	public Class<?> getAnnotation() {
		return annotation;
	}

	public Class<? extends Encryptor> getEncryptor() {
		return encryptor;
	}

	public Class<? extends Salter> getSalter() {
		return salter;
	}

}
