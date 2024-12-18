package com.todo.tomato.ad


import android.app.Activity
import android.app.Application
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.Lifecycle
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.FullScreenContentCallback
import com.google.android.gms.ads.LoadAdError
import com.google.android.gms.ads.MobileAds
import com.todo.tomato.ad.AdUtils.log
import java.util.Date
import java.text.SimpleDateFormat
import com.google.android.gms.ads.appopen.AppOpenAd
import com.google.android.gms.ads.interstitial.InterstitialAd
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback
import com.todo.tomato.ad.AdUtils.TBA

class AdManager(private val application: Application) {
    private val adCache = mutableMapOf<String, Any>()
    private val adLoadInProgress = mutableMapOf<String, Boolean>()
    private val adTimestamps = mutableMapOf<String, Long>()
    private val openId = "ca-app-pub-3940256099942544/9257395921"
    private val tbaId = "ca-app-pub-3940256099942544/1033173712"
    private val clickId = "ca-app-pub-3940256099942544/1033173712"

    init {
        MobileAds.initialize(application) {
            Log.d("AdManager", "AdMob initialized")
        }
    }

    private fun canRequestAd(adType: String): Boolean {
        val currentTime = System.currentTimeMillis()
        val lastLoadTime = adTimestamps[adType] ?: 0L
        return adTimestamps[adType] != null && (currentTime - lastLoadTime > 3000 * 1000) // 50min
    }

    fun loadAd(adType: String) {
        if (adLoadInProgress[adType] == true) return
        adLoadInProgress[adType] = true
        val adId = when (adType) {
            AdUtils.OPEN -> openId
            AdUtils.TBA -> tbaId
            AdUtils.CLICK -> clickId

            else -> ""
        }

        loadAdFromList(adType, adId)
    }

    private fun loadAdFromList(adType: String, adId: String) {
        if (adCache.containsKey(adType) && !canRequestAd(adType)) {
            log("已有$adType 广告，不在加载: ")
            return
        }
        if (adCache.containsKey(adType) && canRequestAd(adType)) {
            log("$adType 广告过期，重新加载: ")
            qcAd(adType)
            loadAd(adType)
            return
        }
        val blackData = AdUtils.getBlackData()

        if (blackData && adType != AdUtils.OPEN) {
            log("黑名单屏蔽$adType 广告，不在加载: ")
            return
        }
        log("$adType 广告，开始加载: id=${adId}")
        when (adType) {
            AdUtils.OPEN -> loadOpenAd(adType, adId)
            AdUtils.TBA -> loadInterstitialAd(adType, adId)
            AdUtils.CLICK -> loadInterstitialAd(adType, adId)
        }
    }

    private fun loadOpenAd(adType: String, adId: String) {
        AppOpenAd.load(application, adId, AdRequest.Builder().build(),
            AppOpenAd.APP_OPEN_AD_ORIENTATION_PORTRAIT,
            object : AppOpenAd.AppOpenAdLoadCallback() {
                override fun onAdLoaded(ad: AppOpenAd) {
                    log("$adType+广告加载成功")
                    adCache[adType] = ad
                    adTimestamps[adType] = System.currentTimeMillis()
                    adLoadInProgress[adType] = false
                }

                override fun onAdFailedToLoad(loadAdError: LoadAdError) {
                    log("${adType}广告加载失败=${loadAdError}")
                    adLoadInProgress[adType] = false

                }
            })
    }


    private fun loadInterstitialAd(adType: String, adId: String) {
        InterstitialAd.load(application, adId, AdRequest.Builder().build(),
            object : InterstitialAdLoadCallback() {
                override fun onAdLoaded(ad: InterstitialAd) {
                    log("${adType}广告加载成功")
                    adCache[adType] = ad
                    adTimestamps[adType] = System.currentTimeMillis()
                    adLoadInProgress[adType] = false
                }

                override fun onAdFailedToLoad(loadAdError: LoadAdError) {
                    log("${adType}广告加载失败=${loadAdError}")
                    adLoadInProgress[adType] = false

                }
            })
    }

    fun qcAd(adType: String) {
        adLoadInProgress.remove(adType)
        adCache.remove(adType)
        adTimestamps.remove(adType)
    }

    fun showAd(adType: String, activity: AppCompatActivity, nextFun: () -> Unit, wait: () -> Unit) {
        val state = activity.lifecycle.currentState.name == Lifecycle.State.RESUMED.name
        if (adCache.containsKey(adType) && state) {
            wait()
            when (val ad = adCache[adType]) {
                is AppOpenAd -> {
                    ad.fullScreenContentCallback =
                        object : FullScreenContentCallback() {
                            override fun onAdDismissedFullScreenContent() {
                                if (!AdUtils.cloneAdState) {
                                    log("关闭-${adType}广告: ")
                                    nextFun()
                                }
                                qcAd(adType)
                            }

                            override fun onAdShowedFullScreenContent() {
                            }

                            override fun onAdClicked() {
                            }
                        }
                    ad.show(activity)
                    log("显示-${adType}广告")
                    adCache.remove(adType)

                }


                is InterstitialAd -> {
                    ad.fullScreenContentCallback = object : FullScreenContentCallback() {
                        override fun onAdDismissedFullScreenContent() {
                            if (!AdUtils.cloneAdState) {
                                log("关闭-${adType}广告: ")
                                nextFun()
                            }
                            qcAd(adType)
                            loadAd(adType)
                        }

                        override fun onAdClicked() {
                            super.onAdClicked()
                        }

                        override fun onAdFailedToShowFullScreenContent(adError: com.google.android.gms.ads.AdError) {
                            adCache.remove(adType)
                        }

                        override fun onAdShowedFullScreenContent() {
                        }
                    }
                    ad.show(activity)
                    log("展示-${adType}广告: ")
                    adCache.remove(adType)
                }
            }
        }
    }

    fun showAdFragment(adType: String, fragmentActivity: FragmentActivity, nextFun: () -> Unit) {
        val state = fragmentActivity.lifecycle.currentState.name == Lifecycle.State.RESUMED.name
        if (adCache.containsKey(adType) && state) {
            when (val ad = adCache[adType]) {
                is InterstitialAd -> {
                    ad.fullScreenContentCallback = object : FullScreenContentCallback() {
                        override fun onAdDismissedFullScreenContent() {
                            if (!AdUtils.cloneAdState) {
                                log("关闭-${adType}广告: ")
                                nextFun()
                            }
                            qcAd(adType)
                            loadAd(adType)
                        }

                        override fun onAdClicked() {
                            super.onAdClicked()
                        }

                        override fun onAdFailedToShowFullScreenContent(adError: com.google.android.gms.ads.AdError) {
                            adCache.remove(adType)
                        }

                        override fun onAdShowedFullScreenContent() {
                        }
                    }
                    ad.show(fragmentActivity)
                    log("展示-${adType}广告: ")
                    adCache.remove(adType)
                }
            }
        }
    }


    fun canShowAd(adType: String): String {
        val ad = adCache[adType]
        val blackData = AdUtils.getBlackData()
        if (blackData && adType != AdUtils.OPEN) {
            return AdUtils.ad_jump_over
        }
        if (ad == null) {
            return AdUtils.ad_wait
        }
        return AdUtils.ad_show
    }


}
