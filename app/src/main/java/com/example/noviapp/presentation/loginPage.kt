package com.example.noviapp.presentation

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.tooling.preview.Preview
import androidx.credentials.CredentialManager
import androidx.credentials.GetCredentialRequest
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.noviapp.R
import com.google.android.libraries.identity.googleid.GetGoogleIdOption
import com.google.android.libraries.identity.googleid.GoogleIdTokenCredential
import io.github.jan.supabase.auth.Auth
import io.github.jan.supabase.auth.auth
import io.github.jan.supabase.auth.providers.Google
import io.github.jan.supabase.auth.providers.builtin.Email
import io.github.jan.supabase.auth.providers.builtin.IDToken
import io.github.jan.supabase.auth.user.UserInfo
import io.github.jan.supabase.createSupabaseClient
import io.github.jan.supabase.postgrest.Postgrest
import kotlinx.coroutines.launch
import java.security.MessageDigest
import java.util.UUID

val supabase = createSupabaseClient(
    supabaseUrl = "https://cbelvhxiygjoxayfoyuk.supabase.co",
    supabaseKey = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJzdXBhYmFzZSIsInJlZiI6ImNiZWx2aHhpeWdqb3hheWZveXVrIiwicm9sZSI6ImFub24iLCJpYXQiOjE3Mzg0ODU2ODIsImV4cCI6MjA1NDA2MTY4Mn0.g12E6H3wIuExkfm7klt9mOtxKIdoyQqIqVnIp4NmmJk"
) {
    install(Auth)
    install(Postgrest)
}

suspend fun signUpWithEmail(email: String, password: String, context: android.content.Context) {
    try {
//        val result =
            supabase.auth.signUpWith(Email) {
            this.email = email
            this.password = password
        }

//        val userInfo: UserInfo? = auth.retrieveUserForCurrentSession()
//
//        // Check if email is verified
//        if (userInfo?.emailConfirmedAt == null) {
//            Toast.makeText(context, "Check your email to verify your account.", Toast.LENGTH_LONG).show()
//        } else {
//            Toast.makeText(context, "Sign-up successful!", Toast.LENGTH_SHORT).show()
//        }

//        Toast.makeText(context, "Sign-up successful!", Toast.LENGTH_SHORT).show()

    } catch (e: Exception) {
//        Toast.makeText(context, "Error: ${e.localizedMessage}", Toast.LENGTH_LONG).show()
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginPage(navController: NavController) {
    var name by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    val context = LocalContext.current
    val coroutineScope = rememberCoroutineScope()
    var gender by remember { mutableStateOf("Select your gender") }
    var city by remember { mutableStateOf("Select city of your Novi") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black)
            .padding(16.dp),
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "Welcome to Novi",
            color = Color.White,
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold
        )
        Spacer(modifier = Modifier.height(16.dp))

        CustomTextField(value = name, onValueChange = { name = it }, label = "Enter your name")
        Spacer(modifier = Modifier.height(8.dp))
        CustomTextField(value = email, onValueChange = { email = it }, label = "Enter email")
        Spacer(modifier = Modifier.height(8.dp))
        CustomTextField(value = password, onValueChange = { password = it }, label = "Enter password")
        Spacer(modifier = Modifier.height(8.dp))
        GenderDropdown(selectedGender = gender, onGenderSelected = { gender = it })
        Spacer(modifier = Modifier.height(8.dp))
        CityDropdown(selectedCity = city, onCitySelected = { city = it })
        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = {
                coroutineScope.launch {
                    signUpWithEmail(email, password, context)
                    // After login, navigate to BotScreen.
                    navController.navigate("bot")
                }
            },
            colors = ButtonDefaults.buttonColors(containerColor = Color.DarkGray),
            shape = RoundedCornerShape(8.dp),
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(text = "Continue →", color = Color.White, fontSize = 16.sp)
        }
        Spacer(modifier = Modifier.height(16.dp))
        GoogleSignInButton()
    }
}


