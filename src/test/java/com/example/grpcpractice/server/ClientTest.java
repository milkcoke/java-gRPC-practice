package com.example.grpcpractice.server;

import com.example.grpcpractice.proto.bank.Balance;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;


class ClientTest {
    @DisplayName("직렬화/역직렬화")
    @Test
    void serialDeserializeTest() throws IOException {
        Balance balance = Balance.newBuilder()
                .setAmount(10_000)
                .build();

        Path path = Paths.get("balance.txt");
        // 1. serialize and write in file
        Files.write(path, balance.toByteArray());
        // 2. read the bytes
        byte[] bytes = Files.readAllBytes(path);

        // 3. deserialize from the bytes!
        Balance readBalance = Balance.parseFrom(bytes);
        System.out.println(readBalance);
    }

}
