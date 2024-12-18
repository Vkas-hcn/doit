package com.todo.tomato.ui.fragments

import android.app.DatePickerDialog
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.todo.tomato.ad.AdUtils
import com.todo.tomato.databinding.FragmentT0Binding
import com.todo.tomato.tools.T0App
import com.todo.tomato.tools.t1F
import com.todo.tomato.tools.adapter.T0Inter
import com.todo.tomato.tools.adapter.T1Adapter
import com.todo.tomato.ui.activitys.T2Activity
import com.todo.tomato.ui.activitys.T3Activity
import com.todo.tomato.tools.vm.T1Vm
import com.todo.tomato.tools.bean.T0Entity
import com.todo.tomato.ui.dialogs.T0Dialog
import com.todo.tomato.tools.t2F
import com.todo.tomato.ui.dialogs.LoadingDialog
import kotlinx.coroutines.Job
import kotlinx.coroutines.TimeoutCancellationException
import kotlinx.coroutines.delay
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import kotlinx.coroutines.withTimeout
import java.util.Calendar

class T0Fragment : Fragment(), T0Inter {

    private lateinit var binding: FragmentT0Binding

    private val t1Vm: T1Vm by viewModels()
    private var jobTBA: Job? = null
    private var jobClick: Job? = null

    private lateinit var loadingDialog: LoadingDialog
    private val t1Adapter: T1Adapter by lazy {
        T1Adapter()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentT0Binding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val c = Calendar.getInstance()
        t1Vm.t0Y = c.get(Calendar.YEAR).toLong()
        t1Vm.t0M = (c.get(Calendar.MONTH) + 1).toLong()
        t1Vm.t0D = c.get(Calendar.DAY_OF_MONTH).toLong()
        binding.t3.text = t1F(t1Vm.t0Y, t1Vm.t0M, t1Vm.t0D)
        loadingDialog = LoadingDialog(requireActivity())

        rp()

        with(binding) {
            t1.setOnClickListener {
                showClickIntAd {
                    startActivity(Intent(requireActivity(), T3Activity::class.java))
                }
            }
            t3.setOnClickListener {
                dLog()
            }
            t4.setOnClickListener {
                dLog()
            }
            t6.setOnClickListener {
                T0Dialog(save = { type, name ->
                    t1Vm.put(
                        T0Entity(
                            name = name,
                            note = "",
                            type = type,
                            createTime = System.currentTimeMillis(),
                            finishTime = 0L,
                            year = t1Vm.t0Y,
                            month = t1Vm.t0M,
                            day = t1Vm.t0D,
                            finish = false
                        )
                    )
                    rp()
                    t1Vm.get()

                }).show(childFragmentManager, "T0Dialog")
            }
            t12.setOnClickListener {
                t1Vm.tab = "todo"
                t12.setTextColor(Color.parseColor("#4A256E"))
                t13.setTextColor(Color.parseColor("#C2C1C3"))
                rp()
                t1Vm.get()
            }
            t13.setOnClickListener {
                t1Vm.tab = "allFinish"
                t12.setTextColor(Color.parseColor("#C2C1C3"))
                t13.setTextColor(Color.parseColor("#4A256E"))
                rp()
                t1Vm.get()
            }

            t1Adapter.action = this@T0Fragment
            t14.adapter = t1Adapter
            t14.layoutManager = LinearLayoutManager(context)

            t15.setOnClickListener {
                showClickIntAd {
                    T0App.t0Entity = null
                    startActivity(Intent(requireActivity(), T2Activity::class.java))
                }
            }
        }
        with(t1Vm) {
            t0ds.observe(viewLifecycleOwner) {
                binding.t16.t0.text =
                    if (tab == "todo") "Your to-do list is clear! \nReady to add a new task?" else "No victories yet! \nComplete a task to celebrate your progress."
                binding.t16.root.isVisible = it.isNullOrEmpty()
                t1Adapter.rl(it)
            }
        }
    }

    override fun onResume() {
        super.onResume()
        t1Vm.get()
        rp()
    }


    private fun dLog() {
        val c = Calendar.getInstance()
        val year = c.get(Calendar.YEAR)
        val month = c.get(Calendar.MONTH)
        val day = c.get(Calendar.DAY_OF_MONTH)
        val datePickerDialog = DatePickerDialog(
            requireActivity(),
            { _, sy, sm, sd ->
                val formattedDate = "$sd ${(sm + 1).t2F()} $sy"
                binding.t3.text = formattedDate
                t1Vm.t0Y = sy.toLong()
                t1Vm.t0M = (sm + 1).toLong()
                t1Vm.t0D = sd.toLong()
                t1Vm.get()
                rp()
            },
            year,
            month,
            day
        )
        datePickerDialog.show()
    }

    override fun delete(id: Long) {
        t1Vm.delete(id)
        t1Vm.get()
        rp()
    }

    override fun finish(entity: T0Entity) {
        showTbaIntAd {
            entity.finish = !entity.finish
            if (entity.finish) {
                entity.finishTime = System.currentTimeMillis()
            } else {
                entity.finishTime = 0L
            }
            t1Vm.update(entity)
            t1Vm.get()
            rp()
        }
    }

    private fun rp() {
        val a = t1Vm.tgb()
        val b = t1Vm.tgc()
        with(binding) {
            t7.progress = if (a == 0L) 0f else (1.0 * a / b * 100).toFloat()
            t8.text = "${a}/${b}"
            t10.text = "${(1.0 * a / b * 100).toInt()}%"
        }
    }

    override fun look(entity: T0Entity) {
        showClickIntAd {
            T0App.t0Entity = entity
            startActivity(Intent(requireActivity(), T2Activity::class.java))
        }
    }

    private fun showTbaIntAd(nextFun: () -> Unit) {
        jobTBA?.cancel()
        jobTBA = null
        jobTBA = lifecycleScope.launch {
            if (T0App.adManagerTba?.canShowAd(AdUtils.TBA) == AdUtils.ad_jump_over) {
                nextFun()
                return@launch
            }
            val state = AdUtils.shouldLoadAd2()
            if (!state) {
                AdUtils.log("TBA广告，不展示: ")
                nextFun()
                return@launch
            }
            T0App.adManagerTba?.loadAd(AdUtils.TBA)
            loadingDialog.showLoading()

            try {
                withTimeout(5000L) {
                    while (isActive) {
                        if (T0App.adManagerTba?.canShowAd(AdUtils.TBA) == AdUtils.ad_show) {
                            T0App.adManagerTba?.showAdFragment(AdUtils.TBA, requireActivity()) {
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

    private fun showClickIntAd(nextFun: () -> Unit) {
        jobClick?.cancel()
        jobClick = null
        jobClick = lifecycleScope.launch {
            if (T0App.adManagerClick?.canShowAd(AdUtils.CLICK) == AdUtils.ad_jump_over) {
                nextFun()
                return@launch
            }
            T0App.adManagerClick?.loadAd(AdUtils.CLICK)
            loadingDialog.showLoading()
            try {
                withTimeout(5000L) {
                    while (isActive) {
                        if (T0App.adManagerClick?.canShowAd(AdUtils.CLICK) == AdUtils.ad_show) {
                            T0App.adManagerClick?.showAdFragment(AdUtils.CLICK, requireActivity()) {
                                nextFun()
                            }
                            loadingDialog.hideLoading()
                            jobClick?.cancel()
                            jobClick = null
                            break
                        }
                        delay(500L)
                    }
                }
            } catch (e: TimeoutCancellationException) {
                nextFun()
                loadingDialog.hideLoading()
                jobClick?.cancel()
                jobClick = null
            }
        }
    }

}