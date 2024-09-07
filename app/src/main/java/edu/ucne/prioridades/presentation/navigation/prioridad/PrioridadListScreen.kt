package edu.ucne.prioridades.presentation.navigation.prioridad

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import edu.ucne.prioridades.Data.dao.entities.PrioridadEntity
import edu.ucne.prioridades.ui.theme.PrioridadesTheme

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
    ) { it->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(it)
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
                modifier = Modifier
                    .padding(15.dp)
            ) {
                Text(
                    "Id",
                    modifier = Modifier
                        .weight(1f)
                        .align(Alignment.CenterVertically),
                    fontSize = 20.sp,
                    fontFamily = FontFamily.SansSerif,
                    textAlign = TextAlign.Center
                )
                Text(
                    "Descripción",
                    modifier = Modifier.weight(2.5f).align(Alignment.CenterVertically),
                    fontSize = 20.sp,
                    fontFamily = FontFamily.SansSerif,
                    textAlign = TextAlign.Center
                )
                Text(
                    "Días Compromiso",
                    modifier = Modifier.weight(2.5f).align(Alignment.CenterVertically),
                    fontSize = 20.sp,
                    fontFamily = FontFamily.SansSerif,
                    textAlign = TextAlign.Center
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
            .clickable {
                goToPrioridadScreen(it.prioridadId?:0)
            }
            .padding(vertical = 15.dp)

    ) {
        Text(
            modifier = Modifier.weight(1f),
            text = it.prioridadId.toString(),
            fontSize = 18.sp,
            fontFamily = FontFamily.SansSerif,
            textAlign = TextAlign.Center
        )
        Text(
            modifier = Modifier.weight(2.5f),
            text = it.descripcion,
            fontSize = 18.sp,
            fontFamily = FontFamily.SansSerif,
            textAlign = TextAlign.Center
        )
        Text(
            modifier = Modifier.weight(2.5f),
            text = it.diasCompromiso.toString(),
            fontSize = 18.sp,
            fontFamily = FontFamily.SansSerif,
            textAlign = TextAlign.Center
        )
    }
    HorizontalDivider()
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun PrioridadListScreenPreview() {
    var prioridadList: List<PrioridadEntity> = listOf(
        PrioridadEntity(1, "Alta", 10),
        PrioridadEntity(2, "Media", 5),
        PrioridadEntity(3, "Baja", 1)
    )
    PrioridadesTheme {
        PrioridadListScreen(
            prioridadList,
            createPrioridad = {},
        ) { }
    }
}