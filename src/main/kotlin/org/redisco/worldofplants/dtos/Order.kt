package org.redisco.worldofplants.dtos

import org.redisco.worldofplants.entities.OrderStatus

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
    val orderNumber: Int
) {
    val subTotalPrice = items.sumOf { it.plant.price * it.quantity }
    val shippingPrice = subTotalPrice * 0.05
    val totalPrice: Double = subTotalPrice + shippingPrice
}