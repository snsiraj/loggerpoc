package org.loggerpoc.app.customer;

import java.util.List;
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
    log.info("Customer Service Handling add customer");
    Customer customerEntity = objMapper.map(customerDto, Customer.class);
    Customer customerEntitySaved = customerRepo.save(customerEntity);
    return objMapper.map(customerEntitySaved, CustomerDto.class);

  }

  public void updateCustomer(CustomerDto customerDto, long id) throws Exception {
    log.info("Customer Service Handling update customer");
    if (id == 1) {
      log.info("Customer Service Handling update customer");
    } else {
      throw new Exception("Customer not found");
    }
  }

  public List<CustomerDto> getAllCustomers() {
    log.info("Customer Service Handling list customers");
    return customerRepo.findAll()
        .stream()
        .map(customer -> objMapper.map(customer, CustomerDto.class))
        .toList();

  }

  public CustomerDto getCustomerById(long id) throws CustomerNotFoundException {
    log.info("Customer Service Handling get customer by id");
    Customer customer = customerRepo.findCustomerBy(id);
    if (customer == null) {
      throw new CustomerNotFoundException("Customer not found in the Repository.");
    }

    return objMapper.map(customer, CustomerDto.class);

  }

}
