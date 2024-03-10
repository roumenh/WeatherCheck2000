package com.example.weathercheck2000.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.weathercheck2000.ui.cityDetail.CityDetailScreen
import com.example.weathercheck2000.ui.cityDetail.CityDetailViewModel
import com.example.weathercheck2000.ui.home.HomeScreen

enum class Screen {
    HOME,
    CITY,
}
sealed class NavigationItem(val route: String) {
    object Home : NavigationItem(Screen.HOME.name)
    object CityDetail : NavigationItem(Screen.CITY.name)
}

@Composable
fun AppNavHost(
    modifier: Modifier = Modifier,
    navController: NavHostController,
    startDestination: String = NavigationItem.Home.route,
) {
    NavHost(
        modifier = modifier,
        navController = navController,
        startDestination = startDestination
    ) {
        composable(NavigationItem.Home.route) {
            HomeScreen(
                onCityClicked = { navController.navigate(NavigationItem.CityDetail.route+"/"+it) }
            )
        }
        composable(NavigationItem.CityDetail.route+"/{city}") { backStackEntry ->
            val city = backStackEntry.arguments?.getString("city")

            val viewModel: CityDetailViewModel = androidx.lifecycle.viewmodel.compose.viewModel()
            val uiState by viewModel.uiState.collectAsState()

            CityDetailScreen(
                uiState = uiState,
                cityName = city ?: "",
            )
        }
    }
}