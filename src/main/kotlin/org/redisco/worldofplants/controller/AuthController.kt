package org.redisco.worldofplants.controller

import org.redisco.worldofplants.service.RegisterRequest
import org.redisco.worldofplants.service.UserService
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.ModelAttribute
import org.springframework.web.bind.annotation.PostMapping

@Controller
class AuthController(
    private val userService: UserService
) {
    @GetMapping("/login")
    fun login(): String = "login"

    @GetMapping("/register")
    fun register(model: Model): String {
        model.addAttribute("registerRequest", RegisterRequest("", "", ""))
        return "register"
    }

    @PostMapping("/register")
    fun registerUser(@ModelAttribute registerRequest: RegisterRequest): String {
        return try {
            userService.registerUser(registerRequest)
            "redirect:/login"
        } catch (_: IllegalArgumentException) {
            "redirect:/register?error"
        }
    }


    @PostMapping("/admin/register")
    @PreAuthorize("hasRole('ADMIN')")
    fun registerAdminUser(@ModelAttribute registerRequest: RegisterRequest): String {
        return try {
            userService.registerUser(registerRequest, admin = true)
            "redirect:/admin/dashboard?adminRegistered"
        } catch (_: IllegalArgumentException) {
            "redirect:/admin/register?error"
        }
    }

}