package io.mattrandom.repositories;

import io.mattrandom.repositories.entities.AssetEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AssetsRepository extends JpaRepository<AssetEntity, Long> {
}
