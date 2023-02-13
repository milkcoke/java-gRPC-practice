package com.example.grpcpractice.server.command;

public interface AddBalanceCommand {
    int execute(int accountId, int amount);
}
