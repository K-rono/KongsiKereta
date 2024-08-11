package com.example.kongsikereta.ui.login

import android.content.Context
import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.kongsikereta.data.UserDriver
import com.example.kongsikereta.data.Vehicle
import com.example.kongsikereta.util.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RegisterScreenViewModel @Inject constructor(private val userRepository: UserRepository) : ViewModel() {
    private val _registerScreenUiState = MutableStateFlow(RegisterScreenUiState())
    val registerScreenUiState = _registerScreenUiState.asStateFlow()

    private val _imageUri = MutableStateFlow<Uri?>(null)
    val imageUri = _imageUri.asStateFlow()

    fun updateIc(ic: String) {
        _registerScreenUiState.update { currentState ->
            currentState.copy(
                ic = ic
            )
        }
    }

    fun updatePassword(password: String) {
        _registerScreenUiState.update { currentState ->
            currentState.copy(
                password = password
            )
        }
    }

    fun updateGender(gender: Boolean) {
        _registerScreenUiState.update { currentState ->
            currentState.copy(
                gender = gender
            )
        }
    }

    fun updateEmail(email: String) {
        _registerScreenUiState.update { currentState ->
            currentState.copy(
                email = email
            )
        }
    }

    fun updateAddress(address: String) {
        _registerScreenUiState.update { currentState ->
            currentState.copy(
                address = address
            )
        }
    }

    fun updatePhoneNum(phoneNumber: String) {
        _registerScreenUiState.update { currentState ->
            currentState.copy(
                phoneNumber = phoneNumber
            )
        }
    }

    fun updateModel(model: String) {
        _registerScreenUiState.update { currentState ->
            currentState.copy(
                model = model
            )
        }
    }

    fun updateCapacity(capacity: Int) {
        _registerScreenUiState.update { currentState ->
            currentState.copy(
                capacity = capacity
            )
        }
    }

    fun updateNotes(notes: String) {
        _registerScreenUiState.update { currentState ->
            currentState.copy(
                notes = notes
            )
        }
    }

    fun updateGenderPickerState(state: Boolean) {
        _registerScreenUiState.update { currentState ->
            currentState.copy(
                isGenderPickerExpanded = state
            )
        }
    }

    fun updateImageUri(uri: Uri?) {
        _imageUri.value = uri
    }

    fun registerUser(context : Context, callback: (Boolean) -> Unit) {
        val value = _registerScreenUiState.value
        val userData = UserDriver(
            ic = value.ic,
            password = value.password,
            gender = value.gender,
            phoneNumber = value.phoneNumber,
            email = value.email,
            address = value.address,
            vehicle = Vehicle(
                model = value.model,
                capacity = value.capacity,
                notes = value.notes
            )
        )

        viewModelScope.launch {
            _imageUri.value?.let { userRepository.registerUser(it,userData,context) }
        }
    }
}

data class RegisterScreenUiState(
    val ic: String = "",
    val password: String = "",
    val gender: Boolean = false,
    val phoneNumber: String = "",
    val email: String = "",
    val address: String = "",
    val model: String = "",
    val capacity: Int = 0,
    val notes: String = "",
    val isGenderPickerExpanded: Boolean = false
)