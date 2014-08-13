package com.sifionsolution.vraptor.encryptor.implementation;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

import org.junit.Before;
import org.junit.Test;

import com.sifionsolution.vraptor.encryptor.Encryptor;
import com.sifionsolution.vraptor.encryptor.salter.EncryptSalter;
import com.sifionsolution.vraptor.encryptor.salter.implementation.ShuffleSalter;

public class Md5EncryptorTest {

	private Encryptor encryptor;
	private EncryptSalter salter;

	@Before
	public void init() {
		encryptor = new Md5Encryptor();
	}

	@Test
	public void shouldEncryptString() {
		String result = encryptor.encrypt("Should be Encrypted");

		assertNotEquals("Should be Encrypted", result);
		assertEquals("8a57f80eca00af71a74cf697fad8fc93", result);
	}

	@Test
	public void shouldEncryptWithSalter() {
		salter = new ShuffleSalter();

		String result = encryptor.encrypt("Should be Encrypted", salter);

		assertNotEquals("Should be Encrypted", result);
		assertNotEquals("8a57f80eca00af71a74cf697fad8fc93", result);
	}

}
