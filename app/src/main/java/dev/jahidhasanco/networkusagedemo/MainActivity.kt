package dev.jahidhasanco.networkusagedemo


import android.Manifest
import android.annotation.SuppressLint
import android.app.usage.NetworkStatsManager
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.provider.Settings
import android.telephony.TelephonyManager
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import dev.jahidhasanco.networkusage.Interval
import dev.jahidhasanco.networkusage.NetSpeed
import dev.jahidhasanco.networkusage.NetworkType
import dev.jahidhasanco.networkusage.NetworkUsageManager
import dev.jahidhasanco.networkusagedemo.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding


    @SuppressLint("HardwareIds")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)


        ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.READ_PHONE_STATE), 34)
        setupPermissions()
        // startActivity(Intent(Settings.ACTION_USAGE_ACCESS_SETTINGS))

        val networkStatsManager =
            getSystemService(Context.NETWORK_STATS_SERVICE) as NetworkStatsManager
        val telephonyManager = getSystemService(Context.TELEPHONY_SERVICE) as TelephonyManager

        val networkUsage = NetworkUsageManager(networkStatsManager, "telephonyManager.subscriberId")

        // Monitor single interval
        // val today = networkUsage.getUsage(Interval.today, NetworkType.MOBILE)

        // Monitor multiple interval
        //  val last30Days = networkUsage.getMultiUsage(listOf(Interval.month, Interval.last30days), NetworkType.WIFI)

        val handler = Handler()

        val runnableCode = object : Runnable {
            override fun run() {
                val now = networkUsage.getUsageNow(NetworkType.WIFI)
                val speeds = NetSpeed.calculateSpeed(now.timeTaken, now.downloads, now.uploads)
                binding.apply {
                    totalSpeedTv.text = speeds[0].speed + " " + speeds[0].unit
                    downUsagesTv.text = speeds[1].speed + " " + speeds[1].unit
                    upUsagesTv.text = speeds[2].speed + " " + speeds[2].unit
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
            this,
            Manifest.permission.READ_PHONE_STATE
        )

        if (permission != PackageManager.PERMISSION_GRANTED) {
            Toast.makeText(this, "Permission to record denied", Toast.LENGTH_SHORT).show()
        }
    }
}