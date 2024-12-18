package com.todo.tomato.ui.activitys

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.OnBackPressedCallback
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import com.todo.tomato.R
import com.todo.tomato.ad.AdUtils
import com.todo.tomato.databinding.ActivityT0Binding
import com.todo.tomato.tools.T0App
import com.todo.tomato.tools.bean.T3Entity
import com.todo.tomato.tools.vm.T0Vm
import com.todo.tomato.tools.rlfk
import com.todo.tomato.tools.the
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.cancel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class T0Activity : AppCompatActivity() {

    private lateinit var binding: ActivityT0Binding
    private val t0Vm: T0Vm by viewModels()
    private var done = false
    private var openJob: Job? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityT0Binding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        onBackPressedDispatcher.addCallback(
            this,
            object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {}
            })
        AdUtils.log("T0Activity-start")
        AdUtils.cloneAdState = false
        t0Vm.getUserType()
        t0Vm.appPoint()
        initAdStart()
        with(t0Vm) {
            t0Pro.observe(this@T0Activity) {
                if (it == 100) {
                    stop()
                } else {
                    val layout = binding.t1.layoutParams
                    layout.width = binding.t0.width / 100 * it
                    binding.t1.layoutParams = layout
                }
            }
        }
        t0Vm.start()
    }


    private fun prepareJump() {
        CoroutineScope(Dispatchers.IO).launch {
            while (true) {
                if (lifecycle.currentState == Lifecycle.State.RESUMED) {
                    withContext(Dispatchers.Main) {
                        startActivity(Intent(this@T0Activity, T1Activity::class.java))
                        finish()
                        cancel()
                    }
                    break
                }
                delay(100)
            }
        }
    }

    override fun onResume() {
        super.onResume()
        t0Vm.resume()
    }

    override fun onPause() {
        super.onPause()
        t0Vm.pause()
    }

    private fun initAdStart() {
        AdUtils.updateUserOpinions(this)
        T0App.adManagerOpen?.loadAd(AdUtils.OPEN)
        lifecycleScope.launch {
            delay(2000)
            T0App.adManagerTba?.loadAd(AdUtils.TBA)
            T0App.adManagerClick?.loadAd(AdUtils.CLICK)
        }
        watingCMP()
    }

    private fun watingCMP() {
        openJob?.cancel()
        openJob = null
        openJob = lifecycleScope.launch {
            while (true) {
                val cmpState = T0App.t0Db.boxFor(T3Entity::class.java).all.isNotEmpty()
                if (cmpState) {
                    t0Vm.start()
                    openOpenAd(this@T0Activity) {
                        prepareJump()
                    }
                    cancel()
                    return@launch
                }
                delay(500)
            }
        }
    }

    suspend fun openOpenAd(activity: AppCompatActivity, jumpFun: () -> Unit) {
        if (T0App.adManagerOpen?.canShowAd(AdUtils.OPEN) == AdUtils.ad_jump_over) {
            jumpFun()
            openJob?.cancel()
            openJob = null
            return
        }
        if (T0App.adManagerOpen?.canShowAd(AdUtils.OPEN) == AdUtils.ad_wait) {
            T0App.adManagerOpen?.loadAd(AdUtils.OPEN)
        }

        var adShown = false
        var attemptCount = 0

        while (attemptCount < 20) {
            delay(500) // 每 500ms 检查一次
            AdUtils.log("等待OPEN广告中。。。$adShown")

            if (adShown) break

            attemptCount++

            if (T0App.adManagerOpen?.canShowAd(AdUtils.OPEN) == AdUtils.ad_show) {
                AdUtils.log("准备OPEN广告中。。。$adShown")
                T0App.adManagerOpen?.showAd(AdUtils.OPEN, activity, {
                    jumpFun()
                }, {
                    adShown = true
                    openJob?.cancel()
                    openJob = null
                })
            }
        }

        // 超过最大尝试次数时跳转
        if (!adShown) {
            AdUtils.log("OPEN广告超时。。。")
            jumpFun()
        }

    }
}