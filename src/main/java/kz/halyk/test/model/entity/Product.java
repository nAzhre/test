package kz.halyk.test.model.entity;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "product", schema = "public")
public class Product {
    @Id
    @GeneratedValue
    @Column(name = "PRODUCT_ID", updatable = false, nullable = false)
    @Schema(description = "Уникальный идентификатор товара", example = "1")
    private Long id;

    @NotNull
    @Column(name = "PRODUCT_NAME", nullable = false)
    @NotBlank(message = "Название продукта не может быть пустым")
    @Size(max = 255, message = "Название продукта не может превышать 255 символов")
    @Schema(description = "Наименование продукта", example = "iPhone 15")
    private String productName;

    @NotNull
    @Column(name = "PRICE", nullable = false)
    @DecimalMin(value = "0", inclusive = false)
    @NotNull(message = "Цена продукта не может быть пустой")
    @PositiveOrZero(message = "Цена продукта должна быть положительной или равна нулю")
    @Schema(description = "Цена продукта/товара", example = "500000")
    private BigDecimal price;

    @Column(name = "PRODUCT_DESCRIPTION", length = 5000)
    @Size(max = 5000, message = "Описание продукта не может превышать 5000 символов")
    @Schema(description = "Описание товара", example = "Новейший телефон нового поколения 20 века черного цвета")
    private String description;

    @Column(name = "PRODUCT_CATEGORY", nullable = false)
    @NotBlank(message = "Категория продукта не может быть пустой")
    @Size(max = 255, message = "Категория продукта не может превышать 255 символов")
    @Schema(description = "Категория товара (смартфон, персональный компьютер и так далее)", example = "Смартфон")
    private String category;
}
