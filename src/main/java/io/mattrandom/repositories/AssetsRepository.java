package io.mattrandom.repositories;

import io.mattrandom.enums.AssetCategory;
import io.mattrandom.repositories.entities.AssetEntity;
import io.mattrandom.repositories.entities.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AssetsRepository extends JpaRepository<AssetEntity, Long> {

    List<AssetEntity> findByAssetCategory(AssetCategory assetCategory);

    List<AssetEntity> findByUserEntity(UserEntity userEntity);
}
