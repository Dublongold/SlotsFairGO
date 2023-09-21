package com.ssslotssfair.gopokiess

import android.os.Bundle
import android.util.Log
import android.view.WindowManager
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AppCompatActivity
import com.ssslotssfair.gopokiess.puzzles.SelectGamePuzzle
import com.ssslotssfair.gopokiess.navigation.PuzzleWithNavigation
import com.ssslotssfair.gopokiess.navigation.NavigationArguments
import com.ssslotssfair.gopokiess.navigation.NavigationBackStackEntry
import java.util.Stack

class OfflineGameImportant: AppCompatActivity() {
    val navigationBackStack: Stack<NavigationBackStackEntry> = Stack()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.important_offline)

        window.setFlags(
            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS)

        val startFragment = SelectGamePuzzle(
            NavigationArguments(
                mutableMapOf("bank" to getSharedPreferences("shared_data", MODE_PRIVATE)
                    .getInt("bank", 10000))
            )
        )

        navigationBackStack.push(
            NavigationBackStackEntry(R.layout.select_game_puzzle, null, startFragment)
        )

        supportFragmentManager.beginTransaction()
            .add(PuzzleWithNavigation.CONTAINER, startFragment)
            .commitNow()

        onBackPressedDispatcher.addCallback(object: OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                Log.i("On back pressed", "Work!")
                navigationBackStack.lastElement().fragment.popBackStack()
            }
        })
    }
}