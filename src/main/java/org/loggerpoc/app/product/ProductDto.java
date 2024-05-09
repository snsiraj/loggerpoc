package org.loggerpoc.app.app.product;

import lombok.Data;
import org.springframework.stereotype.Component;

@Data
@Component
public class ProductDto {
    private int id;
    private String name;
    private String model;
    private String releaseVersion;


}
