package org.loggerpoc.app.app.product;

import lombok.Data;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Data
@Component
@Scope("prototype")
public class Product {
    private int id;
    private String name;
    private String model;
    private String relversion;
    //private Product product;

}
