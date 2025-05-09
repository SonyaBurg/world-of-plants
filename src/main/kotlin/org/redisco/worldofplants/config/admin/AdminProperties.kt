package org.redisco.worldofplants.config.admin

import org.springframework.boot.context.properties.ConfigurationProperties

@ConfigurationProperties(prefix = "app.admin")
class AdminProperties(val username: String, val password: String, val email: String)