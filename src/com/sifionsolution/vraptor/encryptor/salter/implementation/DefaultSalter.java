package com.sifionsolution.vraptor.encryptor.salter.implementation;

import static com.sifionsolution.commons.StringAdapter.getNullSafe;

import com.sifionsolution.vraptor.encryptor.salter.Salter;

public class DefaultSalter implements Salter {

	@Override
	public String salt(String value) {
		return getNullSafe(value);
	}

}
