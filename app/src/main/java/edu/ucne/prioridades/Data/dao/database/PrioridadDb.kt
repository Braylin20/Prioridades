package edu.ucne.prioridades.Data.dao.database

import androidx.room.Database
import androidx.room.RoomDatabase
import edu.ucne.prioridades.Data.dao.dao.PrioridadDao
import edu.ucne.prioridades.Data.dao.entities.PrioridadEntity

@Database(
    entities = [
        PrioridadEntity::class
    ],
    version = 1,
    exportSchema = false
)

abstract class PrioridadDb: RoomDatabase() {
    abstract fun prioridadDao(): PrioridadDao
}