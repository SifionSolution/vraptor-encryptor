package com.sifionsolution.vraptor.encryptor.salter;

import com.sifionsolution.vraptor.encryptor.salter.implementation.DefaultSalter;
import com.sifionsolution.vraptor.encryptor.salter.implementation.ShuffleSalter;

public enum SalterStrategy {
	DEFAULT(new DefaultSalter()), SHUFFLE(new ShuffleSalter());

	private EncryptSalter salter;

	private SalterStrategy(EncryptSalter salter) {
		this.salter = salter;
	}

	public EncryptSalter getSalter() {
		return salter;
	}
}
