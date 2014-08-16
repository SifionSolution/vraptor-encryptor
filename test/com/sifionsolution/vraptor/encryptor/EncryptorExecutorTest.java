package com.sifionsolution.vraptor.encryptor;

import static com.sifionsolution.commons.ContentVerifyer.notEmpty;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

import java.lang.annotation.Annotation;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

import br.com.caelum.vraptor.http.Parameter;

import com.sifionsolution.vraptor.encryptor.annotation.Encrypt;
import com.sifionsolution.vraptor.encryptor.configuration.EncryptConfiguration;
import com.sifionsolution.vraptor.encryptor.implementation.Md5Encryptor;
import com.sifionsolution.vraptor.encryptor.salter.Salter;
import com.sifionsolution.vraptor.encryptor.salter.implementation.DefaultSalter;

public class EncryptorExecutorTest {

	private EncryptorExecutor executor;

	@Mock
	private Parameter parameter;

	private final String toEncrypt = "Should be Encrypted";
	private final String md5Encrypted = "8a57f80eca00af71a74cf697fad8fc93";
	private final String sha512Encrypted = "75047bfb575c4d6f4fedbbc82cf1b5c1c86156bfd98c2924c44e3b2771c3fddadd45c6f3c086b165d2f6cffb98c6e454c83cbba4aa6517ab959440f24cafe872";

	@Before
	public void init() {
		initMocks(this);
	}

	@Test
	public void shouldUseDefaultsWhenNothingSpecified() {
		EncryptConfiguration config = new EncryptConfiguration();
		config.init();

		executor = new EncryptorExecutor(config);

		Annotation[] parameterAnnotations = new Annotation[1];
		parameterAnnotations[0] = new Encrypt() {
			@Override
			public Class<? extends Annotation> annotationType() {
				return null;
			}

			@Override
			public Class<? extends Encryptor> value() {
				return Encryptor.class;
			}

			@Override
			public Class<? extends Salter> salter() {
				return Salter.class;
			}
		};

		when(parameter.getDeclaredAnnotations()).thenReturn(parameterAnnotations);
		String result = executor.encrypt(parameter, toEncrypt);

		assertTrue(notEmpty(result));
		assertEquals(result, sha512Encrypted);
	}

	@Test
	public void shouldUseMappedDefaultsToEncryptWhenEncryptAnnotationIsFound() {
		EncryptConfiguration config = new EncryptConfiguration();
		config.init();

		config.setDefaults(Md5Encryptor.class, DefaultSalter.class);

		executor = new EncryptorExecutor(config);

		Annotation[] parameterAnnotations = new Annotation[1];
		parameterAnnotations[0] = new Encrypt() {
			@Override
			public Class<? extends Annotation> annotationType() {
				return null;
			}

			@Override
			public Class<? extends Encryptor> value() {
				return Encryptor.class;
			}

			@Override
			public Class<? extends Salter> salter() {
				return Salter.class;
			}
		};

		when(parameter.getDeclaredAnnotations()).thenReturn(parameterAnnotations);
		String result = executor.encrypt(parameter, toEncrypt);

		assertTrue(notEmpty(result));
		assertEquals(result, md5Encrypted);
	}

	@Test
	public void shouldUseAnnotatedDefaultsToEncryptWhenEncryptAnnotationIsFound() {
		EncryptConfiguration config = new EncryptConfiguration();
		config.init();

		executor = new EncryptorExecutor(config);

		Annotation[] parameterAnnotations = new Annotation[1];
		parameterAnnotations[0] = new Encrypt() {
			@Override
			public Class<? extends Annotation> annotationType() {
				return null;
			}

			@Override
			public Class<? extends Encryptor> value() {
				return Md5Encryptor.class;
			}

			@Override
			public Class<? extends Salter> salter() {
				return DefaultSalter.class;
			}
		};

		when(parameter.getDeclaredAnnotations()).thenReturn(parameterAnnotations);
		String result = executor.encrypt(parameter, toEncrypt);

		assertTrue(notEmpty(result));
		assertEquals(result, md5Encrypted);
	}

	@Test
	public void shouldUseMappedEncryptAnnotation() {
		EncryptConfiguration config = new EncryptConfiguration();
		config.init();

		config.map(Encrypt.class, Md5Encryptor.class, DefaultSalter.class);

		executor = new EncryptorExecutor(config);

		Annotation[] parameterAnnotations = new Annotation[1];
		parameterAnnotations[0] = new Encrypt() {
			@Override
			public Class<? extends Annotation> annotationType() {
				return null;
			}

			@Override
			public Class<? extends Encryptor> value() {
				return Encryptor.class;
			}

			@Override
			public Class<? extends Salter> salter() {
				return Salter.class;
			}
		};

		when(parameter.getDeclaredAnnotations()).thenReturn(parameterAnnotations);
		String result = executor.encrypt(parameter, toEncrypt);

		assertTrue(notEmpty(result));
		assertEquals(result, md5Encrypted);
	}

	@Test
	public void shouldUseCustomAnnotation() {
		EncryptConfiguration config = new EncryptConfiguration();
		config.init();

		config.map(PasswordEncryptAnnotation.class, Md5Encryptor.class, DefaultSalter.class);

		executor = new EncryptorExecutor(config);

		Annotation[] parameterAnnotations = new Annotation[1];
		parameterAnnotations[0] = new PasswordEncryptAnnotation() {
			@Override
			public Class<? extends Annotation> annotationType() {
				return null;
			}
		};

		when(parameter.getDeclaredAnnotations()).thenReturn(parameterAnnotations);
		String result = executor.encrypt(parameter, toEncrypt);

		assertTrue(notEmpty(result));
		assertEquals(result, md5Encrypted);
	}
}
