package com.example.grpcpractice.proto.television;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;


class TelevisionTest {

    private static final String FILE_EXTENSION = ".txt";

    @DisplayName("V1 test")
    @Test
    void testV1() throws IOException {
//        Television television = Television.newBuilder()
//                                .setBrand("sony")
//                                .setYear(2015)
//                                .build();

        Path pathV1 = Paths.get("tv-v1" + FILE_EXTENSION);

//        Files.write(pathV1, television.toByteArray());

        byte[] bytes = Files.readAllBytes(pathV1);

        System.out.println(Television.parseFrom(bytes));
    }

    @DisplayName("Read successfully after v1 is updated to v2")
    @Test
    void testV2() throws IOException {

        Path pathV1 = Paths.get("tv-v1" + FILE_EXTENSION);

//        Files.write(pathV1, television.toByteArray());

        byte[] bytes = Files.readAllBytes(pathV1);

        Television television = Television.parseFrom(bytes);
        // read based on tag number
//        System.out.println(television.getModelNumber());
        System.out.println(television.getResolution());

        // tag name 순서 변경만 안하면 rename 정도는 문제 없음.
    }

    @Test
    void createV2TV() throws IOException {

        Television television = Television.newBuilder()
                        .setBrand("sony")
//                        .setModelNumber(2017)
                        .setResolution(ResolutionLevel.FHD)
                        .build();

        Path pathV2 = Paths.get("tv-v2" + FILE_EXTENSION);

        Files.write(pathV2, television.toByteArray());

        byte[] bytes = Files.readAllBytes(pathV2);

        Television readTV = Television.parseFrom(bytes);
        // read based on tag number
//        System.out.println(readTV.getModelNumber());
        System.out.println(readTV.getResolution());

    }

    @DisplayName("Use V2 in V3")
    @Test
    void useV2InV3() throws IOException {
        Television television = Television.newBuilder()
                .setBrand("yepp")
                .setResolution(ResolutionLevel.UHD)
                .build();

        Path pathV2 = Paths.get("tv-v2" + FILE_EXTENSION);

        byte[] bytes = Files.readAllBytes(pathV2);

        Television readTV = Television.parseFrom(bytes);
        System.out.println(readTV);
    }


    @DisplayName("Use V4")
    @Test
    void useV4() throws IOException {
        Television television = Television.newBuilder()
                .setBrand("yepp")
                .setResolution(ResolutionLevel.UHD)
                .build();

        Path pathV2 = Paths.get("tv-v2" + FILE_EXTENSION);

        byte[] bytes = Files.readAllBytes(pathV2);

        Television readTV = Television.parseFrom(bytes);
        System.out.println(readTV.getPrice()); // default value 0 is printed
        System.out.println(readTV);
    }
}
