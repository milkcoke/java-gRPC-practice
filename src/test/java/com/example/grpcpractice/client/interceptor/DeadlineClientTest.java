package com.example.grpcpractice.client.interceptor;

import com.example.grpcpractice.proto.bank.Balance;
import com.example.grpcpractice.proto.bank.BalanceCheckRequest;
import com.example.grpcpractice.proto.bank.BankServiceGrpc;
import io.grpc.*;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.assertThrows;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class DeadlineClientTest {

    private BankServiceGrpc.BankServiceBlockingStub bankServiceBlockingStub;
    private final ClientInterceptor clientInterceptor = new DeadlineInterceptor();

    @BeforeAll
    void setup() {
        ManagedChannel managedChannel = ManagedChannelBuilder.forAddress("localhost", 6443)
                                            .intercept(clientInterceptor)
                                            .usePlaintext()
                                            .build();

        this.bankServiceBlockingStub = BankServiceGrpc.newBlockingStub(managedChannel);
    }

    @DisplayName("Fail for deadline is 2 seconds but 3 sec blocking")
    @Test
    void getBalanceTestWithDeadLine() {
        BalanceCheckRequest balanceCheckRequest = BalanceCheckRequest.newBuilder()
                .setAccountNumber(5)
                .build();


        assertThrows(StatusRuntimeException.class, ()->{
            Balance balance = this.bankServiceBlockingStub
                    .withDeadline(Deadline.after(2, TimeUnit.SECONDS))
                    .getBalance(balanceCheckRequest);
            System.out.println(balance);
        });
    }

    @DisplayName("Success for deadline is 4 seconds but 3 sec blocking")
    @Test
    void getBalanceTestWithDeadLineSuccess() {
        BalanceCheckRequest balanceCheckRequest = BalanceCheckRequest.newBuilder()
                .setAccountNumber(5)
                .build();


        Balance balance = this.bankServiceBlockingStub
                .withDeadlineAfter(4, TimeUnit.SECONDS)
                .getBalance(balanceCheckRequest);

        System.out.println(balance);
    }
}
