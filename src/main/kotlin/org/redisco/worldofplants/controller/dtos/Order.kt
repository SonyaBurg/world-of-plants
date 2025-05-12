package org.redisco.worldofplants.controller.dtos

import org.redisco.worldofplants.data.entities.OrderStatus

data class OrderItem(
    val plant: Plant,
    val quantity: Int
)

data class Order(
    val items: List<OrderItem>,
    val user: User,
    val address: String,
    val date: String,
    val status: OrderStatus,
    val orderNumber: Int,
    val totalPrice: Double
) {
    val subTotalPrice = items.sumOf { it.plant.price * it.quantity }
    val shippingPrice = subTotalPrice * 0.05
}