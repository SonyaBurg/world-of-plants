package org.redisco.worldofplants.dtos

data class BasketItem(
    val quantity: Int,
    val plant: Plant
)

data class Basket(val items: List<BasketItem>) {
    val subTotalPrice: Int = items.sumOf { it.plant.price * it.quantity }
    val shippingPrice = subTotalPrice * 0.05
    val totalPrice = subTotalPrice + shippingPrice
}