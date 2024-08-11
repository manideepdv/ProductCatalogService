package org.projects.productcatalogservice.controllers;

import org.projects.productcatalogservice.dtos.CategoryDto;
import org.projects.productcatalogservice.dtos.ProductDto;
import org.projects.productcatalogservice.models.Category;
import org.projects.productcatalogservice.models.Product;
import org.projects.productcatalogservice.services.IProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/products")
public class ProductController {
    @Autowired
    private IProductService productService;

    @GetMapping
    public List<ProductDto> getProducts() {
        List<Product> products = productService.getAllProducts();
        List<ProductDto> productDtos = new ArrayList<>();
        for (Product product : products) {
            productDtos.add(getProductDto(product));
        }
        return productDtos;
    }

    @GetMapping("{id}")
    public ResponseEntity<ProductDto> getProductById(@PathVariable("id") Long productId) {
//        ProductDto product = new ProductDto();
//        product.setId(productId);
//        return product;
        if (productId <= 0) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid product id");
        }
        Product product = productService.getProductById(productId);
        ProductDto productDto = getProductDto(product);
        MultiValueMap<String, String> headers = new LinkedMultiValueMap<>();
        headers.add("Called-By", "DVM");
        return new ResponseEntity<>(productDto, headers, HttpStatus.OK);
    }

    @PostMapping
    public ProductDto createProduct(@RequestBody ProductDto product) {
        return product;
    }

    @PutMapping("{id}")
    public ProductDto updateProduct(@RequestBody ProductDto productDto, @PathVariable Long id) {
        Product productToUpdate = productService.updateProduct(getProduct(productDto), id);
        return getProductDto(productToUpdate);
    }

    private ProductDto getProductDto(Product product) {
        ProductDto productDto = new ProductDto();
        productDto.setId(product.getId());
        productDto.setName(product.getName());
        productDto.setDescription(product.getDescription());
        productDto.setPrice(product.getPrice());
        productDto.setImageUrl(product.getImageUrl());
        if (product.getCategory() != null) {
            CategoryDto categoryDto = new CategoryDto();
            categoryDto.setId(product.getCategory().getId());
            categoryDto.setName(product.getCategory().getName());
            categoryDto.setDescription(product.getCategory().getDescription());
            productDto.setCategory(categoryDto);
        }
        return productDto;
    }

    private Product getProduct(ProductDto productDto) {
        Product product = new Product();
        product.setName(productDto.getName());
        product.setDescription(productDto.getDescription());
        product.setPrice(productDto.getPrice());
        product.setImageUrl(productDto.getImageUrl());
        if (productDto.getCategory() != null) {
            Category category = new Category();
            category.setId(productDto.getCategory().getId());
            category.setName(productDto.getCategory().getName());
            category.setDescription(productDto.getCategory().getDescription());
            product.setCategory(category);
        }
        return product;
    }
}
