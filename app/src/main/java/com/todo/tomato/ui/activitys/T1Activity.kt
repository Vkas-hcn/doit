package com.todo.tomato.ui.activitys

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.todo.tomato.R
import com.todo.tomato.databinding.ActivityT1Binding
import com.todo.tomato.tools.adapter.VpAdapter
import com.todo.tomato.tools.vm.T1Vm
import com.todo.tomato.tools.the

class T1Activity : AppCompatActivity() {
    private lateinit var binding: ActivityT1Binding
    private val t1Vm: T1Vm by viewModels()

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
        the = true
        with(binding) {
            t0.isUserInputEnabled = false
            t0.adapter = vpAdapter

            t1.setOnClickListener {
                t1.setImageResource(R.mipmap.t_icon9)
                t2.setImageResource(R.mipmap.t_icon8)
                t0.currentItem = 0
            }
            t2.setOnClickListener {
                t1.setImageResource(R.mipmap.t_icon16)
                t2.setImageResource(R.mipmap.t_icon15)
                t0.currentItem = 1
            }
        }
    }

    override fun onResume() {
        super.onResume()
        t1Vm.homePoint()
    }
}