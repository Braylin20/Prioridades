package edu.ucne.prioridades.presentation.navigation.ticket

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import edu.ucne.prioridades.Data.dao.entities.TicketEntity
import edu.ucne.prioridades.presentation.navigation.prioridad.SwipeToDeleteContainer


@Composable
fun TicketListScreen(
    viewModel: TicketViewModel = hiltViewModel(),
    goToTicketScreen: (Int) -> Unit,
    createTicket: () -> Unit
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    TicketBodyListScreen(
        uiState,
        goToTicketScreen,
        createTicket,
        onTicketSelected = viewModel::selectedTicket,
        onDelete = viewModel::delete,
    )
}


@Composable
fun TicketBodyListScreen(
    uiState: UiState,
    goToTicketScreen: (Int)-> Unit,
    createTicket: ()-> Unit,
    onTicketSelected: (Int) -> Unit,
    onDelete: (TicketEntity) -> Unit
) {
    Scaffold(
        floatingActionButton = {
            FloatingActionButton(
                onClick = createTicket,
                modifier = Modifier.padding(16.dp)
            ) {
                Icon(Icons.Filled.Add, contentDescription = null)
            }
        }
    ) { innerpadding->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerpadding)
        ) {
            Text(
                text = "Lista de Tickets",
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
                    fontSize = 13.sp,
                    fontFamily = FontFamily.SansSerif,
                    textAlign = TextAlign.Center
                )
                Text(
                    "Prioridad",
                    modifier = Modifier
                        .weight(2.5f)
                        .align(Alignment.CenterVertically),
                    fontSize = 13.sp,
                    fontFamily = FontFamily.SansSerif,
                    textAlign = TextAlign.Center
                )
                Text(
                    "Descripción",
                    modifier = Modifier
                        .weight(2.5f)
                        .align(Alignment.CenterVertically),
                    fontSize = 13.sp,
                    fontFamily = FontFamily.SansSerif,
                    textAlign = TextAlign.Center
                )
                Text(
                    "Asunto",
                    modifier = Modifier
                        .weight(2.5f)
                        .align(Alignment.CenterVertically),
                    fontSize = 13.sp,
                    fontFamily = FontFamily.SansSerif,
                    textAlign = TextAlign.Center
                )
                Text(
                    "Fecha",
                    modifier = Modifier
                        .weight(2.5f)
                        .align(Alignment.CenterVertically),
                    fontSize = 13.sp,
                    fontFamily = FontFamily.SansSerif,
                    textAlign = TextAlign.Center
                )
            }
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 15.dp)
            ) {
                items(
                    uiState.tickets,
                    key = { it.prioridadId!! }
                ) {
                    SwipeToDeleteContainer(
                        item = it,
                        onDelete = onDelete
                    ) { ticket ->
                        TicketRow(ticket,goToTicketScreen, onTicketSelected)
                    }
                }
            }
        }


    }
}


@Composable
fun TicketRow(
    it: TicketEntity,
    goToTicketScreen:(Int) -> Unit,
    onTicketSelected :(Int) -> Unit
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .clickable {
                goToTicketScreen(it.prioridadId?:0)
            }
            .background(MaterialTheme.colorScheme.background)
            .padding(vertical = 15.dp)
    ) {
        Text(
            modifier = Modifier.weight(1f),
            text = it.ticketId.toString(),
            fontSize = 18.sp,
            fontFamily = FontFamily.SansSerif,
            textAlign = TextAlign.Center
        )
        Text(
            modifier = Modifier.weight(2.5f),
            text = it.prioridadId.toString(),
            fontSize = 18.sp,
            fontFamily = FontFamily.SansSerif,
            textAlign = TextAlign.Center
        )
        Text(
            modifier = Modifier.weight(2.5f),
            text = it.descripcion.toString(),
            fontSize = 18.sp,
            fontFamily = FontFamily.SansSerif,
            textAlign = TextAlign.Center
        )
        Text(
            modifier = Modifier.weight(2.5f),
            text = it.asunto.toString(),
            fontSize = 18.sp,
            fontFamily = FontFamily.SansSerif,
            textAlign = TextAlign.Center
        )
        Text(
            modifier = Modifier.weight(2.5f),
            text = it.date.toString(),
            fontSize = 18.sp,
            fontFamily = FontFamily.SansSerif,
            textAlign = TextAlign.Center
        )
    }
}