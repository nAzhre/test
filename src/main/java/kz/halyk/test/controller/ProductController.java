package kz.halyk.test.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import kz.halyk.test.model.dto.ProductFilterBody;
import kz.halyk.test.model.entity.Product;
import kz.halyk.test.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/products")
@Tag(name = "Product", description = "РЕСТ по продуктам")
public class ProductController {
    private final ProductService productService;

    @PostMapping("/create")
    @Operation(summary = "Создание нового продукта")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Данные найдены", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = Product.class))}),
            @ApiResponse(responseCode = "500", description = "Произошла ошибка", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = Error.class))}),
            @ApiResponse(responseCode = "401", description = "Unauthorized access", content = @Content),
            @ApiResponse(responseCode = "400", description = "Некорректные данные", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = Error.class),
                            examples = {
                                    @ExampleObject(name = "InvalidProductExample",
                                            value = "{\"message\": \"Некорректные данные товара\", " +
                                                    "\"timestamp\": \"2022-04-28T10:15:30\"}",
                                            description = "Вывод ошибки если данные не были проставлены при создании")
                            })
            })
    })
    public ResponseEntity<Product> createProduct(@Valid @RequestBody Product product) {
        return new ResponseEntity<>(productService.createProduct(product), HttpStatus.CREATED);
    }

    @PostMapping("/get/filter")
    @Operation(summary = "Получение товаров с фильтрацией и пагинацией")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Данные найдены", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = Product.class))}),
            @ApiResponse(responseCode = "500", description = "Произошла ошибка", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = Error.class))}),
            @ApiResponse(responseCode = "401", description = "Unauthorized access", content = @Content),
            @ApiResponse(responseCode = "400", description = "Ошибка при удалении", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = Error.class),
                            examples = {
                                    @ExampleObject(name = "Product get exception",
                                            value = "{\"message\": \"Ошибка\", " +
                                                    "\"timestamp\": \"2022-04-28T10:15:30\"}",
                                            description = "Вывод ошибки")
                            })
            }),
            @ApiResponse(responseCode = "404", description = "Ресурс не найден", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = Error.class),
                            examples = {
                                    @ExampleObject(name = "Product Find exception",
                                            value = "{\"message\": \"Ошибка\", " +
                                                    "\"timestamp\": \"2022-04-28T10:15:30\"}",
                                            description = "Вывод ошибки")
                            })
            })
    })
    public ResponseEntity<Page<Product>> getProductsWithFilter(@RequestBody ProductFilterBody filterBody) {
        return ResponseEntity.ok(productService.getAllProductsWithPage(filterBody));

    }

    @DeleteMapping("/delete")
    @Operation(summary = "Удаление продукта")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Данные найдены", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = Boolean.class))}),
            @ApiResponse(responseCode = "500", description = "Произошла ошибка", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = Error.class))}),
            @ApiResponse(responseCode = "401", description = "Unauthorized access", content = @Content),
            @ApiResponse(responseCode = "400", description = "Ошибка при удалении", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = Error.class),
                            examples = {
                                    @ExampleObject(name = "ProductDeletion Exception",
                                            value = "{\"message\": \"Ошибка при удалении продукта\", " +
                                                    "\"timestamp\": \"2022-04-28T10:15:30\"}",
                                            description = "Вывод ошибки если удаление произошло с ошибкой")
                            })
            })
    })
    public ResponseEntity<Boolean> deleteProduct(@RequestBody Product product) {
        return ResponseEntity.ok(productService.deleteProduct(product));
    }

    @PutMapping("/update")
    @Operation(summary = "Обновление уже существущего продукта")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Данные обновлены", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = Product.class))}),
            @ApiResponse(responseCode = "500", description = "Произошла ошибка", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = Error.class))}),
            @ApiResponse(responseCode = "401", description = "Unauthorized access", content = @Content),
            @ApiResponse(responseCode = "400", description = "Ошибка при удалении", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = Error.class),
                            examples = {
                                    @ExampleObject(name = "Product get exception",
                                            value = "{\"message\": \"Ошибка\", " +
                                                    "\"timestamp\": \"2022-04-28T10:15:30\"}",
                                            description = "Вывод ошибки")
                            })
            }),
            @ApiResponse(responseCode = "404", description = "Ресурс не найден", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = Error.class),
                            examples = {
                                    @ExampleObject(name = "Product Find exception",
                                            value = "{\"message\": \"Ошибка\", " +
                                                    "\"timestamp\": \"2022-04-28T10:15:30\"}",
                                            description = "Вывод ошибки")
                            })
            })
    })
    public ResponseEntity<Product> updateProduct(@Valid @RequestBody Product product) {
        return ResponseEntity.ok(productService.updateProduct(product));
    }

    @GetMapping("/get/{id}")
    @Operation(summary = "Получение продукта по уникальному номеру")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Данные найдены", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = Product.class))}),
            @ApiResponse(responseCode = "500", description = "Произошла ошибка", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = Error.class))}),
            @ApiResponse(responseCode = "401", description = "Unauthorized access", content = @Content),
            @ApiResponse(responseCode = "400", description = "Ошибка при поиске продукта", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = Error.class),
                            examples = {
                                    @ExampleObject(name = "Product get exception",
                                            value = "{\"message\": \"Ошибка\", " +
                                                    "\"timestamp\": \"2022-04-28T10:15:30\"}",
                                            description = "Вывод ошибки")
                            })
            }),
            @ApiResponse(responseCode = "404", description = "Ресурс не найден", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = Error.class),
                            examples = {
                                    @ExampleObject(name = "Product Find exception",
                                            value = "{\"message\": \"Ошибка\", " +
                                                    "\"timestamp\": \"2022-04-28T10:15:30\"}",
                                            description = "Вывод ошибки")
                            })
            })
    })
    public ResponseEntity<Product> getProductById(@PathVariable Long id) {
        return ResponseEntity.ok(productService.getProductById(id));
    }
}
