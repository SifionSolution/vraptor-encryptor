# vraptor-encrytor

Easy encryption for VRaptor 4. Receive encrypted data directly on your Controller method.

# Install and Configuration

* Manually [add the jar file](http://repo1.maven.org/maven2/com/sifionsolution/vraptor-encryptor/) to your app 

* Or ... add our dependency to your maven build script:

```xml
<dependency>
    <groupId>com.sifionsolution</groupId>
    <artifactId>vraptor-encryptor</artifactId>
    <version>1.0</version> <!-- Check for the latest version -->
</dependency>
```

* Or ... try it out with Gradle: 

```gradle
compile 'com.sifionsolution:vraptor-encryptor:1.0' //Check for the latest version
```


# Usage and Features

## Basic usage

The default configuration will encrypt your data with Sha512 encryption and use the *default encryption salter*. The default salter gets null safe version of your String [(check the source for details)](https://github.com/SifionSolution/vraptor-encryptor/blob/work/src/com/sifionsolution/vraptor/encryptor/salter/implementation/DefaultSalter.java#L11).  All you you need is the ```@Encrypt``` annotation on your actions **parameter**:

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

You can change the encryption type by selecting the [encryption strategy class](https://github.com/SifionSolution/vraptor-encryptor/tree/work/src/com/sifionsolution/vraptor/encryptor/implementation):

```java
@Post("/register")
public void register(@NotNull @Valid SignUpUser user, @Encrypt(Md5Encryptor.class) String password) {
	validator.onErrorRedirectTo(RegisterController.class).index();
    
    //Password is encrypted
	dao.register(user, password);

	result.redirectTo(RootController.class).index();
}
```

## Choose your Salter

You can specify the [salter strategy class](https://github.com/SifionSolution/vraptor-encryptor/tree/work/src/com/sifionsolution/vraptor/encryptor/salter/implementation). Here´s an example:

```java
	@Post("/register")
	public void register(@NotNull @Valid SignUpUser user, @Encrypt(salter=ShuffleSalter.class) String password) {
		validator.onErrorRedirectTo(RegisterController.class).index();
 	    
 	    //Password is encrypted
		dao.register(user, password);

		result.redirectTo(RootController.class).index();
	}
```



## Implement your Salter or/and Encryptor

You can also implement your own strategies. To do this, all you need to do is implement your own [Salter](https://github.com/SifionSolution/vraptor-encryptor/blob/work/src/com/sifionsolution/vraptor/encryptor/salter/Salter.java) or [Encryptor](https://github.com/SifionSolution/vraptor-encryptor/blob/work/src/com/sifionsolution/vraptor/encryptor/Encryptor.java)
and specify the class on your annotation (or mapping annotation).

## Change the @Encrypt annotation defaults 

If you want to use @Encrypt annotation, but not with Sha512 and our default Salter, you can change the plugins configuration. So everywhere you choose to use @Encrypt we´ll use your prefered strategy.
To do this, you need to create an ```@ApplicationScoped``` that implements ```EncryptorConfigurator```.
This is how to do it:

```java
@ApplicationScoped
public class EncryptorConfiguration implements EncryptorConfigurator {

	@Override
	public void configure(Configuration config) {
		config.setDefaults(Md5Encryptor.class, ShuffleSalter.class);
	}

}
```

Now every time you use ```@Encrypt``` we will use Md5 encryption and the Shuffle salter. Note that you can use your own versions of encryptor and salter here.
 
## Create your own annotations with your own strategies

This feature is pretty cool, because if you use more than one type of encryption, you don´t need to spread out the configuration through many Controllers using ```@Encrypt(value=, salter=)```. Imagine how hard it would be to maintain if you have to change one of those strategies?
So, you can create your own annotation and map it. Let´s create two annotations as an example:

We´ll use the following annotation to encrypt passwords.

```java
@Retention(RUNTIME)
@Target(PARAMETER)
public @interface PasswordEncryption {
}
```

And this one to create hashes:

```java
@Retention(RUNTIME)
@Target(PARAMETER)
public @interface HashEncryption {
}
```

So now, all we need to do is configure it (again, implementing ```EncryptorConfigurator```):

```java
@ApplicationScoped
public class EncryptorConfiguration implements EncryptorConfigurator {

	@Override
	public void configure(Configuration config) {
		config.map(PasswordEncryption.class, Sha512Encryptor.class, ShuffleSalter.class);
		config.map(HashEncryption.class, Md5Encryptor.class, DefaultSalter.class);
	}

}
```

Cool now we can use the annotation we created like this:

```java
@Post("/register")
public void register(@NotNull @Valid SignUpUser user, @PasswordEncryption String password) {
	validator.onErrorRedirectTo(RegisterController.class).index();
    
    //Password is encrypted
	dao.register(user, password);

	result.redirectTo(RootController.class).index();
}

@Post("/checkHash")
public void check(@HashEncryption String hash) {
	validator.onErrorRedirectTo(RootController.class).index();
    
	//Do something with the MD5 hash:
	System.out.println(hash);
	
	result.redirectTo(RootController.class).index();
}
```



## (Coming soon) @Encrypt Field injection

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
        
        // user.getPassword will return an encrypted string
	dao.register(user); 

	result.redirectTo(RootController.class).index();
}

```
