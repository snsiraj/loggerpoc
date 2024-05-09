package org.loggerpoc.app.app.customerproductmap;

import java.util.List;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

@Data
@Slf4j
public class CustomerProductMap {
  private int id;
  private int customerId;
  private int productId;
  private List<Integer> customerIds;
  private List<Integer> productIds;

}
