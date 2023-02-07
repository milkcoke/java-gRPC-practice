package com.example.grpcpractice.server;

import io.grpc.Server;
import io.grpc.ServerBuilder;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.IOException;

@SpringBootApplication
@RequiredArgsConstructor
public class GrpcServer {
    private final BankService bankService;

    public static void main(String[] args){
        SpringApplication.run(GrpcServer.class, args);
    }

    @PostConstruct
    public void start() throws IOException, InterruptedException {
        Server server = ServerBuilder.forPort(6443)
                .addService(bankService)
                .build();

        server.start();

        server.awaitTermination();
    }
}
