package org.loggerpoc.app.app.customer;

import lombok.Data;
import org.springframework.stereotype.Component;

@Data
@Component
public class CustomerDto {
    private int id;
    private String firstName;
    private String lastName;
    private String mobile;


}
