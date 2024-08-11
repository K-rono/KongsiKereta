package com.example.kongsikereta.ui.Rides

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material3.Button
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TimePicker
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.material3.rememberTimePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.kongsikereta.util.DateConverter
import java.util.Calendar
import java.util.Date

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CreateRideScreen(
    createRideViewModel: CreateRideViewModel,
    onCreate: () -> Unit,
) {
    val context = LocalContext.current
    val scrollState = rememberScrollState()
    val uiState = createRideViewModel.createRideScreenUiState.collectAsState()

    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxSize()
            .padding(8.dp)
            .verticalScroll(scrollState)
    ) {
        Text(text = "Create Rides", fontWeight = FontWeight.Bold, fontSize = 40.sp)
        ElevatedCard {
            Column(
                modifier = Modifier.padding(8.dp)
            ) {
                Row {
                    TextField(
                        value = DateConverter.millisToFormattedDate(uiState.value.date),
                        onValueChange = {},
                        readOnly = true,
                        label = { Text(text = "Date")}
                    )
                    Spacer(modifier = Modifier.padding(8.dp))
                    IconButton(onClick = { createRideViewModel.updateDatePickerStatus(true) }) {
                        Icon(
                            imageVector = Icons.Default.DateRange,
                            contentDescription = "Date Picker"
                        )
                    }
                }
                if (uiState.value.isDatePickerExpanded) {
                    val datePickerState = rememberDatePickerState()

                    DatePickerDialog(
                        onDismissRequest = { createRideViewModel.updateDatePickerStatus(false) },
                        confirmButton = {
                            if (datePickerState.selectedDateMillis != null) {
                                createRideViewModel.updateDatePickerStatus(false)
                                createRideViewModel.updateDate(datePickerState.selectedDateMillis!!)
                            }
                        }) {
                        DatePicker(state = datePickerState, dateValidator = {
                            !Date(it).before(Date())
                        })

                    }
                }
            }
            Spacer(modifier = Modifier.height(16.dp))
            Row {
                TextField(value = uiState.value.time, onValueChange = {}, readOnly = true,label = { Text(text = "Time")})
                Spacer(modifier = Modifier.width(8.dp))
                IconButton(onClick = { createRideViewModel.updateTimePickerStatus(true) }) {
                    Icon(
                        imageVector = Icons.Default.KeyboardArrowDown,
                        contentDescription = "Time Picker"
                    )
                }
            }
            if (uiState.value.isTimePickerExpanded) {
                val currentTime = Calendar.getInstance()

                val timePickerState = rememberTimePickerState(
                    initialHour = currentTime.get(Calendar.HOUR_OF_DAY),
                    initialMinute = currentTime.get(Calendar.MINUTE),
                    is24Hour = true
                )

                Column() {
                    TimePicker(
                        state = timePickerState,
                    )
                    Button(onClick = { createRideViewModel.updateTimePickerStatus(false) }) {
                        Text("Dismiss")
                    }
                    Button(onClick = {
                        createRideViewModel.updateTimePickerStatus(false)
                        createRideViewModel.updateTime("${timePickerState.hour}:${timePickerState.minute}")
                    }) {
                        Text("Confirm")
                    }
                }

            }

        }
        Spacer(modifier = Modifier.height(16.dp))
        ElevatedCard {
            Column(modifier = Modifier.padding(8.dp)) {
                TextField(
                    value = uiState.value.origin,
                    onValueChange = { createRideViewModel.updateOrigin(it) },label = { Text(text = "Origin")})
                Spacer(modifier = Modifier.height(16.dp))
                TextField(
                    value = uiState.value.destination,
                    onValueChange = { createRideViewModel.updateDestination(it) },label = { Text(text = "Destination")})
                Spacer(modifier = Modifier.height(16.dp))
                TextField(value = uiState.value.fare.toString(), onValueChange = {
                    val value = it.toFloatOrNull()
                    if (value != null) {
                        createRideViewModel.updateFare(value)
                    }
                },label = { Text(text = "Fare(RM)")})

            }
        }
        Spacer(modifier = Modifier.height(64.dp))
        Button(onClick = {
            createRideViewModel.createRide(context) { status ->
                if (status) {
                    createRideViewModel.clearUiState()
                    onCreate()
                }
            }
        }) {
            Text(text = "Create Ride")
        }
    }
}