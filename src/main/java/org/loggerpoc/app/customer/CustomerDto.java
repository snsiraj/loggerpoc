package org.loggerpoc.app.customer;

import lombok.Data;
import org.loggerpoc.framework.annotation.MaskFeild;
import org.loggerpoc.framework.annotation.MaskLogger;
import org.springframework.stereotype.Component;

//@MaskLogger
@Data
@Component
public class CustomerDto {
    private int id;
    private String firstName;
    private String lastName;
    //@MaskFeild(mask = "*****")
    private String mobile;


}
