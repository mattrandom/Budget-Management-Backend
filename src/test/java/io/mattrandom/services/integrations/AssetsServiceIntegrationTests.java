package io.mattrandom.services.integrations;

import io.mattrandom.enums.AssetCategory;
import io.mattrandom.repositories.AssetsRepository;
import io.mattrandom.repositories.UserRepository;
import io.mattrandom.repositories.entities.AssetEntity;
import io.mattrandom.repositories.entities.UserEntity;
import io.mattrandom.services.AssetsService;
import io.mattrandom.services.dtos.AssetDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@Transactional
@SpringBootTest
@WithMockUser(username = "principal", password = "pass")
public class AssetsServiceIntegrationTests {

    @Autowired
    private AssetsRepository assetsRepository;

    @Autowired
    private AssetsService assetsService;

    @Autowired
    private UserRepository userRepository;

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

    private UserEntity saveMockedUserInDB() {
        UserEntity userEntity = new UserEntity();
        userEntity.setUsername("principal");
        userEntity.setPassword("pass");
        return userRepository.save(userEntity);
    }

    private void initializingDbWithDefaultPrincipal() {
        UserEntity principal = saveMockedUserInDB();

        AssetEntity entity1 = AssetEntity.builder()
                .amount(BigDecimal.valueOf(10))
                .incomeDate(LocalDateTime.now())
                .assetCategory(AssetCategory.OTHER)
                .userEntity(principal)
                .build();

        AssetEntity entity2 = AssetEntity.builder()
                .amount(BigDecimal.valueOf(20))
                .incomeDate(LocalDateTime.now())
                .assetCategory(AssetCategory.LOAN_RETURN)
                .userEntity(principal)
                .build();

        assetsRepository.saveAll(List.of(entity1, entity2));

    }

    private UserEntity saveSecondMockedUserInDB() {
        UserEntity userEntity = new UserEntity();
        userEntity.setUsername("notLoggedIn");
        userEntity.setPassword("pass");
        return userRepository.save(userEntity);
    }

    private void initializingDbWithNotLoggedInUser() {
        UserEntity principal = saveSecondMockedUserInDB();

        AssetEntity entity1 = AssetEntity.builder()
                .amount(BigDecimal.valueOf(10))
                .incomeDate(LocalDateTime.now())
                .assetCategory(AssetCategory.OTHER)
                .userEntity(principal)
                .build();

        AssetEntity entity2 = AssetEntity.builder()
                .amount(BigDecimal.valueOf(20))
                .incomeDate(LocalDateTime.now())
                .assetCategory(AssetCategory.LOAN_RETURN)
                .userEntity(principal)
                .build();

        assetsRepository.saveAll(List.of(entity1, entity2));

    }
}
