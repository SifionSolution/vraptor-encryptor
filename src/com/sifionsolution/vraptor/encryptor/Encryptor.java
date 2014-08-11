package com.sifionsolution.vraptor.encryptor;

import com.sifionsolution.vraptor.encryptor.salter.EncryptSalter;

public interface Encryptor {
	public String encrypt(String plain);

	public String encrypt(String plain, EncryptSalter salter);
}
