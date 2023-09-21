package com.ssslotssfair.gopokiess.navigation

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import com.ssslotssfair.gopokiess.puzzles.FirstGamePuzzle
import com.ssslotssfair.gopokiess.OfflineGameImportant
import com.ssslotssfair.gopokiess.puzzles.PrivacyPuzzle
import com.ssslotssfair.gopokiess.R
import com.ssslotssfair.gopokiess.puzzles.SecondGamePuzzle
import com.ssslotssfair.gopokiess.puzzles.SelectGamePuzzle
import java.util.Stack

/**
 * This is class for easier navigation between fragments.
 * @param arguments object with data, which you want share between fragments. If no data - set it null.
 */
abstract class PuzzleWithNavigation(arguments: NavigationArguments?): Fragment() {
    protected val args = arguments
    private lateinit var navigationBackStack: Stack<NavigationBackStackEntry>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val activity = requireActivity()
        if(activity is OfflineGameImportant) {
            navigationBackStack = activity.navigationBackStack
        }
        else {
            Log.w(LOG_TAG, "Current activity is not activity with navigation back stack. Back stack was installed with an empty stack.")
        }
    }

    fun navigate(destination: Int, arguments: NavigationArguments? = null) {
        val fragment = when(destination) {
            R.layout.select_game_puzzle -> SelectGamePuzzle(arguments)
            R.layout.first_game_puzzle -> FirstGamePuzzle(arguments)
            R.layout.second_game_puzzle -> SecondGamePuzzle(arguments)
            R.layout.privacy_puzzle -> PrivacyPuzzle(arguments)
            else -> null
        }
        if(fragment != null) {
            val fragmentManager = requireActivity().supportFragmentManager
            val transaction = fragmentManager.beginTransaction()
            transaction.replace(CONTAINER, fragment)
            navigationBackStack.push(
                NavigationBackStackEntry(destination, arguments, fragment)
            )
            Log.i(LOG_TAG, "Navigated. Current fragment: ${navigationBackStack.lastElement().fragment.javaClass.canonicalName}.(${navigationBackStack.size})")
            transaction.commitAllowingStateLoss()
        }
        else {
            Log.e(LOG_TAG, "Invalid destination. Given destination: $destination")
            requireActivity().finish()
        }
    }

    fun navigateWithAdd(destination: Int, arguments: NavigationArguments?) {
        val fragment = when(destination) {
            R.layout.select_game_puzzle -> SelectGamePuzzle(arguments)
            R.layout.first_game_puzzle -> FirstGamePuzzle(arguments)
            R.layout.second_game_puzzle -> SecondGamePuzzle(arguments)
            R.layout.privacy_puzzle -> PrivacyPuzzle(arguments)
            else -> null
        }
        if(fragment != null) {
            val fragmentManager = requireActivity().supportFragmentManager
            val transaction = fragmentManager.beginTransaction()
            transaction.addToBackStack(destination.toString())
            transaction.add(CONTAINER, fragment)
            Log.i(LOG_TAG, "Size before: ${navigationBackStack.size}")
            navigationBackStack.push(
                NavigationBackStackEntry(destination, arguments, fragment)
            )
            Log.i(LOG_TAG, "Size after: ${navigationBackStack.size}")
            Log.i(LOG_TAG, "Navigated with add. Current fragment: ${navigationBackStack.lastElement().fragment.javaClass.canonicalName}.(${navigationBackStack.size})")
            transaction.commitAllowingStateLoss()
        }
        else {
            Log.e(LOG_TAG, "Invalid destination. Given destination: $destination")
            requireActivity().finish()
        }
    }

    fun popBackStack() {
        if(navigationBackStack.size > 1) {
            val fragmentManager = requireActivity().supportFragmentManager
            val transaction = fragmentManager.beginTransaction()
            transaction.remove(navigationBackStack.pop().fragment)
            Log.i(LOG_TAG, "Popped back stack. Current fragment: ${navigationBackStack.lastOrNull()?.fragment?.javaClass?.name ?: SelectGamePuzzle::class.java.name}.")
            if(navigationBackStack.isNotEmpty()) {
                transaction.replace(CONTAINER, navigationBackStack.lastElement().fragment)
            }
            else {
                requireActivity().finish()
            }
            transaction.commitAllowingStateLoss()
        }
        else {
            Log.i(LOG_TAG, "Back stack is empty. Finish application.")
            requireActivity().finish()
        }
    }

    companion object {
        const val LOG_TAG = "Fragment navigation"
        val CONTAINER = R.id.fragmentContainer
    }
}