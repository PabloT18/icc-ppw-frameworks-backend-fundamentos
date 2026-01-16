package ec.edu.ups.icc.fundamentos01.products.dtos;

import java.util.Set;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

public class UpdateProductDto {

    @NotBlank
    @Size(min = 3, max = 150)
    public String name;

    @NotNull
    @Positive
    public Double price;

    @Size(max = 500)
    public String description;

    @Size(min = 1, message = "El producto debe tener al menos una categoría")
    public Set<Long> categoryIds; // Múltiples categorías

    /// NO PUEDO ACTUALIZAR EL DUEÑO DEL PRODUCTO
    /// public Long userId;
}
