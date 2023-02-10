package com.example.grpcpractice.server.command;

import com.example.grpcpractice.server.repository.BankRepository;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class DefaultAddBalanceCommand implements AddBalanceCommand{
    private BankRepository bankRepository;
    @Override
    public int execute(int accountId, int amount) {
        return bankRepository.addBalanceById(accountId, amount);
    }
}
