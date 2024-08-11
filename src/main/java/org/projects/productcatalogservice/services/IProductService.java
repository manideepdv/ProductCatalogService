package org.projects.productcatalogservice.services;

import org.projects.productcatalogservice.models.Product;

import java.util.List;

public interface IProductService {
    public Product createProduct(Product product);
    public Product getProductById(Long id);
    public List<Product> getAllProducts();
    public Product updateProduct(Product product, Long id);
}
