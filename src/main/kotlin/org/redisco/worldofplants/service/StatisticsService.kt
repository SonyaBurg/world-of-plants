package org.redisco.worldofplants.service

import org.redisco.worldofplants.dtos.GeneralStatistics
import org.redisco.worldofplants.repository.OrderedPlantsRepository
import org.redisco.worldofplants.repository.OrdersRepository
import org.redisco.worldofplants.repository.UserRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

interface StatisticsService {
    fun getGeneralStatistics(): GeneralStatistics
}

@Service
class StatisticsServiceImpl(
    private val ordersRepository: OrdersRepository,
    private val userRepository: UserRepository,
    private val orderedPlantsRepository: OrderedPlantsRepository,
) : StatisticsService {
    @Transactional(readOnly = true)
    override fun getGeneralStatistics(): GeneralStatistics {
        val totalOrders = ordersRepository.count()
        val totalUsers = userRepository.count()
        val totalRevenue = ordersRepository.findTotalRevenue()
        val totalPlantsSold = orderedPlantsRepository.getAllOrderedCount()
        return GeneralStatistics(
            totalRevenue = totalRevenue / 100,
            totalUsers = totalUsers,
            totalOrders = totalOrders,
            totalPlantSold = totalPlantsSold
        )
    }
}