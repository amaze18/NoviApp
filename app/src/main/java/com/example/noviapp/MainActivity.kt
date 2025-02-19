package com.example.noviapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.noviapp.presentation.LoginPage
import com.example.noviapp.ui.theme.NoviAppTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            NoviAppTheme {
                val navController = rememberNavController()
                Box(modifier = Modifier.padding(vertical = 16.dp)) {
                    NavHost(navController, startDestination = "home") {
                        composable("home") { NoviAIUI(navController) }
                        composable("login") { LoginPage() }
                    }
                }
            }
        }
    }
}

@Composable
fun NoviAIUI(navController: NavController) {
    val colors = MaterialTheme.colorScheme

    Box(modifier = Modifier.fillMaxSize()) {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .background(colors.background)
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            item { CultureVoScreen() }

            val features = listOf(
                "Culturally Intelligent" to "Your Novi is culturally adept to the city they belong to. They know the city like a local—its personality, offerings, and challenges.",
                "Emotionally Intelligent" to "NOVI will understand you like no other. Discuss your life’s dreams, hopes, fears, and goals with them. They will care for you, for who you are!",
                "Always there for you" to "Treat your NOVI as your constant source for emotional sustenance. They are always available for you when the world might not be."
            )

            items(features) { (title, description) ->
                FeatureCard(title, description)
                Spacer(modifier = Modifier.height(16.dp))
            }

            item { CultureVoFooter() }
        }
    }
}

@Composable
fun FeatureCard(title: String, description: String) {
    Card(
        shape = RoundedCornerShape(24.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFF020617)),
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        Column(
            modifier = Modifier
                .padding(24.dp)
                .fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = title,
                color = Color.White,
                fontSize = 18.sp,
                fontWeight = FontWeight.ExtraBold,
                textAlign = TextAlign.Center
            )
            Spacer(modifier = Modifier.height(12.dp))
            Text(
                text = description,
                color = Color(0xFFC7C7C7),
                fontSize = 14.sp,
                textAlign = TextAlign.Center
            )
        }
    }
}

@Composable
fun CultureVoScreen() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black)
            .padding(16.dp),
        contentAlignment = Alignment.Center
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(
                text = "CultureVo",
                fontSize = 18.sp,
                color = Color.White,
                modifier = Modifier
                    .background(Color.DarkGray, shape = RoundedCornerShape(16.dp))
                    .padding(horizontal = 24.dp, vertical = 8.dp)
            )
            Spacer(modifier = Modifier.height(16.dp))
            Box(
                modifier = Modifier
                    .clip(RoundedCornerShape(12.dp))
                    .background(Color(0xFF202020))
                    .border(1.dp, Color(0xFFFFA500), RoundedCornerShape(12.dp))
                    .padding(8.dp)
            ) {
                Text(
                    text = "\uD83C\uDF89 Introducing your companions from New Delhi, India",
                    fontSize = 14.sp,
                    color = Color(0xFFFFA500),
                    textAlign = TextAlign.Center
                )
            }
            Spacer(modifier = Modifier.height(32.dp))
            Text(text = "CultureVo presents to you", fontSize = 14.sp, color = Color.Gray)
            Text(text = "NOVI AI", fontSize = 32.sp, fontWeight = FontWeight.Bold, color = Color.White)
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = "Your AI companion who understands you culturally and emotionally.",
                fontSize = 16.sp,
                color = Color.White,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(horizontal = 24.dp)
            )
            Text(
                text = "Always there for you, with all the care in the world!",
                fontSize = 16.sp,
                color = Color.White,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(horizontal = 24.dp)
            )
            Spacer(modifier = Modifier.height(32.dp))
            Button(
                onClick = { },
                colors = ButtonDefaults.buttonColors(containerColor = Color.White),
                shape = RoundedCornerShape(12.dp),
                modifier = Modifier.shadow(8.dp, shape = RoundedCornerShape(12.dp))
            ) {
                Text(text = "Start Chatting", color = Color.Black, fontSize = 16.sp)
            }
        }
    }
}

@Composable
fun CultureVoFooter() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "CultureVo", fontSize = 20.sp, fontWeight = FontWeight.Bold, color = Color.White)
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = "Your AI companion who understands you culturally and emotionally. Always there for you, with all the care in the world!",
            fontSize = 14.sp,
            color = Color(0xFFC7C7C7),
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(horizontal = 16.dp)
        )
        Spacer(modifier = Modifier.height(16.dp))
        Text(text = "Reach out to us at\nsupport@culturevo.com", fontSize = 14.sp, fontWeight = FontWeight.Medium, color = Color.White, textAlign = TextAlign.Center)
        Spacer(modifier = Modifier.height(16.dp))
        Divider(color = Color.Gray.copy(alpha = 0.5f), thickness = 0.5.dp)
        Spacer(modifier = Modifier.height(16.dp))
        Text(text = "©2024 CultureVo AI. All rights reserved.", fontSize = 12.sp, color = Color(0xFFC7C7C7))
    }
}

@Preview
@Composable
fun PreviewNoviAIUI() {
    val navController = rememberNavController()
    NoviAppTheme { NoviAIUI(navController) }
}
