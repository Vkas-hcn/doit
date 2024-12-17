package com.todo.tomato.tools

import com.todo.tomato.BuildConfig
import org.json.JSONObject
import java.util.Locale
import java.util.UUID

var t0P = 0L


fun Long.t0F(): String {
    val f = this / 1000 / 60
    val m = this / 1000 % 60
    return "${if (f < 10) "0${f}" else f}:${if (m < 10) "0${m}" else m}"
}

fun t1F(y: Long, m: Long, d: Long): String {
    return "${if (d < 10) "0${d}" else d} ${m.toInt().t2F()} $y"
}

fun Int.t2F(): String {
    return when (this) {
        1 -> "January"
        2 -> "February"
        3 -> "March"
        4 -> "April"
        5 -> "May"
        6 -> "June"
        7 -> "July"
        8 -> "August"
        9 -> "September"
        10 -> "October"
        11 -> "November"
        12 -> "December"
        else -> ""
    }
}


val tbaNormal: JSONObject
    get() {
        return JSONObject().apply {
            put("meat", JSONObject().apply {
                put("uphill", "01")
                put("eldest", Locale.getDefault().language + "_" + Locale.getDefault().country)
                put("remnant", "01")
            })
            put("denture", JSONObject().apply {
                put("feldman", BuildConfig.VERSION_NAME)
                put("nile", UUID.randomUUID())
                put("eta", "spheroid")
            })
            put("slater", JSONObject().apply {
                put("homonym", "01")
                put("fatty", System.currentTimeMillis().toString())
                put("bartok", "01")
            })
            put("megabyte", JSONObject().apply {
                put("expiable", "01")
                put("gumdrop", "com.planner.todolist.ess")
                put("regret", commonDistinctId)
            })
        }
    }

val tbaAddress = if (BuildConfig.DEBUG) {
    "https://test-forest.plannertodolist.com/syllabi/compton/birthday"
} else {
    "https://forest.plannertodolist.com/piss/drawn"
}