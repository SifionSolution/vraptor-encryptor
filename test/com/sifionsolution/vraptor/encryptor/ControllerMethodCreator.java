package com.sifionsolution.vraptor.encryptor;

import java.lang.reflect.Method;

import net.vidageek.mirror.dsl.Mirror;
import br.com.caelum.vraptor.controller.ControllerMethod;
import br.com.caelum.vraptor.controller.DefaultBeanClass;
import br.com.caelum.vraptor.controller.DefaultControllerMethod;

public class ControllerMethodCreator {

	public static ControllerMethod create(Class<?> clazz, String method) {
		return new DefaultControllerMethod(new DefaultBeanClass(clazz), getMethod(clazz, method));
	}

	private static Method getMethod(Class<?> clazz, String method) {
		return new Mirror().on(clazz).reflect().method(method).withAnyArgs();
	}

}
