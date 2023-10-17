package com.bouargalne.billingservice.feign;

import com.bouargalne.billingservice.model.Product;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.hateoas.PagedModel;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "PRODUCT-SERVICE")
public interface ProductRestClient {

    @GetMapping(path = "/products")
    PagedModel<Product> pageProducts(); //@RequestParam(value = "name") int page, @RequestParam(value = "size") int size

    @GetMapping(path = "/products/{id}")
    Product getProductById(@PathVariable Long id);
}