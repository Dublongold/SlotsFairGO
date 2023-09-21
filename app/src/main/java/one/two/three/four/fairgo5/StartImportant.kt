package one.two.three.four.fairgo5

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.WindowManager
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import one.two.three.four.fairgo5.useful.ActivityIntentCreator
import one.two.three.four.fairgo5.useful.GetOutdoorData
import one.two.three.four.fairgo5.useful.StateFlowHelper

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

        getOutdoorData.initializeOneSignal()
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
}