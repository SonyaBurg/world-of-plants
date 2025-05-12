package org.redisco.worldofplants.config.admin

import org.redisco.worldofplants.data.entities.Role
import org.redisco.worldofplants.data.repository.UserRepository
import org.redisco.worldofplants.service.RegisterRequest
import org.redisco.worldofplants.service.UserService
import org.slf4j.LoggerFactory
import org.springframework.boot.CommandLineRunner
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class AdminInitializer(
    private val userService: UserService,
    private val userRepository: UserRepository,
    private val adminProperties: AdminProperties,
) {
    @Bean
    fun initializeAdmin(): CommandLineRunner {
        return CommandLineRunner {
            val adminExists = userRepository.findAll().any { it.role == Role.ADMIN }

            if (!adminExists) {
                val adminUsername = adminProperties.username
                val adminPassword = adminProperties.password
                val adminEmail = adminProperties.email

                val registerRequest = RegisterRequest(adminUsername, adminPassword, adminEmail)
                userService.registerUser(registerRequest, admin = true)
                LOG.info("Default admin user created: $adminUsername")
            }
        }
    }

    companion object {
        private val LOG = LoggerFactory.getLogger(AdminInitializer::class.java)
    }
}