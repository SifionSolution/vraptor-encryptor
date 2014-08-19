package com.sifionsolution.vraptor.encryptor;

import com.sifionsolution.vraptor.encryptor.annotation.Encrypt;

public class MyController {

	public void first(@Encrypt String password, Integer number, Object obj) {
	}

	public void middle(Integer number, @Encrypt String password, Object obj) {
	}

	public void last(Object obj, Integer number, @Encrypt String password) {
	}

	public void unused(String whatever, Object obj) {
	}

	public void custom(String whatever, Object obj, @PasswordEncryptAnnotation String password) {
	}

}
