For `Maven` to get a Git project into your build:
**Step 1:**  Add the `JitPack` repository to your `build` file

```sh
	<repositories>
		<repository>
		    <id>jitpack.io</id>
		    <url>https://jitpack.io</url>
		</repository>
	</repositories>
```

**Step 2:** Add the dependency

```sh
	<dependency>
	    <groupId>com.github.JahidHasanCO</groupId>
	    <artifactId>NetworkUsage</artifactId>
	    <version>1.0.0</version>
	</dependency>
```
