package one.two.three.four.fairgo5.fragments

import android.os.Bundle
import android.view.View
import android.widget.ImageButton
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatButton
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import one.two.three.four.fairgo5.R
import one.two.three.four.fairgo5.navigation.FragmentWithNavigation
import one.two.three.four.fairgo5.navigation.NavigationArguments
import one.two.three.four.fairgo5.useful.StateFlowHelper

open class GamePuzzle(arguments: NavigationArguments? = null): FragmentWithNavigation(arguments) {
    protected val stateFlowHelper = StateFlowHelper()

    protected val jobsList = mutableListOf<Job>()

    open val valuesMap = mapOf(
        "bank" to MutableStateFlow(args!!.getInteger("bank", 99999)),
        "win" to MutableStateFlow(args.getInteger("win", 0)),
        "bet" to MutableStateFlow(args.getInteger("bet", 100))
    )

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        jobsList.add(observeValueAndSetText(valuesMap["bank"]!!, R.id.bank_data))
        jobsList.add(observeValueAndSetText(valuesMap["bet"]!!, R.id.bet_data))
        jobsList.add(observeValueAndSetText(valuesMap["win"]!!, R.id.win_data))
        view.findViewById<ImageButton>(R.id.back_clicker).setOnClickListener {
            popBackStack()
        }
        for(element in valuesMap.entries) {
            jobsList.add(stateFlowHelper.observeValue(element.value) {
                args!!.setInteger(element.key, element.value.value)
            })
        }
        setBetButton(true) {
            val bet = valuesMap["bet"]
            if (bet != null) {
                bet.value += 10
            }
        }
        setBetButton(false) {
            val bet = valuesMap["bet"]
            if (bet != null && bet.value > 10) {
                bet.value -= 10
            }
        }
    }

    private fun setBetButton(isPlus: Boolean, action: (View) -> Unit) {
        val buttonId = if(isPlus) R.id.plus_clicker else R.id.minus_clicker
        view?.findViewById<AppCompatButton>(buttonId)?.setOnClickListener(action)
    }

    private fun<T> observeValueAndSetText(stateFlow: StateFlow<T>, textId: Int): Job {
        return stateFlowHelper.observeValue(stateFlow) {
            view?.findViewById<TextView>(textId)?.text = it.toString()
        }
    }

    override fun onPause() {
        super.onPause()
        requireActivity().getSharedPreferences("shared_data", AppCompatActivity.MODE_PRIVATE)
            .edit().putInt("bank", valuesMap["bank"]!!.value).apply()
    }

    override fun onDetach() {
        super.onDetach()
        for(job in jobsList) {
            job.cancel()
        }
        requireActivity().getSharedPreferences("shared_data", AppCompatActivity.MODE_PRIVATE)
            .edit().putInt("bank", valuesMap["bank"]!!.value).apply()
    }
}