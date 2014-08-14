package com.sifionsolution.vraptor.encryptor.configuration.map;

import com.sifionsolution.vraptor.encryptor.Encryptor;
import com.sifionsolution.vraptor.encryptor.salter.Salter;

public class AnnotationMapping {
	private Class<?> annotation;
	private Class<? extends Encryptor> encryptor;
	private Class<? extends Salter> salter;

	// TODO private Class<?> executor;

	public AnnotationMapping(Class<?> annotation, Class<? extends Encryptor> encryptor,
			Class<? extends Salter> salter) {
		this.annotation = annotation;
		this.encryptor = encryptor;
		this.salter = salter;
	}

	public void setEncryptor(Class<? extends Encryptor> encryptor) {
		this.encryptor = encryptor;
	}

	public void setSalter(Class<? extends Salter> salter) {
		this.salter = salter;
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
