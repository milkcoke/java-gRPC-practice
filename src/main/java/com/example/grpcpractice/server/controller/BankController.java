package com.example.grpcpractice.server.controller;

import com.example.grpcpractice.proto.bank.Balance;
import com.example.grpcpractice.proto.bank.BalanceCheckRequest;
import com.example.grpcpractice.proto.bank.BankServiceGrpc;
import com.example.grpcpractice.server.dto.BalanceDTO;
import com.example.grpcpractice.server.service.BankService;
import com.example.grpcpractice.server.vo.BalanceVO;
import io.grpc.stub.StreamObserver;
import lombok.RequiredArgsConstructor;
import net.devh.boot.grpc.server.service.GrpcService;

@GrpcService()
@RequiredArgsConstructor
public class BankController extends BankServiceGrpc.BankServiceImplBase {
    private final BankService bankService;
    // return type is void
    // since there's stream observer

    @Override
    public void getBalance(BalanceCheckRequest request, StreamObserver<Balance> responseObserver) {
        // error channel : onError => 이때는 onComplete 호출할 필요 없음
        // data channel: onNext => 완료되면 onComplete 호출

        int reqAccountId = request.getAccountNumber();

        BalanceDTO balanceDTO = this.bankService.readBalance(new BalanceVO(reqAccountId));
        int amount = balanceDTO.amount();

        Balance balance = Balance.newBuilder()
                        .setAmount(amount)
                        .build();

        responseObserver.onNext(balance);
        responseObserver.onCompleted();
    }
}
