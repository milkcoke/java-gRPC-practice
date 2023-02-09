package com.example.grpcpractice.server.repository;

public interface BankRepository {
    int findBalanceById(int accountId);

    Integer deposit(int accountId, int amount);

    Integer withdraw(int accountId, int amount);
}
