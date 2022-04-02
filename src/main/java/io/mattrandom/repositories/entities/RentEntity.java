package io.mattrandom.repositories.entities;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;

@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor

@Entity
@Table(name = "rents")
public class RentEntity extends AbstractEntity {

    private String tenantName;
    private String tenantSurname;
    private Boolean isRent;
    private Boolean isPaid;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "room_id")
    private RoomEntity room;
}
