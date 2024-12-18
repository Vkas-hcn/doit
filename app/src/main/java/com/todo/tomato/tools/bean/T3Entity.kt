package com.todo.tomato.tools.bean

import io.objectbox.annotation.Entity
import io.objectbox.annotation.Id

@Entity
data class T3Entity(
    @Id var id: Long = 0,
    var cmpState: String = "",
)
