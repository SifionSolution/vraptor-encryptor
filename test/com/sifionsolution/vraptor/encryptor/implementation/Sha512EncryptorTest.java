package com.sifionsolution.vraptor.encryptor.implementation;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

import org.junit.Before;
import org.junit.Test;

import com.sifionsolution.vraptor.encryptor.Encryptor;
import com.sifionsolution.vraptor.encryptor.salter.EncryptSalter;
import com.sifionsolution.vraptor.encryptor.salter.implementation.ShuffleSalter;

public class Sha512EncryptorTest {

	private Encryptor encryptor;
	private EncryptSalter salter;

	@Before
	public void init() {
		encryptor = new Sha512Encryptor();
	}

	@Test
	public void shouldEncryptString() {
		String result = encryptor.encrypt("Should be Encrypted");

		assertNotEquals("Should be Encrypted", result);
		assertEquals(
				"75047bfb575c4d6f4fedbbc82cf1b5c1c86156bfd98c2924c44e3b2771c3fddadd45c6f3c086b165d2f6cffb98c6e454c83cbba4aa6517ab959440f24cafe872",
				result);
	}

	@Test
	public void shouldEncryptWithSalter() {
		salter = new ShuffleSalter();

		String result = encryptor.encrypt("Should be Encrypted", salter);

		assertNotEquals("Should be Encrypted", result);
		assertNotEquals(
				"75047bfb575c4d6f4fedbbc82cf1b5c1c86156bfd98c2924c44e3b2771c3fddadd45c6f3c086b165d2f6cffb98c6e454c83cbba4aa6517ab959440f24cafe872",
				result);
	}

}
