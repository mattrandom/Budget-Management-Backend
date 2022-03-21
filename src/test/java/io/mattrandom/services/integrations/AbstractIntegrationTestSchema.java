package io.mattrandom.services.integrations;

import io.mattrandom.enums.AssetCategory;
import io.mattrandom.enums.ExpenseCategory;
import io.mattrandom.repositories.AssetsRepository;
import io.mattrandom.repositories.ExpenseRepository;
import io.mattrandom.repositories.UserRepository;
import io.mattrandom.repositories.entities.AssetEntity;
import io.mattrandom.repositories.entities.ExpenseEntity;
import io.mattrandom.repositories.entities.UserEntity;
import io.mattrandom.services.AssetsService;
import io.mattrandom.services.ExpenseService;
import io.mattrandom.services.security.CustomUserDetailsService;
import io.mattrandom.services.security.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Transactional
@SpringBootTest
@WithMockUser(username = "principal", password = "pass")
public abstract class AbstractIntegrationTestSchema {

    @Autowired
    protected AssetsRepository assetsRepository;
    @Autowired
    protected AssetsService assetsService;
    @Autowired
    protected UserRepository userRepository;
    @Autowired
    protected ExpenseRepository expenseRepository;
    @Autowired
    protected ExpenseService expenseService;
    @Autowired
    protected JwtService jwtService;
    @Autowired
    protected CustomUserDetailsService customUserDetailsService;
    @Autowired
    protected AuthenticationManager authenticationManager;

    protected static final String PRINCIPAL_LOGIN = "principal";
    protected static final String PRINCIPAL_PASSWORD = "pass";

    protected UserEntity saveMockedUserInDB() {
        UserEntity userEntity = new UserEntity();
        userEntity.setUsername("principal");
        userEntity.setPassword("pass");
        return userRepository.save(userEntity);
    }

    protected void initializingDbWithDefaultPrincipal() {
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

    protected UserEntity saveSecondMockedUserInDB() {
        UserEntity userEntity = new UserEntity();
        userEntity.setUsername("notLoggedIn");
        userEntity.setPassword("pass");
        return userRepository.save(userEntity);
    }

    protected void initializingDbWithNotLoggedInUser() {
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

    protected Long initializingExpenseDB(UserEntity userEntity) {
        ExpenseEntity expenseEntity = ExpenseEntity.builder()
                .amount(BigDecimal.TEN)
                .expenseDate(LocalDateTime.now())
                .expenseCategory(ExpenseCategory.ENTERTAINMENT)
                .userEntity(userEntity)
                .build();

        ExpenseEntity savedEntity = expenseRepository.save(expenseEntity);
        return savedEntity.getId();
    }

    protected Long initializingExpenseDB(UserEntity userEntity, String datePrefix) {
        String dateSuffix = "T00:00:00.00";
        ExpenseEntity expenseEntity = ExpenseEntity.builder()
                .amount(BigDecimal.TEN)
                .expenseDate(LocalDateTime.parse(datePrefix + dateSuffix))
                .expenseCategory(ExpenseCategory.ENTERTAINMENT)
                .userEntity(userEntity)
                .build();

        ExpenseEntity savedEntity = expenseRepository.save(expenseEntity);
        return savedEntity.getId();
    }
}
