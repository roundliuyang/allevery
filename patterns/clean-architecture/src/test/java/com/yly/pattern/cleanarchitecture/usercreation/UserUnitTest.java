package com.yly.pattern.cleanarchitecture.usercreation;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class UserUnitTest {

    @Test
    void given123Password_whenPasswordIsNotValid_thenIsFalse() {
        User user = new CommonUser("Baeldung", "123");

        assertThat(user.passwordIsValid()).isFalse();
    }
}
