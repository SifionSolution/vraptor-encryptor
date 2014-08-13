# vraptor-encrytor

Receive encrypted data directly on your Controller method.

# Install and Configuration

Soon will be available on Maven.

# Usage and Features

## Basic usage

The default configuration will encrypt your data with Sha512 encryption and use the *default encryption salter*. The default salter gets null safe version of your String [(check the source for details)](https://github.com/SifionSolution/vraptor-encryptor/blob/work/src/com/sifionsolution/vraptor/encryptor/salter/implementation/DefaultSalter.java#L11).  All you you need its the ```@Encrypt``` annotation on your actions **parameter**:

```java
@Post("/register")
public void register(@NotNull @Valid SignUpUser user, @Encrypt String password) {
	validator.onErrorRedirectTo(RegisterController.class).index();
  //Password is encrypted
	dao.register(user, password);

	result.redirectTo(RootController.class).index();
}
``` 

## Choose encryption

You can change the encryption type by selecting the [encryption strategy (check available strategies)](https://github.com/SifionSolution/vraptor-encryptor/blob/work/src/com/sifionsolution/vraptor/encryptor/EncryptStrategy.java):

```java
@Post("/register")
public void register(@NotNull @Valid SignUpUser user, @Encrypt(EncryptStrategy.MD5) String password) {
	validator.onErrorRedirectTo(RegisterController.class).index();
  //Password is encrypted
	dao.register(user, password);

	result.redirectTo(RootController.class).index();
}

```

## Choose your Salter

*(Coming soon)* - You can specify the [salter strategy (check available strategies)](https://github.com/SifionSolution/vraptor-encryptor/blob/work/src/com/sifionsolution/vraptor/encryptor/EncryptStrategy.java). Here´s an example:

```java
  //TODO 
```



## Implement your Salter

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
