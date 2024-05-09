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
    if (customerProductMap == null)
      return null;
    customerProductMapDto = objMapper.map(customerProductMap,CustomerProductMapDto.class);
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
    if (isValidCusomter(customerid) && isValidProduct(productid) && isAlreadyMapped(customerid,
        productid)) {
      customerProductMapRepo.deleteCustomerProductMap(customerid, productid);
    } else {
      log.error("CustomerProductMap Service Handling delete customer {} product {} failed",
          customerid,
          productid);
    }
    log.info("CustomerProductMap Service Handling delete customer {} product {}", customerid,
        productid);
  }

  public void updateCustomerProductMap(long customerid, long productid) {
    log.info("CustomerProductMap Service Handling map customer {} product {}", customerid,
        productid);
    if (isValidCusomter(customerid) && isValidProduct(productid) && !isAlreadyMapped(customerid,
        productid)) {
      customerProductMapRepo.mapCustomerProduct(customerid, productid);
    } else {
      log.error("CustomerProductMap Service Handling map customer {} product {} failed", customerid,
          productid);
      throw new RuntimeException("Customer or Product not found or already mapped");
    }

  }

  private boolean isValidCusomter(long customerid) {

    WebClient webClient = WebClient.create("http://localhost:8081/customer/" + customerid);
    HttpStatusCode statusCode = webClient.get()
            .header("X-App-Name", getTransactionId())
            .retrieve()
            .toEntity(String.class)
            .flatMap(e-> Mono.just(e.getStatusCode()))
            .onErrorReturn(HttpStatus.NOT_FOUND)
            .block();
    log.info("Customer API status code: {}", statusCode);
    return statusCode == HttpStatus.OK;
  }

  private boolean isValidProduct(long productid) {
    WebClient webClient = WebClient.create("http://localhost:8081/product/" + productid);
    HttpStatusCode statusCode = webClient.get()
        .header("X-App-Name", getTransactionId())
        .retrieve()
        .toEntity(String.class)
        .flatMap(e-> Mono.just(e.getStatusCode()))
        .onErrorReturn(HttpStatus.NOT_FOUND)
        .block();
    log.info("Product API status code: {}", statusCode);
    return statusCode == HttpStatus.OK;
  }

  private boolean isAlreadyMapped(long customerid, long productid) {
    return customerProductMapRepo.isAlreadyMapped(customerid, productid);
  }
  private String getTransactionId() {
    return (String) httpServletRequest.getAttribute("X-App-Name");
  }

}
