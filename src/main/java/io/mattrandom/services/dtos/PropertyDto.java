package io.mattrandom.services.dtos;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PropertyDto {

    private Long id;
    private String postalCode;
    private String city;
    private String street;
    private Integer rooms;
    private Boolean single;
    private String house;
}
