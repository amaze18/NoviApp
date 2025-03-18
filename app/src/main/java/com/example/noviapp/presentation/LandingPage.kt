package com.example.noviapp.presentation

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import kotlin.math.roundToInt
import kotlin.random.Random
import androidx.compose.animation.core.Animatable
import androidx.compose.foundation.border
import androidx.compose.ui.unit.IntOffset
import kotlin.math.min

@Composable
fun PreviewBackgroundGradientAnimation(navController: NavController) {
    LazyColumn {
        item {
            Box(modifier = Modifier.fillMaxSize()) {
                BackgroundGradientAnimation {
                    CultureVoScreen(navController)
                }
            }
        }
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

@Composable
fun FeatureCard(
    title: String,
    description: String
) {
    Card(
        shape = RoundedCornerShape(24.dp),
        colors = CardDefaults.cardColors(containerColor = Color.Transparent),
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 28.dp)
    ) {
        Box(
            modifier = Modifier
                .background(
                    brush = Brush.linearGradient(
                        colors = listOf(
                            Color(0xFFF5E2E5),
                            Color(0xFFEBDBEE),
                            Color(0xFFC4DECF)
                        )
                    )
                )
                .padding(24.dp)
        ) {
            Column(
                horizontalAlignment = Alignment.Start,
                modifier = Modifier.padding(vertical = 16.dp)
            ) {
                Text(
                    text = title,
                    color = Color.Black,
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = description,
                    color = Color.DarkGray,
                    fontSize = 18.sp
                )
            }
        }
    }
}

@Composable
fun CultureVoScreen(navController: NavController) {
    val configuration = LocalConfiguration.current
    val screenHeight = configuration.screenHeightDp.dp
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(screenHeight)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(vertical = 15.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Box(
                modifier = Modifier
                    .background(
                        color = Color.White.copy(alpha = 0.45f),
                        shape = RoundedCornerShape(24.dp)
                    )
                    .fillMaxWidth()
                    .padding(vertical = 15.dp, horizontal = 10.dp),
                contentAlignment = Alignment.Center
            ){
                Text(
                    text = "CultureVo",
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Gray,
                    textAlign = TextAlign.Center
//                    modifier = Modifier
//                        .background(Color(0x80FCE7E7), shape = RoundedCornerShape(16.dp))
//                        .padding(horizontal = 24.dp, vertical = 8.dp)
                )
            }


            Spacer(modifier = Modifier.height(screenHeight / 20))
            GlassyAnimatedBox {
                AnimatedGradientText()
            }
            Spacer(modifier = Modifier.height(40.dp))
            Text(
                text = "CultureVo presents to you",
                fontSize = 20.sp,
                color = Color.Black,
                textAlign = TextAlign.Center
            )
            Spacer(modifier = Modifier.height(10.dp))
            Text(
                modifier = Modifier.fillMaxWidth(),
                text = "NOVI AI",
                fontSize = 94.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White,
                textAlign = TextAlign.Center
            )
            Spacer(modifier = Modifier.height(10.dp))
            Text(
                text = "Your Companion Forever",
                fontSize = 20.sp,
                color = Color.DarkGray,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(horizontal = 16.dp)
            )
            Spacer(modifier = Modifier.height(screenHeight / 15))
            Button(
                onClick = { navController.navigate("login") },
                colors = ButtonDefaults.buttonColors(containerColor = Color.Black),
                shape = RoundedCornerShape(12.dp),
                modifier = Modifier.padding(vertical = 10.dp, horizontal = 5.dp)
            ) {
                Text(text = "Start Chatting", color = Color.White, fontSize = 18.sp)
            }
        }
    }
}

