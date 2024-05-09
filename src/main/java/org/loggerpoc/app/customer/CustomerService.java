package org.loggerpoc.app.app.customer;

import java.util.List;
import java.util.Optional;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class CustomerService {

  @Autowired
  ModelMapper objMapper;
  @Autowired
  private CustomerRepo customerRepo;


  public CustomerDto addCustomer(CustomerDto customerDto) {
    log.info("Customer Service Handling add customer: {}", customerDto);
    Customer customerEntity = objMapper.map(customerDto, Customer.class);
    Customer customerEntitySaved = customerRepo.save(customerEntity);
    return objMapper.map(customerEntitySaved, CustomerDto.class);

  }

  public void updateCustomer(CustomerDto customerDto, long id) throws Exception {
    log.info("Customer Service Handling update customer: {}", customerDto);
    if (id == 1) {
      log.info("Customer Service Handling update customer: {}", customerDto);
    } else {
      throw new Exception("Customer not found");
    }
  }

  public List<CustomerDto> getAllCustomers() {
    log.info("Customer Service Handling list customers: ");
    return customerRepo.findAll()
        .stream()
        .map(customer -> objMapper.map(customer, CustomerDto.class))
        .toList();

  }

  public CustomerDto getCustomerById(long id) {
    log.info("Customer Service Handling get customer by id: {}", id);
    Optional<Customer> customer = Optional.ofNullable(customerRepo.findCustomerBy(id));
    customer.orElseThrow(() -> new RuntimeException("Customer not found"));
    return objMapper.map(customerRepo.findCustomerBy(id), CustomerDto.class);

  }

}
