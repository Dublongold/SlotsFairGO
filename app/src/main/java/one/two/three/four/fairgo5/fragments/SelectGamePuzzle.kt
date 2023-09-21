package one.two.three.four.fairgo5.fragments

import android.content.pm.ActivityInfo
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageButton
import one.two.three.four.fairgo5.R
import one.two.three.four.fairgo5.navigation.FragmentWithNavigation
import one.two.three.four.fairgo5.navigation.NavigationArguments

class SelectGamePuzzle(arguments: NavigationArguments? = null): FragmentWithNavigation(arguments) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requireActivity().apply {
            requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE
            requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_LOCKED
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.select_game_puzzle, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        findButtonAndSetClickListener(R.id.pokies_clicker) {
            navigate(R.layout.first_game_puzzle, args)
        }
        findButtonAndSetClickListener(R.id.roulette_clicker) {
            navigate(R.layout.second_game_puzzle, args)
        }
        view.findViewById<ImageButton>(R.id.privacy_clicker).setOnClickListener {
            navigate(R.layout.privacy_puzzle, args)
        }
    }

    private fun findButtonAndSetClickListener(buttonId: Int, onClickListener: (View) -> Unit) {
        view?.findViewById<Button>(buttonId)?.setOnClickListener(onClickListener)
    }
}