package org.projects.productcatalogservice.clients;

import org.projects.productcatalogservice.dtos.FakeStoreProductDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RequestCallback;
import org.springframework.web.client.ResponseExtractor;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Objects;

@Component
public class FakeStoreApiClient {
    @Autowired
    private RestTemplateBuilder restTemplateBuilder;

    private  <T> ResponseEntity<T> requestForEntity(String url, HttpMethod httpMethod, @Nullable Object request, Class<T> responseType, Object... uriVariables) throws RestClientException {
        RestTemplate restTemplate = restTemplateBuilder.build();
        RequestCallback requestCallback = restTemplate.httpEntityCallback(request, responseType);
        ResponseExtractor<ResponseEntity<T>> responseExtractor = restTemplate.responseEntityExtractor(responseType);
        return restTemplate.execute(url, httpMethod, requestCallback, responseExtractor, uriVariables);
    }

    public FakeStoreProductDto getProductById(Long id) {
        ResponseEntity<FakeStoreProductDto> fakeStoreProductDtoResponseEntity = requestForEntity("https://fakestoreapi.com/products/{id}", HttpMethod.GET, null, FakeStoreProductDto.class, id);
        // if (fakeStoreProductDtoResponseEntity.getStatusCode().is2xxSuccessful()) {
            return (Objects.requireNonNull(fakeStoreProductDtoResponseEntity.getBody()));
//        }
//        return null;
    }

    public List<FakeStoreProductDto> getAllProducts() {
        ResponseEntity<FakeStoreProductDto[]> fakeStoreProductDtoResponseEntity = requestForEntity("https://fakestoreapi.com/products",
                HttpMethod.GET, null, FakeStoreProductDto[].class);
        // if (fakeStoreProductDtoResponseEntity.getStatusCode().is2xxSuccessful()) {
            return List.of(Objects.requireNonNull(fakeStoreProductDtoResponseEntity.getBody()));
        // }
    }

    public FakeStoreProductDto updateProduct(FakeStoreProductDto fakeStoreProductDto, Long productId) {
        ResponseEntity<FakeStoreProductDto> fakeStoreProductDtoResponseEntity = requestForEntity("https://fakestoreapi.com/products/{id}",
                HttpMethod.PUT, fakeStoreProductDto, FakeStoreProductDto.class, productId);
        // if (fakeStoreProductDtoResponseEntity.getStatusCode().is2xxSuccessful()) {
            return (Objects.requireNonNull(fakeStoreProductDtoResponseEntity.getBody()));
        // }

    }
}
