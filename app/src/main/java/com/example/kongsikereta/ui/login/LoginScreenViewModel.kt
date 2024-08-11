package com.example.kongsikereta.ui.login

import androidx.lifecycle.ViewModel
import com.example.kongsikereta.util.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class LoginScreenViewModel @Inject constructor(private val userRepository: UserRepository): ViewModel(){
    private val _loginUiState = MutableStateFlow(LoginUiState())
    val loginUiState = _loginUiState.asStateFlow()

    fun updateId(id: String){
        _loginUiState.update {
            currentState -> currentState.copy(userId = id)
        }
    }

    fun updatePassword(password: String){
        _loginUiState.update {
                currentState -> currentState.copy(password = password)
        }
    }

    fun updateAuthState(state: Boolean){
        _loginUiState.update {
                currentState -> currentState.copy(isAuthenticated = state)
        }
    }

}

data class LoginUiState(
    val userId: String = "",
    val password: String = "",
    val isAuthenticated: Boolean = false
)