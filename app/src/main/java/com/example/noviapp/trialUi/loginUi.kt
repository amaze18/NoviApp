package com.example.noviapp.trialUi

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController

@OptIn(ExperimentalFoundationApi::class, ExperimentalMaterial3Api::class)
@Composable
fun LoginScreen(navController: NavController) {
    // Start with empty strings so that placeholder text is shown initially
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    Box(modifier = Modifier.fillMaxSize()) {
        // 1) Background: Gradient with Blur
        Box(
            modifier = Modifier
                .fillMaxSize()
                .blur(radius = 20.dp)
                .background(
                    brush = Brush.verticalGradient(
                        colors = listOf(
                            Color(0xFFFFE0E9), // Light Pink
                            Color(0xFFFDCEDF)  // Peach
                        )
                    )
                )
        )

        // 2) Foreground: Frosted Card
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Card(
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxWidth(0.9f)
                    .clip(RoundedCornerShape(24.dp)),
                elevation = CardDefaults.cardElevation(0.dp), // No shadow
                colors = CardDefaults.cardColors(
                    containerColor = Color.White.copy(alpha = 0.5f) // Frosted effect
                )
            ) {
                Column(
                    modifier = Modifier.padding(24.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    // Title Texts
                    Text(
                        text = "Welcome back to Novi",
                        fontWeight = FontWeight.Bold,
                        fontSize = 22.sp
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = "Login",
                        fontWeight = FontWeight.Bold,
                        fontSize = 18.sp
                    )
                    Spacer(modifier = Modifier.height(16.dp))

                    // Email & Password Fields
                    Column(modifier = Modifier.fillMaxWidth()) {
                        // Email Label
                        Text(
                            text = "Email Address",
                            fontWeight = FontWeight.Medium,
                            fontSize = 14.sp
                        )
                        OutlinedTextField(
                            value = email,
                            onValueChange = { email = it },
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(50.dp)
                                .clip(RoundedCornerShape(12.dp))
                                .background(Color.White),
                            shape = RoundedCornerShape(12.dp),
                            singleLine = true,
                            placeholder = { Text("projectmayhem@fc.com") },
                            colors = TextFieldDefaults.outlinedTextFieldColors(
                                // Use white container in all states
                                containerColor = Color.White,
//                                unfocusedContainerColor = Color.White,
//                                disabledContainerColor = Color.White,
                                errorContainerColor = Color.White,
                                // Remove outlines in all states
                                focusedBorderColor = Color.Transparent,
                                unfocusedBorderColor = Color.Transparent,
                                disabledBorderColor = Color.Transparent,
                                errorBorderColor = Color.Transparent,
                                // Text colors
                                focusedTextColor = Color.Black,
                                unfocusedTextColor = Color.Black,
                                disabledTextColor = Color.Black,
                                errorTextColor = Color.Black,
                                // Placeholder colors
                                focusedPlaceholderColor = Color.Gray,
                                unfocusedPlaceholderColor = Color.Gray,
                                disabledPlaceholderColor = Color.Gray,
                                errorPlaceholderColor = Color.Gray,
                                // Cursor
                                cursorColor = Color.Black
                            )
                        )

                        Spacer(modifier = Modifier.height(8.dp))

                        // Password Label
                        Text(
                            text = "Password",
                            fontWeight = FontWeight.Medium,
                            fontSize = 14.sp
                        )
                        OutlinedTextField(
                            value = password,
                            onValueChange = { password = it },
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(50.dp)
                                .clip(RoundedCornerShape(12.dp))
                                .background(Color.White),
                            shape = RoundedCornerShape(12.dp),
                            singleLine = true,
                            visualTransformation = PasswordVisualTransformation(),
                            placeholder = { Text("********") },
                            colors = TextFieldDefaults.outlinedTextFieldColors(
                                containerColor = Color.White,
//                                unfocusedContainerColor = Color.White,
//                                disabledContainerColor = Color.White,
                                errorContainerColor = Color.White,
                                focusedBorderColor = Color.Transparent,
                                unfocusedBorderColor = Color.Transparent,
                                disabledBorderColor = Color.Transparent,
                                errorBorderColor = Color.Transparent,
                                focusedTextColor = Color.Black,
                                unfocusedTextColor = Color.Black,
                                disabledTextColor = Color.Black,
                                errorTextColor = Color.Black,
                                focusedPlaceholderColor = Color.Gray,
                                unfocusedPlaceholderColor = Color.Gray,
                                disabledPlaceholderColor = Color.Gray,
                                errorPlaceholderColor = Color.Gray,
                                cursorColor = Color.Black
                            )
                        )

                        Spacer(modifier = Modifier.height(4.dp))

                        Text(
                            text = "Forgot Password?",
                            color = Color(0xFFE57373),
                            fontSize = 12.sp,
                            modifier = Modifier.align(Alignment.End)
                        )
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    // "Login" Button with Gradient Background
                    Button(
                        onClick = {
                            navController.navigate("bot")
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(48.dp)
                            .clip(RoundedCornerShape(12.dp)),
                        colors = ButtonDefaults.buttonColors(containerColor = Color.Transparent),
                        contentPadding = PaddingValues()
                    ) {
                        Box(
                            modifier = Modifier
                                .fillMaxSize()
                                .background(
                                    brush = Brush.horizontalGradient(
                                        listOf(
                                            Color(0xFFFF8A65),
                                            Color(0xFFBA68C8)
                                        )
                                    ),
                                    shape = RoundedCornerShape(12.dp)
                                ),
                            contentAlignment = Alignment.Center
                        ) {
                            Text("Login", color = Color.White, fontWeight = FontWeight.Bold)
                        }
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    Divider(thickness = 1.dp, color = Color.LightGray)

                    Spacer(modifier = Modifier.height(16.dp))

                    // "Continue with Google" Button
                    OutlinedButton(
                        onClick = { },
                        modifier = Modifier.fillMaxWidth(),
                        border = BorderStroke(1.dp, Color.Gray),
                        shape = RoundedCornerShape(12.dp)
                    ) {
                        Icon(
                            painter = painterResource(id = android.R.drawable.ic_menu_search),
                            contentDescription = "Google",
                            modifier = Modifier.size(18.dp)
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text("Continue with Google", fontWeight = FontWeight.Medium)
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    // Sign Up Row
                    Row {
                        Text("Don't have an account?")
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(
                            text = "Sign Up",
                            color = Color(0xFFEC407A),
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun LoginScreenPreview() {
//    LoginScreen()
}
