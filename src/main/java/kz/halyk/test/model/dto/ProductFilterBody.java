package kz.halyk.test.model.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;

/*
    Класс для того чтобы строить пагинацию и фильтрацию
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductFilterBody implements Serializable {
    @Schema(description = "Страница", example = "0")
    private int page;
    @Schema(description = "Стоимость товара", example = "500000")
    private BigDecimal money;
    @Schema(description = "Категория товара", example = "Смартфон")
    private String category;
}
