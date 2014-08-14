package com.sifionsolution.vraptor.encryptor.interceptor;

import java.lang.annotation.Annotation;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import br.com.caelum.vraptor.Accepts;
import br.com.caelum.vraptor.AroundCall;
import br.com.caelum.vraptor.Intercepts;
import br.com.caelum.vraptor.controller.ControllerMethod;
import br.com.caelum.vraptor.core.MethodInfo;
import br.com.caelum.vraptor.http.ValuedParameter;
import br.com.caelum.vraptor.interceptor.SimpleInterceptorStack;

import com.sifionsolution.vraptor.encryptor.EncryptorExecutor;
import com.sifionsolution.vraptor.encryptor.annotation.Encrypt;

@Intercepts
@RequestScoped
public class EncryptorInterceptor {

	private MethodInfo methodInfo;
	private EncryptorExecutor executor;

	private static final Logger logger = LoggerFactory.getLogger(EncryptorInterceptor.class);

	/*
	 * CDI eyes only
	 */
	@Deprecated
	public EncryptorInterceptor() {
		this(null, null);
	}

	@Inject
	public EncryptorInterceptor(MethodInfo methodInfo, EncryptorExecutor executor) {
		this.methodInfo = methodInfo;
		this.executor = executor;
	}

	@AroundCall
	public void around(SimpleInterceptorStack stack) {
		logger.debug("EncrytorInterceptor activated");
		ValuedParameter[] valuedParameters = methodInfo.getValuedParameters();

		for (int i = 0; i < valuedParameters.length; i++) {
			ValuedParameter obj = valuedParameters[i];

			Annotation annotation = obj.getParameter().getAnnotation(Encrypt.class);

			if (annotation == null) {
				continue;
			}

			logger.debug("Intercepting parameter name: " + obj.getParameter().getName());

			String newParameter = executor.encrypt(obj.getParameter(), String.valueOf(obj.getValue()));

			methodInfo.setParameter(i, newParameter);
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
