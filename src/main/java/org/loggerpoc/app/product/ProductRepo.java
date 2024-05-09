package org.loggerpoc.app.app.product;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

@Slf4j
@Repository
public class ProductRepo {

  private JdbcTemplate jdbcTemplate;

  public JdbcTemplate getJdbcTemplate() {
    return jdbcTemplate;
  }

  @Autowired
  public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
    this.jdbcTemplate = jdbcTemplate;
  }

  public Product save(Product product) {
    final String INSERT_PRD_SQL = "INSERT INTO product (name, model, release_version) VALUES (?, ?, ?)";
    log.info("ProductRepo save method called");
    KeyHolder keyHolder = new GeneratedKeyHolder();

    jdbcTemplate.update(connection -> {
      PreparedStatement ps = connection
          .prepareStatement(INSERT_PRD_SQL, Statement.RETURN_GENERATED_KEYS);
      ps.setString(1, product.getName());
      ps.setString(2, product.getModel());
      ps.setString(3, product.getRelversion());
      return ps;
    }, keyHolder);

    return findProductBy(keyHolder.getKey().longValue());
  }

  public void update(Product product, long id) {
    log.info("ProductRepo update method called");
    final String UPDATE_CUST_SQL = "UPDATE product SET name = ?, model = ?, release_version = ? WHERE id = ?";
    jdbcTemplate.update(UPDATE_CUST_SQL, product.getName(), product.getModel(),
        product.getRelversion(), id);

  }

  public List<Product> findAll() {
    log.info("ProductRepo findAll method called");
    final String SELECT_CUST_SQL = "SELECT * FROM product";
    return jdbcTemplate.query(SELECT_CUST_SQL, (rs, rowNum) -> {
      Product product = new Product();
      product.setId(rs.getInt("id"));
      product.setName(rs.getString("name"));
      product.setModel(rs.getString("model"));
      product.setRelversion(rs.getString("release_version"));
      return product;
    });

  }

  public Product findProductBy(long id) {
    log.info("CustomerRepo findCustomerBy method called");
    final String SELECT_PRD_SQL = "SELECT * FROM product WHERE id = ?";
    Product p = null;
    try {
        p=jdbcTemplate.queryForObject(SELECT_PRD_SQL, new Object[]{id}, (rs, rowNum) -> {
        Product product = new Product();
        product.setId(rs.getInt("id"));
        product.setName(rs.getString("name"));
        product.setModel(rs.getString("model"));
        product.setRelversion(rs.getString("releaseversion"));
        return product;
      });
    } catch (DataAccessException e) {
      log.error("Product not found", id);
      return p;
    }
    return p;

  }

}
