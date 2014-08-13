package com.sifionsolution.vraptor.encryptor.interceptor;

import static com.sifionsolution.commons.ContentVerifyer.notEmpty;

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

import com.sifionsolution.vraptor.encryptor.EncryptStrategy;
import com.sifionsolution.vraptor.encryptor.Encryptor;
import com.sifionsolution.vraptor.encryptor.annotation.Encrypt;
import com.sifionsolution.vraptor.encryptor.implementation.Sha512Encryptor;
import com.sifionsolution.vraptor.encryptor.salter.EncryptSalter;
import com.sifionsolution.vraptor.encryptor.salter.SalterStrategy;
import com.sifionsolution.vraptor.encryptor.salter.implementation.DefaultSalter;

@Intercepts
@RequestScoped
public class EncryptorInterceptor {

	private MethodInfo methodInfo;

	private static final Logger logger = LoggerFactory.getLogger(EncryptorInterceptor.class);

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
		logger.debug("EncrytorInterceptor activated");
		ValuedParameter[] valuedParameters = methodInfo.getValuedParameters();

		for (int i = 0; i < valuedParameters.length; i++) {
			ValuedParameter obj = valuedParameters[i];

			Annotation annotation = obj.getParameter().getAnnotation(Encrypt.class);

			if (annotation == null) {
				continue;
			}

			logger.debug("Intercepting parameter name: " + obj.getParameter().getName());

			Encryptor encryptor = extractEncryptor(((Encrypt) annotation).value());
			EncryptSalter salter = extractSalter(((Encrypt) annotation).salter());

			methodInfo.setParameter(i, encryptor.encrypt(String.valueOf(obj.getValue()), salter));
		}

		stack.next();
	}

	private EncryptSalter extractSalter(SalterStrategy[] strategyArray) {
		if (notEmpty(strategyArray)) {
			return strategyArray[0].getSalter();
		}

		logger.info("No Salters configured. Return default");

		return new DefaultSalter();
	}

	private Encryptor extractEncryptor(EncryptStrategy[] strategyArray) {
		if (notEmpty(strategyArray)) {
			return strategyArray[0].getEncryptor();
		}

		logger.info("No Encrypt Strategy configured. Return default");

		return new Sha512Encryptor();
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
