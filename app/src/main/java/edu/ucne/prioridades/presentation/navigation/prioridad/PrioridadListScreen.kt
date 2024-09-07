package edu.ucne.prioridades.presentation.navigation.prioridad

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import edu.ucne.prioridades.Data.dao.entities.PrioridadEntity

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PrioridadListScreen(
    prioridadList: List<PrioridadEntity>,
    createPrioridad: () -> Unit,
    goToPrioridadScreen: (Int) -> Unit
) {
    Scaffold(
        floatingActionButton = {
            FloatingActionButton(
                onClick = createPrioridad,
                modifier = Modifier.padding(16.dp)
            ) {
                Icon(Icons.Filled.Add, contentDescription = "Añadir Prioridad")
            }

        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            Text(
                text = "Lista de Prioridades",
                fontSize = 29.sp,
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .padding(24.dp),
                fontFamily = FontFamily.SansSerif
            )
            Row(
                modifier = Modifier.padding(15.dp)
            ) {
                Text(
                    "Id",
                    modifier = Modifier.weight(1f),
                    fontSize = 20.sp,
                    fontFamily = FontFamily.SansSerif
                )
                Text(
                    "Descripción",
                    modifier = Modifier.weight(2.5f),
                    fontSize = 20.sp,
                    fontFamily = FontFamily.SansSerif
                )
                Text(
                    "Días Compromiso",
                    modifier = Modifier.weight(2.5f),
                    fontSize = 20.sp,
                    fontFamily = FontFamily.SansSerif
                )
            }

            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 15.dp),
            ) {
                items(prioridadList) {
                    PrioridadRow(it,goToPrioridadScreen)
                }
            }
        }
    }

}

@Composable
private fun PrioridadRow(
    it: PrioridadEntity,
    goToPrioridadScreen:(Int) -> Unit
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .padding(15.dp)
            .clickable {
                goToPrioridadScreen(it.prioridadId?:0)
            }
    ) {
        Text(
            modifier = Modifier.weight(1f),
            text = it.prioridadId.toString(),
            fontSize = 18.sp,
            fontFamily = FontFamily.SansSerif,
        )
        Text(
            modifier = Modifier.weight(2.5f),
            text = it.descripcion,
            fontSize = 18.sp,
            fontFamily = FontFamily.SansSerif
        )
        Text(
            modifier = Modifier.weight(2f),
            text = it.diasCompromiso.toString(),
            fontSize = 18.sp,
            fontFamily = FontFamily.SansSerif
        )
    }
    HorizontalDivider()
}