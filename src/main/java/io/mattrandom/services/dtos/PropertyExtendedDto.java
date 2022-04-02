package io.mattrandom.services.dtos;

import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.List;

@Data
@Builder
public class PropertyExtendedDto {

    private Long id;

    @Pattern(regexp = "^[0-9]{2}-[0-9]{3}")
    private String postalCode;

    @Size(min = 5)
    private String city;

    @Size(min = 5)
    private String street;

    @Size(min = 3)
    private String house;

    @NotNull
    private Boolean singleFriendly;

    private Boolean isPropertyRent;

    private List<RoomDto> roomsDto;
}
