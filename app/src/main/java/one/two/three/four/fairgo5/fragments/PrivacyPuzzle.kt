package one.two.three.four.fairgo5.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import one.two.three.four.fairgo5.R
import one.two.three.four.fairgo5.navigation.FragmentWithNavigation
import one.two.three.four.fairgo5.navigation.NavigationArguments

class PrivacyPuzzle(arguments: NavigationArguments? = null): FragmentWithNavigation(arguments) {

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