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
        if (done) return
        done = true
        CoroutineScope(Dispatchers.IO).launch {
            while (true) {
                if (lifecycle.currentState == Lifecycle.State.RESUMED) {
                    withContext(Dispatchers.Main) {
                        if (the) {
                            if (rlfk) {
                                finish()
                            } else {
                                startActivity(Intent(this@T0Activity, T1Activity::class.java))
                                finish()
                            }
                        } else {
                            startActivity(Intent(this@T0Activity, T1Activity::class.java))
                            finish()
                        }
                        rlfk = false
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
            T0App.adManagerOpen?.loadAd(AdUtils.TBA)
            T0App.adManagerOpen?.loadAd(AdUtils.CLICK)
        }
        watingCMP()
    }

    private fun watingCMP() {
        CoroutineScope(Dispatchers.Main).launch {
            while (true) {
                val cmpState = T0App.t0Db.boxFor(T3Entity::class.java).all.isNotEmpty()
                if (cmpState) {
                    t0Vm.start()
                    AdUtils.openOpenAd(this@T0Activity) {
                        prepareJump()
                    }
                    cancel()
                    return@launch
                }
                delay(500)
            }
        }
    }
}