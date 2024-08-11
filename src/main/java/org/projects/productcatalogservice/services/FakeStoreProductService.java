package org.projects.productcatalogservice.services;

import org.projects.productcatalogservice.clients.FakeStoreApiClient;
import org.projects.productcatalogservice.dtos.FakeStoreProductDto;
import org.projects.productcatalogservice.models.Category;
import org.projects.productcatalogservice.models.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
public class FakeStoreProductService implements IProductService {
    @Autowired
    private RestTemplateBuilder restTemplateBuilder;

    @Autowired
    private FakeStoreApiClient fakeStoreApiClient;

//    public FakeStoreProductService(RestTemplateBuilder restTemplateBuilder) {
//        this.restTemplateBuilder = restTemplateBuilder;
//    }

    @Override
    public Product createProduct(Product product) {
        return null;
    }

    @Override
    public Product updateProduct(Product product, Long productId) {
        FakeStoreProductDto fakeStoreProductDto = fakeStoreApiClient.updateProduct(getFakeStoreProductDto(product), productId);
        return getProduct(fakeStoreProductDto);
    }

    @Override
    public Product getProductById(Long id) {
        FakeStoreProductDto fakeStoreProductDto = fakeStoreApiClient.getProductById(id);
        if (fakeStoreProductDto != null) {
            return getProduct(fakeStoreProductDto);
        }
        return null;
    }

    @Override
    public List<Product> getAllProducts() {
        List<FakeStoreProductDto> fakeStoreProductDtosList = fakeStoreApiClient.getAllProducts();
        List<Product> products = new ArrayList<>();
        for (FakeStoreProductDto fakeStoreProductDto : fakeStoreProductDtosList) {
            products.add(getProduct(Objects.requireNonNull(fakeStoreProductDto)));
        }
        return products;
    }

    private Product getProduct  (FakeStoreProductDto fakeStoreProductDto) {
        Product product = new Product();
        product.setId(fakeStoreProductDto.getId());
        product.setName(fakeStoreProductDto.getTitle());
        product.setDescription(fakeStoreProductDto.getDescription());
        product.setPrice(fakeStoreProductDto.getPrice());
        product.setImageUrl(fakeStoreProductDto.getImage());
        Category category = new Category();
        category.setName(fakeStoreProductDto.getCategory());
        product.setCategory(category);
        return product;
    }

    private FakeStoreProductDto getFakeStoreProductDto(Product product) {
        FakeStoreProductDto fakeStoreProductDto = new FakeStoreProductDto();
        fakeStoreProductDto.setId(product.getId());
        fakeStoreProductDto.setTitle(product.getName());
        fakeStoreProductDto.setDescription(product.getDescription());
        fakeStoreProductDto.setPrice(product.getPrice());
        fakeStoreProductDto.setImage(product.getImageUrl());
        if (product.getCategory() != null) {
            fakeStoreProductDto.setCategory(product.getCategory().getName());
        }
        return fakeStoreProductDto;
    }


}
