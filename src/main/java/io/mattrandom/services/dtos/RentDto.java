package io.mattrandom.services.dtos;

import lombok.Builder;
import lombok.Data;

import java.util.Set;

@Data
@Builder
public class RentDto {

    private Long id;
    private String tenantName;
    private String tenantSurname;
    private Boolean isPaid;
    private Boolean isRent;
    private RoomDto roomDto;
}
