package com.rai.demo.service;

import com.rai.demo.dto.ProductDto;
import com.rai.demo.model.Category;
import com.rai.demo.model.Product;
import com.rai.demo.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ProductService {


    @Autowired
    private ProductRepository productRepository;

    public void createProduct(ProductDto productDto, Category category) {

        Product product = new Product();
        product.setDescription(productDto.getDescription());
        product.setName(productDto.getName());
        product.setImageURL(productDto.getImageURL());
        product.setPrice(productDto.getPrice());
        product.setCategory(category);

        productRepository.save(product);
    }

    public List<ProductDto> getAllProducts() {
        List<Product> allProducts = productRepository.findAll();
        List<ProductDto> productDtos = new ArrayList<>();
        for(Product product : allProducts){
            productDtos.add(getProductDto(product));
        }
        return productDtos;
    }

    public ProductDto getProductDto(Product product){
        ProductDto productDto = new ProductDto();
        productDto.setDescription(product.getDescription());
        productDto.setName(product.getName());
        productDto.setImageURL(product.getImageURL());
        productDto.setPrice(product.getPrice());
        productDto.setCategoryId(product.getCategory().getId());
        productDto.setId(product.getId());

        return productDto;
    }

    public void updateProduct(ProductDto productDto, Integer productId) throws Exception {
        Optional<Product> optionalProduct = productRepository.findById(productId);

        //throw an exception if product does not exist
        if(!optionalProduct.isPresent()){
            throw new Exception("product is not present");
        }
        Product product = optionalProduct.get();
        product.setDescription(productDto.getDescription());
        product.setName(productDto.getName());
        product.setImageURL(productDto.getImageURL());
        product.setPrice(productDto.getPrice());

        productRepository.save(product);
    }
}
