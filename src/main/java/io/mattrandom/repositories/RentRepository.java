package io.mattrandom.repositories;

import io.mattrandom.repositories.entities.RentEntity;
import io.mattrandom.repositories.entities.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface RentRepository extends JpaRepository<RentEntity, Long> {

    @Modifying(clearAutomatically = true)
    @Query("UPDATE RentEntity r SET r.isRent = true WHERE r.userEntity = :user AND r.id = :rentId")
    void updateAsRented(UserEntity user, Long rentId);
}
