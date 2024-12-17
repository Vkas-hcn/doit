package com.todo.tomato.tools

import android.app.Activity
import android.app.Application
import android.content.Intent
import android.os.Bundle
import com.applovin.adview.AppLovinFullscreenActivity
import com.todo.tomato.ui.activitys.T0Activity

class T0Launcher : Application.ActivityLifecycleCallbacks {

    override fun onActivityCreated(activity: Activity, savedInstanceState: Bundle?) {
    }

    override fun onActivityStarted(activity: Activity) {
    }

    override fun onActivityResumed(activity: Activity) {
        if (t0P != 0L && System.currentTimeMillis() - t0P >= 3000L) {
            rlfk = true
            activity.startActivity(Intent(activity, T0Activity::class.java))
            if (activity is T0Activity) {
                activity.finish()
            }
        }
        t0P = 0L
    }

    override fun onActivityPaused(activity: Activity) {
        t0P = 0L
    }

    override fun onActivityStopped(activity: Activity) {
        if (activity is AppLovinFullscreenActivity) {
            activity.finish()
        }
        t0P = System.currentTimeMillis()
    }


    override fun onActivitySaveInstanceState(activity: Activity, outState: Bundle) {
    }

    override fun onActivityDestroyed(activity: Activity) {
    }
}