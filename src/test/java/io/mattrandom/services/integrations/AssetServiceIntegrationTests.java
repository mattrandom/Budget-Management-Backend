package io.mattrandom.services.integrations;

import io.mattrandom.enums.AssetCategory;
import io.mattrandom.enums.QueryParamMessageEnum;
import io.mattrandom.enums.QueryParamConditionsEnum;
import io.mattrandom.enums.MonthSpecificationEnum;
import io.mattrandom.exceptions.AssetFilterQueryParamException;
import io.mattrandom.exceptions.ExpenseFilterQueryParamException;
import io.mattrandom.repositories.entities.AssetEntity;
import io.mattrandom.repositories.entities.UserEntity;
import io.mattrandom.services.dtos.AssetDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class AssetServiceIntegrationTests extends AbstractIntegrationTestSchema {

    @Test
    void givenInitialStateOfDb_whenGetAllAssets_thenShouldReturnTwoObjects() {
        //given
        initializingAssetsDBWithPrincipal();
        initializingAssetsDBWithNotLoggedInUser();

        //when
        List<AssetDto> allAssets = assetService.getAllAssetsByPrincipal();

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
        assetService.addAsset(assetDto);

        //then
        List<AssetEntity> entities = assetRepository.findAll();
        assertThat(entities).hasSize(1);
        assertThat(entities.get(0).getAmount()).isEqualTo(assetDto.getAmount());
        assertThat(entities.get(0).getAssetCategory()).isEqualTo(assetDto.getAssetCategory());
        assertThat(entities.get(0).getIncomeDate()).isEqualTo(assetDto.getIncomeDate());
        assertThat(entities.get(0).getUserEntity().getUsername()).isEqualTo("principal");

    }

    @Test
    void givenSpecifiedAssetCategory_whenGetAllAssetsByCategory_thenReturnAssetsOnlyWithParticularCategory() {
        //given
        initializingAssetsDBWithPrincipal();
        AssetCategory loanCategory = AssetCategory.LOAN_RETURN;

        //when
        List<AssetDto> allAssetsByCategory = assetService.getAllAssetsByCategory(loanCategory);

        //then
        assertThat(allAssetsByCategory).hasSize(1);
        assertThat(allAssetsByCategory.get(0).getAssetCategory()).isEqualTo(loanCategory);
    }

    @Test
    void givenUsersWithAssets_whenDeletingAssetsByUser_thenTotalAmountOfAssetsIsReduced() {
        //given
        initializingAssetsDBWithPrincipal();
        initializingAssetsDBWithNotLoggedInUser();

        List<UserEntity> userEntities = StreamSupport.stream(userRepository.findAll().spliterator(), false).toList();
        UserEntity user = userEntities.stream().findFirst().get();
        UserEntity userPrincipal = userEntities.stream().filter(userEntity -> userEntity.equals(user)).findFirst().get();

        List<AssetEntity> assetsBeforeDeletingByUser = assetRepository.findAll();
        assertThat(assetsBeforeDeletingByUser).hasSize(4);
        Set<String> usernamesBeforeDeleting = assetsBeforeDeletingByUser.stream()
                .map(assetEntity -> assetEntity.getUserEntity().getUsername())
                .collect(Collectors.toSet());
        assertThat(usernamesBeforeDeleting).hasSize(2);

        //when
        assetService.deleteAssetsByUser(userPrincipal);

        //then
        List<AssetEntity> assetsAfterDeletingByUser = assetRepository.findAll();
        assertThat(assetsAfterDeletingByUser).hasSize(2);

        Set<String> usernamesAfterDeleting = assetsAfterDeletingByUser.stream()
                .map(assetEntity -> assetEntity.getUserEntity().getUsername())
                .collect(Collectors.toSet());
        assertThat(usernamesAfterDeleting).hasSize(1);
        assertThat(assetsAfterDeletingByUser.get(1).getUserEntity()).isNotEqualTo(userPrincipal);
    }

    @Test
    void givenAssetsObjects_whenGetAssetsByFilteredConditions_thenShouldReturnObjects() {
        //given
        UserEntity user = saveMockedUserInDB();
        initializingAssetsDB(user, "2022-01-05");
        initializingAssetsDB(user, "2022-02-05");
        initializingAssetsDB(user, "2022-03-05");
        initializingAssetsDB(user, "2022-04-05");
        initializingAssetsDB(user, "2022-05-05");
        initializingAssetsDB(user, "2022-06-05");

        //when
        Map<String, String> fromToConditions = Map.of(
                QueryParamConditionsEnum.DATE_FROM.getQueryParamKey(), "2022-02-05",
                QueryParamConditionsEnum.DATE_TO.getQueryParamKey(), "2022-05-05"
        );
        List<AssetDto> allExpenses = assetService.getAssetsByFilteredConditions(fromToConditions);

        //then
        assertThat(allExpenses).hasSize(4);
    }

    @Test
    void givenExpenseObjects_whenGetMonthlyExpensesByGivenYear_thenShouldReturnObjects() {
        //given
        UserEntity user = saveMockedUserInDB();
        initializingAssetsDB(user, "2022-01-05");
        initializingAssetsDB(user, "2022-04-01");
        initializingAssetsDB(user, "2022-04-05");
        initializingAssetsDB(user, "2022-04-15");
        initializingAssetsDB(user, "2022-04-30");
        initializingAssetsDB(user, "2022-06-05");

        //when
        Map<String, String> yearMonthConditions = Map.of(
                QueryParamConditionsEnum.MONTH.getQueryParamKey(), MonthSpecificationEnum.APRIL.name(),
                QueryParamConditionsEnum.YEAR.getQueryParamKey(), "2022"
        );
        List<AssetDto> allExpenses = assetService.getAssetsByFilteredConditions(yearMonthConditions);

        //then
        assertThat(allExpenses).hasSize(4);
    }

    @ParameterizedTest(name = "{0}")
    @MethodSource("notSpecifiedOneOfFilterParams")
    void given_when_then(String name, AssetsParameterData data) {
        //given
        UserEntity user = saveMockedUserInDB();
        //when
        AssetFilterQueryParamException result = assertThrows(AssetFilterQueryParamException.class, () -> assetService.getAssetsByFilteredConditions(data.getConditions()));

        //then
        assertThat(result.getMessage()).isEqualTo(QueryParamMessageEnum.NO_ASSET_FILTER_PARAM_KEY.getMessage(data.getNotSpecifiedQueryParam().getQueryParamKey()));

    }

    private static Stream<Arguments> notSpecifiedOneOfFilterParams() {
        return Stream.of(
                Arguments.of("Test case for not specified query param: " + QueryParamConditionsEnum.DATE_TO.getQueryParamKey(),
                        new AssetsParameterData(
                                Map.of(QueryParamConditionsEnum.DATE_FROM.getQueryParamKey(), "2022-02-05"),
                                QueryParamConditionsEnum.DATE_TO)
                ),

                Arguments.of("Test case for not specified query param: " + QueryParamConditionsEnum.DATE_FROM.getQueryParamKey(),
                        new AssetsParameterData(
                                Map.of(QueryParamConditionsEnum.DATE_TO.getQueryParamKey(), "2022-02-05"),
                                QueryParamConditionsEnum.DATE_FROM)
                ),

                Arguments.of("Test case for not specified query param: " + QueryParamConditionsEnum.MONTH.getQueryParamKey(),
                        new AssetsParameterData(
                                Map.of(QueryParamConditionsEnum.YEAR.getQueryParamKey(), "2022"),
                                QueryParamConditionsEnum.MONTH)
                ),

                Arguments.of("Test case for not specified query param: " + QueryParamConditionsEnum.YEAR.getQueryParamKey(),
                        new AssetsParameterData(
                                Map.of(QueryParamConditionsEnum.MONTH.getQueryParamKey(), "april"),
                                QueryParamConditionsEnum.YEAR)
                )
        );
    }

    @Getter
    @AllArgsConstructor
    private static class AssetsParameterData {
        private Map<String, String> conditions;
        private QueryParamConditionsEnum notSpecifiedQueryParam;
    }
}
