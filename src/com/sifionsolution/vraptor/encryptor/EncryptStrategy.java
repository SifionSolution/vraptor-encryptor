package com.sifionsolution.vraptor.encryptor;

import com.sifionsolution.vraptor.encryptor.implementation.Sha512Encryptor;

public enum EncryptStrategy {
	SHA512(new Sha512Encryptor());

	private Encryptor encryptor;

	private EncryptStrategy(Encryptor encryptor) {
		this.encryptor = encryptor;
	}

	public final Encryptor getEncryptor() {
		return encryptor;
	}

}
