package com.sifionsolution.vraptor.encryptor.annotation;

import static java.lang.annotation.ElementType.PARAMETER;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import com.sifionsolution.vraptor.encryptor.Encryptor;
import com.sifionsolution.vraptor.encryptor.salter.EncryptSalter;

@Retention(RUNTIME)
@Target(PARAMETER)
public @interface Encrypt {
	Class<? extends Encryptor> value() default Encryptor.class;

	Class<? extends EncryptSalter> salter() default EncryptSalter.class;
}
