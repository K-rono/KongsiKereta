package com.example.kongsikereta.ui.login

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp

@Composable
fun LoginScreen(
    loginScreenViewModel: LoginScreenViewModel
){
    val uiState = loginScreenViewModel.loginUiState.collectAsState()

    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "Login", fontWeight = FontWeight.Bold)
        Spacer(modifier = Modifier.height(8.dp))
        TextField(value = uiState.value.userId, onValueChange = {loginScreenViewModel.updateId(it)}, label = { Text(
            text = "Identification Number"
        )})
        Spacer(modifier = Modifier.height(8.dp))
        TextField(value = uiState.value.password, onValueChange = {loginScreenViewModel.updatePassword(it)}, label = { Text(
            text = "Password"
        )}, visualTransformation = PasswordVisualTransformation())
        Spacer(modifier = Modifier.height(8.dp))
        Row {
            Button(onClick = { /*TODO*/ }) {
                Text(text = "Login")
            }
            Spacer(modifier = Modifier.width(8.dp))
            Button(onClick = { /*TODO*/ }) {
                Text(text = "Not A User?")
            }
        }
    }
}
