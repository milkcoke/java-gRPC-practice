package com.example.grpcpractice.server;

import com.example.grpcpractice.proto.bank.Balance;
import com.example.grpcpractice.proto.bank.BalanceCheckRequest;
import com.example.grpcpractice.proto.bank.BankServiceGrpc;
import io.grpc.stub.StreamObserver;
import lombok.RequiredArgsConstructor;
import net.devh.boot.grpc.server.service.GrpcService;

@GrpcService()
@RequiredArgsConstructor
public class BankService extends BankServiceGrpc.BankServiceImplBase {
    private final BankRepository bankRepository;
    // return type is void
    // since there's stream observer

    @Override
    public void getBalance(BalanceCheckRequest request, StreamObserver<Balance> responseObserver) {
        // error channel : onError => 이때는 onComplete 호출할 필요 없음
        // data channel: onNext => 완료되면 onComplete 호출

        int reqAccountId = request.getAccountNumber();

        int amount = bankRepository.getBalanceByAccountId(reqAccountId);

        Balance balance = Balance.newBuilder()
                        .setAmount(amount)
                        .build();

        responseObserver.onNext(balance);
        responseObserver.onCompleted();
    }
}
