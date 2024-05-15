package org.loggerpoc.app.customerproductmap;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/map")
public class CustomerProductMapController {

  @Autowired
  private CustomerProductMapService customerProductMapService;


  @PutMapping("/customer/{customerid}/product/{productid}/")
  ResponseEntity<String> updateCustomerProductMap(@PathVariable long customerid,
      @PathVariable long productid) {
    log.info("CustomerProductMap Controller Handling map customer <> product");
    customerProductMapService.updateCustomerProductMap(customerid, productid);
    return ResponseEntity.ok("Customer product map updated successfully.");
  }

  @GetMapping("/customer/{customerid}/")
  ResponseEntity<CustomerProductMapDto> getMappedProductsForCustomer(@PathVariable int customerid) {
    CustomerProductMapDto customerProductMapDto = null;
    try {
      log.info("CustomerProductMap Controller Handling get product for customer");
      customerProductMapDto = customerProductMapService.getAllProductsForCustomer(customerid);
    } catch (Exception e) {
      log.error("CustomerProductMap Controller products not found for customer");
    }
    return ResponseEntity.ok(customerProductMapDto);
  }

  @GetMapping("/product/{productid}/")
  ResponseEntity<CustomerProductMapDto> getMappedCustomerForProduct(@PathVariable int productid) {
    CustomerProductMapDto customerProductMapDto = null;

    try {
      log.info("CustomerProductMap Controller Handling get customer for product");
      customerProductMapDto = customerProductMapService.getAllCustomerForProduct(productid);
    } catch (Exception e) {
      log.error("CustomerProductMap Controller customers not found for product");
    }
    return ResponseEntity.ok(customerProductMapDto);
  }


}
