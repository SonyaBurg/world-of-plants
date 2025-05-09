package org.redisco.worldofplants.entities

import jakarta.persistence.*
import java.time.LocalDateTime
import java.util.*

@Entity
@Table(name = "orders")
class OrderEntity(
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    val user: UserEntity,

    @Column(name = "total_price", nullable = false)
    val totalPrice: Double,

    @Column(nullable = false)
    var address: String,

    @Column(nullable = false)
    val date: LocalDateTime,

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    var status: OrderStatus
) {
    @Id
    @GeneratedValue
    lateinit var id: UUID

    @Column(name = "order_number", nullable = false)
    @SequenceGenerator(name = "order_number_seq", sequenceName = "order_number_seq", allocationSize = 1, initialValue = 1)
    var orderNumber: Int = 0

    @OneToMany(mappedBy = "order", cascade = [CascadeType.ALL], orphanRemoval = true)
    val orderedPlants: MutableSet<OrderedPlantEntity> = mutableSetOf()
}