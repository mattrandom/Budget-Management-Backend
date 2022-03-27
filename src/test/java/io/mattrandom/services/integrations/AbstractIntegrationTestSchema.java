package io.mattrandom.services.integrations;

import io.mattrandom.enums.AssetCategory;
import io.mattrandom.enums.ExpenseCategory;
import io.mattrandom.enums.RoomType;
import io.mattrandom.repositories.*;
import io.mattrandom.repositories.entities.*;
import io.mattrandom.services.AssetService;
import io.mattrandom.services.ExpenseService;
import io.mattrandom.services.PropertyService;
import io.mattrandom.services.RoomService;
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

import static io.mattrandom.services.integrations.AbstractIntegrationTestSchema.PRINCIPAL_PASSWORD;

@Transactional
@SpringBootTest
@WithMockUser(username = AbstractIntegrationTestSchema.PRINCIPAL_LOGIN, password = PRINCIPAL_PASSWORD)
public abstract class AbstractIntegrationTestSchema {

    @Autowired
    protected AssetRepository assetRepository;
    @Autowired
    protected AssetService assetService;
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
    @Autowired
    protected PropertyService propertyService;
    @Autowired
    protected PropertyRepository propertyRepository;
    @Autowired
    protected RoomService roomService;
    @Autowired
    protected RoomRepository roomRepository;

    protected static final String PRINCIPAL_LOGIN = "principal";
    protected static final String PRINCIPAL_PASSWORD = "pass";
    protected static final String DATE_SUFFIX = "T00:00:00.00";

    protected UserEntity saveMockedUserInDB() {
        UserEntity userEntity = new UserEntity();
        userEntity.setUsername(PRINCIPAL_LOGIN);
        userEntity.setPassword(PRINCIPAL_PASSWORD);
        return userRepository.save(userEntity);
    }

    protected UserEntity saveSecondMockedUserInDB() {
        UserEntity userEntity = new UserEntity();
        userEntity.setUsername("notLoggedIn");
        userEntity.setPassword("pass");
        return userRepository.save(userEntity);
    }

    protected void initializingAssetsDBWithPrincipal() {
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

        assetRepository.saveAll(List.of(entity1, entity2));

    }

    protected void initializingAssetsDBWithNotLoggedInUser() {
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

        assetRepository.saveAll(List.of(entity1, entity2));
    }

    protected Long initializingAssetsDB(UserEntity userEntity, String datePrefix) {
        AssetEntity assetEntity = AssetEntity.builder()
                .amount(BigDecimal.TEN)
                .incomeDate(getLocalDateTimeParser(datePrefix))
                .assetCategory(AssetCategory.SALARY)
                .userEntity(userEntity)
                .build();

        AssetEntity savedEntity = assetRepository.save(assetEntity);
        return savedEntity.getId();
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
        ExpenseEntity expenseEntity = ExpenseEntity.builder()
                .amount(BigDecimal.TEN)
                .expenseDate(getLocalDateTimeParser(datePrefix))
                .expenseCategory(ExpenseCategory.ENTERTAINMENT)
                .userEntity(userEntity)
                .build();

        ExpenseEntity savedEntity = expenseRepository.save(expenseEntity);
        return savedEntity.getId();
    }

    protected PropertyEntity initializingPropertyDB(UserEntity userEntity) {
        PropertyEntity propertyEntity = PropertyEntity.builder()
                .postalCode("66-666")
                .city("BigCity")
                .street("Long Street")
                .rooms(2)
                .single(false)
                .house("Flat")
                .userEntity(userEntity)
                .build();

        return propertyRepository.save(propertyEntity);
    }

    protected RoomEntity initializingRoomDB(UserEntity userEntity, RoomType roomType, BigDecimal roomCost) {
        RoomEntity roomEntity = RoomEntity.builder()
                .cost(roomCost)
                .roomType(roomType)
                .userEntity(userEntity)
                .build();

        return roomRepository.save(roomEntity);
    }

    private LocalDateTime getLocalDateTimeParser(String datePrefix) {
        return LocalDateTime.parse(datePrefix + DATE_SUFFIX);
    }
}
