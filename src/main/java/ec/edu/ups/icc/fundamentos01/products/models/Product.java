package ec.edu.ups.icc.fundamentos01.products.models;

import java.util.List;
import java.util.Set;

import ec.edu.ups.icc.fundamentos01.categories.entity.CategoryEntity;
import ec.edu.ups.icc.fundamentos01.products.dtos.CreateProductDto;
import ec.edu.ups.icc.fundamentos01.products.dtos.PartialUpdateProductDto;
import ec.edu.ups.icc.fundamentos01.products.dtos.UpdateProductDto;
import ec.edu.ups.icc.fundamentos01.users.models.UserEntity;

public class Product {

    private Long id;
    private String name;
    private Double price;
    private String description;
    private String createdAt;

    // Constructor privado para forzar uso de factory methods
    public Product(Long id, String name, Double price, String description) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.description = description;
        this.createdAt = java.time.LocalDateTime.now().toString();
    }

    // Constructor sin ID para nuevos productos
    public Product(String name, Double price, String description) {
        this.name = name;
        this.price = price;
        this.description = description;
        this.createdAt = java.time.LocalDateTime.now().toString();
    }

    // ==================== FACTORY METHODS ====================
    public static Product fromDto(CreateProductDto dto) {
        return new Product(
                dto.name,
                dto.price,
                dto.description);
    }

    /**
     * Crea un Product desde una entidad persistente
     * 
     * @param entity Entidad recuperada de la BD
     * @return instancia de Product para lógica de negocio
     */
    public static Product fromEntity(ProductEntity entity) {
        return new Product(
                entity.getId(),
                entity.getName(),
                entity.getPrice(),
                entity.getDescription());
    }

    /**
     * Convierte este Product a una entidad persistente
     * 
     * @return ProductEntity lista para guardar en BD
     */
    public ProductEntity toEntity() {
        ProductEntity entity = new ProductEntity();
        if (this.id > 0) {
            entity.setId((long) this.id);
        }
        entity.setName(this.name);
        entity.setPrice(this.price);
        entity.setDescription(this.description);
        return entity;
    }

    public ProductEntity toEntity(UserEntity owner, Set<CategoryEntity> categories) {
        ProductEntity entity = new ProductEntity();

        if (this.id != null && this.id > 0) {
            entity.setId(this.id);
        }

        entity.setName(this.name);
        entity.setPrice(this.price);
        entity.setDescription(this.description);

        // Asignar relaciones
        entity.setOwner(owner);
        entity.setCategories(categories);

        return entity;
    }

    public Product update(UpdateProductDto dto) {
        this.name = dto.name;
        this.price = dto.price;
        this.description = dto.description;
        return this;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public Product partialUpdate(PartialUpdateProductDto dto) {

        if (dto.name != null) {
            this.name = dto.name;
        }

        if (dto.price != null) {
            this.price = dto.price;
        }

        if (dto.description != null) {
            this.description = dto.description;
        }

        return this;
    }

}
