package ec.edu.ups.icc.fundamentos01.categories.service;

import java.util.List;

import ec.edu.ups.icc.fundamentos01.categories.dto.CategoryResponseDto;
import ec.edu.ups.icc.fundamentos01.categories.dto.CreateCategoriaDto;

public interface CategoriaService {

    void save(CreateCategoriaDto categoria);

    List<CategoryResponseDto> getAll();

}
