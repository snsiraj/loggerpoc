package org.loggerpoc.app.customer;

import lombok.Data;
import org.loggerpoc.framework.annotation.MaskFeild;
import org.springframework.stereotype.Component;

@Data
@Component
public class CustomerDto {
    private int id;
    private String firstName;
    private String lastName;
    @MaskFeild(mask = "*****")
    private String mobile;


}
