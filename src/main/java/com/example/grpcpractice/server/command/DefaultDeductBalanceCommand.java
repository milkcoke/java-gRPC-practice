package com.example.grpcpractice.server.command;

import com.example.grpcpractice.server.repository.BankRepository;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class DefaultDeductBalanceCommand implements DeductBalanceCommand {
    private final BankRepository bankRepository;

    @Override
    public int execute(int amountId, int amount) {
        return bankRepository.deductBalanceById(amountId, amount);
    }
}
