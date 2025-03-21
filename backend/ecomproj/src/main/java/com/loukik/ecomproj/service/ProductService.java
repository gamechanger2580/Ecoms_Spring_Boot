package com.loukik.ecomproj.service;


import com.loukik.ecomproj.model.Product;
import com.loukik.ecomproj.repo.ProdRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.stream.LongStream;

@Service
public class ProductService {

    private final ProdRepo repo;

    @Autowired
    public ProductService(ProdRepo repo){
        this.repo = repo;
    }

    public List<Product> getAllProd(){
        return repo.findAll();
    }

    public Product getProdById(int id) {
        return repo.findById(id).orElse(null);
    }

    public Product addProduct(Product product, MultipartFile imageFile) throws IOException {
        product.setImageName(imageFile.getOriginalFilename());
        product.setImageType(imageFile.getContentType());
        product.setImageDate(imageFile.getBytes());
        return repo.save(product);
    }


    public Product updateProduct(int id, Product product, MultipartFile imageFile) throws IOException {
        Product existingProduct = repo.findById(id).orElseThrow(() -> new RuntimeException("Product not found"));

        existingProduct.setName(product.getName());
        existingProduct.setDescription(product.getDescription());
        existingProduct.setBrand(product.getBrand());
        existingProduct.setPrice(product.getPrice());
        existingProduct.setCategory(product.getCategory());
        existingProduct.setReleaseDate(product.getReleaseDate());
        existingProduct.setAvailable(product.isAvailable());
        existingProduct.setQuantity(product.getQuantity());

        if (imageFile != null && !imageFile.isEmpty()) {
            existingProduct.setImageDate(imageFile.getBytes());
            existingProduct.setImageName(imageFile.getOriginalFilename());
            existingProduct.setImageType(imageFile.getContentType());
        }

        return repo.save(existingProduct);
    }


    public void deleteProduct(int id) {
        repo.deleteById(id);
    }

    public List<Product> searchProducts(String keyword) {
        return repo.searchProducts(keyword);
    }
}
