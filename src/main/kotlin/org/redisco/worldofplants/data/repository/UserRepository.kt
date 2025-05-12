package org.redisco.worldofplants.data.repository

import org.redisco.worldofplants.data.entities.UserEntity
import org.springframework.data.jpa.repository.JpaRepository
import java.util.UUID

interface UserRepository : JpaRepository<UserEntity, UUID> {
    fun findUserEntityByEmail(email: String): UserEntity?
    fun findByLogin(login: String): UserEntity?
    fun existsUserEntityByLogin(login: String): Boolean
    fun findUserEntityByLogin(login: String): UserEntity?
    fun existsUserEntityByEmail(email: String): Boolean
}