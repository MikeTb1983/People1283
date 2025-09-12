package com.example.lunchtray

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.lunchtray.ui.AccompanimentMenuScreen
import com.example.lunchtray.ui.CheckoutScreen
import com.example.lunchtray.ui.EntreeMenuScreen
import com.example.lunchtray.ui.OrderViewModel
import com.example.lunchtray.ui.SideDishMenuScreen
import com.example.lunchtray.ui.StartOrderScreen


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LunchTrayApp() {
    // Create ViewModel
    val viewModel: OrderViewModel = viewModel()

    // Create NavController for navigation
    val navController = rememberNavController()

    Scaffold(
        topBar = {
            // AppBar with title based on current screen
            TopAppBar(
                title = { Text("Lunch Tray App") } // You can make this dynamic based on uiState
            )
        }
    ) { innerPadding ->
        val uiState by viewModel.uiState.collectAsState()

        // Navigation host
        NavHost(
            navController = navController,
            startDestination = "start_order",
            modifier = Modifier.padding(innerPadding)
        ) {
            composable("start_order") {
                StartOrderScreen(
                    onStartOrderButtonClicked = { navController.navigate("entree_menu") }
                )
            }
            composable("entree_menu") {
                EntreeMenuScreen(
                    options = viewModel.entreeMenuItems,
                    onSelectionChanged = { viewModel.updateEntree(it) },
                    onNextButtonClicked = { navController.navigate("side_dish_menu") },
                    onCancelButtonClicked = { viewModel.resetOrder(); navController.popBackStack("start_order", inclusive = false) }
                )
            }
            composable("side_dish_menu") {
                SideDishMenuScreen(
                    options = viewModel.sideDishMenuItems,
                    onSelectionChanged = { viewModel.updateSideDish(it) },
                    onNextButtonClicked = { navController.navigate("accompaniment_menu") },
                    onCancelButtonClicked = { viewModel.resetOrder(); navController.popBackStack("start_order", inclusive = false) }
                )
            }
            composable("accompaniment_menu") {
                AccompanimentMenuScreen(
                    options = viewModel.accompanimentMenuItems,
                    onSelectionChanged = { viewModel.updateAccompaniment(it) },
                    onNextButtonClicked = { navController.navigate("checkout") },
                    onCancelButtonClicked = { viewModel.resetOrder(); navController.popBackStack("start_order", inclusive = false) }
                )
            }
            composable("checkout") {
                CheckoutScreen(
                    orderUiState = uiState,
                    onNextButtonClicked = { navController.navigate("start_order") }, // Or handle order submission
                    onCancelButtonClicked = { viewModel.resetOrder(); navController.popBackStack("start_order", inclusive = false) }
                )
            }
        }
    }
}

