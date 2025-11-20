package com.eni.demo.mcp.infrastructure.jpa.adapters

import com.eni.demo.mcp.domain.product.model.Product
import com.eni.demo.mcp.domain.product.model.ProductId
import com.eni.demo.mcp.domain.product.spi.ProductRepository
import com.eni.demo.mcp.infrastructure.jpa.model.ProductEntity
import com.eni.demo.mcp.infrastructure.jpa.repositories.IJpaProductRepository
import org.springframework.stereotype.Component
import kotlin.jvm.optionals.getOrNull

/**
 * SQL implementation of the ProductRepository interface using JPA.
 * This adapter interacts with the IJpaProductRepository to perform CRUD operations on Product entities.
 * @param repository The JPA repository for Product entities.
 */
@Component
class JpaProductRepositoryAdapter(
    private val repository: IJpaProductRepository
) : ProductRepository {
    override fun findAll(): List<Product> {
        return repository.findAll().map { it.toDomain() }
    }

    override fun getById(id: ProductId): Product? {
        val productEntity = repository.findById(id.value).getOrNull()
        return productEntity?.toDomain()
    }

    override fun create(product: Product): Product {
        val productEntity = repository.save(ProductEntity.fromDomain(product))
        return productEntity.toDomain()
    }

    override fun createBulk(products: List<Product>): List<Product> {
        val productEntities = repository.saveAll<ProductEntity>(
            products.map { ProductEntity.fromDomain(it)
            })
        return productEntities.map { productEntity -> productEntity.toDomain() }
    }

    override fun update(product: Product): Product {
        val productEntity = repository.save(ProductEntity.fromDomain(product))
        return productEntity.toDomain()
    }

    override fun deleteById(id: ProductId) {
        repository.deleteById(id.value)
    }
}