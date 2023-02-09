package com.example.grpcpractice.server.command;

import com.example.grpcpractice.server.BankRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DefaultGetBalanceCommand implements GetBalanceCommand {
    private final BankRepository bankRepository;

    @Override
    public int execute(int accountId) {
        return bankRepository.findBalanceById(accountId);
    }

}
