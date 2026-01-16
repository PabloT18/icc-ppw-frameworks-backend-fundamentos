package ec.edu.ups.icc.fundamentos01.products.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import ec.edu.ups.icc.fundamentos01.products.models.ProductEntity;
import ec.edu.ups.icc.fundamentos01.users.models.UserEntity;

@Repository
public interface ProductRepository extends JpaRepository<ProductEntity, Long> {

    /**
     * Encuentra todos los productos de un usuario específico
     * Spring Data JPA genera: SELECT * FROM products WHERE user_id = ?
     */
    List<ProductEntity> findByOwnerId(Long userId);

    /**
     * Encuentra productos que tienen UNA categoría específica
     * Útil para filtros de categoría
     */
    List<ProductEntity> findByCategoriesId(Long categoryId);

    /**
     * Encuentra productos por nombre de usuario
     * Genera JOIN automáticamente:
     * SELECT p.* FROM products p JOIN users u ON p.user_id = u.id WHERE u.name = ?
     */
    List<ProductEntity> findByOwnerName(String ownerName);

    /**
     * Encuentra productos por nombre de categoría
     * Genera JOIN automáticamente:
     * SELECT p.* FROM products p JOIN categories c ON p.category_id = c.id WHERE
     * c.name = ?
     */
    // List<ProductEntity> findByCategoryName(String categoryName);

    /**
     * Encuentra productos con precio mayor a X de una categoría específica
     * Consulta con múltiples condiciones
     * Genera:
     * SELECT p.* FROM products p WHERE p.category_id = ? AND p.price > ?
     */
    // List<ProductEntity> findByCategoryIdAndPriceGreaterThan(Long categoryId,
    // Double price);

    Optional<UserEntity> findByName(
            String name);

    List<ProductEntity> findByCategoriesName(String categoryName);

    /**
     * Consulta personalizada: productos con TODAS las categorías especificadas
     */
    @Query("SELECT p FROM ProductEntity p " +
            "WHERE SIZE(p.categories) >= :categoryCount " +
            "AND :categoryCount = " +
            "(SELECT COUNT(c) FROM p.categories c WHERE c.id IN :categoryIds)")
    List<ProductEntity> findByAllCategories(@Param("categoryIds") List<Long> categoryIds,
            @Param("categoryCount") long categoryCount);
}