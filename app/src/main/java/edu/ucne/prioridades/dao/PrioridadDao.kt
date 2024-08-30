package edu.ucne.prioridades.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Upsert
import edu.ucne.prioridades.entities.PrioridadEntity
import kotlinx.coroutines.flow.Flow


@Dao
interface PrioridadDao {
    @Upsert()
    suspend fun save(prioridad: PrioridadEntity)

    @Query(
        """
            Select *
            From Prioridades
            where prioridadId = :id
            Limit 1
        """
    )
    suspend fun find(id: Int): PrioridadEntity?

    @Delete
    suspend fun delete(prioridad: PrioridadEntity)

    @Query("Select * From Prioridades")
    fun getAll(): Flow<List<PrioridadEntity>>
}