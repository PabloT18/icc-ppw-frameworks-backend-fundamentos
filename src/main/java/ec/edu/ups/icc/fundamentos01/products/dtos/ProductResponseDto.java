package ec.edu.ups.icc.fundamentos01.products.dtos;

import java.util.List;

import ec.edu.ups.icc.fundamentos01.categories.dto.CategoryResponseDto;

public class ProductResponseDto {
    public int id;
    public String name;
    public Double price;
    public String description;

    // aparesca sus categorias y su dueño
    public UserSummaryDto user;

    public List<CategoryResponseDto> categories;

    public static class UserSummaryDto {
        public int id;
        public String name;
        public String email;
    }

}
