package com.example.kongsikereta.ui.Rides

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.kongsikereta.data.Rides
import com.example.kongsikereta.util.DateConverter

@Composable
fun ViewRideScreen(
    viewRideViewModel: ViewRideViewModel
) {
    if (!viewRideViewModel.isDataFetched) {
        LaunchedEffect(!viewRideViewModel.isDataFetched) {
            viewRideViewModel.loadRides()
        }
    }
    val uiState = viewRideViewModel.viewRidesUiState.collectAsState()

    LazyColumn(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.fillMaxWidth()
    ){
        items(uiState.value.ridesList) { ride ->
            Spacer(modifier = Modifier.height(16.dp))
            LazyColumnItem(ride = ride)
        }
    }
}

@Composable
fun LazyColumnItem(ride: Rides) {
    ElevatedCard {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Text(text = "Date  :  ${DateConverter.millisToFormattedDate(ride.date)}")
            Spacer(modifier = Modifier.height(8.dp))
            Text(text = "Time  :  ${ride.time}")
            Spacer(modifier = Modifier.height(8.dp))
            Text(text = "Origin  :  ${ride.origin}")
            Spacer(modifier = Modifier.height(8.dp))
            Text(text = "Destination  :  ${ride.destination}")
            Spacer(modifier = Modifier.height(8.dp))
            Text(text = "Fare(RM)  :  ${ride.fare}")
            Spacer(modifier = Modifier.height(8.dp))
        }
    }
}