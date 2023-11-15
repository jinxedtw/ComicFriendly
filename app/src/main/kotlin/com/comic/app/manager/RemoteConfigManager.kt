package com.comic.app.manager

import android.app.Activity
import android.util.Log
import com.google.android.gms.tasks.Task
import com.google.firebase.ktx.Firebase
import com.google.firebase.remoteconfig.ConfigUpdate
import com.google.firebase.remoteconfig.ConfigUpdateListener
import com.google.firebase.remoteconfig.FirebaseRemoteConfigException
import com.google.firebase.remoteconfig.ktx.remoteConfig

object RemoteConfigManager {
    private const val TAG = "remote_config"

    private const val KEY = ""

    fun initRemoteConfig(activity: Activity) {
        Firebase.remoteConfig.fetchAndActivate().addOnCompleteListener(activity) { task: Task<Boolean> ->
            handleConfig(task)
        }
        Firebase.remoteConfig.addOnConfigUpdateListener(object : ConfigUpdateListener {
            override fun onUpdate(configUpdate: ConfigUpdate) {
                Firebase.remoteConfig.activate().addOnCompleteListener { task -> handleConfig(task) }
            }

            override fun onError(error: FirebaseRemoteConfigException) {
                Log.e(TAG, "Config update error with code: " + error.code, error)
            }
        })
    }

    fun handleConfig(task: Task<Boolean>) {
        if (task.isSuccessful) {
            try {
                val defaultConfig: String = Firebase.remoteConfig.getString(KEY)

                if (defaultConfig.isNotEmpty()) {
                    Log.i(TAG, "获取到RemoteConfig:$defaultConfig")
                } else {
                    Log.i(TAG, "apiConfig error")
                }
            } catch (e: Exception) {
                e.printStackTrace()
                Log.i(TAG, "apiConfig error ${e.message}")
            }
        } else {
            Log.i(TAG, "firebase config error")
        }
    }
}