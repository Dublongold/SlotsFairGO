package com.ssslotssfair.gopokiess.puzzles

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import com.ssslotssfair.gopokiess.R
import com.ssslotssfair.gopokiess.logic.spin
import com.ssslotssfair.gopokiess.navigation.NavigationArguments
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class FirstGamePuzzle(arguments: NavigationArguments? = null): GamePuzzle(arguments) {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.first_game_puzzle, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        view.findViewById<Button>(R.id.spin_clicker).setOnClickListener {
            it.isEnabled = false
            CoroutineScope(Dispatchers.Main).launch {
                spin()
            }
        }
    }

    companion object {
        val POKIES_IMAGES = listOf(
            R.drawable.poky_01,
            R.drawable.poky_02,
            R.drawable.poky_03,
            R.drawable.poky_04,
            R.drawable.poky_05,
            R.drawable.poky_06,
            R.drawable.poky_07,
            R.drawable.poky_08,
            R.drawable.poky_09,
        )
    }
}