package com.todo.tomato.tools

import android.content.Context
import com.applovin.mediation.MaxAd
import com.applovin.mediation.MaxAdListener
import com.applovin.mediation.MaxError
import com.applovin.mediation.ads.MaxAppOpenAd
import com.applovin.sdk.AppLovinSdk

class MaxAd(private val context: Context) : MaxAdListener {

    private var openAd: MaxAppOpenAd = MaxAppOpenAd("2835a9e7ed861625", context)

    lateinit var openAction: (() -> Unit)

    val openState: String
        get() {
            return if (openAd.isReady) "cool"
            else "nope"
        }

    init {
        openAd.setListener(this)
        openAd.loadAd()
    }

    fun showAd() {
        if (!AppLovinSdk.getInstance(context).isInitialized) return
        if (openAd.isReady) {
            openAd.showAd("«test_placement»")
        } else {
            openAd.loadAd()
        }
    }

    override fun onAdLoaded(ad: MaxAd) {
    }

    override fun onAdLoadFailed(adUnitId: String, error: MaxError) {
        openAd.loadAd()
    }

    override fun onAdDisplayed(ad: MaxAd) {
    }

    override fun onAdClicked(ad: MaxAd) {
    }

    override fun onAdHidden(ad: MaxAd) {
        openAd.loadAd()
        openAction()
    }

    override fun onAdDisplayFailed(ad: MaxAd, error: MaxError) {
        openAd.loadAd()
        openAction()
    }
}
