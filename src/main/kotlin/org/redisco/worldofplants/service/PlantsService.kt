package org.redisco.worldofplants.service

import org.redisco.worldofplants.controller.dtos.Plant
import org.redisco.worldofplants.data.entities.PlantEntity
import org.redisco.worldofplants.data.repository.BasketItemRepository
import org.redisco.worldofplants.data.repository.PlantsRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional


interface PlantsService {
    fun fetchAllPlants(): List<Plant>
    fun fetchAllCategories(): List<String>
    fun fetchPlantsByCategory(category: String): List<Plant>
    fun addPlant(plant: Plant)
    fun updatePlant(originalName: String, updatedPlant: Plant)
    fun deletePlant(name: String)
    fun searchPlants(searchTerm: String): List<Plant>
}

@Service
class PlantsServiceImpl(
    private val plantsRepository: PlantsRepository,
    val basketItemRepository: BasketItemRepository
) : PlantsService {
    @Transactional(readOnly = true)
    override fun fetchAllPlants(): List<Plant> {
        return plantsRepository.findAll().map { it.compose() }
    }

    @Transactional(readOnly = true)
    override fun fetchPlantsByCategory(category: String): List<Plant> {
        return plantsRepository.findPlantEntitiesByCategory(category).map { it.compose() }
    }

    override fun fetchAllCategories(): List<String> = plantsRepository.findAll()
        .map { it.category }
        .distinct()
        .sorted()


    @Transactional
    override fun addPlant(plant: Plant) {
        // Check if plant with same name already exists
        if (plantsRepository.findPlantEntityByName(plant.name) != null) {
            throw IllegalArgumentException("A plant with this name already exists")
        }

        val plantEntity = PlantEntity(
            name = plant.name,
            category = plant.category,
            description = plant.description,
            price = plant.price,
            pictureLink = plant.pictureLink
        )

        plantsRepository.save(plantEntity)
    }

    @Transactional
    override fun updatePlant(originalName: String, updatedPlant: Plant) {
        val existingPlant = plantsRepository.findPlantEntityByName(originalName)
            ?: throw IllegalArgumentException("Plant not found: $originalName")

        if (originalName != updatedPlant.name &&
            plantsRepository.findPlantEntityByName(updatedPlant.name) != null
        ) {
            throw IllegalArgumentException("A plant with the name ${updatedPlant.name} already exists")
        }

        existingPlant.apply {
            name = updatedPlant.name
            category = updatedPlant.category
            description = updatedPlant.description
            price = updatedPlant.price
            pictureLink = updatedPlant.pictureLink
        }

        plantsRepository.save(existingPlant)
    }

    @Transactional
    override fun deletePlant(name: String) {
        val plant = plantsRepository.findPlantEntityByName(name)
            ?: throw IllegalArgumentException("Plant not found: $name")

        basketItemRepository.deleteBasketItemEntitiesByPlantId(plant.id)
        plantsRepository.delete(plant)
    }

    @Transactional(readOnly = true)
    override fun searchPlants(searchTerm: String): List<Plant> {
        return plantsRepository.searchPlants(searchTerm).map { it.compose() }
    }
}
