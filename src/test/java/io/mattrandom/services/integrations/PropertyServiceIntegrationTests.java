package io.mattrandom.services.integrations;

import io.mattrandom.repositories.entities.PropertyEntity;
import io.mattrandom.repositories.entities.UserEntity;
import io.mattrandom.services.dtos.PropertyDto;
import io.mattrandom.services.dtos.PropertyExtendedDto;
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
                .singleFriendly(false)
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

    @Test
    void givenPropertyObject_whenUpdateProperty_thenReturnUpdatedPropertyObject() {
        //given
        UserEntity user = saveMockedUserInDB();
        PropertyEntity propertyEntity = initializingPropertyDB(user);

        PropertyDto propertyDto = PropertyDto.builder()
                .id(propertyEntity.getId())
                .postalCode("00-000")
                .city("New City")
                .street("New Street")
                .singleFriendly(true)
                .house("Flat")
                .build();

        //when
        propertyService.updateProperty(propertyDto);

        //then
        assertThat(propertyRepository.findAll().get(0).getCity()).isEqualTo(propertyDto.getCity());
        assertThat(propertyRepository.findAll().get(0).getStreet()).isEqualTo(propertyDto.getStreet());
    }

    @Test
    void givenProperty_whenDeleteProperty_thenDeleteAndReturnNoContent() {
        //given
        UserEntity user = saveMockedUserInDB();
        initializingPropertyDB(user);
        PropertyDto propertyDto = propertyService.getAllProperties().stream().findFirst().get();

        //when
        propertyService.deleteProperty(propertyDto);

        //then
        assertThat(propertyRepository.findAll()).hasSize(0);
    }
}
