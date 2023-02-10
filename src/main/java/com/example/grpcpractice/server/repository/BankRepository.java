package com.example.grpcpractice.server.repository;

public interface BankRepository {
    int findBalanceById(int accountId);

    Integer addBalanceById(int accountId, int amount);

    Integer deductBalanceById(int accountId, int amount);
}
