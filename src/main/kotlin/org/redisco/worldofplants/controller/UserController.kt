package org.redisco.worldofplants.controller

import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.redisco.worldofplants.controller.dtos.UpdateUserRequest
import org.redisco.worldofplants.service.UserService
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.ModelAttribute
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.servlet.mvc.support.RedirectAttributes

@Controller
class UserController(private val userService: UserService) {
    @GetMapping("/account")
    fun getAccountDetails(model: Model): String {
        val authentication = SecurityContextHolder.getContext().authentication
        val currentUser = userService.getCurrentUser(authentication.name)
        model.addAttribute("user", currentUser)
        return "account"
    }

    @PostMapping("/account")
    fun updateAccountDetails(
        @ModelAttribute updateRequest: UpdateUserRequest,
        model: Model,
        redirectAttributes: RedirectAttributes
    ): String {
        val authentication = SecurityContextHolder.getContext().authentication
        return userService.updateUser(authentication.name, updateRequest)
            .fold(
                onSuccess = {
                    redirectAttributes.addFlashAttribute("message", "Profile updated successfully")
                    "redirect:/account"
                },
                onFailure = { e ->
                    model.addAttribute("error", e.message)
                    model.addAttribute("user", userService.getCurrentUser(authentication.name))
                    "account"
                }
            )
    }

    @PostMapping("/logout")
    fun performLogout(request: HttpServletRequest, response: HttpServletResponse): String {
        val authentication = SecurityContextHolder.getContext().authentication
        if (authentication != null) {
            SecurityContextLogoutHandler().logout(request, response, authentication)
        }
        return "redirect:/"
    }
}