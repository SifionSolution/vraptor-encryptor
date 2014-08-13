package com.sifionsolution.vraptor.encryptor.annotation;

import static com.sifionsolution.vraptor.encryptor.EncryptStrategy.SHA512;
import static com.sifionsolution.vraptor.encryptor.salter.SalterStrategy.DEFAULT;
import static java.lang.annotation.ElementType.PARAMETER;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import com.sifionsolution.vraptor.encryptor.EncryptStrategy;
import com.sifionsolution.vraptor.encryptor.salter.SalterStrategy;

@Retention(RUNTIME)
@Target(PARAMETER)
public @interface Encrypt {
	EncryptStrategy[] value() default SHA512;

	SalterStrategy[] salter() default DEFAULT;

}
