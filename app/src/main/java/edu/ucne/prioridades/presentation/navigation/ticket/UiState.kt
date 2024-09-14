package edu.ucne.prioridades.presentation.navigation.ticket

import edu.ucne.prioridades.Data.dao.entities.PrioridadEntity
import edu.ucne.prioridades.Data.dao.entities.TicketEntity
import java.util.Date

data class UiState(
    val ticketId: Int? = null,
    val prioridadId: Int? = null,
    val date: Date? = null,
    val cliente: String = "",
    val asunto: String = "",
    val descripcion: String = "",
    val errorMessage: String? = null,
    val tickets: List<TicketEntity> = emptyList()
)

fun UiState.toEntity() = TicketEntity(
    ticketId = ticketId,
    prioridadId = prioridadId,
    date = date,
    cliente = cliente,
    asunto = asunto,
    descripcion = descripcion
)