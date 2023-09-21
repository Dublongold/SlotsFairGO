package com.ssslotssfair.gopokiess.puzzles

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import com.ssslotssfair.gopokiess.R
import com.ssslotssfair.gopokiess.navigation.PuzzleWithNavigation
import com.ssslotssfair.gopokiess.navigation.NavigationArguments

class PrivacyPuzzle(arguments: NavigationArguments? = null): PuzzleWithNavigation(arguments) {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.privacy_puzzle, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        view.findViewById<ImageButton>(R.id.back_clicker).setOnClickListener {
            popBackStack()
        }
    }
}