package org.redisco.worldofplants.controller

import org.redisco.worldofplants.dtos.BasketItemInsertRequest
import org.redisco.worldofplants.service.UserService
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.ModelAttribute
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestParam

@Controller
class BasketController(private val userService: UserService) {

    @GetMapping("/basket")
    fun getBasket(model: Model): String {
        val username = SecurityContextHolder.getContext().authentication.name
        val basket = userService.getBasket(username)
        model.addAttribute("cart", basket)
        return "plants/basket"
    }

    @PostMapping("/plants")
    fun insertItem(@ModelAttribute basketItemInsertRequest: BasketItemInsertRequest): String {
        val username = SecurityContextHolder.getContext().authentication.name
        userService.addItemToBasket(username, basketItemInsertRequest.plantName, basketItemInsertRequest.quantity)
        return "redirect:/plants"
    }

    @PostMapping("/basket/delete")
    fun deleteItem(@RequestParam plantName: String): String {
        val username = SecurityContextHolder.getContext().authentication.name
        userService.deleteBasketItem(username, plantName)
        return "redirect:/basket"
    }
}
