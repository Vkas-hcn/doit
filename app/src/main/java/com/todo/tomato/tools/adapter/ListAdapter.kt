package com.todo.tomato.tools.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.todo.tomato.R
import com.todo.tomato.databinding.ItemT0Binding
import com.todo.tomato.tools.twoBack
import com.todo.tomato.tools.bean.T0Entity
import java.text.SimpleDateFormat

class T1Adapter : RecyclerView.Adapter<T1Adapter.T1Vh>() {

    private var list: List<T0Entity> = arrayListOf()
    var action: T0Inter? = null

    fun rl(data: List<T0Entity>) {
        list = data
        notifyDataSetChanged()
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): T1Vh {
        return T1Vh(ItemT0Binding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: T1Vh, position: Int) {
        val data = list[position]
        with(holder.binding) {
            t1.setImageResource(if (!data.finish) R.mipmap.t_icon6 else R.mipmap.t_icon10)
            t1.setOnClickListener {
                action?.finish(data)
            }
            t3.text = data.name
            t4.text = data.type
            t4.setBackgroundResource(data.type.twoBack())
            t5.text = if (data.finish) {
                SimpleDateFormat("yyyy.MM.dd").format(data.finishTime)
            } else {
                SimpleDateFormat("yyyy.MM.dd").format(data.createTime)
            }
            t2.setOnClickListener {
                action?.delete(data.id)
            }
            root.setOnClickListener {
                if (data.finish) {
                    return@setOnClickListener
                }
                action?.look(data)
            }
        }
    }

    inner class T1Vh(val binding: ItemT0Binding) : RecyclerView.ViewHolder(binding.root)
}

interface T0Inter {
    fun delete(id: Long)
    fun finish(entity: T0Entity)
    fun look(entity: T0Entity)
}