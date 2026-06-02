package com.cesar.transportestobon.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.cesar.transportestobon.presentation.screens.*

@Composable
fun AppNavigation() {

    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = Routes.LOGIN
    ) {

        composable(Routes.LOGIN) {
            LoginScreen(navController)
        }

        composable(Routes.DASHBOARD) {
            DashboardScreen(navController)
        }

        composable(Routes.CLIENTES) {
            ClientesScreen()
        }

        composable(Routes.EMPLEADOS) {
            EmpleadosScreen()
        }

        composable(Routes.VEHICULOS) {
            VehiculosScreen()
        }

        composable(Routes.PEDIDOS) {
            PedidosScreen()
        }
    }
}