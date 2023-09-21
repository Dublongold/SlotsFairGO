package com.ssslotssfair.gopokiess.useful

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class StateFlowHelper {
    fun<T> observeValue(data: StateFlow<T>, whatToDo: (T) -> Unit): Job {
        return CoroutineScope(Dispatchers.Main).launch {
            data.collect(whatToDo)
        }
    }
}