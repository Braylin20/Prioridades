package edu.ucne.prioridades.presentation.navigation.prioridad

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import edu.ucne.prioridades.Data.dao.database.PrioridadDb
import edu.ucne.prioridades.Data.dao.entities.PrioridadEntity
import edu.ucne.prioridades.ui.theme.PrioridadesTheme
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking


@Composable
fun PrioridadScreen(
    onGoToPrioridadListScreen: () -> Unit,
    prioridadDb: PrioridadDb,
    prioridadId: Int
) {
    var descripcion by remember { mutableStateOf("") }
    var diasComrpomiso by remember { mutableStateOf("") }
    var message: String? by remember { mutableStateOf("") }
    Scaffold(
        floatingActionButton = {
            FloatingActionButton(
                onClick = onGoToPrioridadListScreen,
                modifier = Modifier.padding(16.dp)
            ) {
                Icon(Icons.Filled.ArrowBack, contentDescription = "Volver Atrás")
            }
        }
    ) { innerPadding ->
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

                    OutlinedTextField(
                        label = { Text(text = "Días Compromiso") },
                        value = diasComrpomiso,
                        onValueChange = { diasComrpomiso = it },
                        modifier = Modifier.fillMaxWidth(),
                        keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number)
                    )
                    Text(
                        text = message.orEmpty(),
                        color = MaterialTheme.colorScheme.error,
                        modifier = Modifier
                            .padding(5.dp)
                            .align(Alignment.CenterHorizontally),
                        style = MaterialTheme.typography.titleMedium
                    )
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.Center
                    ) {
                        LaunchedEffect(prioridadId) {
                            if (prioridadId > 0) {
                                val prioridadExiste = runBlocking {
                                    prioridadDb.prioridadDao().find(prioridadId)
                                }
                                if (prioridadExiste != null) {
                                    descripcion = prioridadExiste.descripcion
                                    diasComrpomiso = prioridadExiste.diasCompromiso.toString()
                                }
                            }
                        }
                        OutlinedButton(
                            onClick = {
                                descripcion = ""
                                diasComrpomiso = ""
                                message = null
                            }
                        ) {
                            Icon(
                                imageVector = Icons.Default.Refresh,
                                contentDescription = null
                            )
                            Text(text = "Nuevo")
                        }
                        val scope = rememberCoroutineScope()
                        val newDiasCompromiso = diasComrpomiso.toIntOrNull()
                        OutlinedButton(
                            onClick = {
                                scope.launch {
                                    val descripcionExiste = prioridadDb.prioridadDao()
                                        .findByDescripcion(descripcion)
                                    message = when {
                                        descripcion.isBlank() -> "Todos los campos son requeridos"
                                        newDiasCompromiso == null -> "Todos los campos son requeridos"
                                        newDiasCompromiso < 1 -> "Este campo debe estar entre 1 y 31"
                                        descripcionExiste != null && descripcionExiste.prioridadId != prioridadId -> "Esta descripción ya existe"
                                        else -> ""
                                    }
                                    if (message.isNullOrEmpty()) {
                                        if (prioridadId > 0) {
                                            prioridadDb.prioridadDao().update(
                                                PrioridadEntity(
                                                    prioridadId = prioridadId,
                                                    descripcion = descripcion,
                                                    diasCompromiso = newDiasCompromiso
                                                )
                                            )
                                            message = "Editado correctamente"
                                        } else {
                                            prioridadDb.prioridadDao().save(
                                                PrioridadEntity(
                                                    descripcion = descripcion,
                                                    diasCompromiso = newDiasCompromiso
                                                )
                                            )
                                            message = "Guardado correctamente"
                                        }
                                        descripcion = ""
                                        diasComrpomiso = ""
                                    }
                                }
                            }
                        ) {
                            Icon(
                                imageVector = Icons.Default.Add,
                                contentDescription = null
                            )
                            Text(text = if (prioridadId > 0) "Actualizar" else "Guardar")
                        }
                        if(prioridadId >0){
                            OutlinedButton(
                                onClick = {
                                    scope.launch {
                                        prioridadDb.prioridadDao().delete(
                                            PrioridadEntity(
                                                prioridadId = prioridadId,
                                                descripcion = descripcion,
                                                diasCompromiso = newDiasCompromiso
                                            )
                                        )
                                    }
                                    descripcion = ""
                                    diasComrpomiso = ""
                                    message = "Eliminado correctamente"
                                }
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Delete,
                                    contentDescription = null,
                                    tint = MaterialTheme.colorScheme.error
                                )
                                Text(text = "Eliminar", color = MaterialTheme.colorScheme.error)
                            }
                        }
                    }
                }
            }
        }
    }

}


