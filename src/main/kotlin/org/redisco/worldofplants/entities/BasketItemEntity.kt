package org.redisco.worldofplants.entities

import jakarta.persistence.*
import java.util.*

@Entity
@Table(name = "basket_items")
class BasketItemEntity(
    @Column(nullable = false)
    var quantity: Int,

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    val user: UserEntity,

    @ManyToOne
    @JoinColumn(name = "plant_id", nullable = false)
    val plant: PlantEntity
) {
    @Id
    @GeneratedValue
    lateinit var id: UUID

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is BasketItemEntity) return false
        return id == other.id
    }

    override fun hashCode(): Int {
        return id.hashCode()
    }

    override fun toString(): String {
        return "BasketItemEntity(id=$id, quantity=$quantity)"
    }
}