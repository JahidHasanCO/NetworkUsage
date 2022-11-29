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



# Interval
`object Interval`

### Variables 
Name| Details |
---|----|
`val today: TimeInterval` | This variable will return today start and end `timeInMillis` and today `Date` as [TimeInterval](#TimeInterval) format. | 
`val yesterday: TimeInterval` | This variable will return yesterday start and end `timeInMillis` and yesterday `Date` as [TimeInterval](#TimeInterval) format. | 
`val lastWeekDaily: List<TimeInterval>` | This variable will return start and end `timeInMillis` and `Date` of every day of last 7 days as list [TimeInterval](#TimeInterval) format. | 
`val lastMonthDaily: List<TimeInterval>` | This variable will return start and end `timeInMillis` and `Date` of every day of last 30 days as list [TimeInterval](#TimeInterval) format. | 
`val last7days: TimeInterval` | This variable will return `last7days` start and end `timeInMillis` and `last7days` `Date` as [TimeInterval](#TimeInterval) format. | 
`val last30days: TimeInterval` | This variable will return `last7days` start and end `timeInMillis` and `last30days` `Date` as [TimeInterval](#TimeInterval) format. | 
`val week: TimeInterval` | This variable will return a `week` of start and end `timeInMillis` and `week` `Date` as [TimeInterval](#TimeInterval) format. | 
`val month: TimeInterval` | This variable will return a `month` of start and end `timeInMillis` and `month` `Date` as [TimeInterval](#TimeInterval) format. | 

### Methods 
Name| Details |
---|----|
`fun monthlyPlan(startDay: Int): TimeInterval ` | This method take `startDay: Int` as a parameter and return start and end `timeInMillis` and `Date` from `startDay` as [TimeInterval](#TimeInterval) format. | 
`fun weeklyPlan(startDay: Int): TimeInterval ` | This method take `startDay: Int` as a parameter and return start and end `timeInMillis` and `Date` from `startDay` as [TimeInterval](#TimeInterval) format. | 

# NetSpeed

# NetworkType

# TimeInterval

# Usage

# Util
