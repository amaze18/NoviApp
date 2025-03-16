package com.example.noviapp.trialUi

import Testimonial
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.tween
import androidx.compose.animation.core.updateTransition
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import botDetails
import kotlinx.coroutines.delay

@Composable
fun AnimatedTestimonials(
    testimonials: List<Testimonial>,
    autoPlay: Boolean = false,
    onChooseTraits: (Testimonial) -> Unit = {} // New callback parameter
) {
    var activeIndex by remember { mutableStateOf(0) }

    LaunchedEffect(autoPlay) {
        if (autoPlay) {
            while (true) {
                delay(5000)
                activeIndex = (activeIndex + 1) % testimonials.size
            }
        }
    }

    // Create a soft pink-to-peach vertical gradient.
    val backgroundGradient = Brush.verticalGradient(
        colors = listOf(
            Color(0xFFFFE5E9),
            Color(0xFFE17A9E)
        )
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(backgroundGradient)
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        // Animated Image Area (on top)
        Box(
            modifier = Modifier
                .size(width = 280.dp, height = 300.dp)
        ) {
            testimonials.forEachIndexed { index, testimonial ->
                val isActive = index == activeIndex
                val randomRotation = remember { (-10..10).random().toFloat() }
                val transition = updateTransition(targetState = isActive, label = "ImageTransition")

                val opacity by transition.animateFloat(
                    transitionSpec = { tween(durationMillis = 400, easing = FastOutSlowInEasing) },
                    label = "opacity"
                ) { active -> if (active) 1f else 0.7f }

                val scale by transition.animateFloat(
                    transitionSpec = { tween(durationMillis = 400, easing = FastOutSlowInEasing) },
                    label = "scale"
                ) { active -> if (active) 1f else 0.95f }

                val rotation by transition.animateFloat(
                    transitionSpec = { tween(durationMillis = 400, easing = FastOutSlowInEasing) },
                    label = "rotation"
                ) { active -> if (active) 0f else randomRotation }

                val zIndexValue = if (isActive) 999f else (testimonials.size + 2 - index).toFloat()

                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .graphicsLayer {
                            alpha = opacity
                            scaleX = scale
                            scaleY = scale
                            rotationZ = rotation
                        }
                        .zIndex(zIndexValue)
                ) {
                    Image(
                        painter = painterResource(id = testimonial.imageResId),
                        contentDescription = testimonial.name,
                        modifier = Modifier
                            .padding(horizontal = 20.dp)
                            .fillMaxSize()
                            .clip(RoundedCornerShape(20.dp)),
                        contentScale = ContentScale.Crop
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Testimonial Text Area (below the image)
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            val testimonial = testimonials[activeIndex]
            Text(
                text = testimonial.name,
                fontSize = 24.sp,
                color = Color.White
            )
            Text(
                text = "${testimonial.persona}, ${testimonial.location}",
                fontSize = 14.sp,
                color = Color.Gray
            )
            Spacer(modifier = Modifier.height(16.dp))
            AnimatedTestimonialText(persona = testimonial.persona)
        }

        Spacer(modifier = Modifier.height(24.dp))

        // Navigation Buttons
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Spacer(modifier = Modifier.width(8.dp))

            // When "Choose traits" is clicked, call the provided callback.
            GradientButton(
                text = "Choose traits",
                onClick = { onChooseTraits(testimonials[activeIndex]) }
            )
            Spacer(modifier = Modifier.height(32.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {

                CircularIconButton(
                    onClick = {
                        activeIndex = (activeIndex - 1 + testimonials.size) % testimonials.size
                    },
                    icon = Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = "Previous"
                )

                Spacer(modifier = Modifier.width(48.dp))

                CircularIconButton(
                    onClick = {
                        activeIndex = (activeIndex + 1) % testimonials.size
                    },
                    icon = Icons.AutoMirrored.Filled.ArrowForward,
                    contentDescription = "Next"
                )

            }
        }
    }
}


@Composable
fun AnimatedTestimonialText(persona: String) {
    val words = persona.split(" ")
    val revealed = remember { mutableStateListOf<Boolean>() }
    LaunchedEffect(persona) {
        revealed.clear()
        repeat(words.size) { revealed.add(false) }
        words.forEachIndexed { i, _ ->
            delay(50)
            revealed[i] = true
        }
    }
    Row {
        words.forEachIndexed { i, word ->
            AnimatedVisibility(visible = revealed.getOrNull(i) == true) {
                Text(
                    text = "$word ",
                    fontSize = 16.sp,
                    color = Color.LightGray
                )
            }
        }
    }
}

@Composable
fun GradientButton(
    text: String,
    onClick: () -> Unit
) {
    val buttonGradient = Brush.horizontalGradient(
        colors = listOf(
            Color(0xFFE192AD),
            Color(0xFFDC427A),
            Color(0xFFE7A435)
        )
    )

    Button(
        onClick = onClick,
        shape = RoundedCornerShape(24.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = Color.Transparent
        ),
        contentPadding = PaddingValues(),
    ) {
        Box(
            modifier = Modifier
                .background(buttonGradient, RoundedCornerShape(24.dp))
                .padding(horizontal = 32.dp, vertical = 12.dp)
        ) {
            Text(text = text, color = Color.White, fontSize = 16.sp)
        }
    }
}


@Composable
fun CircularIconButton(
    onClick: () -> Unit,
    icon: ImageVector,
    contentDescription: String
) {
    IconButton(
        onClick = onClick,
        modifier = Modifier
            .size(48.dp)
            .background(Color.White, shape = CircleShape)
    ) {
        Icon(
            imageVector = icon,
            contentDescription = contentDescription,
            tint = Color.DarkGray
        )
    }
}


@Preview(showBackground = true)
@Composable
fun PreviewAnimatedTestimonials() {
    // Simply call AnimatedTestimonials with the botDetails list
    AnimatedTestimonials(testimonials = botDetails, autoPlay = true)
}
