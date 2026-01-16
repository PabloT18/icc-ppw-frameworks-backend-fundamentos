package ec.edu.ups.icc.fundamentos01.products.models;

import java.util.HashSet;
import java.util.Set;

import ec.edu.ups.icc.fundamentos01.categories.entity.CategoryEntity;
import ec.edu.ups.icc.fundamentos01.core.entities.BaseModel;
import ec.edu.ups.icc.fundamentos01.users.models.UserEntity;
import jakarta.persistence.*;

@Entity
@Table(name = "products")
public class ProductEntity extends BaseModel {

    @Column(nullable = false, length = 150)
    private String name;

    @Column(nullable = false)
    private Double price;

    @Column(length = 500)
    private String description;

    /// atributos relacionales
    /// Con Usuarios donde un usuario puede tener muchos productos

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private UserEntity owner;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "product_categories", // Tabla intermedia
            joinColumns = @JoinColumn(name = "product_id"), // FK hacia products
            inverseJoinColumns = @JoinColumn(name = "category_id") // FK hacia categories
    )
    private Set<CategoryEntity> categories = new HashSet<>();

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

    public UserEntity getOwner() {
        return owner;
    }

    public void setOwner(UserEntity owner) {
        this.owner = owner;
    }

    public Set<CategoryEntity> getCategories() {
        return categories;
    }

    public void setCategories(Set<CategoryEntity> categories) {
        this.categories = categories;
    }

    // ============== MÉTODOS DE CONVENIENCIA ==============
    /**
     * Agrega una categoría al producto y sincroniza la relación bidireccional
     */
    public void addCategory(CategoryEntity category) {
        this.categories.add(category);
    }

    /**
     * Remueve una categoría del producto y sincroniza la relación bidireccional
     */
    public void removeCategory(CategoryEntity category) {
        this.categories.remove(category);
    }

    /**
     * Limpia todas las categorías y sincroniza las relaciones
     */
    public void clearCategories() {

        this.categories.clear();
    }
    // ... resto de getters y setters

}
