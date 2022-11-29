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


# Speed
`data class Speed`

This `data class` has two variables 
- `speed: String` for internet speed as string
- `unit: String` for speed unit (`bits`/`bytes`)

# NetSpeed
`object NetSpeed`

### Public Methods 
Name| Details |
---|----|
`fun setSpeedUnitBits(isSpeedUnitBits: Boolean) ` | This method take `isSpeedUnitBits: Boolean` as a parameter for set speed unit bits or bytes. | 
`fun getSpeedUnitBits(): Boolean` | This method return internet speed unit (`bits`/`bytes`)| 
`fun calculateSpeed(timeTaken: Long, downBytes: Long, upBytes: Long): List<Speed>` | This method take time interval in `milliseconds`, `download bytes` and `upload bytes` and return type cast to human readable `List` of [Speed](#Speed) format.|

# NetworkType
`enum class NetworkType`
This `enum class` has three type:
- `MOBILE`
- `WIFI`
- `ALL`

# TimeInterval
`data class TimeInterval(val start: Long, val end: Long,var date: String = "")`
This `data class` has three variables:
- `start: Long` : for interval start `timeInMillis`.
- `end: Long` : for interval end `timeInMillis`.
- `date: String = ""`: for date of interval.

# Usage
`data class Usage`
This `data class` has three variables:
- `downloads: Long = 0L`
- `uploads: Long = 0L`
- `timeTaken: Long = 0L`

# DataUsage
`data class DataUsage`
This `data class` has three variables:
- `downloads: Long = 0L`
- `uploads: Long = 0L`
- `date: String = ""`

# Util
