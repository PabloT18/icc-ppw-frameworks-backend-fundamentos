package ec.edu.ups.icc.fundamentos01.products.services;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.stereotype.Service;

import ec.edu.ups.icc.fundamentos01.categories.dto.CategoriaResponseDto;
import ec.edu.ups.icc.fundamentos01.categories.entity.CategoryEntity;
import ec.edu.ups.icc.fundamentos01.categories.repository.CategoryRepository;
import ec.edu.ups.icc.fundamentos01.exceptions.domain.BusinessException;
import ec.edu.ups.icc.fundamentos01.exceptions.domain.ConflictException;
import ec.edu.ups.icc.fundamentos01.exceptions.domain.NotFoundException;
import ec.edu.ups.icc.fundamentos01.products.dtos.CreateProductDto;
import ec.edu.ups.icc.fundamentos01.products.dtos.PartialUpdateProductDto;
import ec.edu.ups.icc.fundamentos01.products.dtos.SecureUpdateProductDto;
import ec.edu.ups.icc.fundamentos01.products.dtos.UpdateProductDto;
import ec.edu.ups.icc.fundamentos01.products.dtos.ProductResponseDto;

import ec.edu.ups.icc.fundamentos01.products.mappers.ProductMapper;
import ec.edu.ups.icc.fundamentos01.products.models.Product;
import ec.edu.ups.icc.fundamentos01.products.models.ProductEntity;
import ec.edu.ups.icc.fundamentos01.products.repository.ProductRepository;
import ec.edu.ups.icc.fundamentos01.users.models.UserEntity;
import ec.edu.ups.icc.fundamentos01.users.repository.UserRepository;
import jakarta.validation.constraints.NotNull;

@Service
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepo;

    private final UserRepository userRepo;

    private final CategoryRepository categoryRepo;

    public ProductServiceImpl(ProductRepository productRepo,
            UserRepository userRepo,
            CategoryRepository categoryRepository) {
        this.productRepo = productRepo;
        this.categoryRepo = categoryRepository;
        this.userRepo = userRepo;
    }

    @Override
    public ProductResponseDto create(CreateProductDto dto) {

        // 1. VALIDAR EXISTENCIA DE RELACIONES
        UserEntity owner = userRepo.findById(dto.userId)
                .orElseThrow(() -> new NotFoundException("Usuario no encontrado con ID: " + dto.userId));

        CategoryEntity category = categoryRepo.findById(dto.categoryId)
                .orElseThrow(() -> new NotFoundException("Categoría no encontrada con ID: " + dto.categoryId));

        // Regla: nombre único
        if (productRepo.findByName(dto.name).isPresent()) {
            throw new IllegalStateException("El nombre del producto ya está registrado");
        }

        // 2. CREAR MODELO DE DOMINIO
        Product product = Product.fromDto(dto);

        // 3. CONVERTIR A ENTIDAD CON RELACIONES
        ProductEntity entity = product.toEntity(owner, category);

        // 4. PERSISTIR
        ProductEntity saved = productRepo.save(entity);

        // 5. CONVERTIR A DTO DE RESPUESTA
        return toResponseDto(saved);
    }

    @Override
    public List<ProductResponseDto> findAll() {
        return productRepo.findAll()
                .stream()
                .map(this::toResponseDto)
                .toList();
    }

    // ============== MÉTODOS HELPER ==============

    @Override
    public ProductResponseDto findById(Long id) {
        return productRepo.findById(id)
                .map(this::toResponseDto)
                .orElseThrow(() -> new NotFoundException("Producto no encontrado con ID: " + id));
    }

    @Override
    public List<ProductResponseDto> findByUserId(Long userId) {

        // Validar que el usuario existe
        if (!userRepo.existsById(userId)) {
            throw new NotFoundException("Usuario no encontrado con ID: " + userId);
        }

        return productRepo.findByOwnerId(userId)
                .stream()
                .map(this::toResponseDto)
                .toList();
    }

    @Override
    public List<ProductResponseDto> findByCategoryId(Long categoryId) {

        // Validar que la categoría existe
        if (!categoryRepo.existsById(categoryId)) {
            throw new NotFoundException("Categoría no encontrada con ID: " + categoryId);
        }

        return productRepo.findByCategoryId(categoryId)
                .stream()
                .map(this::toResponseDto)
                .toList();
    }

    @Override
    public ProductResponseDto update(Long id, UpdateProductDto dto) {

        // 1. BUSCAR PRODUCTO EXISTENTE
        ProductEntity existing = productRepo.findById(id)
                .orElseThrow(() -> new NotFoundException("Producto no encontrado con ID: " + id));

        // 2. VALIDAR NUEVA CATEGORÍA (si cambió)
        CategoryEntity newCategory = categoryRepo.findById(dto.categoryId)
                .orElseThrow(() -> new NotFoundException("Categoría no encontrada con ID: " + dto.categoryId));

        // 3. ACTUALIZAR USANDO DOMINIO
        Product product = Product.fromEntity(existing);
        product.update(dto);

        // 4. CONVERTIR A ENTIDAD MANTENIENDO OWNER ORIGINAL
        ProductEntity updated = product.toEntity(existing.getOwner(), newCategory);
        updated.setId(id); // Asegurar que mantiene el ID

        // 5. PERSISTIR Y RESPONDER
        ProductEntity saved = productRepo.save(updated);
        return toResponseDto(saved);
    }

    @Override
    public void delete(Long id) {

        ProductEntity product = productRepo.findById(id)
                .orElseThrow(() -> new NotFoundException("Producto no encontrado con ID: " + id));

        // Eliminación física (también se puede implementar lógica)
        productRepo.delete(product);
    }

    private ProductResponseDto toResponseDto(ProductEntity entity) {
        ProductResponseDto dto = new ProductResponseDto();
        dto.id = entity.getId().intValue();
        dto.name = entity.getName();
        dto.price = entity.getPrice();
        dto.description = entity.getDescription();

        ProductResponseDto.UserSummaryDto ownerDto = new ProductResponseDto.UserSummaryDto();
        ownerDto.id = entity.getOwner().getId().intValue();
        ownerDto.name = entity.getOwner().getName();

        CategoriaResponseDto categoryDto = new CategoriaResponseDto();
        categoryDto.id = entity.getCategory().getId();
        categoryDto.nombre = entity.getCategory().getName();

        dto.user = ownerDto;
        dto.category = categoryDto;
        return dto;

    }

}