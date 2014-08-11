package com.sifionsolution.vraptor.encryptor.interceptor;

import java.lang.annotation.Annotation;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;

import br.com.caelum.vraptor.Accepts;
import br.com.caelum.vraptor.AroundCall;
import br.com.caelum.vraptor.Intercepts;
import br.com.caelum.vraptor.controller.ControllerMethod;
import br.com.caelum.vraptor.core.MethodInfo;
import br.com.caelum.vraptor.http.ValuedParameter;
import br.com.caelum.vraptor.interceptor.SimpleInterceptorStack;
import br.com.caelum.vraptor.observer.ParametersInstantiator;

import com.sifionsolution.vraptor.encryptor.annotation.Encrypt;

@Intercepts(after = ParametersInstantiator.class)
@RequestScoped
public class EncryptorInterceptor {

	private MethodInfo methodInfo;

	/*
	 * CDI eyes only
	 */
	@Deprecated
	public EncryptorInterceptor() {
		this(null);
	}

	@Inject
	public EncryptorInterceptor(MethodInfo methodInfo) {
		this.methodInfo = methodInfo;
	}

	@AroundCall
	public void around(SimpleInterceptorStack stack) {
		ValuedParameter[] valuedParameters = methodInfo.getValuedParameters();

		for (ValuedParameter obj : valuedParameters) {
			if (obj.getParameter().isAnnotationPresent(Encrypt.class)) {
				String value = String.valueOf(obj.getValue());
				System.out.println(value);
			}
		}

		stack.next();
	}

	@Accepts
	public boolean accepts(ControllerMethod method) {
		Annotation[][] annotations = method.getMethod().getParameterAnnotations();
		for (Annotation[] ann : annotations) {
			for (Annotation a : ann) {
				if (a instanceof Encrypt)
					return true;
			}
		}
		return false;
	}

}
