package com.example.noviapp.presentation

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import botDetails
import com.example.noviapp.trialUi.AnimatedTestimonials


@Composable
fun BotScreen(navController: NavController) {
    AnimatedTestimonials(
        testimonials = botDetails,
        autoPlay = true,
        onChooseTraits = { selectedTestimonial ->
            navController.navigate("traits/${selectedTestimonial.name}")
        }
    )
}

@Preview
@Composable
fun BotScreenPreview() {
    BotScreen(navController = NavController(LocalContext.current))
}
