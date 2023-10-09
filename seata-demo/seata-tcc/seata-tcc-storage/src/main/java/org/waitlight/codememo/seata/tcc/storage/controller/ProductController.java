package org.waitlight.codememo.seata.tcc.storage.controller;

import org.waitlight.codememo.seata.tcc.storage.action.StorageReduceAction;
import org.waitlight.codememo.seata.tcc.storage.model.Product;
import org.waitlight.codememo.seata.tcc.storage.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/products")
@RequiredArgsConstructor
public class ProductController {
    private final ProductRepository productRepository;
    private final StorageReduceAction storageReduceAction;

    @RequestMapping(method = {RequestMethod.POST, RequestMethod.PUT})
    public void submit(@RequestBody Product product) {
        productRepository.save(product);
    }

    @GetMapping
    public Iterable<Product> list() {
        return productRepository.findAll();
    }

    @PostMapping("/reduce")
    public void reduce(@RequestParam("id") Long id, @RequestParam("amount") Integer amount) {
        storageReduceAction.reduce(null, id, amount);
    }
}
