package io.mattrandom.repositories.entities;

import io.mattrandom.enums.RoomType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;
import java.math.BigDecimal;

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
}
