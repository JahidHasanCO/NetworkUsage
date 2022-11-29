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

### Public Methods
Usages In | Details |
--------|--
open [NetworkUsageManager](docs/docs.md) | `fun getUsageNow(networkType: NetworkType: Usage ` return Usage of current `Timestamp`. It take [NetworkType](#NetworkType) as a Parameter. | 
open [NetworkUsageManager](docs/docs.md) | `fun getUsage(interval: TimeInterval, networkType: NetworkType): Usage` return Usage of a single `Timestamp`. It takes [TimeInterval](#TimeInterval) and [NetworkType](#NetworkType) for get a single time prieod of Data. |
open [NetworkUsageManager](docs/docs.md) | `    fun getMultiUsage(intervals: List<TimeInterval>, networkType: NetworkType): List<DataUsage>` return List of Usage of multiple `Timestamp`. It takes list of [TimeInterval](#TimeInterval) and [NetworkType](#NetworkType) for get multi-time prieod of Data. |

