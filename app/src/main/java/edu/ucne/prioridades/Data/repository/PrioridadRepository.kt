package edu.ucne.prioridades.Data.repository

import edu.ucne.prioridades.Data.dao.dao.PrioridadDao
import edu.ucne.prioridades.Data.dao.entities.PrioridadEntity
import javax.inject.Inject

class PrioridadRepository @Inject constructor(
    private val prioridadDao: PrioridadDao
) {
    suspend fun save(prioridad: PrioridadEntity) = prioridadDao.save(prioridad)

    suspend fun getPrioridad(id:Int) = prioridadDao.find(id)

    suspend fun delete(prioridad: PrioridadEntity) = prioridadDao.delete(prioridad)

    fun getPrioridades() = prioridadDao.getAll()

    suspend fun exist(descripcion: String) = prioridadDao.findByDescripcion(descripcion)
}
