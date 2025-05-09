package org.redisco.worldofplants.config

import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.security.core.Authentication
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler
import org.springframework.stereotype.Component

@Component
class AuthSuccessHandler : SavedRequestAwareAuthenticationSuccessHandler() {

    override fun onAuthenticationSuccess(
        request: HttpServletRequest,
        response: HttpServletResponse,
        authentication: Authentication
    ) {
        val authorities = authentication.authorities
        val isAdmin = authorities.any { it.authority == "ROLE_ADMIN" }

        defaultTargetUrl = if (isAdmin) {
            "/admin/dashboard"
        } else {
            "/"
        }

        super.onAuthenticationSuccess(request, response, authentication)
    }
}