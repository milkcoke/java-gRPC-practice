package com.example.grpcpractice.client;

import com.example.grpcpractice.proto.bank.Balance;
import com.example.grpcpractice.proto.bank.BalanceCheckRequest;
import com.example.grpcpractice.proto.bank.BankServiceGrpc;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import static org.junit.jupiter.api.Assertions.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class BankClientTest {

    private BankServiceGrpc.BankServiceBlockingStub blockingStub;

    @BeforeAll
    public void setup() {
        ManagedChannel managedChannel = ManagedChannelBuilder.forAddress("localhost", 6443)
                                                                .usePlaintext()
                                                                .build();

        // client library
        this.blockingStub = BankServiceGrpc.newBlockingStub(managedChannel);
    }

    @DisplayName("잔고 체크")
    @Test
    void balanceTest() {
        BalanceCheckRequest balanceCheckRequest = BalanceCheckRequest.newBuilder()
                                                    .setAccountNumber(2)
                                                    .build();

        Balance balance = this.blockingStub.getBalance(balanceCheckRequest);

        Assertions.assertThat(balance.getAmount()).isEqualTo(2 * 10_000);
    }
}
