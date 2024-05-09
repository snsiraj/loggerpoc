package org.loggerpoc.app.customer;

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
public class CustomerRepo {

  private JdbcTemplate jdbcTemplate;

  public JdbcTemplate getJdbcTemplate() {
    return jdbcTemplate;
  }

  @Autowired
  public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
    this.jdbcTemplate = jdbcTemplate;
  }

  public Customer save(Customer customer) {
    final String INSERT_CUST_SQL = "INSERT INTO customer (first_name, last_name, mobile) VALUES (?, ?, ?)";
    log.info("CustomerRepo save method called");
    KeyHolder keyHolder = new GeneratedKeyHolder();

    jdbcTemplate.update(connection -> {
      PreparedStatement ps = connection
          .prepareStatement(INSERT_CUST_SQL, Statement.RETURN_GENERATED_KEYS);
      ps.setString(1, customer.getFirstName());
      ps.setString(2, customer.getLastName());
      ps.setString(3, customer.getMobile());
      return ps;
    }, keyHolder);

    return findCustomerBy(keyHolder.getKey().longValue());
  }

  public void update(Customer customer, long id) {
    log.info("CustomerRepo update method called");
    final String UPDATE_CUST_SQL = "UPDATE customer SET first_name = ?, last_name = ?, mobile = ? WHERE id = ?";
    jdbcTemplate.update(UPDATE_CUST_SQL, customer.getFirstName(), customer.getLastName(),
        customer.getMobile(), id);

  }

  public List<Customer> findAll() {
    log.info("CustomerRepo findAll method called");
    final String SELECT_CUST_SQL = "SELECT * FROM customer";
    List<Customer> customerList = jdbcTemplate.query(SELECT_CUST_SQL, (rs, rowNum) -> {
      Customer customer = new Customer();
      customer.setId(rs.getInt("id"));
      customer.setFirstName(rs.getString("first_name"));
      customer.setLastName(rs.getString("last_name"));
      customer.setMobile(rs.getString("mobile"));
      return customer;
    });
    return customerList;
  }

  public Customer findCustomerBy(long id) {
    log.info("CustomerRepo findCustomerBy method called");
    final String SELECT_CUST_SQL = "SELECT * FROM customer WHERE id = ?";
    Customer c = null;
    try {
      c = jdbcTemplate.queryForObject(SELECT_CUST_SQL, new Object[]{id}, (rs, rowNum) -> {
        Customer customer = new Customer();
        customer.setId(rs.getInt("id"));
        customer.setFirstName(rs.getString("first_name"));
        customer.setLastName(rs.getString("last_name"));
        customer.setMobile(rs.getString("mobile"));
        return customer;
      });
    } catch (DataAccessException e) {
      log.error("Customer not found with id: {}", id);
      return c;
    }
    return c;
  }

}
