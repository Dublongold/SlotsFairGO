package com.ssslotssfair.gopokiess.puzzles

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import com.ssslotssfair.gopokiess.R
import com.ssslotssfair.gopokiess.logic.spin
import com.ssslotssfair.gopokiess.navigation.NavigationArguments
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class SecondGamePuzzle(arguments: NavigationArguments? = null): GamePuzzle(arguments) {


    override val valuesMap = mapOf(
        "bank" to MutableStateFlow(args!!.getInteger("bank", 99999)),
        "win" to MutableStateFlow(args.getInteger("win", 0)),
        "bet" to MutableStateFlow(args.getInteger("bet", 100)),
        "rotation" to MutableStateFlow(0)
        )

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.second_game_puzzle, container, false)
    }

    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        view.findViewById<Button>(R.id.spin_clicker).setOnClickListener {
            it.isEnabled = false
            CoroutineScope(Dispatchers.Main).launch {
                spin()
            }
        }
        jobsList.add(stateFlowHelper.observeValue(valuesMap["rotation"]!!) {
            view.findViewById<ImageView>(R.id.wheel_body).rotation = it.toFloat()
        })
    }

    companion object {
        val FOR_RANDOM = listOf(1, 3, 6, 4, 8, 5, 9, 10, 15, 13, 14)
    }
}
