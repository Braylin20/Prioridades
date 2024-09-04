package edu.ucne.prioridades

import android.graphics.Paint.Style
import android.graphics.drawable.Icon
import android.os.Bundle
import android.widget.Space
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.room.Room
import edu.ucne.prioridades.database.PrioridadDb
import edu.ucne.prioridades.entities.PrioridadEntity
import edu.ucne.prioridades.ui.theme.PrioridadesTheme
import kotlinx.coroutines.launch
import androidx.lifecycle.compose.LocalLifecycleOwner
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.runBlocking
import java.util.Collections.list


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
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(innerPadding)
                    ) {
                        PrioridadScreen()
                    }

                }
            }
        }
    }

    @Composable
    fun PrioridadScreen(
    ) {
        var descripcion by remember { mutableStateOf("") }
        var diasComrpomiso by remember { mutableStateOf("") }
        var errorMessageDes: String? by remember { mutableStateOf(null) }
        var errorMessageDias: String? by remember { mutableStateOf("") }
        var errorMessageExist: String? by remember { mutableStateOf("") }

        Scaffold { innerPadding ->

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
                    .padding(8.dp)
            ) {
                ElevatedCard(
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(15.dp),
                        horizontalArrangement = Arrangement.Center
                    ) {
                        Text(
                            text = "Registro de Prioridades",
                            style = MaterialTheme.typography.headlineMedium
                        )
                    }
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(8.dp)
                    ) {
                        OutlinedTextField(
                            label = { Text(text = "Descripción") },
                            value = descripcion,
                            onValueChange = { descripcion = it },
                            modifier = Modifier.fillMaxWidth(),
                        )
                        Text(
                            text = errorMessageDes.orEmpty(),
                            color = MaterialTheme.colorScheme.error
                        )
                        OutlinedTextField(
                            label = { Text(text = "Días Compromiso") },
                            value = diasComrpomiso,
                            onValueChange = { diasComrpomiso = it },
                            modifier = Modifier.fillMaxWidth(),
                            keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number)
                        )
                        Text(
                            text = errorMessageDias.orEmpty(),
                            color = MaterialTheme.colorScheme.error
                        )

                        Text(
                            text = errorMessageExist.orEmpty(),
                            color = MaterialTheme.colorScheme.error,
                            modifier = Modifier.padding(5.dp).align(Alignment.CenterHorizontally),
                            style = MaterialTheme.typography.titleMedium
                        )
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.Center
                        ) {
                            OutlinedButton(
                                onClick = {
                                    descripcion = ""
                                    diasComrpomiso = ""
                                    errorMessageDes = null
                                    errorMessageDias = null
                                    errorMessageExist = null
                                }
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Add,
                                    contentDescription = null
                                )
                                Text(text = "Nuevo")
                            }
                            val scope = rememberCoroutineScope()
                            OutlinedButton(
                                onClick = {
                                    val newDiasCompromiso = diasComrpomiso.toIntOrNull()
                                    val descripcionExiste = runBlocking {findByDescription(descripcion)}
                                    if (descripcion.isBlank()) {
                                        errorMessageDes = "Este campo es requerido"
                                    }
                                    if (newDiasCompromiso == null) {
                                        errorMessageDias = "Este campo es requerido"
                                        return@OutlinedButton
                                    }
                                    if (newDiasCompromiso > 31 || newDiasCompromiso < 1) {
                                        errorMessageDias = "Este campo debe estar entre 1 y 31"
                                    }
                                    if(descripcionExiste != null){
                                        errorMessageExist = "Esta descripción ya existe"
                                    }
                                    else {
                                        scope.launch {
                                            savePrioridad(
                                                PrioridadEntity(
                                                    descripcion = descripcion,
                                                    diasCompromiso = newDiasCompromiso
                                                )
                                            )
                                            descripcion = ""
                                            diasComrpomiso = ""
                                            errorMessageDes = null
                                            errorMessageDias = null
                                            errorMessageExist = null
                                        }
                                    }
                                }
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Edit,
                                    contentDescription = "Save Button"
                                )
                                Text(text = "Guardar")
                            }
                        }
                    }
                }
                val lifecycleOwner = LocalLifecycleOwner.current
                val prioridadList by prioridadDb.prioridadDao().getAll()
                    .collectAsStateWithLifecycle(
                        initialValue = emptyList(),
                        lifecycleOwner = lifecycleOwner,
                        minActiveState = Lifecycle.State.STARTED
                    )
                PrioridadListScreen(prioridadList)
            }

        }

    }

    @Composable
    fun PrioridadListScreen(prioridadList: List<PrioridadEntity>) {
        Column(
            modifier = Modifier.fillMaxSize()
        ) {

            Text(
                text = "Lista de Prioridades",
                style = MaterialTheme.typography.titleLarge,
                modifier = Modifier.align(Alignment.CenterHorizontally).padding(24.dp)
            )
            Row(
            ){
                Text("Id", modifier = Modifier.weight(1f))
                Text("Descripción", modifier = Modifier.weight(2.5f))
                Text("Días Compromiso", modifier = Modifier.weight(2.5f))
            }

            LazyColumn(
                modifier = Modifier.fillMaxSize(),
            ) {
                items(prioridadList) {
                    PrioridadRow(it)
                }

            }
        }
    }

    @Composable
    private fun PrioridadRow(it: PrioridadEntity) {

        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(5.dp)
        ) {
            Text(
                modifier = Modifier.weight(1f),
                text = it.prioridadId.toString()
            )
            Text(
                modifier = Modifier.weight(2.5f),
                text = it.descripcion,
            )
            Text(
                modifier = Modifier.weight(2f),
                text = it.diasCompromiso.toString()
            )
        }
        HorizontalDivider()
    }

    private suspend fun savePrioridad(prioridad: PrioridadEntity) {
        prioridadDb.prioridadDao().save(prioridad)
    }

    private suspend fun findByDescription(descripcion: String): PrioridadEntity? {
        val existe =prioridadDb.prioridadDao().findByDescripcion(descripcion)
        return existe
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
            PrioridadListScreen(prioridadList)
        }
    }
}

