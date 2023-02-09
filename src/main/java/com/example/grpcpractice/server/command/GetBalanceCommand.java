package com.example.grpcpractice.server.command;

public interface GetBalanceCommand {

    int execute(int accountId);

}
