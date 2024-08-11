package com.example.kongsikereta.ui.Rides

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
import javax.inject.Inject

@HiltViewModel
class ViewRideViewModel @Inject constructor(private val userRepository: UserRepository) :
    ViewModel() {
    private val _viewRidesUiState = MutableStateFlow(ViewRidesUiState())
    val viewRidesUiState: StateFlow<ViewRidesUiState> = _viewRidesUiState.asStateFlow()

    var isDataFetched = _viewRidesUiState.value.ridesList.isNotEmpty()

    fun loadRides() {
        val userId = PreferencesManager.getStringPreference("userId")
        viewModelScope.launch {
            _viewRidesUiState.update {
                ViewRidesUiState(
                    userRepository.fetchRides(userId)
                )
            }
        }
    }

}

data class ViewRidesUiState(
    val ridesList: List<Rides> = emptyList()
)