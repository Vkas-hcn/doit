package com.todo.tomato.tools.vm

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.todo.tomato.tools.T0App
import com.todo.tomato.tools.bean.T0Entity
import com.todo.tomato.tools.bean.T0Entity_
import com.todo.tomato.tools.distinctId
import com.todo.tomato.tools.tbaAddress
import com.todo.tomato.tools.tbaNormal
import fuel.Fuel
import fuel.post
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.json.JSONObject
import java.text.SimpleDateFormat


class T1Vm : ViewModel() {

    var t0Y: Long = 0
    var t0M: Long = 0
    var t0D: Long = 0

    private val t0Time = 25 * 60 * 1000L
    private val t1Time = 5 * 60 * 1000L

    private var t2Time = 0L
    val t3Time: MutableLiveData<Long> = MutableLiveData()
    var t4 = false
    private var t5: Job? = null

    var t6Working = true
    fun t0P(): Float {
        val b = if (t6Working) t0Time else t1Time
        return ((b * 1.0 - t2Time) / b).toFloat() * 100f
    }

    fun tst(isWork: Boolean = true) {
        t6Working = isWork
        t2Time = if (isWork) t0Time else t1Time
        tsp()
        t5 = CoroutineScope(Dispatchers.IO).launch {
            while (true) {
                if (!t4) {
                    if (t2Time >= 0L) {
                        t2Time -= 1000L
                        t3Time.postValue(t2Time)
                    }
                }
                delay(1000)
            }
        }
    }

    fun tpa() {
        t4 = true
    }

    fun tre() {
        t4 = false
    }

    fun tsp() {
        t4 = false
        t5?.cancel()
    }


    val t0ds: MutableLiveData<List<T0Entity>> = MutableLiveData()


    var tab = "todo"
    fun get() {
        if (tab == "todo") {
            tga()
        } else {
            getAllFinish()
        }
    }

    private fun getAllFinish() {
        t0ds.postValue(
            T0App.t0Db.boxFor(T0Entity::class.java).query()
                .equal(T0Entity_.year, t0Y)
                .equal(T0Entity_.month, t0M)
                .equal(T0Entity_.day, t0D)
                .equal(T0Entity_.finish, true).orderDesc(T0Entity_.finishTime).build()
                .find()
        )
    }


    fun tgc(): Long {
        return T0App.t0Db.boxFor(T0Entity::class.java).query()
            .equal(T0Entity_.year, t0Y)
            .equal(T0Entity_.month, t0M)
            .equal(T0Entity_.day, t0D)
            .build()
            .count()
    }

    fun tgb(): Long {
        return T0App.t0Db.boxFor(T0Entity::class.java).query()
            .equal(T0Entity_.year, t0Y)
            .equal(T0Entity_.month, t0M)
            .equal(T0Entity_.day, t0D)
            .equal(T0Entity_.finish, true)
            .build()
            .count()
    }

    private fun tga() {
        t0ds.postValue(
            T0App.t0Db.boxFor(T0Entity::class.java).query()
                .equal(T0Entity_.year, t0Y)
                .equal(T0Entity_.month, t0M)
                .equal(T0Entity_.day, t0D)
                .equal(T0Entity_.finish, false)
                .orderDesc(T0Entity_.createTime).build()
                .find()
        )
    }

    fun put(entity: T0Entity) {
        T0App.t0Db.boxFor(T0Entity::class.java).put(entity)
    }

    fun delete(id: Long) {
        T0App.t0Db.boxFor(T0Entity::class.java).remove(id)
    }

    fun update(entity: T0Entity) {
        T0App.t0Db.boxFor(T0Entity::class.java).put(entity)
    }


    private var homeSelfJob: Job? = null

    fun homePoint(retryTime: Int = 10) {
        homeSelfJob?.cancel()
        homeSelfJob = CoroutineScope(Dispatchers.IO).launch {
            runCatching {
                val p = tbaNormal.apply {
                    put("abound", "f_home")
                    put("tonal", JSONObject().apply {
                        put("con", distinctId)
                    })
                }.toString()
                Log.e("tbTest", "t1 P : ${p}")

                Fuel.post(
                    tbaAddress,
                    body = p,
                ).apply {
                    if (statusCode == 200) {
                        val resultTag = body.string()
                        Log.e("tbTest", resultTag)
                    } else {
                        Log.e("tbTest", "t1 error  P : ${statusCode}")
                        if (retryTime > 0) {
                            delay(2000)
                            homePoint(retryTime - 1)
                        }
                    }
                }
            }.onFailure {
                if (retryTime > 0) {
                    delay(2000)
                    homePoint(retryTime - 1)
                }
            }
        }
    }
}