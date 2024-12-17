package com.todo.tomato.tools.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.todo.tomato.R
import com.todo.tomato.databinding.ItemT1Binding
import com.todo.tomato.tools.bean.T1Entity

class TypeAdapter : RecyclerView.Adapter<TypeAdapter.T2Vh>() {

    private val t0Types = arrayListOf(
        T1Entity(
            type = "Work",
            backSource = R.drawable.t8
        ),
        T1Entity(
            type = "Study",
            backSource = R.drawable.t9
        ),
        T1Entity(
            type = "Health",
            backSource = R.drawable.t10
        ),
        T1Entity(
            type = "Social",
            backSource = R.drawable.t11
        ),
        T1Entity(
            type = "Family",
            backSource = R.drawable.t12
        ),
        T1Entity(
            type = "Emergency",
            backSource = R.drawable.t13
        ),
        T1Entity(
            type = "Entertainment",
            backSource = R.drawable.t14
        ),
        T1Entity(
            type = "Personal",
            backSource = R.drawable.t15
        ),
        T1Entity(
            type = "Other",
            backSource = R.drawable.t16
        ),
    )

    var selectType = ""

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): T2Vh {
        return T2Vh(ItemT1Binding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun getItemCount(): Int {
        return t0Types.size
    }

    override fun onBindViewHolder(holder: T2Vh, position: Int) {
        val data = t0Types[position]
        with(holder.binding) {
            t0.setBackgroundResource(data.backSource)
            t1.isVisible = data.type == selectType
            t2.text = data.type
            root.setOnClickListener {
                selectType = data.type
                notifyDataSetChanged()
            }
        }
    }

    inner class T2Vh(val binding: ItemT1Binding) : RecyclerView.ViewHolder(binding.root)
}