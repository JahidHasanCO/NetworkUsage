# NetworkUsage

NetworkUsage is an Android Studio library to monitor cellular and Wi-Fi data usage easily. It is useful as a real-time internet speed monitor and also monitors daily, weekly and monthly data usage. [![](https://jitpack.io/v/JahidHasanCO/NetworkUsage.svg)](https://jitpack.io/#JahidHasanCO/NetworkUsage) [![Android CI](https://github.com/JahidHasanCO/NetworkUsage/actions/workflows/android.yml/badge.svg?branch=master)](https://github.com/JahidHasanCO/NetworkUsage/actions/workflows/android.yml)

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

### Documentation
`NetworkUsageManager`
`public class NetworkUsageManager` 
Parameter | Details
---|---|
context: Context | Interface to global information about an application environment. This is an abstract class whose implementation is provided by the Android system. |
subscriberId: String? | In `TelephonyManager` has `getSubscriberId()` Returns the unique subscriber ID, for example, the IMSI for a GSM phone. |

To initialize `NetworkUsageManager` you need to create and object and pass as a parameter `Context` and `TelephonyManager.getSubscriberId()` by default we provide an `utility function` to `getSubscriberId()`.  `Util.getSubscriberId(this)` will return subscriber id if it available. So, this method can be `return null`.

#### To create an Object

```kotlin
     val networkUsage = NetworkUsageManager(this, Util.getSubscriberId(this))
```

### Public Methods
Usages In | Details |
--------|--
open [NetworkUsageManager](docs/docs.md) | `fun getUsageNow(networkType: NetworkType: Usage ` return Usage of current `Timestamp`. It take [NetworkType]() as a Parameter. | 
open [NetworkUsageManager](docs/docs.md) | `fun getUsage(interval: TimeInterval, networkType: NetworkType): Usage` return Usage of a single `Timestamp`. It takes [TimeInterval]() and [NetworkType]() for get a single time prieod of Data. |
open [NetworkUsageManager](docs/docs.md) | `    fun getMultiUsage(intervals: List<TimeInterval>, networkType: NetworkType): List<DataUsage>` return List of Usage of multiple `Timestamp`. It takes list of [TimeInterval]() and [NetworkType]() for get multi-time prieod of Data. |

Full [Documentation](docs/docs.md) is here.

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

For creating an object of `NetworkUsageManager` and initialize it you need to pass `Context` and `SubscriberId`
```kotlin
        val networkUsage = NetworkUsageManager(this, Util.getSubscriberId(this))
```
```kotlin
        val handler = Handler()
        val runnableCode = object : Runnable {
            override fun run() {
                val now = networkUsage.getUsageNow(NetworkType.ALL)
                val speeds = NetSpeed.calculateSpeed(now.timeTaken, now.downloads, now.uploads)
                val todayM = networkUsage.getUsage(Interval.today, NetworkType.MOBILE)
                val todayW = networkUsage.getUsage(Interval.today, NetworkType.WIFI)

                binding.wifiUsagesTv.text = Util.formatData(todayW.downloads, todayW.uploads)[2]
                binding.dataUsagesTv.text = Util.formatData(todayM.downloads, todayM.uploads)[2]
                binding.apply {
                    totalSpeedTv.text = speeds[0].speed + "" + speeds[0].unit
                    downUsagesTv.text = "Down: " + speeds[1].speed + speeds[1].unit
                    upUsagesTv.text = "Up: " + speeds[2].speed + speeds[2].unit
                }
                handler.postDelayed(this, 1000)
            }
        }

        runnableCode.run()
```
And This way You can get Real-Time Internet Speed. 

### License
NetworkUsage is [MIT licensed.](LICENSE)

### Contributing 💡
If you want to contribute to this project and make it better with new ideas, your pull request is very welcomed.
If you find any issue just put it in the repository issue section, thank you.

