package ec.edu.ups.icc.fundamentos01.categories.service;

import java.util.List;

import org.springframework.stereotype.Service;

import ec.edu.ups.icc.fundamentos01.categories.dto.CategoriaResponseDto;
import ec.edu.ups.icc.fundamentos01.categories.dto.CreateCategoriaDto;
import ec.edu.ups.icc.fundamentos01.categories.entity.CategoryEntity;
import ec.edu.ups.icc.fundamentos01.categories.repository.CategoryRepository;

@Service
public class CategoriaServiceImp implements CategoriaService {

    private CategoryRepository categoryRepository;

    public CategoriaServiceImp(CategoryRepository repo) {
        this.categoryRepository = repo;
    }

    @Override
    public void save(CreateCategoriaDto categoria) {

        CategoryEntity entity = new CategoryEntity();
        entity.setName(categoria.name);
        entity.setDescription(categoria.descripcion);

        categoryRepository.save(entity);

    }

    @Override
    public List<CategoriaResponseDto> getAll() {
        return categoryRepository.findAll().stream().map(category -> {
            CategoriaResponseDto dto = new CategoriaResponseDto();
            dto.id = category.getId();
            dto.nombre = category.getName();
            dto.descripcion = category.getDescription();
            return dto;
        }).toList();
    }

}
