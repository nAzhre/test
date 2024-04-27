package kz.halyk.test.specification;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import kz.halyk.test.model.dto.ProductFilterBody;
import kz.halyk.test.model.entity.Product;
import kz.halyk.test.model.entity.Product_;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
public class ProductSpecification implements Specification<Product> {
    private final ProductFilterBody filterBody;


    /*
    Фильтрация товаров по полям категорий или по деньгам
     */
    @Override
    public Predicate toPredicate(Root<Product> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
        final List<Predicate> predicates = new ArrayList<>();
        if (filterBody.getCategory() != null && !filterBody.getCategory().isBlank()) {
            predicates.add(criteriaBuilder.equal(root.get(Product_.CATEGORY),
                    filterBody.getCategory()));
        }
        if (filterBody.getMoney() != null) {
            predicates.add(criteriaBuilder.equal(root.get(Product_.PRICE),
                    filterBody.getMoney()));
        }

        return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
    }
}
