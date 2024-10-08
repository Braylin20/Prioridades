package edu.ucne.prioridades

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.rememberNavController
import androidx.room.Room
import dagger.hilt.android.AndroidEntryPoint
import edu.ucne.prioridades.Data.dao.database.PrioridadDb
import edu.ucne.prioridades.Data.dao.entities.PrioridadEntity
import edu.ucne.prioridades.presentation.navigation.PrioridadNavHost
import edu.ucne.prioridades.presentation.navigation.prioridad.PrioridadListScreen
import edu.ucne.prioridades.ui.theme.PrioridadesTheme

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            PrioridadesTheme {
                val navHost = rememberNavController()
                PrioridadNavHost(navHost)
            }
        }
    }

    @Preview(showBackground = true, showSystemUi = true)
    @Composable
    fun PrioridadScreenPreview() {
        var prioridadList: List<PrioridadEntity> = listOf(
            PrioridadEntity(1, "Alta", 10),
            PrioridadEntity(2, "Media", 5),
            PrioridadEntity(3, "Baja", 1)
        )
        PrioridadesTheme {

        }
    }
}

