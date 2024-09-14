package edu.ucne.prioridades.presentation.navigation.ticket

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import edu.ucne.prioridades.Data.dao.entities.TicketEntity
import edu.ucne.prioridades.Data.repository.TicketRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.util.Date
import javax.inject.Inject


@HiltViewModel
class TicketViewModel @Inject constructor(
    private val ticketRepository: TicketRepository
) : ViewModel(){
    private val _uiState = MutableStateFlow(UiState())
    val uiState = _uiState.asStateFlow()

    init{
        getTickets()
    }

    fun save(){
        viewModelScope.launch {
            ticketRepository.save(_uiState.value.toEntity())
        }
    }

    private fun getTickets(){
        viewModelScope.launch {
            ticketRepository.getTickets().collect{tickets ->
                _uiState.update {
                    it.copy(tickets = tickets)
                }
            }
        }
    }

    fun delete(ticket: TicketEntity){
        viewModelScope.launch {
            ticketRepository.delete(ticket)
        }
    }

    fun selectedTicket(ticketId:Int){
        viewModelScope.launch {
            if(ticketId >0){
                val ticket = ticketRepository.getTicket(ticketId)
                _uiState.update {
                    it.copy(
                        ticketId = ticket?.ticketId,
                        prioridadId = ticket?.prioridadId,
                        asunto = ticket?.asunto ?: "",
                        cliente = ticket?.cliente ?: "",
                        descripcion = ticket?.descripcion ?: "",
                        date = ticket?.date
                    )
                }
            }
        }
    }

    fun nuevo(){
        _uiState.update {
            it.copy(
                ticketId = null,
                prioridadId = null,
                asunto = "",
                descripcion = "",
                cliente = "",
                date = null
            )
        }
    }

    fun onAsuntoChange(asunto: String){
        _uiState.update {
            it.copy(asunto = asunto)
        }
    }

    fun onClienteChange(cliente: String){
        _uiState.update {
            it.copy(cliente = cliente)
        }
    }

    fun onDescripcionChange(descripcion: String){
        _uiState.update {
            it.copy(descripcion = descripcion)
        }
    }
     fun onDateChange(date: Date){
         _uiState.update {
             it.copy(date = date)
         }
     }
    fun onPrioridadIdChange(prioridadId: Int){
        _uiState.update {
            it.copy(prioridadId = prioridadId)
        }
    }

}