package io.mattrandom.repositories.entities;

import io.mattrandom.enums.RoomType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.List;

@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor

@Entity
@Table(name = "rooms")
public class RoomEntity extends AbstractEntity {

    private BigDecimal cost;

    @Enumerated(EnumType.STRING)
    private RoomType roomType;

    @OneToMany(
            fetch = FetchType.EAGER,
            cascade = CascadeType.ALL,
            mappedBy = "room",
            orphanRemoval = true
    )
    private List<RentEntity> rents;

    public void addRent(RentEntity rent) {
        this.rents.add(rent);
        rent.setRoom(this);
    }

    public void removeRent(RentEntity rent) {
        this.rents.remove(rent);
        rent.setRoom(null);
    }
}
