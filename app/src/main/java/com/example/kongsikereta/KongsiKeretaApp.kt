package com.example.kongsikereta

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.internal.composableLambda
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.kongsikereta.ui.Rides.CreateRideScreen
import com.example.kongsikereta.ui.Rides.CreateRideViewModel
import com.example.kongsikereta.ui.Rides.ViewRideScreen
import com.example.kongsikereta.ui.Rides.ViewRideViewModel
import com.example.kongsikereta.ui.driver.DriverProfileScreen
import com.example.kongsikereta.ui.driver.DriverProfileScreenViewModel
import com.example.kongsikereta.ui.login.LoginScreen
import com.example.kongsikereta.ui.login.LoginScreenViewModel
import com.example.kongsikereta.ui.login.RegisterScreen
import com.example.kongsikereta.ui.login.RegisterScreenViewModel
import com.example.kongsikereta.util.PreferencesManager

enum class KongsiKeretaScreens() {
    LOGIN(), REGISTRATION(), DRIVER_PROFILE(), CREATE_RIDE(), VIEW_RIDE(),
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun KongsiKeretaApp(
    navController: NavHostController = rememberNavController(), modifier: Modifier
) {
    val context = LocalContext.current
    PreferencesManager.init(context)
    val backStackEntry by navController.currentBackStackEntryAsState()
    val currentScreen = backStackEntry?.destination?.route
        ?: if (PreferencesManager.getBooleanPreference("isLoggedIn")) KongsiKeretaScreens.DRIVER_PROFILE.name else KongsiKeretaScreens.LOGIN.name

    Scaffold(topBar = { TopAppBar(title = { Text(text = "KongsiKereta") }) }, bottomBar = {
        if (currentScreen !in listOf(
                KongsiKeretaScreens.LOGIN.name, KongsiKeretaScreens.REGISTRATION.name
            )
        ) {
            BottomAppBar {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier.clickable { navController.navigate(route = KongsiKeretaScreens.DRIVER_PROFILE.name) }) {
                        Icon(
                            imageVector = Icons.Default.AccountCircle,
                            contentDescription = "Profile Page"
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(text = "Profile")
                    }
                    Column(horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier.clickable { navController.navigate(route = KongsiKeretaScreens.CREATE_RIDE.name) }) {
                        Icon(
                            imageVector = Icons.Default.AddCircle,
                            contentDescription = "Create Ride"
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(text = "Create Ride")
                    }
                    Column(horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier.clickable { navController.navigate(route = KongsiKeretaScreens.VIEW_RIDE.name) }) {
                        Icon(
                            imageVector = Icons.Default.LocationOn,
                            contentDescription = "View Rides"
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(text = "View Rides")
                    }
                }
            }
        }
    }) { it ->
        val loginScreenViewModel: LoginScreenViewModel = hiltViewModel()
        val registerScreenViewModel: RegisterScreenViewModel = hiltViewModel()
        val driverProfileScreenViewModel: DriverProfileScreenViewModel = hiltViewModel()
        val createRideViewModel: CreateRideViewModel = hiltViewModel()
        val viewRideViewModel: ViewRideViewModel = hiltViewModel()
        NavHost(
            navController = navController,
            startDestination = if(PreferencesManager.getBooleanPreference("isLoggedIn")) KongsiKeretaScreens.DRIVER_PROFILE.name else KongsiKeretaScreens.LOGIN.name,
            modifier = Modifier.padding(it)
        ) {
            composable(route = KongsiKeretaScreens.LOGIN.name) {
                LoginScreen(loginScreenViewModel,
                    registerClicked = { navController.navigate(KongsiKeretaScreens.REGISTRATION.name) },
                    loginMatched = { navController.navigate(KongsiKeretaScreens.DRIVER_PROFILE.name) })
            }
            composable(route = KongsiKeretaScreens.REGISTRATION.name) {
                RegisterScreen(registerScreenViewModel = registerScreenViewModel) { navController.navigateUp() }
            }
            composable(route = KongsiKeretaScreens.DRIVER_PROFILE.name) {
                DriverProfileScreen(driverProfileScreenViewModel = driverProfileScreenViewModel){
                    navController.navigate(KongsiKeretaScreens.LOGIN.name)
                }
            }
            composable(route = KongsiKeretaScreens.CREATE_RIDE.name){
                CreateRideScreen(createRideViewModel = createRideViewModel) {
                    navController.navigate(route = KongsiKeretaScreens.VIEW_RIDE.name)
                }
            }
            composable(route = KongsiKeretaScreens.VIEW_RIDE.name){
                ViewRideScreen(viewRideViewModel = viewRideViewModel)
            }

        }
    }


}