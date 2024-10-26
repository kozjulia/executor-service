package ru.t1;

import ru.t1.executor.MyExecutorService;

public class Main {

    public static void main(String[] args) {

        MyExecutorService myExecutorService = new MyExecutorService(3);

        for (int i = 0; i < 10; i++) {

            int number = i;
            myExecutorService.execute(() -> {
                String message = Thread.currentThread().getName() + ", задача №" + number;
                System.out.println(message);
            });
        }

        myExecutorService.awaitTermination();
        myExecutorService.shutdown();

         myExecutorService.execute(() -> {
            String message = Thread.currentThread().getName() + ", задача после shutdown";
            System.out.println(message);
        });
    }
}
