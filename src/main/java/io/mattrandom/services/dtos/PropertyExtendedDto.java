package io.mattrandom.services.dtos;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class PropertyExtendedDto {

    private Long id;
    private String postalCode;
    private String city;
    private String street;
    private String house;
    private Boolean singleFriendly;
    private Boolean isPropertyRent;
    private List<RoomDto> roomsDto;
}
