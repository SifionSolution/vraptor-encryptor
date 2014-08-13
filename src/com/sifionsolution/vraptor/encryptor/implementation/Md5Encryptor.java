package com.sifionsolution.vraptor.encryptor.implementation;

import com.google.common.hash.Hashing;
import com.sifionsolution.vraptor.encryptor.Encryptor;
import com.sifionsolution.vraptor.encryptor.salter.EncryptSalter;
import com.sifionsolution.vraptor.encryptor.salter.implementation.ShuffleSalter;

public class Md5Encryptor implements Encryptor {

	@Override
	public String encrypt(String plain) {
		return encrypt(plain, new ShuffleSalter());
	}

	@Override
	public String encrypt(String plain, EncryptSalter salter) {
		if (salter == null)
			throw new IllegalArgumentException("The salter cannot be null.");

		return toMd5(salter.salt(plain));
	}

	private String toMd5(String salt) {
		return Hashing.md5().hashUnencodedChars(salt).toString();
	}
}
