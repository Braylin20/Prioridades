package edu.ucne.prioridades.Data.dao.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Upsert
import edu.ucne.prioridades.Data.dao.entities.TicketEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface TicketDao {
    @Upsert
    suspend fun save(ticket: TicketEntity)

    @Query("""
        Select * From Tickets Where ticketId = :id Limit 1
        """)
    suspend fun find(id: Int): TicketEntity?

    @Delete
    suspend fun delete(ticket: TicketEntity)

    @Query("Select * From Tickets")
    fun getAll(): Flow<List<TicketEntity>>
}