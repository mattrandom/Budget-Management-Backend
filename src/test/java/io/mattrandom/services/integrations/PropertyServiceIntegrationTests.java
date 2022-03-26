package io.mattrandom.services.integrations;

import io.mattrandom.services.dtos.PropertyDto;
import org.junit.jupiter.api.Test;

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
}
