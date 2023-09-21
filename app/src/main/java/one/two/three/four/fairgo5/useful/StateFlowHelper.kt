package one.two.three.four.fairgo5.useful

import androidx.appcompat.app.AppCompatActivity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlin.concurrent.thread

class StateFlowHelper {
    fun<T> observeValue(data: StateFlow<T>, whatToDo: (T) -> Unit): Job {
        return CoroutineScope(Dispatchers.Main).launch {
            data.collect(whatToDo)
        }
    }
}