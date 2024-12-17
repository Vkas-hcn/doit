package com.todo.tomato.tools.vm

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.todo.tomato.BuildConfig
import com.todo.tomato.tools.bean.T2Entity
import com.todo.tomato.tools.T0App
import com.todo.tomato.tools.distinctId
import com.todo.tomato.tools.tbaAddress
import com.todo.tomato.tools.tbaNormal
import fuel.Fuel
import fuel.get
import fuel.post
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.json.JSONObject
import java.text.SimpleDateFormat

class T0Vm : ViewModel() {

    private var runJob: Job? = null

    private var canRun = true

    private var runStep = 0

    val t0Pro: MutableLiveData<Int> = MutableLiveData()

    fun start() {
        resume()
        runJob?.cancel()
        runJob = CoroutineScope(Dispatchers.IO).launch {
            while (true) {
                if (canRun) {
                    runStep += 1
                    if (runStep <= 100) {
                        t0Pro.postValue(runStep)
                    }
                }
                delay(100)
            }
        }
    }

    fun pause() {
        canRun = false
    }

    fun resume() {
        canRun = true
    }

    fun stop() {
        runJob?.cancel()
    }

    private var userJob: Job? = null

    fun getUserType() {
        var i = 0
        userJob?.cancel()
        if (!T0App.t0Db.boxFor(T2Entity::class.java).all.isNullOrEmpty()) {
            return
        } else {
            userJob = CoroutineScope(Dispatchers.IO).launch {
                runCatching {
                    i = 0
                    Fuel.get(
                        "https://altruism.plannertodolist.com/anthony/ira", parameters = listOf(
                            "eta" to "spheroid",
                            "gumdrop" to "com.planner.todolist.ess",
                            "fatty" to "${System.currentTimeMillis()}",
                            "feldman" to BuildConfig.VERSION_NAME,
                        )
                    ).apply {
                        if (statusCode == 200) {
                            i = 1
                            val resultTag = body.string()
                            T0App.t0Db.boxFor(T2Entity::class.java).put(T2Entity(value = resultTag))
                        }
                    }
                }.onFailure {
                    CoroutineScope(Dispatchers.IO).launch {
                        delay(10000L)
                        i += 1
                        getUserType()
                    }
                }
            }
        }
    }


    private var selfJob: Job? = null

    fun appPoint(retryTime: Int = 10) {
        selfJob?.cancel()
        selfJob = CoroutineScope(Dispatchers.IO).launch {
            runCatching {
                val p = tbaNormal.apply {
                    put("abound", "f_open")
                    put("tonal", JSONObject().apply {
                        put("con", distinctId)
                    })
                }.toString()
                Log.e("tbTest", "t0 P : ${p}")

                Fuel.post(
                    tbaAddress,
                    body = p,
                ).apply {
                    if (statusCode == 200) {
                        val resultTag = body.string()
                        Log.e("tbTest", resultTag)
                    } else {
                        Log.e("tbTest", "t0error ${statusCode}")
                        if (retryTime > 0) {
                            delay(2000)
                            appPoint(retryTime - 1)
                        }
                    }
                }
            }.onFailure {
                if (retryTime > 0) {
                    delay(2000)
                    appPoint(retryTime - 1)
                }
            }
        }
    }
}