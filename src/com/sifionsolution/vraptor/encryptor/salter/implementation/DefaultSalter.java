package com.sifionsolution.vraptor.encryptor.salter.implementation;

import static com.sifionsolution.commons.StringAdapter.getNullSafe;

import com.sifionsolution.vraptor.encryptor.salter.EncryptSalter;

public class DefaultSalter implements EncryptSalter {

	@Override
	public String salt(String value) {
		return getNullSafe(value);
	}

}
