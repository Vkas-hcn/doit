package com.todo.tomato.ui.dialogs

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.todo.tomato.databinding.DialogT0Binding
import com.todo.tomato.tools.RandomTasks
import com.todo.tomato.tools.getRandomTask
import com.todo.tomato.tools.twoBack

class T0Dialog(private val save: ((String, String) -> Unit)) : DialogFragment() {

    private lateinit var binding: DialogT0Binding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DialogT0Binding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        with(binding) {
            val type = RandomTasks.random()
            t1.text = type
            t1.setBackgroundResource(type.twoBack())
            t3.text = type.getRandomTask()
            t2.setOnClickListener {
                val t = RandomTasks.random()
                t1.setBackgroundResource(t.twoBack())
                t1.text = t
                t3.text = t.getRandomTask()
            }
            t5.setOnClickListener {
                dismiss()
            }
            t6.setOnClickListener {
                save(t1.text.toString(), t3.text.toString())
                dismiss()
            }
        }
    }
}