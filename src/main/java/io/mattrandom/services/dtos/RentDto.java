package io.mattrandom.services.dtos;

import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Set;

@Data
@Builder
public class RentDto {

    private Long id;

    @Size(min = 3)
    private String tenantName;

    @Size(min = 3)
    private String tenantSurname;

    @NotNull
    private Boolean isPaid;

    @NotNull
    private Boolean isRent;

    private RoomDto roomDto;
}