@Composable
fun CustomTextField(value: String, onValueChange: (String) -> Unit, label: String) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        label = { Text(label, color = Color.White) },
        modifier = Modifier.fillMaxWidth(),
        colors = OutlinedTextFieldDefaults.colors(
            focusedTextColor = Color.White,
            unfocusedTextColor = Color.Gray,
            cursorColor = Color.White,
            focusedBorderColor = Color.White,
            unfocusedBorderColor = Color.Gray
        )
    )
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GenderDropdown(selectedGender: String, onGenderSelected: (String) -> Unit) {
    var expanded by remember { mutableStateOf(false) }

    ExposedDropdownMenuBox(
        expanded = expanded,
        onExpandedChange = { expanded = it }
    ) {
        OutlinedTextField(
            value = selectedGender,
            onValueChange = {},
            readOnly = true,
            label = { Text("Select your gender", color = Color.White) },
            modifier = Modifier
                .fillMaxWidth()
                .menuAnchor(),
            colors = OutlinedTextFieldDefaults.colors(
                focusedTextColor = Color.White,   // Ensure text appears white when focused
                unfocusedTextColor = Color.White, // Ensure text appears white when not focused
                focusedBorderColor = Color.White,
                unfocusedBorderColor = Color.Gray,
                cursorColor = Color.White
            )
        )
        ExposedDropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            DropdownMenuItem(text = { Text("Male", color = Color.White) }, onClick = {
                onGenderSelected("Male")
                expanded = false
            })
            DropdownMenuItem(text = { Text("Female", color = Color.White) }, onClick = {
                onGenderSelected("Female")
                expanded = false
            })
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CityDropdown(selectedCity: String, onCitySelected: (String) -> Unit) {
    var expanded by remember { mutableStateOf(false) }

    ExposedDropdownMenuBox(
        expanded = expanded,
        onExpandedChange = { expanded = it }
    ) {
        OutlinedTextField(
            value = selectedCity,
            onValueChange = {},
            readOnly = true,
            label = { Text("Select city of your Novi", color = Color.White) },
            modifier = Modifier
                .fillMaxWidth()
                .menuAnchor(),
            colors = OutlinedTextFieldDefaults.colors(
                focusedTextColor = Color.White,
                unfocusedTextColor = Color.White,
                focusedBorderColor = Color.White,
                unfocusedBorderColor = Color.Gray,
                cursorColor = Color.White
            )
        )
        ExposedDropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            DropdownMenuItem(text = { Text("Delhi", color = Color.White) }, onClick = {
                onCitySelected("Delhi")
                expanded = false
            })
        }
    }
}


@Composable
fun GoogleSignInButton() {
    val context = LocalContext.current
    val coroutineScope = rememberCoroutineScope()

    val onClick: () -> Unit = {
        val credentialManager = CredentialManager.create(context)

        val rawNonce = UUID.randomUUID().toString()
        val digest = MessageDigest.getInstance("SHA-256").digest(rawNonce.toByteArray())
        val hashNonce = digest.joinToString("") { "%02x".format(it) }

        val googleIdOption = GetGoogleIdOption.Builder()
            .setFilterByAuthorizedAccounts(false)
            .setServerClientId("424181401703-t6rs65tv49hqtbbrv4n26has18it16df.apps.googleusercontent.com")
            .setNonce(hashNonce)
            .build()

        val request = GetCredentialRequest.Builder()
            .addCredentialOption(googleIdOption)
            .build()

        coroutineScope.launch {
            try {
                val result = credentialManager.getCredential(
                    request = request,
                    context = context
                )
                val credential = result.credential
                val googleIdTokenCredential = GoogleIdTokenCredential.createFrom(credential.data)
                val googleIdToken = googleIdTokenCredential.idToken

                supabase.auth.signInWith(IDToken){
                    idToken = googleIdToken
                    provider = Google
                    nonce = rawNonce
                }

                Toast.makeText(context, "You are signed in", Toast.LENGTH_SHORT).show()
            } catch (e: Exception) {
                Toast.makeText(context, "You are Not signed in", Toast.LENGTH_SHORT).show()
            }
        }
    }

    Button(
        onClick = onClick,
        colors = ButtonDefaults.buttonColors(containerColor = Color.Transparent),
        shape = RoundedCornerShape(50),
        modifier = Modifier
            .fillMaxWidth()
            .border(2.dp, Color.White, RoundedCornerShape(50))
            .clip(RoundedCornerShape(50))
            .padding(4.dp) // Add padding for spacing
    ) {
        Icon(
            painter = painterResource(id = R.drawable.google_logo),
            contentDescription = "Google Icon",
            tint = Color.Unspecified,
            modifier = Modifier.size(24.dp)
        )
        Spacer(modifier = Modifier.width(8.dp)) // Space between icon & text
        Text(
            text = "Sign in with Google",
            color = Color.White,
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold
        )
    }

}

@Preview
@Composable
fun PreviewLoginPage() {
    val navController = rememberNavController()
    LoginPage(navController)
}
