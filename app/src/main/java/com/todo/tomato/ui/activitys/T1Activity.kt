package com.todo.tomato.ui.activitys

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import com.todo.tomato.R
import com.todo.tomato.ad.AdUtils
import com.todo.tomato.databinding.ActivityT1Binding
import com.todo.tomato.tools.T0App.Companion.adManagerTba
import com.todo.tomato.tools.adapter.VpAdapter
import com.todo.tomato.tools.vm.T1Vm
import com.todo.tomato.tools.the
import com.todo.tomato.ui.dialogs.LoadingDialog
import kotlinx.coroutines.Job
import kotlinx.coroutines.TimeoutCancellationException
import kotlinx.coroutines.delay
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import kotlinx.coroutines.withTimeout

class T1Activity : AppCompatActivity() {
    private lateinit var binding: ActivityT1Binding
    private val t1Vm: T1Vm by viewModels()
    private var jobTBA: Job? = null
    private lateinit var loadingDialog: LoadingDialog
    private val vpAdapter: VpAdapter by lazy {
        VpAdapter(supportFragmentManager, this.lifecycle)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityT1Binding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        loadingDialog = LoadingDialog(this)
        the = true
        with(binding) {
            t0.isUserInputEnabled = false
            t0.adapter = vpAdapter

            t1.setOnClickListener {
                showTbaIntAd(true)  {
                    t1.setImageResource(R.mipmap.t_icon9)
                    t2.setImageResource(R.mipmap.t_icon8)
                    t0.currentItem = 0
                }
            }
            t2.setOnClickListener {
                showTbaIntAd(true) {
                    t1.setImageResource(R.mipmap.t_icon16)
                    t2.setImageResource(R.mipmap.t_icon15)
                    t0.currentItem = 1
                }
            }
        }
    }

    override fun onResume() {
        super.onResume()
        t1Vm.homePoint()
    }

    private fun showTbaIntAd(istba: Boolean, nextFun: () -> Unit) {
        jobTBA?.cancel()
        jobTBA = null
        jobTBA = lifecycleScope.launch {
            if (adManagerTba?.canShowAd(AdUtils.TBA) == AdUtils.ad_jump_over) {
                nextFun()
                return@launch
            }
            val state = if (istba) {
                AdUtils.shouldLoadAd()
            } else {
                AdUtils.shouldLoadAd2()
            }
            if (!state) {
                AdUtils.log("TBA广告，不展示: ")
                nextFun()
                return@launch
            }
            if (adManagerTba?.canShowAd(AdUtils.TBA) == AdUtils.ad_wait) {
                adManagerTba?.loadAd(AdUtils.TBA)
            }
            loadingDialog.showLoading()

            try {
                withTimeout(5000L) {
                    while (isActive) {
                        if (adManagerTba?.canShowAd(AdUtils.TBA) == AdUtils.ad_show) {
                            adManagerTba?.showAd(AdUtils.TBA, this@T1Activity) {
                                nextFun()
                            }
                            loadingDialog.hideLoading()

                            break
                        }
                        delay(500L)
                    }
                }
            } catch (e: TimeoutCancellationException) {
                nextFun()
                loadingDialog.hideLoading()
            }
        }
    }

}