package ci.nsu.moble.people

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import ci.nsu.moble.people.ui.theme.PeopleTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            PeopleTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    GreetingScreen(
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}

@Composable
fun GreetingScreen(modifier: Modifier = Modifier) {
    // List of available colors
    val availableColors = listOf(
        Color.Red,
        Color.Yellow,
        Color.Green,
        Color.Blue,
        Color.Magenta,
        Color.Cyan
    )

    // State to track current color index
    var currentColorIndex by remember { mutableStateOf(0) }

    // Button click handler
    val onButtonClick = {
        // Cycle through colors
        currentColorIndex = (currentColorIndex + 1) % availableColors.size
    }

    // Get current color
    val currentColor = availableColors[currentColorIndex]

    Button(
        onClick = onButtonClick,
        modifier = modifier
    ) {
        Text(
            text = "Hello! Current color: ${currentColor.toString()}",
            color = currentColor
        )
    }
}

/*@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}*/

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    PeopleTheme {
        GreetingScreen()
    }
}