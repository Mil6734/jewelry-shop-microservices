package org.jewelry.jewelryshop.catalogservice.repository;

import org.jewelry.jewelryshop.catalogservice.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long>, ProductFilterRepository {

}
