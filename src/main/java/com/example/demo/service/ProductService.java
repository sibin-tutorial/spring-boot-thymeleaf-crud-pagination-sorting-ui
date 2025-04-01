package com.example.demo.service;

import com.example.demo.entity.Product;
import com.example.demo.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductService {
    
    private final ProductRepository productRepository;
    
    @Autowired
    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }
    
    public Page<Product> listAll(int pageNum, String sortField, String sortDir, String keyword) {
        int pageSize = 5;
        
        Pageable pageable = PageRequest.of(pageNum - 1, pageSize,
                sortDir.equals("asc") ? Sort.by(sortField).ascending()
                                     : Sort.by(sortField).descending());
        
        if (keyword != null && !keyword.isEmpty()) {
            return productRepository.search(keyword, pageable);
        }
        
        return productRepository.findAll(pageable);
    }
    
    public List<Product> listAll() {
        return productRepository.findAll();
    }
    
    public void save(Product product) {
        productRepository.save(product);
    }
    
    public Product get(Long id) {
        Optional<Product> result = productRepository.findById(id);
        return result.orElseThrow(() -> new RuntimeException("Could not find product with ID: " + id));
    }
    
    public void delete(Long id) {
        productRepository.deleteById(id);
    }
}