@Composable
fun CultureVoFooter() {
    val backgroundColor = Color(0xFFF3E9E9)
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(backgroundColor)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "CultureVo",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black
            )
            Spacer(modifier = Modifier.height(18.dp))
            Text(
                text = "Your AI companion who understands you culturally and emotionally.",
                fontSize = 16.sp,
                color = Color(0xFF383535),
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(horizontal = 16.dp)
            )
            Spacer(modifier = Modifier.height(16.dp))
            HorizontalDivider(
                thickness = 2.dp,
                color = Color.Gray.copy(alpha = 0.5f)
            )
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = "©2024 CultureVo AI. All rights reserved.",
                fontSize = 14.sp,
                color = Color(0xFF383535)
            )
            Spacer(modifier = Modifier.height(6.dp))
        }
    }
}

@Composable
fun GlassyAnimatedBox(content: @Composable () -> Unit) {
    // Create an infinite animation to drive the gradient animation.
    val infiniteTransition = rememberInfiniteTransition()
    val animatedOffset by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 100f,
        animationSpec = infiniteRepeatable(
            tween(durationMillis = 2000, easing = LinearEasing),
            repeatMode = RepeatMode.Reverse
        )
    )

    // Define an animated gradient brush for the border.
    val animatedBorderBrush = Brush.linearGradient(
        colors = listOf(
            Color(0xFF863894),
            Color(0xFFA63B5F),
            Color(0xFFAD6954)
        ),
        start = Offset(x = animatedOffset, y = animatedOffset),
        end = Offset(x = animatedOffset + 200f, y = animatedOffset + 200f)
    )

    // The glassy effect: a semi-transparent background with no shadow.
    Box(
        modifier = Modifier
            .background(
                color =  Color.White.copy(alpha = 0.45f),
                shape = RoundedCornerShape(36.dp)
            )
            .border(
                width = 2.dp,
                brush = animatedBorderBrush,
                shape = RoundedCornerShape(36.dp)
            )
            .padding(horizontal = 22.dp, vertical = 18.dp)
    ) {
        content()
    }
}

@Composable
fun AnimatedGradientText() {
    // Animate a float value to drive the gradient offset.
    val infiniteTransition = rememberInfiniteTransition()
    val animatedOffset by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 100f,
        animationSpec = infiniteRepeatable(
            tween(durationMillis = 2000, easing = LinearEasing),
            repeatMode = RepeatMode.Reverse
        )
    )

    // Use the same gradient for the text.
    val animatedBrush = Brush.linearGradient(
        colors = listOf(
            Color(0xFF640775),
            Color(0xFFE72867),
            Color(0xFFE7805F)
        ),
        start = Offset(x = animatedOffset, y = animatedOffset),
        end = Offset(x = animatedOffset + 200f, y = animatedOffset + 200f)
    )

    Text(
        text = buildAnnotatedString {
            withStyle(
                style = SpanStyle(
                    brush = animatedBrush,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold
                )
            ) {
                append("\uD83C\uDF89 Introducing your companions from \nNew Delhi, India")
            }
        },
        textAlign = TextAlign.Center
    )
}


@Preview(showBackground = true)
@Composable
fun PreviewCultureVoScreen() {
    val navController = rememberNavController()
    CultureVoScreen(navController)
}

@Preview(showBackground = true)
@Composable
fun PreviewBackgroundAnimation() {
    val navController = rememberNavController()
    PreviewBackgroundGradientAnimation(navController)
}




