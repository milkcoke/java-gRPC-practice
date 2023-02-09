package com.example.grpcpractice.server;

import com.example.grpcpractice.server.controller.BankController;
import com.example.grpcpractice.server.interceptor.AuthInterceptor;
import com.example.grpcpractice.server.interceptor.RequestInterceptor;
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
    private final BankController bankService;

    public static void main(String[] args){
        SpringApplication.run(GrpcServer.class, args);
    }

    @PostConstruct
    public void start() throws IOException, InterruptedException {
        Server server = ServerBuilder.forPort(6443)
                // interceptors are executed in reverse order
                .intercept(new AuthInterceptor())
                .intercept(new RequestInterceptor())
                .addService(bankService)
                .build();

        server.start();

        server.awaitTermination();
    }
}
