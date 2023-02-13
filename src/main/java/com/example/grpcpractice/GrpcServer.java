package com.example.grpcpractice;

import com.example.grpcpractice.server.controller.BankController;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@RequiredArgsConstructor
public class GrpcServer {
    private final BankController bankService;

    public static void main(String[] args){
        SpringApplication.run(GrpcServer.class, args);
    }

}
