package com.todo.tomato.tools.bean

import io.objectbox.annotation.Entity
import io.objectbox.annotation.Id


@Entity
data class T0Entity(
    @Id var id: Long = 0,
    var name: String,
    var note: String,
    var type: String,
    var createTime: Long,
    var finishTime: Long,
    var year: Long,
    var month: Long,
    var day: Long,
    var finish: Boolean,
)

