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
		logger.info("EncrytorInterceptor activated");
		ValuedParameter[] valuedParameters = methodInfo.getValuedParameters();

		for (int i = 0; i < valuedParameters.length; i++) {
			ValuedParameter obj = valuedParameters[i];

			if (!executor.containsAny(obj.getParameter().getAnnotations()))
				continue;

			logger.info("Intercepting parameter name: " + obj.getParameter().getName());

			String newParameter = executor.encrypt(obj.getParameter(), String.valueOf(obj.getValue()));

			methodInfo.setParameter(i, newParameter);
		}

		stack.next();
	}

	@Accepts
	public boolean accepts(ControllerMethod method) {
		logger.info("Trying to intercept method from Controller " + method.getController().getType().getName());

		Annotation[][] annotations = method.getMethod().getParameterAnnotations();
		for (Annotation[] ann : annotations) {
			for (Annotation a : ann) {
				if (executor.contains(a))
					return true;
			}
		}
		return false;
	}

}
