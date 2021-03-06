package com.sifionsolution.vraptor.encryptor.implementation;

import java.nio.charset.Charset;

import com.google.common.hash.Hashing;
import com.sifionsolution.vraptor.encryptor.Encryptor;

public class Md5Encryptor implements Encryptor {

	@Override
	public String encrypt(String salted) {
		return Hashing.md5().hashString(salted, Charset.forName("UTF-8")).toString();
	}
}
