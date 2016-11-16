Basic Set up like base activity, base fragments, toolbar, navigation drawer.


# Download

Include `jitpack.io` inside of **root** project `build.gradle`:

```groovy
allprojects {
		repositories {
			...
			maven { url "https://jitpack.io" }
		}
	}
```

After that you can easily include the library in your **app** `build.gradle`:

```groovy
dependencies {
	        compile 'com.github.Nuvoex:Lumiere:v2'
	}
```

That's it build your project.
