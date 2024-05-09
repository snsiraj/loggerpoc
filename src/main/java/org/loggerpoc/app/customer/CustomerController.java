package org.loggerpoc.app.app.customer;

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
@RequestMapping("/customer")
@Slf4j
public class CustomerController {
  
       @Autowired
       private CustomerService customerService;
  
       @PostMapping("/add")
       ResponseEntity<CustomerDto> addCustomer(@RequestBody CustomerDto customerDto){
           log.info("Customer Controller Handling add customer: {}", customerDto);
           CustomerDto customerDtoRes = customerService.addCustomer(customerDto);
           return ResponseEntity.ok(customerDtoRes);
       }
  
       @PutMapping("/{id}")
       ResponseEntity<String> updateCustomer(@RequestBody CustomerDto customerDto, @PathVariable long id){
           try {
               log.info("Customer Controller Handling update customer: {}", customerDto);
               customerService.updateCustomer(customerDto,id);
           } catch (Exception e) {
               return ResponseEntity.status(HttpStatus.NOT_FOUND.value()).body("Customer not found");
           }
           return ResponseEntity.ok("Customer updated successfully.");
       }
  
       @GetMapping
       ResponseEntity<List<CustomerDto>> getAllCustomers(){
           log.info("Customer Controller Handling list customers: ");
           List<CustomerDto> customerDtoResList = customerService.getAllCustomers();
           return ResponseEntity.ok(customerDtoResList);
       }
       @GetMapping("/{id}")
        ResponseEntity<CustomerDto> getCustomerById(@PathVariable long id){
            log.info("Customer Controller Handling get customer by id: {}", id);
         try {
           CustomerDto customerDtoRes = customerService.getCustomerById(id);
              return ResponseEntity.ok(customerDtoRes);
         } catch (Exception e) {
              return ResponseEntity.notFound().build();
         }
       }

}
