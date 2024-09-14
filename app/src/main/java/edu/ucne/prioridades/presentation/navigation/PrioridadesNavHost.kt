package edu.ucne.prioridades.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import edu.ucne.prioridades.Data.dao.database.PrioridadDb
import edu.ucne.prioridades.presentation.navigation.prioridad.PrioridadListScreen
import edu.ucne.prioridades.presentation.navigation.prioridad.PrioridadScreen

@Composable
fun PrioridadNavHost(
    navHostController: NavHostController,
) {
    NavHost(
        navController = navHostController,
        startDestination = Screen.PrioridadList
    ) {
        composable<Screen.PrioridadList> {
            PrioridadListScreen(
                createPrioridad = {navHostController.navigate(Screen.Prioridad(0))},
                goToPrioridadScreen = {navHostController.navigate(Screen.Prioridad(it))}
            )
        }
        composable<Screen.Prioridad> {
            val prioridadId = it.toRoute<Screen.Prioridad>().prioridadId
            PrioridadScreen(
                onGoToPrioridadListScreen = { navHostController.navigateUp() },
                prioridadId = prioridadId
            )
        }
    }
}