package com.sifionsolution.vraptor.encryptor.salter.implementation;

import static com.sifionsolution.commons.StringAdapter.getNullSafe;

import com.sifionsolution.vraptor.encryptor.salter.EncryptSalter;

public class ShuffleSalter implements EncryptSalter {

	@Override
	public String salt(String value) {
		return shuffle(getNullSafe(value));
	}

	private String shuffle(String str) {
		int middle = str.length() / 2;

		StringBuilder builder = new StringBuilder();

		builder.append(str.substring(middle)).append(str.substring(0, middle));

		return builder.reverse().toString();
	}
}
