package com.example.kongsikereta.ui.driver

import android.content.Context
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.kongsikereta.data.UserDriver
import com.example.kongsikereta.util.JsonHelper
import com.example.kongsikereta.util.PreferencesManager
import com.example.kongsikereta.util.UserRepository
import com.google.firebase.firestore.auth.User
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.io.File
import javax.inject.Inject

@HiltViewModel
class DriverProfileScreenViewModel @Inject constructor(private val userRepository: UserRepository): ViewModel(){
    private val _driverProfileUiState = MutableStateFlow(driverProfileUiState())
    val driverProfileUiState: StateFlow<driverProfileUiState> = _driverProfileUiState.asStateFlow()

    var isDataFetched = userRepository.currentUserDriver != null
    fun loadDriverProfile(context: Context){
        viewModelScope.launch {
            var value = userRepository.currentUserDriver
            if(isDataFetched == false){
                val userId = PreferencesManager.getStringPreference("userId")
                val password = PreferencesManager.getStringPreference("password")
                userRepository.signIn(userId,password,context){}
                value = userRepository.currentUserDriver
                Log.i("currentUser",value.toString())

            }
            if(value != null){
                val profilePic = value["profilePic"]
                val driver = JsonHelper.parseJson<UserDriver>(value["userData"]!!)

                _driverProfileUiState.update { currentState ->
                    currentState.copy(
                        driver = driver!!,
                        profilePic = profilePic
                    )
                }
            }
        }
    }

    fun logOut(){
        PreferencesManager.clearPreferences()
        userRepository.currentUserDriver = null
    }
}

data class driverProfileUiState(
    val driver: UserDriver = UserDriver(),
    val profilePic: File? = null,
)