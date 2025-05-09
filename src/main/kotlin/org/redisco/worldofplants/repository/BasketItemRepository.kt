package org.redisco.worldofplants.repository

import org.redisco.worldofplants.entities.BasketItemEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Modifying
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface BasketItemRepository : JpaRepository<BasketItemEntity, UUID> {
    @Query("SELECT b FROM BasketItemEntity b WHERE b.user.id = :userId AND b.plant.id = :plantId")
    fun findByUserIdAndPlantId(
        @Param("userId") userId: UUID,
        @Param("plantId") plantId: UUID
    ): BasketItemEntity?

    @Query("SELECT b FROM BasketItemEntity b WHERE b.user.id = :userId")
    fun findBasketItemEntityByUserId(userId: UUID): List<BasketItemEntity>

    @Modifying
    @Query("DELETE FROM BasketItemEntity b WHERE b.plant.id = :plantId")
    fun deleteBasketItemEntitiesByPlantId(plantId: UUID)
}