package org.redisco.worldofplants.data.repository

import org.redisco.worldofplants.data.entities.OrderedPlantEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import java.util.UUID

interface OrderedPlantsRepository : JpaRepository<OrderedPlantEntity, UUID> {
    @Query("SELECT SUM(o.quantity) FROM OrderedPlantEntity o")
    fun getAllOrderedCount(): Int
}