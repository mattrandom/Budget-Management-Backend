package io.mattrandom.repositories;

import io.mattrandom.repositories.entities.PropertyEntity;
import io.mattrandom.repositories.entities.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PropertyRepository extends JpaRepository<PropertyEntity, Long> {

    @Query("SELECT p FROM PropertyEntity p WHERE p.userEntity = :user AND p.isSold = :isSold")
    List<PropertyEntity> findByUserEntityAndSold(UserEntity user, Boolean isSold);

    @Query("SELECT p FROM PropertyEntity p WHERE p.userEntity = :user")
    List<PropertyEntity> findByUserEntity(UserEntity user);

    @Modifying(clearAutomatically = true)
    @Query("UPDATE PropertyEntity p SET p.isSold = true WHERE p.userEntity = :user AND p.id = :propertyId")
    void updatePropertyAsSold(UserEntity user, Long propertyId);
}