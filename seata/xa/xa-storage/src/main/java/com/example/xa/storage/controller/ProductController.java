package com.example.xa.storage.controller;

import com.example.xa.storage.model.Product;
import com.example.xa.storage.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/products")
public class ProductController {
    @Autowired
    private ProductRepository productRepository;

    @RequestMapping(method = {RequestMethod.POST, RequestMethod.PUT})
    public void submit(@RequestBody Product product) {
        productRepository.save(product);
    }

    @GetMapping
    public Iterable<Product> list() {
        return productRepository.findAll();
    }

    @Transactional
    @PostMapping("/reduce")
    public void reduce(@RequestParam("id") Long id, @RequestParam("amount") Integer amount) {
        productRepository.findById(id).map(p -> {
            p.setStock(p.getStock() - amount);
            return p;
        }).ifPresent(p -> productRepository.save(p));
    }
}
