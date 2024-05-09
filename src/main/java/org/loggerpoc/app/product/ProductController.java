package org.loggerpoc.app.app.product;

import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/product")
@Slf4j
public class ProductController {

  @Autowired
  private ProductService customerService;
  @Autowired
  private ProductService productService;

  @PostMapping("/add")
  ResponseEntity<ProductDto> addCustomer(@RequestBody ProductDto productDto) {
    log.info("Customer Controller Handling add customer: {}", productDto);
    ProductDto productDtoRes = customerService.addProduct(productDto);
    return ResponseEntity.ok(productDtoRes);
  }

  @PutMapping("/{id}")
  ResponseEntity<String> updateCustomer(@RequestBody ProductDto productDto, @PathVariable long id) {
    try {
      log.info("Customer Controller Handling update customer: {}", productDto);
      productService.updateProduct(productDto, id);
    } catch (Exception e) {
      return ResponseEntity.status(HttpStatus.NOT_FOUND.value()).body("Customer not found");
    }
    return ResponseEntity.ok("Customer updated successfully.");
  }

  @GetMapping
  ResponseEntity<List<ProductDto>> getAllCustomers() {
    log.info("Customer Controller Handling list customers: ");
    List<ProductDto> productDtoResList = productService.getAllProducts();
    return ResponseEntity.ok(productDtoResList);
  }

  @GetMapping("/{id}")
  ResponseEntity<ProductDto> getCustomerById(@PathVariable long id) {
    log.info("Customer Controller Handling get customer by id: {}", id);
    ProductDto productDtoRes = productService.getProductById(id);
    if (productDtoRes == null) {
      return ResponseEntity.status (HttpStatus.NOT_FOUND).build();
    }
    return ResponseEntity.ok(productDtoRes);
  }

}
