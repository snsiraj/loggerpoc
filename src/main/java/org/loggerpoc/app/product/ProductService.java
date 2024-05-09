package org.loggerpoc.app.product;

import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class ProductService {

  @Autowired
  ModelMapper objMapper;
  @Autowired
  private ProductRepo productRepo;


  public ProductDto addProduct(ProductDto productDto) {
    log.info("Customer Service Handling add customer: {}", productDto);
    Product productEntity = objMapper.map(productDto, Product.class);
    Product productEntitySaved = productRepo.save(productEntity);
    return objMapper.map(productEntitySaved, ProductDto.class);

  }

  public void updateProduct(ProductDto customerDto, long id) throws Exception {
    log.info("Customer Service Handling update customer: {}", customerDto);
    if (id == 1) {
      log.info("Customer Service Handling update customer: {}", customerDto);
    } else {
      throw new Exception("Customer not found");
    }
  }

  public List<ProductDto> getAllProducts() {
    log.info("Customer Service Handling list customers: ");
    return productRepo.findAll()
        .stream()
        .map(product -> objMapper.map(product, ProductDto.class))
        .toList();

  }

  public ProductDto getProductById(long id) {
    log.info("Customer Service Handling get customer by id: {}", id);
    var product=productRepo.findProductBy(id);
    if(product==null){
      return null;
    }
    return objMapper.map(product, ProductDto.class);

  }

}
