package io.mattrandom.services.integrations;

import io.mattrandom.enums.AssetCategory;
import io.mattrandom.repositories.entities.AssetEntity;
import io.mattrandom.repositories.entities.UserEntity;
import io.mattrandom.services.dtos.AssetDto;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static org.assertj.core.api.Assertions.assertThat;

public class AssetsServiceIntegrationTests extends AbstractIntegrationTestSchema {

    @Test
    void givenInitialStateOfDb_whenGetAllAssets_thenShouldReturnTwoObjects() {
        //given
        initializingDbWithDefaultPrincipal();
        initializingDbWithNotLoggedInUser();

        //when
        List<AssetDto> allAssets = assetsService.getAllAssetsByPrincipal();

        //then
        assertThat(allAssets).hasSize(2);
    }

    @Test
    void givenAssetDto_whenAddAsset_thenSaveObjectTODb() {
        //given
        AssetDto assetDto = AssetDto.builder()
                .amount(BigDecimal.valueOf(10))
                .incomeDate(LocalDateTime.now())
                .assetCategory(AssetCategory.RENTAL_INCOME)
                .build();

        saveMockedUserInDB();

        //when
        assetsService.addAsset(assetDto);

        //then
        List<AssetEntity> entities = assetsRepository.findAll();
        assertThat(entities).hasSize(1);
        assertThat(entities.get(0).getAmount()).isEqualTo(assetDto.getAmount());
        assertThat(entities.get(0).getAssetCategory()).isEqualTo(assetDto.getAssetCategory());
        assertThat(entities.get(0).getIncomeDate()).isEqualTo(assetDto.getIncomeDate());
        assertThat(entities.get(0).getUserEntity().getUsername()).isEqualTo("principal");

    }

    @Test
    void givenSpecifiedAssetCategory_whenGetAllAssetsByCategory_thenReturnAssetsOnlyWithParticularCategory() {
        //given
        initializingDbWithDefaultPrincipal();
        AssetCategory loanCategory = AssetCategory.LOAN_RETURN;

        //when
        List<AssetDto> allAssetsByCategory = assetsService.getAllAssetsByCategory(loanCategory);

        //then
        assertThat(allAssetsByCategory).hasSize(1);
        assertThat(allAssetsByCategory.get(0).getAssetCategory()).isEqualTo(loanCategory);
    }

    @Test
    void givenUsersWithAssets_whenDeletingAssetsByUser_thenTotalAmountOfAssetsIsReduced() {
        //given
        initializingDbWithDefaultPrincipal();
        initializingDbWithNotLoggedInUser();

        List<UserEntity> userEntities = StreamSupport.stream(userRepository.findAll().spliterator(), false).toList();
        UserEntity user = userEntities.stream().findFirst().get();
        UserEntity userPrincipal = userEntities.stream().filter(userEntity -> userEntity.equals(user)).findFirst().get();

        List<AssetEntity> assetsBeforeDeletingByUser = assetsRepository.findAll();
        assertThat(assetsBeforeDeletingByUser).hasSize(4);
        Set<String> usernamesBeforeDeleting = assetsBeforeDeletingByUser.stream()
                .map(assetEntity -> assetEntity.getUserEntity().getUsername())
                .collect(Collectors.toSet());
        assertThat(usernamesBeforeDeleting).hasSize(2);

        //when
        assetsService.deleteAssetsByUser(userPrincipal);

        //then
        List<AssetEntity> assetsAfterDeletingByUser = assetsRepository.findAll();
        assertThat(assetsAfterDeletingByUser).hasSize(2);

        Set<String> usernamesAfterDeleting = assetsAfterDeletingByUser.stream()
                .map(assetEntity -> assetEntity.getUserEntity().getUsername())
                .collect(Collectors.toSet());
        assertThat(usernamesAfterDeleting).hasSize(1);
        assertThat(assetsAfterDeletingByUser.get(1).getUserEntity()).isNotEqualTo(userPrincipal);
    }
}
