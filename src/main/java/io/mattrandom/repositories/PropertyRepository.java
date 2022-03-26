package io.mattrandom.repositories;

import io.mattrandom.repositories.entities.PropertyEntity;
import io.mattrandom.repositories.entities.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PropertyRepository extends JpaRepository<PropertyEntity, Long> {

    List<PropertyEntity> findByUserEntity(UserEntity userEntity);
}