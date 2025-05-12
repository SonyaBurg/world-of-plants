package org.redisco.worldofplants.repository

import org.redisco.worldofplants.entities.PlantEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import java.util.UUID

interface PlantsRepository : JpaRepository<PlantEntity, UUID> {
    fun findPlantEntitiesByCategory(category: String): List<PlantEntity>
    fun findPlantEntityByName(name: String): PlantEntity?

    @Query("SELECT p FROM PlantEntity p WHERE LOWER(p.name) LIKE LOWER(CONCAT('%', :searchTerm, '%')) OR LOWER(p.description) LIKE LOWER(CONCAT('%', :searchTerm, '%'))")
    fun searchPlants(@Param("searchTerm") searchTerm: String): List<PlantEntity>
}
