package com.todo.tomato.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.todo.tomato.R
import com.todo.tomato.databinding.FragmentT1Binding
import com.todo.tomato.tools.vm.T1Vm
import com.todo.tomato.tools.t0F

class T1Fragment : Fragment() {
    private lateinit var binding: FragmentT1Binding
    private val t1Vm: T1Vm by activityViewModels()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentT1Binding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        with(binding) {
            t5.setOnClickListener {
                t1Vm.tsp()
                refreshResult()
            }
            t6.setOnClickListener {
                if (t1Vm.t4) {
                    t1Vm.tre()
                    t6.setImageResource(R.mipmap.t_icon17)
                } else {
                    t1Vm.tpa()
                    t6.setImageResource(R.mipmap.t_icon19)
                }
            }
            t7.setOnClickListener {
                t1Vm.tst()
                t7.isVisible = false
                t5.isVisible = true
                t6.isVisible = true
            }
        }
        with(t1Vm) {
            t3Time.observe(viewLifecycleOwner) {
                binding.t4.text = it.t0F()
                binding.t2.progress = t0P()
                if (it == 0L) {
                    refreshResult()
                }
            }
        }
    }

    private fun refreshResult() {
        with(binding) {
            t8.isVisible = true
            t9.setImageResource(R.mipmap.t_icon20)
            t10.text =
                if (t1Vm.t6Working)
                    "Great job! You've completed a focused 25-minute session. Take a short break before starting the next one!"
                else "Break time’s over! Ready to dive back into work? Let’s start the next session!"
            t11.text = if (t1Vm.t6Working) "Rest Mode" else "Work Mode"
            t11.setOnClickListener {
                t8.isVisible = false
                t2.progress = 0f
                t7.isVisible = false
                t5.isVisible = true
                t6.isVisible = true
                t6.setImageResource(R.mipmap.t_icon17)

                if (t1Vm.t6Working) {
                    t1Vm.tst(false)
                } else {
                    t1Vm.tst()
                }
            }
        }
    }
}