package io.mattrandom.repositories;

import io.mattrandom.enums.AssetCategory;
import io.mattrandom.repositories.entities.AssetEntity;
import io.mattrandom.repositories.entities.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface AssetRepository extends JpaRepository<AssetEntity, Long> {

    List<AssetEntity> findByAssetCategory(AssetCategory assetCategory);

    List<AssetEntity> findByUserEntity(UserEntity userEntity);

    void deleteByUserEntity(UserEntity userEntity);

    @Query("SELECT a from AssetEntity a WHERE a.userEntity = :user AND a.incomeDate BETWEEN :incomeDateFrom AND :incomeDateTo")
    List<AssetEntity> findByIncomeDateBetween(UserEntity user, LocalDateTime incomeDateFrom, LocalDateTime incomeDateTo);
}
