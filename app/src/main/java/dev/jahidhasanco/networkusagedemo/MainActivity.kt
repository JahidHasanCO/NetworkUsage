package dev.jahidhasanco.networkusagedemo


import android.Manifest
import android.annotation.SuppressLint
import android.app.usage.NetworkStatsManager
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.os.Handler
import android.provider.Settings
import android.telephony.TelephonyManager
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import dev.jahidhasanco.networkusage.*
import dev.jahidhasanco.networkusagedemo.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding


    @SuppressLint("HardwareIds")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        setupPermissions()


        val networkStatsManager =
            getSystemService(Context.NETWORK_STATS_SERVICE) as NetworkStatsManager

        val networkUsage = NetworkUsageManager(networkStatsManager, Util.getSubscriberId(this))


        // Monitor single interval


        // Monitor multiple interval
        val last30Days = networkUsage.getMultiUsage(
            listOf(Interval.month, Interval.last30days), NetworkType.WIFI
        )

        var last30DaysString = ""

        last30Days.forEach {
            last30DaysString += "${it.timeTaken} : ${
                Util.formatData(
                    it.downloads,
                    it.uploads
                )[2]
            } \n"
        }

        binding.pastData.text = last30DaysString

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
                    totalSpeedTv.text = speeds[0].speed + " " + speeds[0].unit
                    downUsagesTv.text = "Down: " + speeds[1].speed + speeds[1].unit
                    upUsagesTv.text = "Up: " + speeds[2].speed + speeds[2].unit


                }
                handler.postDelayed(this, 1000)
            }
        }

        runnableCode.run()
        // Observe realtime usage

        //   binding.pastData.text = "Up: ${last30Days[0].uploads} Down: ${last30Days[0].downloads}"

    }

    private fun setupPermissions() {
        val permission = ContextCompat.checkSelfPermission(
            this, Manifest.permission.READ_PHONE_STATE
        )

        if (permission != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(
                this, arrayOf(Manifest.permission.READ_PHONE_STATE), 34
            )
        }

        val permission2 = ContextCompat.checkSelfPermission(
            this, Manifest.permission.PACKAGE_USAGE_STATS
        )

        if (permission2 != PackageManager.PERMISSION_GRANTED) {
            // startActivity(Intent(Settings.ACTION_USAGE_ACCESS_SETTINGS))
        }
    }
}