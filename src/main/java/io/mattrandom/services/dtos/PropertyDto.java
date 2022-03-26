package io.mattrandom.services.dtos;

import lombok.*;

import java.io.Serializable;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PropertyDto {

    private Long id;
    private String postalCode;
    private String city;
    private String street;
    private Integer rooms;
    private Boolean single;
    private String house;
}
