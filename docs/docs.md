# NetworkUsageManager
`public class NetworkUsageManager` 

### NetworkUsageManager 
Parameter | Details
---|---|
context: Context | Interface to global information about an application environment. This is an abstract class whose implementation is provided by the Android system. |
subscriberId: String? | In `TelephonyManager` has `getSubscriberId()` Returns the unique subscriber ID, for example, the IMSI for a GSM phone. |

To initialize `NetworkUsageManager` you need to create and object and pass as a parameter `Context` and `TelephonyManager.getSubscriberId()` by default we provide an `utility function` to `getSubscriberId()`.  `Util.getSubscriberId(this)` will return subscriber id if it available. So, this method can be `return null`.

#### To create an Object

```kotlin
     val networkUsage = NetworkUsageManager(this, Util.getSubscriberId(this))
```
