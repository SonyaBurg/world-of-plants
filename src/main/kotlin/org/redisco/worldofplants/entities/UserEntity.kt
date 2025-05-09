package org.redisco.worldofplants.entities

import jakarta.persistence.*
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.UserDetails
import java.util.*

@Entity
@Table(name = "users")
class UserEntity(
    @Column(name = "login", nullable = false)
    var login: String,

    @Column(name = "email", unique = true)
    val email: String,

    @Column(name = "pass")
    var pass: String,

    @Column(name = "role")
    @Enumerated(EnumType.STRING)
    val role: Role = Role.USER,

    @Column(name = "first_name")
    var firstName: String? = null,

    @Column(name = "last_name")
    var lastName: String? = null,

    @Column(name = "phone_number")
    var phoneNumber: String? = null,

    @Column(name = "address")
    var address: String? = null

) : UserDetails {
    @Id
    @GeneratedValue
    lateinit var id: UUID

    @OneToMany(mappedBy = "user")
    val orders: MutableSet<OrderEntity> = mutableSetOf()

    @OneToMany(mappedBy = "user", cascade = [CascadeType.ALL], orphanRemoval = true)
    val basketItems: MutableSet<BasketItemEntity> = mutableSetOf()

    override fun getAuthorities(): Collection<GrantedAuthority?> =
        listOf(SimpleGrantedAuthority("ROLE_${role.name}"))

    override fun getPassword(): String? = pass

    override fun getUsername(): String = login
    override fun isAccountNonExpired(): Boolean = true
    override fun isAccountNonLocked(): Boolean = true
    override fun isCredentialsNonExpired(): Boolean = true
    override fun isEnabled(): Boolean = true

}

enum class Role {
    USER,
    ADMIN
}
