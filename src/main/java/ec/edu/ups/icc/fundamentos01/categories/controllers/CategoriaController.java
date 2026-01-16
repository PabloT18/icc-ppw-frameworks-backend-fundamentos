
package ec.edu.ups.icc.fundamentos01.categories.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ec.edu.ups.icc.fundamentos01.Fundamentos01Application;
import ec.edu.ups.icc.fundamentos01.categories.dto.CategoriaResponseDto;
import ec.edu.ups.icc.fundamentos01.categories.dto.CreateCategoriaDto;
import ec.edu.ups.icc.fundamentos01.categories.service.CategoriaService;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("/api/categories")
public class CategoriaController {

    private final Fundamentos01Application fundamentos01Application;

    private CategoriaService categoriaService;

    public CategoriaController(CategoriaService categoriaService, Fundamentos01Application fundamentos01Application) {
        this.categoriaService = categoriaService;
        this.fundamentos01Application = fundamentos01Application;
    }

    @PostMapping()
    public ResponseEntity<String> save(@RequestBody CreateCategoriaDto categoriaDto) {
        categoriaService.save(categoriaDto);
        return ResponseEntity.ok("Categoria creada");
    }

    @GetMapping()
    public ResponseEntity<List<CategoriaResponseDto>> getAll() {
        List<CategoriaResponseDto> list = categoriaService.getAll();

        return ResponseEntity.ok(list);
    }

}
