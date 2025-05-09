package org.redisco.worldofplants.controller

import org.redisco.worldofplants.service.OrdersService
import org.redisco.worldofplants.service.UserService
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping

@Controller
class OrdersController(
    private val ordersService: OrdersService,
    private val userService: UserService
) {
    @GetMapping("/orders")
    fun getOrders(model: Model): String {
        val username = SecurityContextHolder.getContext().authentication.name
        val orders = ordersService.getOrdersForCustomer(username)
        model.addAttribute("orders", orders)
        return "orders"
    }

    @PostMapping("/orders")
    fun placeOder(model: Model): String {
        val username = SecurityContextHolder.getContext().authentication.name
        val address = userService.getCurrentUser(username).address
        if (address.isEmpty()) {
            return "redirect:/account?addressRequired"
        }
        val orders = ordersService.placeOrder(username, address)
        model.addAttribute("orders", orders)
        return "redirect:/orders"
    }
}