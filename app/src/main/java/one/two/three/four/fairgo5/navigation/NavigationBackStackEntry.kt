package one.two.three.four.fairgo5.navigation

data class NavigationBackStackEntry (
    val destination: Int,
    val arguments: NavigationArguments?,
    val fragment: FragmentWithNavigation
)