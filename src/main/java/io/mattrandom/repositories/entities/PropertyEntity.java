package io.mattrandom.repositories.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;
import java.util.List;

@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor

@Entity
@Table(name = "properties")
public class PropertyEntity extends AbstractEntity {

    private String postalCode;
    private String city;
    private String street;
    private String house;
    private Boolean isSingleFriendly;
    private Boolean isSold;

    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "property_id")
    private List<RoomEntity> rooms;
}
