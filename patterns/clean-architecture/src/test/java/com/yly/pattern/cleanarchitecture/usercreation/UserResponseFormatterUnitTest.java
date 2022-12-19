package com.yly.pattern.cleanarchitecture.usercreation;

import org.junit.jupiter.api.Test;
import org.springframework.web.server.ResponseStatusException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class UserResponseFormatterUnitTest {

    UserResponseFormatter userResponseFormatter = new UserResponseFormatter();

    @Test
    void givenDateAnd3HourTime_whenPrepareSuccessView_thenReturnOnly3HourTime() {
        UserResponseModel modelResponse = new UserResponseModel("baeldung", "2020-12-20T03:00:00.000");
        UserResponseModel formattedResponse = userResponseFormatter.prepareSuccessView(modelResponse);

        assertThat(formattedResponse.getCreationTime()).isEqualTo("03:00:00");
    }

    @Test
    void whenPrepareFailView_thenThrowHttpConflictException() {
        assertThatThrownBy(() -> userResponseFormatter.prepareFailView("Invalid password"))
            .isInstanceOf(ResponseStatusException.class);
    }
}
