package com.sifionsolution.vraptor.encryptor.salter.implementation;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.Before;
import org.junit.Test;

import com.sifionsolution.vraptor.encryptor.salter.Salter;

public class ShuffleSalterTest {
	private Salter salter;

	@Before
	public void init() {
		salter = new ShuffleSalter();
	}

	@Test
	public void shouldReturnShuffledValue() {
		String result = salter.salt("value");

		assertNotEquals("value", result);
		assertEquals("aveul", result);
	}

	@Test
	public void shouldBeNullSafe() {
		assertNotNull(salter.salt(null));
		assertEquals("", salter.salt(null));
	}

	@Test
	public void shouldReturnEmptyString() {
		assertEquals("", salter.salt(""));
	}
}
