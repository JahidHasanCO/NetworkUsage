package dev.jahidhasanco.networkusagedemo


import android.Manifest
import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import dev.jahidhasanco.networkusage.*
import dev.jahidhasanco.networkusagedemo.data.model.UsagesData
import dev.jahidhasanco.networkusagedemo.databinding.ActivityMainBinding
import dev.jahidhasanco.networkusagedemo.presentation.adapter.DataUsagesAdapter


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var dataUsagesAdapter: DataUsagesAdapter
    private var usagesDataList = ArrayList<UsagesData>()

    @SuppressLint("HardwareIds")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        setupPermissions()

        val networkUsage = NetworkUsageManager(this, Util.getSubscriberId(this))

        val last30DaysWIFI = networkUsage.getMultiUsage(
            Interval.lastMonthDaily, NetworkType.WIFI
        )

        val last30DaysMobile = networkUsage.getMultiUsage(
            Interval.lastMonthDaily, NetworkType.MOBILE
        )

        for (i in last30DaysWIFI.indices) {
            usagesDataList.add(
                UsagesData(
                    Util.formatData(
                        last30DaysMobile[i].downloads,
                        last30DaysMobile[i].uploads
                    )[2],
                    Util.formatData(
                        last30DaysWIFI[i].downloads,
                        last30DaysWIFI[i].uploads
                    )[2],
                    last30DaysWIFI[i].date
                )
            )
        }

        val last7DaysTotalWIFI = networkUsage.getUsage(
            Interval.last7days, NetworkType.WIFI
        )

        val last7DaysTotalMobile = networkUsage.getUsage(
            Interval.last7days, NetworkType.MOBILE
        )

        val last30DaysTotalWIFI = networkUsage.getUsage(
            Interval.last30days, NetworkType.WIFI
        )

        val last30DaysTotalMobile = networkUsage.getUsage(
            Interval.last30days, NetworkType.MOBILE
        )

        usagesDataList.add(
            UsagesData(
                Util.formatData(
                    last7DaysTotalMobile.downloads,
                    last7DaysTotalMobile.uploads
                )[2],
                Util.formatData(
                    last7DaysTotalWIFI.downloads,
                    last7DaysTotalWIFI.uploads
                )[2],
                "Last 7 Days"
            )
        )

        usagesDataList.add(
            UsagesData(
                Util.formatData(
                    last30DaysTotalMobile.downloads,
                    last30DaysTotalMobile.uploads
                )[2],
                Util.formatData(
                    last30DaysTotalWIFI.downloads,
                    last30DaysTotalWIFI.uploads
                )[2],
                "Last 30 Days"
            )
        )

        dataUsagesAdapter = DataUsagesAdapter(usagesDataList)
        binding.monthlyDataUsagesRv.layoutManager = LinearLayoutManager(this)
        binding.monthlyDataUsagesRv.setHasFixedSize(true)
        binding.monthlyDataUsagesRv.adapter = dataUsagesAdapter

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
            //startActivity(Intent(Settings.ACTION_USAGE_ACCESS_SETTINGS))
        }
    }
}