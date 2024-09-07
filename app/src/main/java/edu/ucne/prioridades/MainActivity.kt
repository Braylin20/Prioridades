package edu.ucne.prioridades

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import androidx.room.Room
import edu.ucne.prioridades.Data.dao.database.PrioridadDb
import edu.ucne.prioridades.Data.dao.entities.PrioridadEntity
import edu.ucne.prioridades.presentation.navigation.PrioridadNavHost
import edu.ucne.prioridades.presentation.navigation.Screen
import edu.ucne.prioridades.presentation.navigation.prioridad.PrioridadListScreen
import edu.ucne.prioridades.presentation.navigation.prioridad.PrioridadScreen
import edu.ucne.prioridades.ui.theme.PrioridadesTheme


class MainActivity : ComponentActivity() {
    private lateinit var prioridadDb: PrioridadDb

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        prioridadDb = Room.databaseBuilder(
            applicationContext,
            PrioridadDb::class.java,
            "Prioridad.db"
        ).fallbackToDestructiveMigration()
            .build()

        setContent {
            PrioridadesTheme {
                val navHost = rememberNavController()
                PrioridadNavHost(navHost,prioridadDb)
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

