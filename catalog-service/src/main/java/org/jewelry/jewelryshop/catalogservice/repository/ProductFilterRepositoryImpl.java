package org.jewelry.jewelryshop.catalogservice.repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.*;
import org.jewelry.jewelryshop.catalogservice.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Repository
public class ProductFilterRepositoryImpl implements ProductFilterRepository {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Page<Product> findWithFilters(BigDecimal minPrice, BigDecimal maxPrice, String material, Boolean newProduct, Pageable pageable) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Product> cq = cb.createQuery(Product.class);
        Root<Product> root = cq.from(Product.class);

        List<Predicate> predicates = new ArrayList<>();

        if (minPrice != null) {
            predicates.add(cb.greaterThanOrEqualTo(root.get("price"), minPrice));
        }

        if (maxPrice != null) {
            predicates.add(cb.lessThanOrEqualTo(root.get("price"), maxPrice));
        }

        if (material != null && !material.isBlank()) {
            predicates.add(cb.equal(cb.lower(root.get("material")), material.toLowerCase()));
        }

        if (newProduct != null) {
            predicates.add(cb.equal(root.get("newProduct"), newProduct));
        }

        cq.where(predicates.toArray(new Predicate[0]));

        // Сортировка
        if (pageable.getSort().isSorted()) {
            pageable.getSort().forEach(order -> {
                if (order.isAscending()) {
                    cq.orderBy(cb.asc(root.get(order.getProperty())));
                } else {
                    cq.orderBy(cb.desc(root.get(order.getProperty())));
                }
            });
        }

        TypedQuery<Product> query = entityManager.createQuery(cq);

        // Пагинация
        query.setFirstResult((int) pageable.getOffset());
        query.setMaxResults(pageable.getPageSize());

        // Подсчёт общего количества
        CriteriaQuery<Long> countQuery = cb.createQuery(Long.class);
        Root<Product> countRoot = countQuery.from(Product.class);
        countQuery.select(cb.count(countRoot)).where(predicates.toArray(new Predicate[0]));
        Long total = entityManager.createQuery(countQuery).getSingleResult();

        return new PageImpl<>(query.getResultList(), pageable, total);
    }
}