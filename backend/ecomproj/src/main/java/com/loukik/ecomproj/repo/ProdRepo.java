package com.loukik.ecomproj.repo;

import com.loukik.ecomproj.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

// JpaRepository<class, data type of primarykey)
@Repository
public interface ProdRepo extends JpaRepository<Product , Integer> {
}
