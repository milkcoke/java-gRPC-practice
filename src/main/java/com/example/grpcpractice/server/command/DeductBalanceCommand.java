package com.example.grpcpractice.server.command;

public interface DeductBalanceCommand {
    int execute(int amountId, int amount);
}
