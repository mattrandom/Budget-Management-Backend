package io.mattrandom.repositories.entities;

import lombok.*;

import javax.persistence.*;
import java.util.Objects;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor

@Entity
@Table(name = "properties")
public class PropertyEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, updatable = false)
    private Long id;
    private String postalCode;
    private String city;
    private String street;
    private Integer rooms;
    private Boolean single;
    private String house;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private UserEntity userEntity;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PropertyEntity that = (PropertyEntity) o;
        return Objects.equals(id, that.id) && Objects.equals(postalCode, that.postalCode) && Objects.equals(rooms, that.rooms) && Objects.equals(single, that.single) && Objects.equals(city, that.city) && Objects.equals(street, that.street) && Objects.equals(house, that.house) && Objects.equals(userEntity, that.userEntity);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, postalCode, rooms, single, city, street, house, userEntity);
    }

    @Override
    public String toString() {
        return "PropertyEntity{" +
                "id=" + id +
                ", postalCode='" + postalCode + '\'' +
                ", rooms=" + rooms +
                ", single=" + single +
                ", city='" + city + '\'' +
                ", street='" + street + '\'' +
                ", house='" + house + '\'' +
                ", userEntity=" + userEntity +
                '}';
    }
}
