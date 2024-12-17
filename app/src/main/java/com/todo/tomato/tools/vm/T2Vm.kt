package com.todo.tomato.tools.vm

import androidx.lifecycle.ViewModel
import com.todo.tomato.tools.T0App
import com.todo.tomato.tools.bean.T0Entity

class T2Vm : ViewModel() {

    var t0Y: Long = 0
    var t0M: Long = 0
    var t0D: Long = 0

    fun put(entity: T0Entity) {
        T0App.t0Db.boxFor(T0Entity::class.java).put(entity)
    }

    fun update(entity: T0Entity) {
        T0App.t0Db.boxFor(T0Entity::class.java).put(entity)
    }
}