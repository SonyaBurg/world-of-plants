package org.redisco.worldofplants.repository

import org.redisco.worldofplants.entities.PlantEntity
import org.springframework.data.jpa.repository.JpaRepository
import java.util.UUID

interface PlantsRepository : JpaRepository<PlantEntity, UUID> {
    fun findPlantEntitiesByCategory(category: String): List<PlantEntity>
    fun findPlantEntityByName(name: String): PlantEntity?
}