package com.se.dr.bo.seconddrawingboard.base

import android.app.Activity
import android.app.Application
import android.content.Intent
import android.os.Bundle
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.OnLifecycleEvent
import androidx.lifecycle.ProcessLifecycleOwner
import com.se.dr.bo.seconddrawingboard.ui.start.StartActivity
import com.tencent.mmkv.MMKV
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class App : Application(), LifecycleObserver {
    private var flag = 0
    private var job_sdb: Job? = null
    private var top_activity_sdb: Activity? = null
    override fun onCreate() {
        super.onCreate()
        MMKV.initialize(this)
        ProcessLifecycleOwner.get().lifecycle.addObserver(this)
        setActivityLifecyclesdb(this)
    }

    companion object {
        var isBackDatasdb = false
        var whetherBgdb = false
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_START)
    fun onMoveToForeground() {
        job_sdb?.cancel()
        job_sdb = null
        if (whetherBgdb && !isBackDatasdb) {
            jumpGuidePagesdb()
        }
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
    fun onStopState() {
        job_sdb = GlobalScope.launch {
            whetherBgdb = false
            delay(3000L)
            whetherBgdb = true
        }
    }

    private fun jumpGuidePagesdb() {
        whetherBgdb = false
        val intent = Intent(top_activity_sdb, StartActivity::class.java)
        top_activity_sdb?.startActivity(intent)
    }

    fun setActivityLifecyclesdb(application: Application) {
        application.registerActivityLifecycleCallbacks(object : ActivityLifecycleCallbacks {
            override fun onActivityCreated(activity: Activity, savedInstanceState: Bundle?) {
                top_activity_sdb = activity
            }

            override fun onActivityStarted(activity: Activity) {
                top_activity_sdb = activity
                flag++
                isBackDatasdb = false
            }

            override fun onActivityResumed(activity: Activity) {
                top_activity_sdb = activity
            }

            override fun onActivityPaused(activity: Activity) {
                top_activity_sdb = activity
            }

            override fun onActivityStopped(activity: Activity) {
                flag--
                if (flag == 0) {
                    isBackDatasdb = true
                }
            }

            override fun onActivitySaveInstanceState(activity: Activity, outState: Bundle) {
            }

            override fun onActivityDestroyed(activity: Activity) {
                top_activity_sdb = null
            }
        })
    }
}