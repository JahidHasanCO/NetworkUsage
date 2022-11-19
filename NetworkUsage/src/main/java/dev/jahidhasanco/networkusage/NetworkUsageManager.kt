package dev.jahidhasanco.networkusage

import android.app.usage.NetworkStats
import android.app.usage.NetworkStatsManager
import android.net.NetworkCapabilities
import android.net.TrafficStats

class NetworkUsageManager(
    private val statsManager: NetworkStatsManager,
    private val subscriberId: String
) {

    var currentTXByte = 0L
    var currentRXByte = 0L
    var lastTXByte = 0L
    var lastRXByte = 0L
    var totalTXByte = 0L
    var totalRXByte = 0L

//    fun initialUsages(networkType: NetworkType) {
//        when (networkType) {
//            NetworkType.MOBILE -> {
//                currentTXByte = TrafficStats.getMobileTxBytes()
//                currentRXByte = TrafficStats.getMobileRxBytes()
//            }
//            NetworkType.WIFI -> {
//                currentTXByte = TrafficStats.getTotalTxBytes() - TrafficStats.getMobileTxBytes()
//                currentRXByte = TrafficStats.getTotalRxBytes() - TrafficStats.getMobileRxBytes()
//
//            }
//            NetworkType.ALL -> {
//                currentTXByte = TrafficStats.getTotalTxBytes()
//                currentRXByte = TrafficStats.getTotalRxBytes()
//            }
//        }
//    }


    fun getUsageNow(networkType: NetworkType): Usage {

        return when (networkType) {
            NetworkType.MOBILE -> {
                Usage(TrafficStats.getMobileRxBytes(), TrafficStats.getMobileTxBytes())
            }
            NetworkType.WIFI -> {
                totalRXByte = TrafficStats.getTotalRxBytes()
                totalTXByte = TrafficStats.getTotalTxBytes()
                currentTXByte = TrafficStats.getMobileTxBytes()
                currentRXByte = TrafficStats.getMobileRxBytes()
                lastRXByte = currentRXByte
                lastTXByte = currentTXByte
                Usage(
                    totalRXByte - currentRXByte,
                    totalTXByte - currentTXByte
                )
            }
            NetworkType.ALL -> {
                Usage(TrafficStats.getTotalRxBytes(), TrafficStats.getTotalTxBytes())
            }
        }

    }

    fun getUsageNowALL(networkType: NetworkType): Usage {

        return when (networkType) {
            NetworkType.MOBILE -> {
                Usage(TrafficStats.getMobileRxBytes(), TrafficStats.getMobileTxBytes())
            }
            NetworkType.WIFI -> {
                Usage(
                    TrafficStats.getTotalRxBytes() - TrafficStats.getMobileRxBytes(),
                    TrafficStats.getTotalTxBytes() - TrafficStats.getMobileTxBytes()
                )
            }
            NetworkType.ALL -> {
                Usage(TrafficStats.getTotalRxBytes(), TrafficStats.getTotalTxBytes())
            }
        }

    }

    fun getUsage(interval: TimeInterval, networkType: NetworkType): Usage {
        val stats = statsManager.queryDetails(
            when (networkType) {
                NetworkType.MOBILE -> NetworkCapabilities.TRANSPORT_CELLULAR
                NetworkType.WIFI -> NetworkCapabilities.TRANSPORT_WIFI
                NetworkType.ALL -> NetworkCapabilities.TRANSPORT_CELLULAR and NetworkCapabilities.TRANSPORT_WIFI
            }, subscriberId, interval.start, interval.end
        )

        val bucket = NetworkStats.Bucket()
        val usage = Usage()

        while (stats.hasNextBucket()) {
            stats.getNextBucket(bucket)

            usage.downloads += bucket.rxBytes
            usage.uploads += bucket.txBytes
        }

        stats.close()
        return usage
    }

    fun getMultiUsage(intervals: List<TimeInterval>, networkType: NetworkType): List<Usage> {
        var start = intervals[0].start
        var end = intervals[0].end

        val usages = mutableMapOf<TimeInterval, Usage>()

        for (interval in intervals) {
            if (interval.start < start)
                start = interval.start

            if (interval.end > end)
                end = interval.end

            usages[interval] = Usage()
        }

        val stats = statsManager.queryDetails(
            when (networkType) {
                NetworkType.MOBILE -> NetworkCapabilities.TRANSPORT_CELLULAR
                NetworkType.WIFI -> NetworkCapabilities.TRANSPORT_WIFI
                NetworkType.ALL -> NetworkCapabilities.TRANSPORT_CELLULAR and NetworkCapabilities.TRANSPORT_WIFI
            }, subscriberId, start, end
        )

        val bucket = NetworkStats.Bucket()

        while (stats.hasNextBucket()) {
            stats.getNextBucket(bucket)

            for (interval in intervals) {
                if (checkBucketInterval(bucket, interval.start, interval.end)) {
                    usages[interval]!!.downloads += bucket.rxBytes
                    usages[interval]!!.uploads += bucket.txBytes
                }
            }
        }

        stats.close()
        return usages.values.toList()
    }

    private fun checkBucketInterval(bucket: NetworkStats.Bucket, start: Long, end: Long): Boolean {
        return ((bucket.startTimeStamp in start..end) || (bucket.endTimeStamp in (start + 1) until end))
    }

}