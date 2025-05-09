package org.redisco.worldofplants.entities

import jakarta.persistence.*
import java.util.*

@Entity
@Table(name = "ordered_plants")
class OrderedPlantEntity(
    @Column(name = "name", nullable = false, length = 100)
    val name: String,

    @Column(nullable = false)
    var quantity: Int,

    @ManyToOne
    @JoinColumn(name = "order_id", nullable = false)
    val order: OrderEntity,

    @Column(name = "pictureLink", nullable = false)
    val pictureLink: String,

    @Column(name ="price", nullable = false)
    val price: Int,

    @Column(name = "description", nullable = false)
    val description: String,

    @Column(name = "category")
    val category: String
) {
    @Id
    @GeneratedValue
    lateinit var id: UUID
}
