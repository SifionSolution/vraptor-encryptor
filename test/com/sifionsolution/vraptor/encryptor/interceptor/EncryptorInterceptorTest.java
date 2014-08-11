package com.sifionsolution.vraptor.encryptor.interceptor;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.MockitoAnnotations.initMocks;

import java.lang.reflect.Method;

import net.vidageek.mirror.dsl.Mirror;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

import br.com.caelum.vraptor.controller.ControllerMethod;
import br.com.caelum.vraptor.controller.DefaultBeanClass;
import br.com.caelum.vraptor.controller.DefaultControllerMethod;
import br.com.caelum.vraptor.core.MethodInfo;

import com.sifionsolution.vraptor.encryptor.MyController;

public class EncryptorInterceptorTest {

	private EncryptorInterceptor interceptor;

	@Mock
	private MethodInfo methodInfo;

	@Before
	public void init() {
		initMocks(this);
		interceptor = new EncryptorInterceptor(methodInfo);
	}

	@Test
	public void shouldAcceptEncryptAnnotation() {
		ControllerMethod first = getControllerMethod(MyController.class, "first");
		assertTrue(interceptor.accepts(first));

		ControllerMethod middle = getControllerMethod(MyController.class, "middle");
		assertTrue(interceptor.accepts(middle));

		ControllerMethod last = getControllerMethod(MyController.class, "last");
		assertTrue(interceptor.accepts(last));

	}

	@Test
	public void shouldNotAcceptIfEncryptAnnotationNotPresent() {
		ControllerMethod unused = getControllerMethod(MyController.class, "unused");
		assertFalse(interceptor.accepts(unused));
	}

	private ControllerMethod getControllerMethod(Class<?> clazz, String method) {
		return new DefaultControllerMethod(new DefaultBeanClass(clazz), getMethod(clazz, method));
	}

	private Method getMethod(Class<?> clazz, String method) {
		return new Mirror().on(clazz).reflect().method(method).withAnyArgs();
	}

}
