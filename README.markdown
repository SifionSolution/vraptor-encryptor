# vraptor-encrytor

Receive encrypted data directly on your Controller method.

# Install and Configuration

Soon will be available on Maven.

# Usage and Features

## Basic usage

The default configuration will encrypt your data twice with Sha512 encryption and use the [default shuffle salter](https://github.com/SifionSolution/vraptor-encryptor/blob/work/src/com/sifionsolution/vraptor/encryptor/salter/implementation/ShuffleSalter.java). All you you need its the ```@Encrypt``` annotation on your actions **parameter**:

```java
@Post("/register")
public void register(@NotNull @Valid SignUpUser user, @Encrypt String password) {
	validator.onErrorRedirectTo(RegisterController.class).index();
  //Password is encrypted
	dao.register(user, password);

	result.redirectTo(RootController.class).index();
}
``` 

## Choosing another encryption type

*(Coming soon)* - You can change the encryption type by selecting the [encryption strategy](https://github.com/SifionSolution/vraptor-encryptor/blob/work/src/com/sifionsolution/vraptor/encryptor/EncryptStrategy.java):

```java

//stub

@Post("/register")
public void register(@NotNull @Valid SignUpUser user, @Encrypt(EncryptStrategy.MD5) String password) {
	validator.onErrorRedirectTo(RegisterController.class).index();
  //Password is encrypted
	dao.register(user, password);

	result.redirectTo(RootController.class).index();
}

```

## Implementing your own Salter

*(Coming soon)* - You can also implement your own salter. To do this, all you need to do is:

```java
  //TODO 
```

## @Encrypt Field injection

*(Coming soon)* - For better code organization, you can use the ```@Encrypt``` annotation in your entity or DTO:


```java

//stub

public class SignUpUser{
  @NotNull
  private String username;

  @NotNull
  @Encrypt
  private String password;
}
```

And receive that DTO directly on your action:

```java

//stub

@Post("/register")
public void register(@NotNull @Valid SignUpUser user) {
	validator.onErrorRedirectTo(RegisterController.class).index();

	dao.register(user);

	result.redirectTo(RootController.class).index();
}

```
