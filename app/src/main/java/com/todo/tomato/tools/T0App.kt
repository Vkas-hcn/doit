package com.todo.tomato.tools

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import com.applovin.sdk.AppLovinMediationProvider
import com.applovin.sdk.AppLovinSdk
import com.applovin.sdk.AppLovinSdkInitializationConfiguration
import com.google.firebase.Firebase
import com.google.firebase.FirebaseApp
import com.google.firebase.initialize
import com.todo.tomato.ad.AdManager
import com.todo.tomato.tools.bean.MyObjectBox
import com.todo.tomato.tools.bean.T0Entity
import io.objectbox.BoxStore
import java.security.SecureRandom
import java.util.UUID

class T0App : Application() {

    override fun onCreate() {
        super.onCreate()
        t0Name = packageName
        Firebase.initialize(this)
        FirebaseApp.initializeApp(this)
        t0Db = MyObjectBox.builder().androidContext(this).build()
        sharedPreferences = this.getSharedPreferences("TCtoList", Context.MODE_PRIVATE)
        adManagerOpen = AdManager(this)
        adManagerTba = AdManager(this)
        adManagerClick = AdManager(this)
        registerActivityLifecycleCallbacks(T0Launcher())

        val initConfig = AppLovinSdkInitializationConfiguration.builder(
            "HJFhpJAwSFJc4vKhpSiTESSEs1rhEL_ONC9UU5cc7qLd22D_FuuhMAeMiI0CVFV72QZ3JBGOL7XSQHMWp6krE2",
            this
        )
            .setMediationProvider(AppLovinMediationProvider.MAX)
            .build()
        AppLovinSdk.getInstance(this).initialize(initConfig) { sdkConfig ->
            maxAd = MaxAd(this)
        }
    }

    companion object {
        lateinit var t0Db: BoxStore
        lateinit var t0Name: String
        var maxAd: MaxAd? = null
        var t0Entity: T0Entity? = null
        lateinit var sharedPreferences: SharedPreferences
        var adManagerOpen: AdManager? = null
        var adManagerTba: AdManager? = null
        var adManagerClick: AdManager? = null
    }
}

var rlfk = false
var the = false


val distinctId: String
    get() {
        val cache = T0App.sharedPreferences.getString("distinctId", "")
        if (cache.isNullOrEmpty()) {
            val real = NumberMaster.findI()
            T0App.sharedPreferences.edit().putString("distinctId", real).apply()
            return real
        } else {
            return cache
        }
    }


val commonDistinctId: String
    get() {
        val cache = T0App.sharedPreferences.getString("commonDistinctId", "")
        if (cache.isNullOrEmpty()) {
            val real = UUID.randomUUID().toString()
            T0App.sharedPreferences.edit().putString("commonDistinctId", real).apply()
            return real
        } else {
            return cache
        }
    }


object NumberMaster {
    fun findI(): String {
        val secureRandom = SecureRandom()
        val stringBuilder = StringBuilder()
        for (i in 0..8) {
            val digit = secureRandom.nextInt(10)
            stringBuilder.append(digit)
        }
        return stringBuilder.toString()
    }
}