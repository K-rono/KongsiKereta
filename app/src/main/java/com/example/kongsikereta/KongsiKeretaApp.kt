package com.example.kongsikereta

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.internal.composableLambda
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.kongsikereta.ui.login.LoginScreen
import com.example.kongsikereta.ui.login.LoginScreenViewModel
import com.example.kongsikereta.ui.login.RegisterScreen
import com.example.kongsikereta.ui.login.RegisterScreenViewModel

enum class KongsiKeretaScreens() {
    LOGIN(),
    REGISTRATION(),
    DRIVER_PROFILE()
}


@Composable
fun KongsiKeretaApp(
    navController: NavHostController = rememberNavController(),
    modifier: Modifier
) {
    Scaffold { it ->
        val loginScreenViewModel: LoginScreenViewModel = hiltViewModel()
        val registerScreenViewModel: RegisterScreenViewModel = hiltViewModel()

        NavHost(
            navController = navController,
            startDestination = KongsiKeretaScreens.LOGIN.name,
            modifier = Modifier.padding(it)
        ) {
            composable(route = KongsiKeretaScreens.LOGIN.name){
                LoginScreen(loginScreenViewModel)
            }
            composable(route = KongsiKeretaScreens.REGISTRATION.name){
                RegisterScreen(registerScreenViewModel = registerScreenViewModel)
            }
        }
    }


}