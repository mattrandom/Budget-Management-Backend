package io.mattrandom.services.integrations;

import io.mattrandom.repositories.entities.UserEntity;
import io.mattrandom.services.dtos.PropertyDto;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class PropertyServiceIntegrationTests extends AbstractIntegrationTestSchema {

    @Test
    void givenPropertyObject_whenAddProperty_thenSaveIntoDB() {
        //given
        saveMockedUserInDB();
        PropertyDto propertyDto = PropertyDto.builder()
                .postalCode("66-666")
                .city("BigCity")
                .street("Long Street")
                .rooms(2)
                .single(false)
                .house("Flat")
                .build();

        //when
        propertyService.addProperty(propertyDto);

        //then
        assertThat(propertyRepository.findAll()).hasSize(1);
    }

    @Test
    void givenPropertyList_whenGetAllProperties_thenReturnPropertiesBelongToPrincipal() {
        //given
        UserEntity user = saveMockedUserInDB();
        initializingPropertyDB(user);

        //when
        List<PropertyDto> propertiesDto = propertyService.getAllProperties();

        //then
        assertThat(propertiesDto).hasSize(1);
    }
}
