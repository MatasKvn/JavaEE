package org.example.demo;

import jakarta.ejb.Asynchronous;
import jakarta.ejb.Stateless;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Future;
import java.util.concurrent.FutureTask;

@Stateless
public class HeavyCalc {
    @Asynchronous
    public Future<String> heavyCalculation() throws InterruptedException {
        return CompletableFuture.supplyAsync(() -> {
                try {
                    Thread.sleep(10000); // simulate slow work
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                return "Calculation completed!";
            });
    }
}
