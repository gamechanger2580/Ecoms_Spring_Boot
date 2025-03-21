package com.loukik.ecomproj.controller;

import com.loukik.ecomproj.model.Product;
import com.loukik.ecomproj.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/api")
public class ProductController {

    private final ProductService productService;

    @Autowired
    public ProductController(ProductService productService){
        this.productService = productService;
    }

    @RequestMapping("/")
    public String greet(){
        return "Hello";
    }

    @GetMapping("/products")
    public ResponseEntity<List<Product>> getAllProducts(){
        return new ResponseEntity<>(productService.getAllProd(), HttpStatus.OK) ;
    }

    @GetMapping("/products/{id}")
    public ResponseEntity<Product> getProdById(@PathVariable int id){

        Product product = productService.getProdById(id);

        if(product != null)
        {
            return new ResponseEntity<>(product, HttpStatus.OK);
        }
        else {
            return  new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/products")
    public ResponseEntity<?> addProduct(@RequestPart Product product,
                                        @RequestPart MultipartFile imageFile){
        try {
            Product product1 = productService.addProduct(product, imageFile);
            return new ResponseEntity<>(product1, HttpStatus.CREATED);
        }
        catch (Exception e)
        {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/products/{id}/image")
    public ResponseEntity<byte[]> getImageByProductId(@PathVariable int id)
    {
        Product product = productService.getProdById(id);
        byte[] imageFile = product.getImageDate();

        return ResponseEntity.ok().contentType(MediaType.valueOf(product.getImageType())).body(imageFile);
    }

    @PutMapping("/products/{id}")
    public ResponseEntity<String> updateProduct(@PathVariable int id,
                                                @RequestPart("product") Product product,
                                                @RequestPart(value = "imageFile", required = false) MultipartFile imageFile) {
        try {
            Product updatedProduct = productService.updateProduct(id, product, imageFile);
            return new ResponseEntity<>("Updated", HttpStatus.OK);
        } catch (IOException e) {
            return new ResponseEntity<>("Failed to update product", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @DeleteMapping("/products/{id}")
    public ResponseEntity<String> deleteProduct(@PathVariable int id)
    {
        Product product = productService.getProdById(id);
        if(product != null)
        {
            productService.deleteProduct(id);
            return new ResponseEntity<>("Deleted", HttpStatus.OK);
        }
        else{
            return new ResponseEntity<>("Product not found", HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/products/search")
    public ResponseEntity<List<Product>> searchProducts(@RequestParam String keyword) {

        List<Product> products = productService.searchProducts(keyword);
        System.out.println("searching with " + keyword);
        return new ResponseEntity<>(products, HttpStatus.OK);
    }



}
