package com.eni.demo.mcp.infrastructure.jpa.adapters

import com.eni.demo.mcp.domain.cart.model.Cart
import com.eni.demo.mcp.domain.cart.model.CartId
import com.eni.demo.mcp.domain.cart.spi.CartRepository
import com.eni.demo.mcp.infrastructure.jpa.model.CartEntity
import com.eni.demo.mcp.infrastructure.jpa.repositories.IJpaCartRepository
import org.springframework.stereotype.Component
import kotlin.jvm.optionals.getOrNull

/**
 * SQL implementation of the CartRepository interface using JPA.
 * This adapter interacts with the IJpaCartRepository to perform CRUD operations on Cart entities.
 * @param repository The JPA repository for Cart entities.
 */
@Component
class JpaCartRepositoryAdapter(
    private val repository: IJpaCartRepository
) : CartRepository {
    override fun findAll(): List<Cart> {
        return repository.findAll().map { it.toDomain() }
    }

    override fun getById(id: CartId): Cart? {
        val cartEntity = repository.findById(id.value).getOrNull()
        return cartEntity?.toDomain()
    }

    override fun create(cart: Cart): Cart {
        val cartEntity = repository.save(CartEntity.fromDomain(cart))
        return cartEntity.toDomain()
    }

    override fun update(cart: Cart): Cart {
        val cartEntity = repository.save(CartEntity.fromDomain(cart))
        return cartEntity.toDomain()
    }

    override fun deleteById(id: CartId) {
        repository.deleteById(id.value)
    }

}