package com.example.grpcpractice.server;

import com.example.grpcpractice.proto.address.Address;
import com.example.grpcpractice.proto.car.BodyStyle;
import com.example.grpcpractice.proto.car.Car;
import com.example.grpcpractice.proto.car.Dealer;
import com.example.grpcpractice.proto.user.*;
import com.google.protobuf.StringValue;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.Map;

import static org.assertj.core.api.Assertions.*;

class UserClientTest {
    private final Address address = Address.newBuilder()
            .setPostbox(123)
            .setCity("Seoul")
            .setStreet("Gangnam street")
            .build();

    private final Car genesis = Car.newBuilder()
            .setMake("Hyundai")
            .setModel("Genesis")
            .setYear(2023)
//            .setBodyStyle(BodyStyle.COUPE)
            .build();

    private final Car sonanta = Car.newBuilder()
            .setMake("Hyundai")
            .setModel("Sonata")
            .setYear(2023)
            .setBodyStyle(BodyStyle.SEDAN)
            .build();


    @DisplayName("create User 테스트")
    @Test
    void testUser() {
        User user = User.newBuilder()
                .setName("Falcon")
                .setAge(30)
                .setAddress(address)
                .addCar(genesis)
                .addCar(sonanta)
                .build();

        System.out.println(user);
    }


    @DisplayName("Map 테스트")
    @Test
    void testMap() {
        Dealer dealer = Dealer.newBuilder()
                .putModel(1, genesis)
                .putModel(2, sonanta)
                .build();

        Map<Integer, Car> dealerMap = dealer.getModelMap();

        assertThat(dealerMap.get(1).getModel()).isEqualTo("Genesis");
        assertThat(dealerMap.get(2).getModel()).isEqualTo("Sonata");
    }

    @DisplayName("enum default value 테스트")
    @Test
    void testEnumDefaultValue() {
        System.out.println(genesis.getBodyStyle()); // 0
    }


    @DisplayName("Default value")
    @Test
    void testDefaultValue() {
        User user = User.newBuilder().build();

        assertThat(user.getAge()).isEqualTo(0); // default number value is zero
        assertThat(user.hasAddress()).isEqualTo(false);

    }

    @DisplayName("Test Wrapper class")
    @Test
    void testWrapper() {
        User user = User.newBuilder()
                .setRegisterDay(StringValue.newBuilder().setValue(LocalDateTime.now().toString()).build())
                .build();

        System.out.println(user.getRegisterDay());
    }
}
