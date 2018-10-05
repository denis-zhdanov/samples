package org.denis;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Start {
    public static void main(String[] args) throws InterruptedException {
        SpringApplication.run(Start.class);
        while (true) {
            Thread.currentThread().join();
        }
    }
}
