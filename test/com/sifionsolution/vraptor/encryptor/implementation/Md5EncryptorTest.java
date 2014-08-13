package com.sifionsolution.vraptor.encryptor.implementation;

import static org.junit.Assert.assertNotEquals;

import org.junit.Before;
import org.junit.Test;

import com.sifionsolution.vraptor.encryptor.Encryptor;

public class Md5EncryptorTest {

	private Encryptor encryptor;

	@Before
	public void init() {
		encryptor = new Md5Encryptor();
	}

	@Test
	public void shouldEncryptString() {
		assertNotEquals("Should be Encrypted", encryptor.encrypt("Should be Encrypted"));
	}

}
