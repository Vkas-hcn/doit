package com.todo.tomato.ad

import android.os.Handler
import android.os.Looper
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.google.android.ump.ConsentDebugSettings
import com.google.android.ump.ConsentInformation
import com.google.android.ump.ConsentRequestParameters
import com.google.android.ump.UserMessagingPlatform
import com.todo.tomato.BuildConfig
import com.todo.tomato.tools.T0App
import com.todo.tomato.tools.T0App.Companion.adManagerOpen
import com.todo.tomato.tools.bean.T2Entity
import com.todo.tomato.tools.bean.T3Entity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

object AdUtils {
    private var loadCount = 0
    var cloneAdState = false
    val OPEN = "OPEN"
    val TBA = "TBA"
    val CLICK = "CLICK"
    val ad_jump_over = "ad_jump_over"
    val ad_wait = "ad_wait"
    val ad_show = "ad_show"
    fun log(msg: String) {
        if (BuildConfig.DEBUG) {
            Log.e("AdUtils", msg)
        }
    }

    fun shouldLoadAd(): Boolean {
        log("shouldLoadAd")

        loadCount++
        return loadCount % 2 == 0 // 偶数次返回true，奇数次返回false
    }

    private var loadCount2 = 0

    fun shouldLoadAd2(): Boolean {
        loadCount2++
        return loadCount2 % 3 == 0 // 每隔2次加载广告，即第3、6、9次等
    }

    fun getBlackData(): Boolean {

        val bean = T0App.t0Db.boxFor(T2Entity::class.java).all
        if (bean.isEmpty()) {
            return true
        }
        val bData = T0App.t0Db.boxFor(T2Entity::class.java).all[0].value
        return bData != "moraine"
    }

    fun updateUserOpinions(activity: AppCompatActivity) {
        val cmpState = T0App.t0Db.boxFor(T3Entity::class.java).all.isNotEmpty()
        if (cmpState) {
            return
        }
        log("updateUserOpinions")
        val debugSettings =
            ConsentDebugSettings.Builder(activity)
                .setDebugGeography(ConsentDebugSettings.DebugGeography.DEBUG_GEOGRAPHY_EEA)
                .addTestDeviceHashedId("76A730E9AE68BD60E99DF7B83D65C4B4")
                .build()
        val params = ConsentRequestParameters
            .Builder()
            .setConsentDebugSettings(debugSettings)
            .build()
        val consentInformation: ConsentInformation =
            UserMessagingPlatform.getConsentInformation(activity)
        consentInformation.requestConsentInfoUpdate(
            activity,
            params, {
                UserMessagingPlatform.loadAndShowConsentFormIfRequired(activity) {
                    if (consentInformation.canRequestAds()) {
                        T0App.t0Db.boxFor(T3Entity::class.java).put(T3Entity(cmpState = "1"))

                    }
                }
            },
            {
                T0App.t0Db.boxFor(T3Entity::class.java).put(T3Entity(cmpState = "1"))
            }
        )
    }
}