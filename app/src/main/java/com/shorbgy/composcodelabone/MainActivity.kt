package com.shorbgy.composcodelabone

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.spring
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.text.font.FontWeight
import com.shorbgy.composcodelabone.ui.theme.ComposeCodeLabOneTheme

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ComposeCodeLabOneTheme {
                MyApp()
            }
        }
    }

    @Composable
    fun MyApp() {
        var shouldShowOnboarding by rememberSaveable { mutableStateOf(true) }

        if (shouldShowOnboarding) {
            OnboardingScreen(onContinueClicked = { shouldShowOnboarding = false })
        } else {
            Greetings()
        }
    }

    @Composable
    fun OnboardingScreen(onContinueClicked: () -> Unit) {
        Surface {
            Column(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Text(text = "Welcome to the basics codelab!")
                Button(
                    onClick = onContinueClicked,
                    modifier = Modifier.padding(24.dp)
                ) {
                    Text(text = "Continue")
                }
            }
        }
    }

    @Composable
    fun Greetings(names: List<String> = List(1000) { "$it" }) {
        LazyColumn(modifier = Modifier.padding(vertical = 0.dp)) {
            items(names) { item ->
                Greeting(name = item)
            }
        }
    }

    @Composable
    fun Greeting(name: String) {
        Card(
            backgroundColor = MaterialTheme.colors.primary,
            modifier = Modifier.padding(vertical = 4.dp, horizontal = 8.dp)
        ) {
            CardContent(name = name)
        }
    }

    @Composable
    fun CardContent(name: String) {
        var expanded by rememberSaveable { mutableStateOf(false) }
        val extraPadding by animateDpAsState(
            targetValue = if (expanded) 48.dp else 0.dp,
            animationSpec = spring(
                dampingRatio = Spring.DampingRatioMediumBouncy,
                stiffness = Spring.StiffnessLow
            )
        )
        Surface(
            modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
            color = MaterialTheme.colors.primary
        ) {
            Row(modifier = Modifier.padding(24.dp)) {
                Column(
                    modifier = Modifier
                        .weight(1f)
                        .padding(bottom = extraPadding.coerceAtLeast(0.dp))
                ) {
                    Text(text = "Hello,")
                    Text(
                        text = name, style = MaterialTheme.typography.h4.copy(
                            fontWeight = FontWeight.ExtraBold
                        )
                    )
                    if (expanded) {
                        Text(
                            text = ("Composem ipsum color sit lazy, " +
                                    "padding theme elit, sed do bouncy. ").repeat(4),
                        )
                    }
                }
                IconButton(onClick = { expanded = !expanded }) {
                    Icon(
                        imageVector = if (expanded) Icons.Filled.KeyboardArrowUp else Icons.Filled.KeyboardArrowDown,
                        contentDescription = if (expanded) "Show Less" else "Show More"
                    )
                }
            }

        }
    }
}