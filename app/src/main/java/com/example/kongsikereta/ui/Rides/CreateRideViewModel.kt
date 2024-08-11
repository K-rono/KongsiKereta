package com.example.kongsikereta.ui.Rides

import android.content.Context
import android.widget.Toast
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.kongsikereta.data.Rides
import com.example.kongsikereta.util.PreferencesManager
import com.example.kongsikereta.util.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import okhttp3.Callback
import java.util.Date
import javax.inject.Inject

@HiltViewModel
class CreateRideViewModel @Inject constructor(private val userRepository: UserRepository) :
    ViewModel() {
    private val _createRideScreenUiState = MutableStateFlow(createRideScreenUiState())
    val createRideScreenUiState: StateFlow<createRideScreenUiState> =
        _createRideScreenUiState.asStateFlow()

    fun clearUiState(){
        _createRideScreenUiState.update {
            createRideScreenUiState()
        }
    }
    fun updateDate(date: Long) {
        _createRideScreenUiState.update { currentState ->
            currentState.copy(date = date)
        }
    }

    fun updateTime(time: String) {
        _createRideScreenUiState.update { currentState ->
            currentState.copy(time = time)
        }
    }

    fun updateOrigin(origin: String) {
        _createRideScreenUiState.update { currentState ->
            currentState.copy(origin = origin)
        }
    }

    fun updateDestination(destination: String) {
        _createRideScreenUiState.update { currentState ->
            currentState.copy(destination = destination)
        }
    }

    fun updateFare(fare: Float) {
        _createRideScreenUiState.update { currentState ->
            currentState.copy(fare = fare)
        }
    }

    fun updateDatePickerStatus(status: Boolean) {
        _createRideScreenUiState.update { currentState ->
            currentState.copy(isDatePickerExpanded = status)
        }
    }

    fun updateTimePickerStatus(status: Boolean) {
        _createRideScreenUiState.update { currentState ->
            currentState.copy(isTimePickerExpanded = status)
        }
    }

    fun createRide(context: Context, callback: (Boolean) -> Unit) {
        var status = false
        val value = _createRideScreenUiState.value
        if(value.time == "" || value.origin == "" || value.destination == "" || value.fare == 0f ){
            Toast.makeText(context, "Error, Fields Empty", Toast.LENGTH_LONG).show()
            callback(false)
            return
        }
        val userId = PreferencesManager.getStringPreference("userId")
        val rideInfo = Rides(
            date = value.date,
            time = value.time,
            origin = value.origin,
            destination = value.destination,
            fare = value.fare,
            userId = userId
        )
        viewModelScope.launch {
            userRepository.createRide(rideInfo, context) { success ->
                status = success
            }
        }.invokeOnCompletion { callback(status) }
    }

}

data class createRideScreenUiState(
    val date: Long = Date().time,
    val time: String = "",
    val origin: String = "",
    val destination: String = "",
    val fare: Float = 0f,
    val isDatePickerExpanded: Boolean = false,
    val isTimePickerExpanded: Boolean = false,
)