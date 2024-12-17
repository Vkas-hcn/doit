package com.todo.tomato.tools.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.todo.tomato.ui.fragments.T0Fragment
import com.todo.tomato.ui.fragments.T1Fragment

class VpAdapter(t0: FragmentManager?, t1: Lifecycle?) : FragmentStateAdapter(t0!!, t1!!) {

    override fun createFragment(position: Int): Fragment {
        return if (position == 1) {
            T1Fragment()
        } else {
            T0Fragment()
        }
    }

    override fun getItemCount(): Int {
        return 2
    }
}