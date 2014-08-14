package com.sifionsolution.vraptor.encryptor.annotation;

import static java.lang.annotation.ElementType.PARAMETER;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import com.sifionsolution.vraptor.encryptor.Encryptor;
import com.sifionsolution.vraptor.encryptor.implementation.Sha512Encryptor;
import com.sifionsolution.vraptor.encryptor.salter.EncryptSalter;
import com.sifionsolution.vraptor.encryptor.salter.implementation.DefaultSalter;

@Retention(RUNTIME)
@Target(PARAMETER)
public @interface Encrypt {
	Class<? extends Encryptor> value() default Sha512Encryptor.class;

	Class<? extends EncryptSalter> salter() default DefaultSalter.class;
}
