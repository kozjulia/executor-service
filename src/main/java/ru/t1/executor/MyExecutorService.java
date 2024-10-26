package ru.t1.executor;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Queue;
import java.util.concurrent.Executor;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * @author YKozlova
 */
public class MyExecutorService implements Executor {

    private final Queue<Runnable> workQueue;
    private final List<PoolThread> poolThreads = new ArrayList<>();
    private boolean isStopped = false;

    public MyExecutorService(int poolSize) {
        if (poolSize < 0)
            throw new IllegalArgumentException();
        workQueue = new LinkedBlockingQueue<>();

        for (int i = 0; i < poolSize; i++) {
            PoolThread poolThreadRunnable =
                    new PoolThread(workQueue);

            poolThreads.add(poolThreadRunnable);
        }
        for (PoolThread runnable : poolThreads) {
            new Thread(runnable).start();
        }
    }

    @Override
    public void execute(Runnable command) {
        if (Objects.isNull(command))
            throw new NullPointerException();

        if (this.isStopped)
            throw new IllegalStateException("MyExecutorService shutdown");

        this.workQueue.offer(command);
    }

    public void shutdown() {
        this.isStopped = true;
        for (PoolThread runnable : poolThreads) {
            runnable.stop();
        }
    }

    public void awaitTermination() {
        while (!this.workQueue.isEmpty()) {
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                System.out.println(e.getMessage());
            }
        }
    }
}
