package dev.jahidhasanco.networkusage

import java.util.*

data class Speed(
    val speed: String,
    val unit: String
)

object NetSpeed {

    private var speedValue = ""
    private var speedUnit = ""

    fun getSpeed(s: Long, isSpeedUnitBits: Boolean = false): Speed {
        var speed = s

//        if (!isSpeedUnitBits) {
//            speed *= 8
//        }

        if (speed < 1048576) {
            speedUnit =
                if (isSpeedUnitBits) "kb/s" else "kB/s"
            speedValue = (speed / 1024).toString()
        } else if (speed < 1073741824) {
            speedUnit =
                if (isSpeedUnitBits) "mb/s" else "MB/s"
            speedValue =
                (speed / 1048576).toString()

        } else {
            speedUnit =
                if (isSpeedUnitBits) "gb/s" else "GB/s"
            speedValue = (speed / 1073741824).toString()
        }

        return Speed(speedValue, speedUnit)
    }
}