@Composable
fun BackgroundGradientAnimation(
//    gradientBackgroundStart: Color = Color(0xFFD7ACB2),
//    gradientBackgroundMid: Color = Color(0x94F563BB),
//    gradientBackgroundEnd: Color = Color(0xFFE8C8CD),
    gradientBackgroundStart : Color = Color(0xFFFFA3B1),
    gradientBackgroundEnd : Color = Color(0xFFFFE5E9),
    firstColor: Color = Color(250, 230, 255),
    secondColor: Color = Color(255, 240, 120),
    thirdColor: Color = Color(255, 120, 140),
    fourthColor: Color = Color(255, 140, 160),
    fifthColor: Color = Color(255, 230, 235),
    sixthColor: Color = Color(225, 175, 255),
    blendingValue: BlendMode = BlendMode.Screen,
    circleSize: Dp? = null,
    interactive: Boolean = true,
    content: @Composable () -> Unit = {}
) {
    val configuration = LocalConfiguration.current
    val density = LocalDensity.current

    // Convert screen dimensions to pixels
    val screenWidthPx = with(density) { configuration.screenWidthDp.dp.toPx() }
    val screenHeightPx = with(density) { configuration.screenHeightDp.dp.toPx() }

    // Default circle size: 30% of screen width
    val defaultSize = configuration.screenWidthDp.dp * 0.5f
    val finalSize = circleSize ?: defaultSize
    val finalSizePx = with(density) { finalSize.toPx() }

    /**
     * Ten explicit offsets from the top-left corner of the screen.
     * - Indices 6 & 7 are placed "between bottom and center" as requested.
     */
    val initialOffsets = listOf(
        // 0: Top-left
        Offset(x = 0f, y = 0f),
        // 1: Top-center
//        Offset(x = (screenWidthPx - finalSizePx) / 2f, y = 0f),
        // 2: Top-right
        Offset(x = screenWidthPx - finalSizePx, y = 0f),
        // 3: Middle-left
        Offset(x = 0f, y = (screenHeightPx - finalSizePx) / 2f),
        // 4: Center
        Offset(
            x = (screenWidthPx - finalSizePx) / 2f,
            y = (screenHeightPx - finalSizePx) / 2f
        ),
        // 5: Middle-right
        Offset(
            x = screenWidthPx - finalSizePx,
            y = (screenHeightPx - finalSizePx) / 2f
        ),
        // 6: Between bottom & center #1 (secondColor)
        Offset(
            x = (screenWidthPx - finalSizePx) / 2f,
            y = (screenHeightPx - finalSizePx) * 0.66f
        ),
        // 7: Between bottom & center #2 (fifthColor)
        Offset(
            x = (screenWidthPx - finalSizePx) / 2f,
            y = (screenHeightPx - finalSizePx) * 0.75f
        ),
        // 8: Bottom-left
        Offset(x = 0f, y = screenHeightPx - finalSizePx),
        // 9: Bottom-center
        Offset(
            x = (screenWidthPx - finalSizePx) / 2f,
            y = screenHeightPx - finalSizePx
        ),
    )

    val colorSets = listOf(
        // 0
        listOf(firstColor, firstColor.copy(alpha = 0.8f)),
        // 1
        listOf(secondColor, secondColor.copy(alpha = 0.8f)),
        // 2
        listOf(thirdColor, thirdColor.copy(alpha = 0.8f)),
        // 3
        listOf(sixthColor, sixthColor.copy(alpha = 0.8f)),
        // 4
        listOf(fourthColor, fourthColor.copy(alpha = 0.8f)),
        // 5
        listOf(fifthColor, fifthColor.copy(alpha = 0.8f)),
        // 6: secondColor
        listOf(secondColor, secondColor.copy(alpha = 0.8f)),
        // 7: fifthColor
        listOf(fifthColor, fifthColor.copy(alpha = 0.8f)),
        // 8
        listOf(sixthColor, sixthColor.copy(alpha = 0.8f)),
        // 9
        listOf(sixthColor, sixthColor.copy(alpha = 0.8f)),
    )

    // If the user wants to interact (drag).
    val pointerModifier = if (interactive) {
        Modifier.pointerInput(Unit) {
            detectDragGestures { change, _ ->
                change.consume()
            }
        }
    } else Modifier

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                brush = Brush.linearGradient(
                    colors = listOf(gradientBackgroundStart , gradientBackgroundEnd),
                    start = Offset.Zero,
                    end = Offset(screenWidthPx, screenHeightPx)
                )
            )
            .then(pointerModifier)
    ) {
        // Draw circles behind the content
        Box(modifier = Modifier.fillMaxSize()) {
            val circleCount = min(colorSets.size, initialOffsets.size)
            repeat(circleCount) { i ->
                AnimatedGradientCircle(
                    colors = colorSets[i],
                    size = finalSize,
                    animationDuration = 4000 + (i * 500), // slightly stagger durations
                    blendingValue = blendingValue,
                    initialOffset = initialOffsets[i]
                )
            }
        }
        // Content on top
        Box(
            modifier = Modifier
                .fillMaxSize()
                .zIndex(1f)
        ) {
            content()
        }
    }
}

