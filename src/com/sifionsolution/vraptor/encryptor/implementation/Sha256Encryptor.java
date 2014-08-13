package com.sifionsolution.vraptor.encryptor.implementation;

import java.nio.charset.Charset;

import com.google.common.hash.Hashing;
import com.sifionsolution.vraptor.encryptor.Encryptor;
import com.sifionsolution.vraptor.encryptor.salter.EncryptSalter;
import com.sifionsolution.vraptor.encryptor.salter.implementation.DefaultSalter;

public class Sha256Encryptor implements Encryptor {

	@Override
	public String encrypt(String plain) {
		return encrypt(plain, new DefaultSalter());
	}

	@Override
	public String encrypt(String plain, EncryptSalter salter) {
		if (salter == null)
			throw new IllegalArgumentException("The salter cannot be null.");

		return toSha256(salter.salt(plain));
	}

	private String toSha256(String salt) {
		return Hashing.sha256().hashString(salt, Charset.forName("UTF-8")).toString();
	}
}