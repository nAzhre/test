package kz.halyk.test.service;

import kz.halyk.test.exception.InvalidProductException;
import kz.halyk.test.exception.NegativePriceException;
import kz.halyk.test.exception.ProductDeletionException;
import kz.halyk.test.exception.ProductNotFoundException;
import kz.halyk.test.model.dto.ProductFilterBody;
import kz.halyk.test.model.entity.Product;
import kz.halyk.test.repository.ProductRepository;
import kz.halyk.test.specification.ProductSpecification;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class ProductService {
    private final ProductRepository productRepository;

    public Page<Product> getAllProductsWithPage(ProductFilterBody filterBody) {
        final int countRecords = 25; // кол-во записей на одной странице
        Pageable page = PageRequest.of(filterBody.getPage(), countRecords,
                Sort.by("productName").descending()); // сортировка по имени продукта

        return productRepository.findAll(new ProductSpecification(filterBody), page);
    }

    public Product createProduct(Product product) {
        if (Objects.isNull(product)
                || StringUtils.isEmpty(product.getProductName())
                || product.getPrice() == null
                || StringUtils.isEmpty(product.getCategory())) {
            throw new InvalidProductException("Некорректные данные товара");
        }

        if (product.getPrice().compareTo(BigDecimal.ZERO) < 0) {
            throw new NegativePriceException("Цена не может быть отрицательной");
        }

        return productRepository.save(product);
    }

    public Product updateProduct(Product product) {
        Long id = product.getId();
        return productRepository.findById(id)
                .map(existingProduct -> {
                    product.setId(id);
                    return productRepository.save(product);
                })
                .orElseThrow(() -> new ProductNotFoundException("Продукта с ID " + id + " нет в базе"));
    }


    public Boolean deleteProduct(Product product) {
        try {
            productRepository.delete(product);
            return true;
        } catch (ProductDeletionException exception) {
            log.error("Причина ошибка при удалении сущности: {}", exception.getMessage());
            return false;
        }
    }

    public Product getProductById(Long id) {
        Optional<Product> productOptional = productRepository.findById(id);
        return productOptional.orElseThrow(() ->
                new ProductNotFoundException("Продукт не найден по указанному идентификатору: " + id));
    }

}