@Composable
fun AnimatedGradientCircle(
    colors: List<Color>,
    size: Dp,
    animationDuration: Int,
    blendingValue: BlendMode,
    initialOffset: Offset = Offset.Zero,
    modifier: Modifier = Modifier
) {
    // Pulsing scale animation.
    val infiniteTransition = rememberInfiniteTransition()
    val scale by infiniteTransition.animateFloat(
        initialValue = 0.9f,
        targetValue = 1.1f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = animationDuration, easing = LinearEasing),
            repeatMode = RepeatMode.Reverse
        )
    )

    // Calculate screen bounds and circle size.
    val configuration = LocalConfiguration.current
    val density = LocalDensity.current
    val screenWidthPx = with(density) { configuration.screenWidthDp.dp.toPx() }
    val screenHeightPx = with(density) { configuration.screenHeightDp.dp.toPx() }
    val circleSizePx = with(density) { size.toPx() }

    // Valid x range remains full screen width.
    val xRange = 0f..(screenWidthPx - circleSizePx)

    // Determine if this circle should stay in the top half or bottom half.
    val isTopHalf = initialOffset.y < screenHeightPx / 3
    val yRangeRestricted = if (isTopHalf) {
        // Top half: from 0 to half the screen minus the circle's height.
        0f..(screenHeightPx / 2 - circleSizePx).coerceAtLeast(0f)
    } else {
        // Bottom half: from half the screen to the bottom.
        (screenHeightPx / 2) .. (screenHeightPx - circleSizePx)
    }

    // Separate Animatable for x and y positions.
    val animatableX = remember { Animatable(initialOffset.x) }
    val animatableY = remember { Animatable(initialOffset.y) }

    // Animate x: pick a random target in xRange.
    LaunchedEffect(Unit) {
        while (true) {
            val targetX = Random.nextFloat() * (xRange.endInclusive - xRange.start) + xRange.start
            val durationX = Random.nextInt(3000, 7000)
            animatableX.animateTo(
                targetX,
                animationSpec = tween(durationMillis = durationX, easing = LinearEasing)
            )
        }
    }
    // Animate y: pick a random target in the restricted y range.
    LaunchedEffect(Unit) {
        while (true) {
            val targetY = Random.nextFloat() * (yRangeRestricted.endInclusive - yRangeRestricted.start) + yRangeRestricted.start
            val durationY = Random.nextInt(3000, 7000)
            animatableY.animateTo(
                targetY,
                animationSpec = tween(durationMillis = durationY, easing = LinearEasing)
            )
        }
    }

    // Draw the glowing circle with the calculated offset and scale.
    Canvas(
        modifier = modifier
            .offset {
                androidx.compose.ui.unit.IntOffset(
                    x = animatableX.value.roundToInt(),
                    y = animatableY.value.roundToInt()
                )
            }
            .size(size)
            .graphicsLayer {
                scaleX = scale
                scaleY = scale
            }
            .blur(50.dp) // High blur for a soft glowing effect.
    ) {
        val radiusPx = size.toPx() / 2
        // Three-stop radial gradient: bright center -> mid fade -> transparent edge.
        drawCircle(
            brush = Brush.radialGradient(
                colors = listOf(
                    colors[0],                   // Bright center.
                    colors[1].copy(alpha = 0.5f), // Mid fade.
                    colors[1].copy(alpha = 0f)    // Transparent edge.
                ),
                center = center,
                radius = radiusPx
            ),
            radius = radiusPx,
            center = center,
            blendMode = blendingValue
        )
    }
}



