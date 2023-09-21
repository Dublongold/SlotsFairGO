package com.ssslotssfair.gopokiess.navigation

data class NavigationBackStackEntry (
    val destination: Int,
    val arguments: NavigationArguments?,
    val fragment: PuzzleWithNavigation
)