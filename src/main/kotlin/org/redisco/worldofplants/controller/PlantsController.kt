package org.redisco.worldofplants.controller

import org.redisco.worldofplants.service.PlantsService
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestParam

@Controller
class PlantsController(
    private val plantService: PlantsService,
) {
    @GetMapping("/")
    fun homepage(model: Model): String {
        return "index"
    }

    @GetMapping("/plants")
    fun listPlants(
        @RequestParam(required = false) category: String?,
        model: Model
    ): String {
        val plants = if (category != null) {
            plantService.fetchPlantsByCategory(category)
        } else {
            plantService.fetchAllPlants()
        }
        val categories = plantService.fetchAllCategories()
        model.addAttribute("plants", plants)
        model.addAttribute("categories", categories)
        model.addAttribute("selectedCategory", category)
        return "plants/list"
    }
}