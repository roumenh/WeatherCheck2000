package com.example.weathercheck2000.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.weathercheck2000.ui.about.AboutScreen
import com.example.weathercheck2000.ui.addCity.AddCityScreen
import com.example.weathercheck2000.ui.addCity.AddCityViewModel
import com.example.weathercheck2000.ui.cityDetail.CityDetailScreen
import com.example.weathercheck2000.ui.cityDetail.CityDetailViewModel
import com.example.weathercheck2000.ui.gallery.GalleryScreen
import com.example.weathercheck2000.ui.gallery.GalleryViewModel
import com.example.weathercheck2000.ui.home.HomeScreen
import com.example.weathercheck2000.ui.home.HomeViewModel
import org.koin.androidx.compose.koinViewModel

enum class Screen {
    HOME,
    ADD_CITY,
    CITY,
    GALLERY,
    ABOUT
}

sealed class NavigationItem(val route: String) {
    data object Home : NavigationItem(Screen.HOME.name)
    data object CityDetail : NavigationItem(Screen.CITY.name)
    data object AddCity : NavigationItem(Screen.ADD_CITY.name)
    data object Gallery : NavigationItem(Screen.GALLERY.name)
    data object About : NavigationItem(Screen.ABOUT.name)
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

        // -------- HOME -----------------
        composable(NavigationItem.Home.route) {


            val viewModel: HomeViewModel = koinViewModel()
            val uiState by viewModel.uiState.collectAsState()

            HomeScreen(
                onCityClicked = { navController.navigate(NavigationItem.CityDetail.route + "/" + it.toString()) },
                onOpenGalleryClicked = { navController.navigate(NavigationItem.Gallery.route) },
                onAboutClicked = { navController.navigate(NavigationItem.About.route) },
                onAddCityClicked = { navController.navigate(NavigationItem.AddCity.route) },
                uiState = uiState
            )
        }

        // -------- ADD CITY -----------------
        composable(NavigationItem.AddCity.route) {

            val viewModel: AddCityViewModel = koinViewModel()
            // val uiState by viewModel.uiState.collectAsState()

            AddCityScreen(
                onAddCity = { name, lat, lon ->
                    if (viewModel.addCity(name, lat, lon)) {
                        navController.navigate(NavigationItem.Home.route)
                    } //TODO else show error somehow :) need to improve the view Model and UiState :)
                },
                onBackPressed = { navController.popBackStack() }
            )
        }

        // -------- CITY DETAIL -----------------
        composable(NavigationItem.CityDetail.route + "/{cityId}") { backStackEntry ->
            val cityId = backStackEntry.arguments?.getString("cityId")?.toInt() //Todo optimize

            val viewModel: CityDetailViewModel = koinViewModel()
            val uiState by viewModel.uiState.collectAsState()
            val allCities by viewModel.allCities.collectAsState()

            if (cityId != null) {
                CityDetailScreen(
                    uiState = uiState,
                    initialCityId = cityId,
                    listOfAllCities = allCities,
                    onBackPressed = { navController.popBackStack() },
                    onDeleteCityClicked = {
                        viewModel.deleteCity(cityId)
                        //Eventually here can be some result check
                        navController.popBackStack()
                    },
                    fetchDataForCityId = { viewModel.fetchWeatherDataForCity(it) }
                )
            }
        }

        // -------- GALLERY -----------------
        composable(NavigationItem.Gallery.route) {

            val viewModel: GalleryViewModel = koinViewModel()
            val uiState by viewModel.uiState.collectAsState()

            GalleryScreen(
                uiState = uiState,
                onBackPressed = { navController.popBackStack() }
            )

        }

        // ---------- ABOUT (Settings) -----------------
        composable(NavigationItem.About.route){

            AboutScreen(
                onBackPressed = { navController.popBackStack() }
            )

        }
    }
}