package com.example.noviapp.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.tooling.preview.Preview

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginPage() {
    var name by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var genderExpanded by remember { mutableStateOf(false) }
    var gender by remember { mutableStateOf("Select your gender") }
    var cityExpanded by remember { mutableStateOf(false) }
    var city by remember { mutableStateOf("Select city of your Novi") }

    val backgroundColor = MaterialTheme.colorScheme.background
    val textColor = MaterialTheme.colorScheme.onBackground
    val fieldBorderColor = MaterialTheme.colorScheme.primary
    val buttonColor = MaterialTheme.colorScheme.primary
    val buttonTextColor = MaterialTheme.colorScheme.onPrimary

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(backgroundColor)
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "Welcome to Novi", color = textColor, fontSize = 24.sp, fontWeight = FontWeight.Bold)
        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = name,
            onValueChange = { name = it },
            label = { Text("Enter your name") },
            modifier = Modifier.fillMaxWidth(),
            colors = TextFieldDefaults.outlinedTextFieldColors(
                focusedBorderColor = fieldBorderColor,
                unfocusedBorderColor = fieldBorderColor,
                cursorColor = textColor,
            )
        )
        Spacer(modifier = Modifier.height(8.dp))

        OutlinedTextField(
            value = email,
            onValueChange = { email = it },
            label = { Text("Enter email") },
            modifier = Modifier.fillMaxWidth(),
            colors = TextFieldDefaults.outlinedTextFieldColors(
                focusedBorderColor = fieldBorderColor,
                unfocusedBorderColor = fieldBorderColor,
                cursorColor = textColor
            )
        )
        Spacer(modifier = Modifier.height(8.dp))

        ExposedDropdownMenuBox(expanded = genderExpanded, onExpandedChange = { genderExpanded = it }) {
            OutlinedTextField(
                value = gender,
                onValueChange = {},
                readOnly = true,
                label = { Text("Select your gender") },
                modifier = Modifier.fillMaxWidth().clickable { genderExpanded = true },
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    focusedBorderColor = fieldBorderColor,
                    unfocusedBorderColor = fieldBorderColor,
                    cursorColor = textColor
                )
            )
            ExposedDropdownMenu(expanded = genderExpanded, onDismissRequest = { genderExpanded = false }) {
                DropdownMenuItem(text = { Text("Male") }, onClick = {
                    gender = "Male"
                    genderExpanded = false
                })
                DropdownMenuItem(text = { Text("Female") }, onClick = {
                    gender = "Female"
                    genderExpanded = false
                })
            }
        }
        Spacer(modifier = Modifier.height(8.dp))

        ExposedDropdownMenuBox(expanded = cityExpanded, onExpandedChange = { cityExpanded = it }) {
            OutlinedTextField(
                value = city,
                onValueChange = {},
                readOnly = true,
                label = { Text("What do you want your Novi to be from?") },
                modifier = Modifier.fillMaxWidth().clickable { cityExpanded = true },
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    focusedBorderColor = fieldBorderColor,
                    unfocusedBorderColor = fieldBorderColor,
                    cursorColor = textColor
                )
            )
            ExposedDropdownMenu(expanded = cityExpanded, onDismissRequest = { cityExpanded = false }) {
                DropdownMenuItem(text = { Text("New York") }, onClick = {
                    city = "Delhi"
                    cityExpanded = false
                })
            }
        }
        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = {},
            colors = ButtonDefaults.buttonColors(containerColor = buttonColor),
            shape = RoundedCornerShape(50),
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(text = "Continue →", color = buttonTextColor)
        }
    }
}

@Preview
@Composable
fun PreviewLoginPage() {
    LoginPage()
}
