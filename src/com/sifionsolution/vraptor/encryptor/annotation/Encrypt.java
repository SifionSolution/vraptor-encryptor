package com.sifionsolution.vraptor.encryptor.annotation;

import static java.lang.annotation.ElementType.PARAMETER;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import com.sifionsolution.vraptor.encryptor.EncryptStrategy;

@Retention(RUNTIME)
@Target(PARAMETER)
public @interface Encrypt {
	EncryptStrategy[] value();
}
