package com.todo.tomato.ui.activitys

import android.app.DatePickerDialog
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import com.todo.tomato.R
import com.todo.tomato.ad.AdUtils
import com.todo.tomato.databinding.ActivityT2Binding
import com.todo.tomato.tools.T0App
import com.todo.tomato.tools.t1F
import com.todo.tomato.tools.adapter.TypeAdapter
import com.todo.tomato.tools.vm.T2Vm
import com.todo.tomato.tools.bean.T0Entity
import com.todo.tomato.tools.t2F
import com.todo.tomato.ui.dialogs.LoadingDialog
import kotlinx.coroutines.Job
import kotlinx.coroutines.TimeoutCancellationException
import kotlinx.coroutines.delay
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import kotlinx.coroutines.withTimeout
import java.util.Calendar

class T2Activity : AppCompatActivity() {
    private lateinit var binding: ActivityT2Binding
    private val t2Vm: T2Vm by viewModels()
    private val typeAdapter: TypeAdapter by lazy {
        TypeAdapter()
    }
    private var jobClick: Job? = null
    private lateinit var loadingDialog: LoadingDialog
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityT2Binding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        loadingDialog = LoadingDialog(this)
        if (T0App.t0Entity != null) {
            typeAdapter.selectType = T0App.t0Entity!!.type
            binding.t9.setText(T0App.t0Entity?.name ?: "")
            binding.t10.setText(T0App.t0Entity?.note ?: "")
            t2Vm.t0Y = T0App.t0Entity!!.year
            t2Vm.t0M = T0App.t0Entity!!.month
            t2Vm.t0D = T0App.t0Entity!!.day
        } else {
            val c = Calendar.getInstance()
            t2Vm.t0Y = c.get(Calendar.YEAR).toLong()
            t2Vm.t0M = (c.get(Calendar.MONTH) + 1).toLong()
            t2Vm.t0D = c.get(Calendar.DAY_OF_MONTH).toLong()
        }
        binding.t3.text = t1F(t2Vm.t0Y, t2Vm.t0M, t2Vm.t0D)

        with(binding) {
            t3.setOnClickListener {
                dLog()
            }
            t4.setOnClickListener {
                dLog()
            }
            t6.adapter = typeAdapter
            t6.layoutManager = GridLayoutManager(this@T2Activity, 5)
            t0.setOnClickListener { finish() }
            t11.setOnClickListener {
                if (t9.text.toString().isEmpty() || typeAdapter.selectType == "") {
                    Toast.makeText(this@T2Activity, "Please input something", Toast.LENGTH_LONG)
                        .show()
                    return@setOnClickListener
                }
                t11ClickFun()
            }
        }
    }

    private fun t11ClickFun() {
        with(binding) {

            if (T0App.t0Entity != null) {
                with(T0App.t0Entity!!) {
                    this.name = t9.text.toString()
                    this.note = t10.text.toString()
                    this.type = typeAdapter.selectType
                    this.createTime = System.currentTimeMillis()
                    this.finishTime = 0L
                    this.year = t2Vm.t0Y
                    this.month = t2Vm.t0M
                    this.day = t2Vm.t0D
                    this.finish = false
                }
                t2Vm.update(T0App.t0Entity!!)
            } else {
                t2Vm.put(
                    T0Entity(
                        name = t9.text.toString(),
                        note = t10.text.toString(),
                        type = typeAdapter.selectType,
                        createTime = System.currentTimeMillis(),
                        finishTime = 0L,
                        year = t2Vm.t0Y,
                        month = t2Vm.t0M,
                        day = t2Vm.t0D,
                        finish = false
                    )
                )
            }
            Toast.makeText(this@T2Activity, "Saved successfully", Toast.LENGTH_LONG).show()
            finish()
        }
    }

    private fun dLog() {
        val c = Calendar.getInstance()
        val year = c.get(Calendar.YEAR)
        val month = c.get(Calendar.MONTH)
        val day = c.get(Calendar.DAY_OF_MONTH)
        val datePickerDialog = DatePickerDialog(
            this@T2Activity,
            { _, sy, sm, sd ->
                val formattedDate = "$sd ${(sm + 1).t2F()} $sy"
                binding.t3.text = formattedDate
                t2Vm.t0Y = sy.toLong()
                t2Vm.t0M = (sm + 1).toLong()
                t2Vm.t0D = sd.toLong()
            },
            year,
            month,
            day
        )
        datePickerDialog.show()
    }

}