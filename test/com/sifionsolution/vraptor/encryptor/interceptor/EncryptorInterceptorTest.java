package com.sifionsolution.vraptor.encryptor.interceptor;

import static com.sifionsolution.vraptor.encryptor.ControllerMethodCreator.create;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.MockitoAnnotations.initMocks;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

import br.com.caelum.vraptor.controller.ControllerMethod;
import br.com.caelum.vraptor.core.MethodInfo;
import br.com.caelum.vraptor.interceptor.SimpleInterceptorStack;

import com.sifionsolution.vraptor.encryptor.EncryptorExecutor;
import com.sifionsolution.vraptor.encryptor.MyController;
import com.sifionsolution.vraptor.encryptor.PasswordEncryptAnnotation;
import com.sifionsolution.vraptor.encryptor.configuration.Configuration;

public class EncryptorInterceptorTest {

	private EncryptorInterceptor interceptor;

	@Mock
	private MethodInfo methodInfo;

	@Mock
	private EncryptorExecutor executor;

	@Mock
	private SimpleInterceptorStack stack;

	@Before
	public void init() {
		initMocks(this);

		interceptor = new EncryptorInterceptor(methodInfo, executor);
	}

	@Test
	public void shouldAcceptWhenEncryptAnnotationIsFirstParameter() {
		ControllerMethod first = create(MyController.class, "first");
		assertTrue(interceptor.accepts(first));
	}

	@Test
	public void shouldAcceptWhenEncryptAnnotationIsAMiddleParameter() {
		ControllerMethod middle = create(MyController.class, "middle");
		assertTrue(interceptor.accepts(middle));
	}

	@Test
	public void shouldAcceptWhenEncryptAnnotationIsLastParameter() {
		ControllerMethod last = create(MyController.class, "last");
		assertTrue(interceptor.accepts(last));
	}

	@Test
	public void shouldNotAcceptIfEncryptAnnotationNotPresent() {
		ControllerMethod unused = create(MyController.class, "unused");
		assertFalse(interceptor.accepts(unused));
	}

	@Test
	public void shouldAcceptIfMappedAnnotationIsPresent() {
		Configuration configuration = new Configuration();
		configuration.map(PasswordEncryptAnnotation.class, null);

		EncryptorExecutor executor = new EncryptorExecutor(configuration);
		interceptor = new EncryptorInterceptor(methodInfo, executor);

		ControllerMethod custom = create(MyController.class, "custom");
		assertTrue(interceptor.accepts(custom));
	}
}
