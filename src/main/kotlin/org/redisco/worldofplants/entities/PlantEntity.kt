package org.redisco.worldofplants.entities

import jakarta.persistence.*
import java.util.*

@Entity
@Table(name = "plants")
class PlantEntity(
    @Column(name = "name", nullable = false)
    var name: String,

    @Column(name = "category", nullable = false)
    var category: String,

    @Column(name = "description", nullable = false)
    var description: String,

    @Column(name = "items_in_stock", nullable = false)
    var itemsInStock: Int,

    @Column(name = "price", nullable = false)
    var price: Int,

    @Column(name = "picture_link", nullable = false)
    var pictureLink: String
) {
    @Id
    @GeneratedValue
    lateinit var id: UUID
}
