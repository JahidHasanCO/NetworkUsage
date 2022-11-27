# NetworkUsage

NetworkUsage is an Android Studio library to monitor cellular and Wi-Fi data usage easily. It is useful as a real-time internet speed monitor and also monitors daily, weekly and monthly data usage. [![](https://jitpack.io/v/JahidHasanCO/NetworkUsage.svg)](https://jitpack.io/#JahidHasanCO/NetworkUsage)

## Preview 
<img src="https://github.com/JahidHasanCO/NetworkUsage/blob/master/ART/s2.jpg" width="270" height="585"> <img src="https://github.com/JahidHasanCO/NetworkUsage/blob/master/ART/s1.jpg" width="270" height="585"> 

# Installation
**For Gradle:**

**Step 1:** Add the `JitPack` repository to your `build` file
Add it in your root `build.gradle` at the end of repositories:
```sh
	allprojects {
		repositories {
			maven { url 'https://jitpack.io' }
		}
	}
```

**Step 2:** Add the dependency
```sh
dependencies {
	        implementation 'com.github.JahidHasanCO:NetworkUsage:1.0.0'
	}
```

For `Maven` [check this](docs/maven.md) for your reference.

### Examples
For this library, it requires `minSdk 23` in your project. It requires two `uses-permission` in your [AndroidManifest.xml](app/src/main/AndroidManifest.xml) file. First one is `<uses-permission android:name="android.permission.READ_PHONE_STATE" />` and second one is `<uses-permission
        android:name="android.permission.READ_PRIVILEGED_PHONE_STATE"
        tools:ignore="ProtectedPermissions" />`

First, you need to check if `PERMISSION_GRANTED` or not. So, first check for permissions by calling `setupPermissions()` function. 

```kotlin
    private fun setupPermissions() {
        val permission = ContextCompat.checkSelfPermission(
            this, Manifest.permission.READ_PHONE_STATE
        )
        if (permission != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(
                this, arrayOf(Manifest.permission.READ_PHONE_STATE), 34
            )
        }
        if (!checkUsagePermission()) {
            Toast.makeText(this, "Please allow usage access", Toast.LENGTH_SHORT).show()
        }
    }
```
For allow app for `USAGE_ACCESS` one time `checkUsagePermission()` this function will return `ACTION_USAGE_ACCESS_SETTINGS` `PERMISSION_GRANTED` or not.
```kotlin
    private fun checkUsagePermission(): Boolean {
        val appOps = getSystemService(Context.APP_OPS_SERVICE) as AppOpsManager
        var mode = 0
        mode = appOps.checkOpNoThrow(
            "android:get_usage_stats", Process.myUid(),
            packageName
        )
        val granted = mode == AppOpsManager.MODE_ALLOWED
        if (!granted) {
            val intent = Intent(Settings.ACTION_USAGE_ACCESS_SETTINGS)
            startActivity(intent)
            return false
        }
        return true
    }
```

### License
NetworkUsage is [MIT licensed.](LICENSE)

### Contributing 💡
If you want to contribute to this project and make it better with new ideas, your pull request is very welcomed.
If you find any issue just put it in the repository issue section, thank you.

