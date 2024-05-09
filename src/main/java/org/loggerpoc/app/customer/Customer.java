package org.loggerpoc.app.app.customer;

import lombok.Data;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Data
@Component
@Scope("prototype")
public class Customer {
    private int id;
    private String firstName;
    private String lastName;
    private String mobile;
    //private Product product;

}
