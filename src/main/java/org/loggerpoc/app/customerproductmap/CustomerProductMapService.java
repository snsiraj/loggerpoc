package org.loggerpoc.app.customerproductmap;

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
@Slf4j
public class CustomerProductMapService {

  @Autowired
  ModelMapper objMapper;
  @Autowired
  private CustomerProductMapRepo customerProductMapRepo;
  @Autowired
  private HttpServletRequest httpServletRequest;

  public CustomerProductMapDto getAllCustomerForProduct(int productid) {
    log.info("CustomerProductMap Service Handling list customer product maps: ");
    CustomerProductMapDto customerProductMapDto = null;
    CustomerProductMap customerProductMap = customerProductMapRepo.findAllCustomerForProduct(
        productid);
    if (customerProductMap == null) {
      return null;
    }
    customerProductMapDto = objMapper.map(customerProductMap, CustomerProductMapDto.class);
    return customerProductMapDto;
  }

  public CustomerProductMapDto getAllProductsForCustomer(int customerid) {
    log.info("CustomerProductMap Service Handling list customer product maps: ");
    CustomerProductMap customerProductMap = customerProductMapRepo.findAllProductsForCustomer(
        customerid);
    CustomerProductMapDto customerProductMapDto = objMapper.map(customerProductMap,
        CustomerProductMapDto.class);
    return customerProductMapDto;
  }

  public void deleteCustomerProductMap(long customerid, long productid) {
    if (isValid("customer",customerid) && isValid("product",productid) && isAlreadyMapped(customerid,
        productid)) {
      customerProductMapRepo.deleteCustomerProductMap(customerid, productid);
    } else {
      log.error("CustomerProductMap Service Handling delete customer <> product failed");
    }
    log.info("CustomerProductMap Service Handling delete customer <> product");
  }

  public void updateCustomerProductMap(long customerid, long productid) {
    log.info("CustomerProductMap Service Handling map customer <> product");
    if (isValid("customer",customerid) && isValid("product",productid) && !isAlreadyMapped(customerid,
        productid)) {
      customerProductMapRepo.mapCustomerProduct(customerid, productid);
    } else {
      throw new CustomerProductMapException("Customer or Product not found or already mapped");
    }

  }

  @Autowired
  private WebClient webClient;

  private boolean isValid(String action,long id) {
    String url = "http://localhost:8081/%s/%s".formatted(action, id);
//    WebClient webClient = WebClient
//        .create(url);
    HttpStatusCode statusCode = webClient
        .get()
        .uri(url)
        .header("X-App-Name", getTransactionId())
        .retrieve()
        .toEntity(String.class)
        .flatMap(e -> Mono.just(e.getStatusCode()))
        .onErrorReturn(HttpStatus.NOT_FOUND)
        .block();
    return statusCode == HttpStatus.OK;
  }



  private boolean isAlreadyMapped(long customerid, long productid) {
    return customerProductMapRepo.isAlreadyMapped(customerid, productid);
  }

  private String getTransactionId() {
    return (String) httpServletRequest.getAttribute("X-App-Name");
  }

}
