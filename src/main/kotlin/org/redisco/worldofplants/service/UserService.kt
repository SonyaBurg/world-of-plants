package org.redisco.worldofplants.service

import org.redisco.worldofplants.controller.dtos.Basket
import org.redisco.worldofplants.controller.dtos.UpdateUserRequest
import org.redisco.worldofplants.controller.dtos.User
import org.redisco.worldofplants.data.entities.BasketItemEntity
import org.redisco.worldofplants.data.entities.Role
import org.redisco.worldofplants.data.entities.UserEntity
import org.redisco.worldofplants.data.repository.BasketItemRepository
import org.redisco.worldofplants.data.repository.PlantsRepository
import org.redisco.worldofplants.data.repository.UserRepository
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

interface UserService {
    fun getBasket(username: String): Basket
    fun registerUser(registerRequest: RegisterRequest, admin: Boolean = false)
    fun getCurrentUser(username: String): User
    fun updateUser(username: String, updateRequest: UpdateUserRequest): Result<User>
    fun addItemToBasket(login: String, itemName: String, quantity: Int): BasketItemEntity
    fun deleteBasketItem(username: String, plantName: String)
}

@Service
class UserServiceImpl(
    private val userRepository: UserRepository,
    private val passwordEncoder: PasswordEncoder,
    private val plantsRepository: PlantsRepository,
    private val basketItemsRepository: BasketItemRepository,
) : UserDetailsService, UserService {
    @Transactional(readOnly = true)
    override fun getBasket(username: String): Basket {
        val user = userRepository.findUserEntityByLogin(username)
            ?: error("Customer with username $username not found")
        val basketItems = user.basketItems.map { it -> it.compose() }
        return Basket(basketItems)
    }

    @Transactional
    override fun addItemToBasket(login: String, itemName: String, quantity: Int): BasketItemEntity {
        val user = userRepository.findUserEntityByLogin(login)
            ?: error("User not found with login: $login")
        val plant = plantsRepository.findPlantEntityByName(itemName)
            ?: error("Plant with name $itemName not found")

        val existingItem = basketItemsRepository.findByUserIdAndPlantId(user.id, plant.id)
        if (existingItem != null) {
            existingItem.quantity += quantity
            return basketItemsRepository.save(existingItem)
        }

        val newItem = BasketItemEntity(
            quantity = quantity,
            plant = plant,
            user = user
        )
        return basketItemsRepository.save(newItem)
    }

    @Transactional(readOnly = true)
    override fun loadUserByUsername(username: String): UserDetails {
        return userRepository.findByLogin(username) ?: throw UsernameNotFoundException("User not found")
    }

    @Transactional
    override fun registerUser(registerRequest: RegisterRequest, admin: Boolean) {
        if (userRepository.existsUserEntityByLogin(registerRequest.login)) {
            throw IllegalArgumentException("Username already exists")
        }
        if (userRepository.existsUserEntityByEmail(registerRequest.email)) {
            throw IllegalArgumentException("Email already exists")
        }

        val user = UserEntity(
            login = registerRequest.login,
            pass = passwordEncoder.encode(registerRequest.password),
            email = registerRequest.email,
            role = if (admin) Role.ADMIN else Role.USER,
            phoneNumber = registerRequest.phoneNumber
        )
        userRepository.save(user)
    }

    @Transactional(readOnly = true)
    override fun getCurrentUser(username: String): User {
        val userEntity = userRepository.findByLogin(username)
            ?: throw UsernameNotFoundException("User not found")

        return userEntity.compose()
    }

    @Transactional
    override fun updateUser(username: String, updateRequest: UpdateUserRequest): Result<User> {
        val userEntity = userRepository.findByLogin(username)
            ?: throw UsernameNotFoundException("User not found")

        if (updateRequest.login != username && userRepository.existsUserEntityByLogin(updateRequest.login)) {
            return Result.failure(IllegalArgumentException("Username already exists"))
        }

        userEntity.apply {
            login = updateRequest.login
            firstName = updateRequest.firstName
            lastName = updateRequest.lastName
            phoneNumber = updateRequest.phoneNumber
            address = updateRequest.address
        }

        val savedUser = userRepository.save(userEntity)
        return Result.success(savedUser.compose())
    }

    @Transactional
    override fun deleteBasketItem(username: String, plantName: String) {
        val user = userRepository.findUserEntityByLogin(username)
            ?: error("User not found with login: $username")
        val plant = plantsRepository.findPlantEntityByName(plantName)
            ?: error("Plant with name $plantName not found")

        val basketItem = basketItemsRepository.findByUserIdAndPlantId(user.id, plant.id)
            ?: error("Basket item not found")

        basketItemsRepository.delete(basketItem)
    }
}

data class RegisterRequest(
    val login: String,
    val password: String,
    val email: String,
    val phoneNumber: String? = null

)
