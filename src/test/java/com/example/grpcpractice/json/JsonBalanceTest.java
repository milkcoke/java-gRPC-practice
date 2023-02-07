package com.example.grpcpractice.json;

import com.example.grpcpractice.proto.bank.Balance;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.protobuf.InvalidProtocolBufferException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.IOException;

class JsonBalanceTest {
    @DisplayName("JSON 직렬화 및 역직렬화 속도 비교")
    @Test
    void compareJsonAndProtobuf() {

        JsonBalance jsonBalance = new JsonBalance(5_000);
        ObjectMapper mapper = new ObjectMapper();

        Runnable jsonRunnable = () -> {
            byte[] bytes = new byte[0];
            try {
                bytes = mapper.writeValueAsBytes(jsonBalance);
                JsonBalance deserializedJsonBalance = mapper.readValue(bytes, JsonBalance.class);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        };

        Balance balance = Balance.newBuilder()
                .setAmount(5_000)
                .build();

        Runnable protobufRunnable = () -> {
            byte[] bytes = balance.toByteArray();
            try {
                Balance deserializedBalance = Balance.parseFrom(bytes);
            } catch (InvalidProtocolBufferException e) {
                throw new RuntimeException(e);
            }
        };

        runPerformanceTest(jsonRunnable, "json");
        runPerformanceTest(protobufRunnable, "protobuf");
    }

    private static void runPerformanceTest(Runnable runnable, String typeName) {
        long currentTime = System.currentTimeMillis();
        for (var i = 0; i < 1_000_000; i++) {
            runnable.run();
        }
        long endTime = System.currentTimeMillis();
        System.out.println(typeName + " " + (endTime - currentTime) + " ms elapsed");
    }

}
