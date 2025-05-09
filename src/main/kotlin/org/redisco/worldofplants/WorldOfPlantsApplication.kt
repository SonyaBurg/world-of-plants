package org.redisco.worldofplants

import org.redisco.worldofplants.config.admin.AdminProperties
import org.redisco.worldofplants.service.UserService
import org.springframework.boot.CommandLineRunner
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.boot.runApplication
import org.springframework.stereotype.Component

@SpringBootApplication
@EnableConfigurationProperties(AdminProperties::class)
class WorldOfPlantsApplication

fun main(args: Array<String>) {
    runApplication<WorldOfPlantsApplication>(*args)
}

@Component
class Runner(private val service: UserService) : CommandLineRunner {
    override fun run(vararg args: String?) {
//        service.getOrdersForCustomer("john.doe@example.com").forEach { println(it) }
    }
}
