package com.ssslotssfair.gopokiess

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.WindowManager
import com.ssslotssfair.gopokiess.useful.ActivityIntentCreator
import com.ssslotssfair.gopokiess.useful.GetOutdoorData
import com.ssslotssfair.gopokiess.useful.StateFlowHelper
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow

class StartImportant : AppCompatActivity() {
    private val dataState = MutableStateFlow(DataReceivingState.IN_PROCESS)
    private val stateFlowHelper = StateFlowHelper()
    private lateinit var dateStateJob: Job

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.important_start)

        window.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS)

        dateStateJob = stateFlowHelper.observeValue(dataState) {
            when(it) {
                DataReceivingState.OK -> {
                    Log.i(DataReceivingState.LOG_TAG, "OK")
                    startActivity(ActivityIntentCreator.online(this))
                    finish()
                }
                DataReceivingState.BAD -> {
                    Log.i(DataReceivingState.LOG_TAG, "BAD")
                    startActivity(ActivityIntentCreator.offline(this))
                    finish()
                }
                else -> Log.i(DataReceivingState.LOG_TAG, it.toString())
            }
        }
        val getOutdoorData = GetOutdoorData()

        getOutdoorData.initializeOneSignal(applicationContext, ONE_SIGNAL_ID)
        getOutdoorData.getDataFromFirebase(applicationContext) {
            if (it == null) {
                dataState.value = DataReceivingState.BAD
            } else {
                dataState.value = DataReceivingState.OK
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        dateStateJob.cancel()
    }

    enum class DataReceivingState {
        OK,
        BAD,
        IN_PROCESS;

        companion object {
            const val LOG_TAG = "Data state"
        }
    }
    companion object {
        const val ONE_SIGNAL_ID = "9266eecb-ae80-4d3c-a30b-b215057b00da"
    }
}