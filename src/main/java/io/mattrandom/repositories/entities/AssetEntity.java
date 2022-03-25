package io.mattrandom.repositories.entities;

import io.mattrandom.enums.AssetCategory;
import lombok.*;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Objects;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor

@Entity
@Table(name = "assets")
public class AssetEntity {

    @Id
    @Column(name = "id", nullable = false, updatable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private BigDecimal amount;
    private LocalDateTime incomeDate;

    @Enumerated(EnumType.STRING)
    private AssetCategory assetCategory;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private UserEntity userEntity;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AssetEntity that = (AssetEntity) o;
        return Objects.equals(id, that.id) && Objects.equals(userEntity, that.userEntity);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, userEntity);
    }

    @Override
    public String toString() {
        return "AssetEntity{" +
                "id=" + id +
                ", amount=" + amount +
                ", incomeDate=" + incomeDate +
                ", assetCategory=" + assetCategory +
                ", userEntity=" + userEntity +
                '}';
    }
}
