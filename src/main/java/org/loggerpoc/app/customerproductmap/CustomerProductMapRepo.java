package org.loggerpoc.app.customerproductmap;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class CustomerProductMapRepo {

  private JdbcTemplate jdbcTemplate;

  public JdbcTemplate getJdbcTemplate() {
    return jdbcTemplate;
  }

  @Autowired
  public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
    this.jdbcTemplate = jdbcTemplate;
  }

  public void mapCustomerProduct(long customerid, long productid) {
    final String INSERT_CUST_PRD_SQL = "INSERT INTO customer_product (customer_id, product_id) VALUES (?, ?)";
    jdbcTemplate.update(INSERT_CUST_PRD_SQL, customerid, productid);
  }

  public void deleteCustomerProductMap(long customerid, long productid) {
    final String DELETE_CUST_PRD_SQL = "DELETE FROM customer_product WHERE customer_id = ? AND product_id = ?";
    jdbcTemplate.update(DELETE_CUST_PRD_SQL, customerid, productid);
  }

  public CustomerProductMap findAllCustomerForProduct(int productid) {
    CustomerProductMap custPrdMap = null;
    final String SELECT_CUST_PRD_SQL = "SELECT customer_id FROM customer_product WHERE product_id = ?";
    List<Integer> customerIdList = jdbcTemplate.queryForList(SELECT_CUST_PRD_SQL,
        new Object[]{productid}, Integer.class);
    if (!customerIdList.isEmpty()) {
      custPrdMap = new CustomerProductMap();
      custPrdMap.setCustomerIds(customerIdList);
      custPrdMap.setProductId(productid);
    }
    return custPrdMap;
  }

  public CustomerProductMap findAllProductsForCustomer(int customerid) {
    CustomerProductMap custPrdMap = null;
    final String SELECT_CUST_PRD_SQL = "SELECT * FROM customer_product WHERE customer_id = ?";
    List<Integer> productIdList = jdbcTemplate.queryForList(SELECT_CUST_PRD_SQL,
        new Object[]{customerid}, Integer.class);

    if (!productIdList.isEmpty()) {
      custPrdMap = new CustomerProductMap();
      custPrdMap.setProductIds(productIdList);
      custPrdMap.setCustomerId(customerid);
    }
    return custPrdMap;
  }

  public boolean isAlreadyMapped(long customerid, long productid) {
    final String SELECT_CUST_PRD_SQL = "SELECT * FROM customer_product WHERE customer_id = ? AND product_id = ?";
    return !jdbcTemplate.query(SELECT_CUST_PRD_SQL, new Object[]{customerid, productid},
        (rs, rowNum) -> true).isEmpty();
  }


}
