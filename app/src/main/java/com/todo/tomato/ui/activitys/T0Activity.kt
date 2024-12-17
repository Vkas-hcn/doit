package com.todo.tomato.ui.activitys

import android.content.Intent
import android.os.Bundle
import androidx.activity.OnBackPressedCallback
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.Lifecycle
import com.todo.tomato.R
import com.todo.tomato.databinding.ActivityT0Binding
import com.todo.tomato.tools.T0App
import com.todo.tomato.tools.vm.T0Vm
import com.todo.tomato.tools.rlfk
import com.todo.tomato.tools.the
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class T0Activity : AppCompatActivity() {

    private lateinit var binding: ActivityT0Binding
    private val t0Vm: T0Vm by viewModels()
    private var done = false
    private var job: Job? = null
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
        checkAd()
        with(t0Vm) {
            t0Pro.observe(this@T0Activity) {
                if (it == 100) {
                    stop()
                    prepareJump()
                } else {
                    val layout = binding.t1.layoutParams
                    layout.width = binding.t0.width / 100 * it
                    binding.t1.layoutParams = layout
                }
            }
        }
        t0Vm.start()
    }

    private fun checkAd() {
        done = false
        job?.cancel()
        job = CoroutineScope(Dispatchers.IO).launch {
            var lt = 0L
            while (true) {
                if (lt >= 10000L && lifecycle.currentState == Lifecycle.State.RESUMED) {
                    prepareJump()
                    break
                }
                if (T0App.maxAd != null && T0App.maxAd!!.openState == "cool"
                    && lifecycle.currentState == Lifecycle.State.RESUMED
                    && lt >= 2000L
                ) {
                    T0App.maxAd!!.openAction = {
                        prepareJump()
                    }
                    T0App.maxAd!!.showAd()
                    break
                }
                if (lifecycle.currentState == Lifecycle.State.RESUMED) {
                    lt += 300L
                }
                delay(300L)
            }
        }
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
}