package com.example.grpcpractice.proto.credentials;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class CredentialsTest {

    private Credentials credentials;

    private static void login(Credentials credentials) {
        switch (credentials.getModeCase()) {
            case EMAILMODE -> System.out.println(credentials.getEmailMode());
            case PHONEMODE -> System.out.println(credentials.getPhoneMode());
        }
    }

    @DisplayName("이메일 인증")
    @Test
    void testEmail() {
        EmailCredentials emailCredentials = EmailCredentials.newBuilder()
                                            .setEmail("falcon@tistory.com")
                                            .setPassword("khazix123")
                                            .build();

        credentials = Credentials.newBuilder()
                .setEmailMode(emailCredentials)
                .build();

        login(credentials);
    }

    @DisplayName("핸드폰 인증")
    @Test
    void testPhoneOtp() {
        PhoneOtp phoneOtp = PhoneOtp.newBuilder()
                                .setNumber(1231202021)
                                .setCode(1010101)
                                .build();

        credentials = Credentials.newBuilder()
                            .setPhoneMode(phoneOtp)
                            .build();

        login(credentials);

    }

    @DisplayName("양 모드 모두 적용시 마지막 하나만")
    @Test
    void testBothModeSet() {
        EmailCredentials emailCredentials = EmailCredentials.newBuilder()
                .setEmail("falcon@tistory.com")
                .setPassword("khazix123")
                .build();

        PhoneOtp phoneOtp = PhoneOtp.newBuilder()
                .setNumber(1231202021)
                .setCode(1010101)
                .build();

        credentials = Credentials.newBuilder()
                .setEmailMode(emailCredentials)
                // only one mode is set
                .setPhoneMode(phoneOtp)
                .build();

        login(credentials);

    }
}
