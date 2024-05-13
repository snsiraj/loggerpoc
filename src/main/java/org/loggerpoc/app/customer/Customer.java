package org.loggerpoc.app.customer;

import lombok.Data;
import org.loggerpoc.framework.annotation.MaskFeild;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Data
@Component
@Scope("prototype")
public class Customer {
    private int id;
    private String firstName;
    private String lastName;
    @MaskFeild(mask = "XXXXX")
    private String mobile;
    //private Product product;

}
