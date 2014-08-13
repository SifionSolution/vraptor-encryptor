package com.sifionsolution.vraptor.encryptor.implementation;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

import org.junit.Before;
import org.junit.Test;

import com.sifionsolution.vraptor.encryptor.Encryptor;
import com.sifionsolution.vraptor.encryptor.salter.EncryptSalter;
import com.sifionsolution.vraptor.encryptor.salter.implementation.ShuffleSalter;

public class Sha256EncryptorTest {

	private Encryptor encryptor;
	private EncryptSalter salter;

	@Before
	public void init() {
		encryptor = new Sha256Encryptor();
	}

	@Test
	public void shouldEncryptString() {
		String result = encryptor.encrypt("Should be Encrypted");

		assertNotEquals("Should be Encrypted", result);
		assertEquals("9ac9b489f27dd2f6155e06708cb57e21a93461bbe564dfcc2889d2e31cfade2b", result);
	}

	@Test
	public void shouldEncryptWithSalter() {
		salter = new ShuffleSalter();

		String result = encryptor.encrypt("Should be Encrypted", salter);

		assertNotEquals("Should be Encrypted", result);
		assertNotEquals("9ac9b489f27dd2f6155e06708cb57e21a93461bbe564dfcc2889d2e31cfade2b", result);
	}

}
