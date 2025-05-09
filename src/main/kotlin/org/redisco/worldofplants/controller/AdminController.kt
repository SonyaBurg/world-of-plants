package org.redisco.worldofplants.controller

import org.redisco.worldofplants.dtos.Order
import org.redisco.worldofplants.dtos.Plant
import org.redisco.worldofplants.entities.OrderStatus
import org.redisco.worldofplants.service.OrdersService
import org.redisco.worldofplants.service.PlantsService
import org.springframework.http.ResponseEntity
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.ResponseBody
import org.springframework.web.servlet.mvc.support.RedirectAttributes


@Controller
@RequestMapping("/admin")
@PreAuthorize("hasRole('ADMIN')")
class AdminController(
    private val plantService: PlantsService,
    private val ordersService: OrdersService
) {

    @GetMapping("/plants")
    fun plants(model: Model): String {
        val plants = plantService.fetchAllPlants()
        model.addAttribute("plants", plants)
        return "admin/plants"
    }

    @PostMapping("/plants/add")
    fun addPlant(
        @RequestParam name: String,
        @RequestParam category: String,
        @RequestParam description: String,
        @RequestParam priceCents: Int,
        @RequestParam pictureLink: String,
        redirectAttributes: RedirectAttributes
    ): String {
        try {
            val plant = Plant(
                name = name,
                category = category,
                description = description,
                price = priceCents,
                pictureLink = pictureLink
            )
            plantService.addPlant(plant)
            redirectAttributes.addAttribute("success", "Plant added successfully")
        } catch (e: Exception) {
            redirectAttributes.addAttribute("error", "Failed to add plant: ${e.message}")
        }
        return "redirect:/admin/plants"
    }

    @PostMapping("/plants/update")
    fun updatePlant(
        @RequestParam originalName: String,
        @RequestParam name: String,
        @RequestParam category: String,
        @RequestParam description: String,
        @RequestParam priceCents: Int,
        @RequestParam pictureLink: String,
        redirectAttributes: RedirectAttributes
    ): String {
        try {
            val updatedPlant = Plant(
                name = name,
                category = category,
                description = description,
                price = priceCents,
                pictureLink = pictureLink
            )
            plantService.updatePlant(originalName, updatedPlant)
            redirectAttributes.addAttribute("success", "Plant updated successfully")
        } catch (e: Exception) {
            redirectAttributes.addAttribute("error", "Failed to update plant: ${e.message}")
        }
        return "redirect:/admin/plants"
    }

    @PostMapping("/plants/delete")
    fun deletePlant(
        @RequestParam name: String,
        redirectAttributes: RedirectAttributes
    ): String {
        try {
            plantService.deletePlant(name)
            redirectAttributes.addAttribute("success", "Plant deleted successfully")
        } catch (e: Exception) {
            redirectAttributes.addAttribute("error", "Failed to delete plant: ${e.message}")
        }
        return "redirect:/admin/plants"
    }

    @GetMapping("/orders")
    fun viewOrders(
        model: Model,
        @RequestParam(required = false) status: String?,
    ): String {
        val orders = if (status.isNullOrEmpty()) {
            ordersService.getAllOrders()
        } else {
            ordersService.getOrdersByStatus(OrderStatus.valueOf(status.uppercase()))
        }

        model.addAttribute("orders", orders)
        return "admin/orders"
    }

    @PostMapping("/orders/update-status")
    fun updateOrderStatus(
        @RequestParam orderNumber: Int,
        @RequestParam status: String,
        redirectAttributes: RedirectAttributes
    ): String {
        try {
            ordersService.updateOrderStatus(orderNumber, OrderStatus.valueOf(status.uppercase()))
            redirectAttributes.addAttribute("success", "Order status updated successfully")
        } catch (e: Exception) {
            redirectAttributes.addAttribute("error", "Failed to update order status: ${e.message}")
        }
        return "redirect:/admin/orders"
    }

    @GetMapping("/orders/{orderNumber}/details")
    @ResponseBody
    fun getOrderDetails(@PathVariable orderNumber: Int): ResponseEntity<Order> {
        return try {
            val order = ordersService.getOrderDetails(orderNumber)
            ResponseEntity.ok(order)
        } catch (_: Exception) {
            ResponseEntity.notFound().build()
        }
    }

    @GetMapping("/dashboard")
    fun dashboard(model: Model): String {
        val orderStatusStatistics = ordersService.countOrdersByStatus()
        val monthlyRevenueData = ordersService.getMonthlyRevenue()
//        val generalStatistics = ordersService.getGeneralStatistics()
        model.addAttribute("orderStatistics", orderStatusStatistics)
        model.addAttribute("monthlyRevenue", monthlyRevenueData)

        return "admin/dashboard"
    }

}
