package io.mattrandom.repositories.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import javax.persistence.Entity;
import javax.persistence.Table;

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
    private Integer rooms;
    private Boolean single;
    private String house;
}
