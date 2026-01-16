package ec.edu.ups.icc.fundamentos01.products.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import ec.edu.ups.icc.fundamentos01.products.models.ProductEntity;
import ec.edu.ups.icc.fundamentos01.users.models.UserEntity;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@Repository
public interface ProductRepository extends JpaRepository<ProductEntity, Long> {

    /**
     * Encuentra todos los productos de un usuario específico
     * Spring Data JPA genera: SELECT * FROM products WHERE user_id = ?
     */
    List<ProductEntity> findByOwnerId(Long userId);

    /**
     * Encuentra todos los productos de una categoría específica
     * Spring Data JPA genera: SELECT * FROM products WHERE category_id = ?
     */
    List<ProductEntity> findByCategoryId(Long categoryId);

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
    List<ProductEntity> findByCategoryName(String categoryName);

    /**
     * Encuentra productos con precio mayor a X de una categoría específica
     * Consulta con múltiples condiciones
     * Genera:
     * SELECT p.* FROM products p WHERE p.category_id = ? AND p.price > ?
     */
    List<ProductEntity> findByCategoryIdAndPriceGreaterThan(Long categoryId, Double price);

    Optional<UserEntity> findByName(
            String name);
}