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
    log.info("Product Service Handling add product");
    Product productEntity = objMapper.map(productDto, Product.class);
    Product productEntitySaved = productRepo.save(productEntity);
    return objMapper.map(productEntitySaved, ProductDto.class);

  }

  public void updateProduct(ProductDto customerDto, long id) throws Exception {
    log.info("Product Service Handling update product");
    if (id == 1) {
      log.info("Product Service Handling update product");
    } else {
      throw new Exception("Customer not found");
    }
  }

  public List<ProductDto> getAllProducts() {
    log.info("Product Service Handling list products");
    return productRepo.findAll()
        .stream()
        .map(product -> objMapper.map(product, ProductDto.class))
        .toList();

  }

  public ProductDto getProductById(long id) {
    log.info("Product Service Handling get product by id");
    var product=productRepo.findProductBy(id);
    if(product==null){
      throw new ProductNotFoundException("Product not found in the Repository.");
    }
    return objMapper.map(product, ProductDto.class);

  }

}
