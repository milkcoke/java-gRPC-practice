package com.example.grpcpractice.client.deadline;

import com.example.grpcpractice.proto.bank.Balance;
import com.example.grpcpractice.proto.bank.BalanceCheckRequest;
import com.example.grpcpractice.proto.bank.BankServiceGrpc;
import io.grpc.Deadline;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.StatusRuntimeException;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.assertThrows;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class DeadlineClientTest {

    private BankServiceGrpc.BankServiceBlockingStub bankServiceBlockingStub;

    @BeforeAll
    void setup() {
        ManagedChannel managedChannel = ManagedChannelBuilder.forAddress("localhost", 6443)
                                            .usePlaintext()
                                            .build();

        this.bankServiceBlockingStub = BankServiceGrpc.newBlockingStub(managedChannel);
    }

    @DisplayName("Deadline test 2 seconds but 3 sec blocking")
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
}
