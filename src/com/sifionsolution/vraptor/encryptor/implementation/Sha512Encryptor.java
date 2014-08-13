package com.sifionsolution.vraptor.encryptor.implementation;

import com.google.common.hash.Hashing;
import com.sifionsolution.vraptor.encryptor.Encryptor;
import com.sifionsolution.vraptor.encryptor.salter.EncryptSalter;
import com.sifionsolution.vraptor.encryptor.salter.implementation.DefaultSalter;

public class Sha512Encryptor implements Encryptor {

	@Override
	public String encrypt(String plain) {
		return encrypt(plain, new DefaultSalter());
	}

	@Override
	public String encrypt(String plain, EncryptSalter salter) {
		if (salter == null)
			throw new IllegalArgumentException("The salter cannot be null.");

		final String encrypted = toSha512(salter.salt(plain));
		String encryptedTwice = toSha512(salter.salt(encrypted));

		return encryptedTwice;
	}

	private String toSha512(String salt) {
		return Hashing.sha512().hashUnencodedChars(salt).toString();
	}
}
