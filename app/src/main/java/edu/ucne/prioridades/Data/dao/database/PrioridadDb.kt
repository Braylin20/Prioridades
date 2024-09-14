package edu.ucne.prioridades.Data.dao.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverter
import androidx.room.TypeConverters
import edu.ucne.prioridades.Data.dao.converters.Converters
import edu.ucne.prioridades.Data.dao.dao.PrioridadDao
import edu.ucne.prioridades.Data.dao.dao.TicketDao
import edu.ucne.prioridades.Data.dao.entities.PrioridadEntity
import edu.ucne.prioridades.Data.dao.entities.TicketEntity

@Database(
    entities = [
        PrioridadEntity::class,
        TicketEntity::class
    ],
    version = 2,
    exportSchema = false
)
@TypeConverters(Converters::class)
abstract class PrioridadDb: RoomDatabase() {
    abstract fun prioridadDao(): PrioridadDao
    abstract fun ticketDao(): TicketDao
}
