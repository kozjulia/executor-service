package ru.t1.executor;

import java.util.Objects;
import java.util.Queue;

/**
 * @author YKozlova
 */
public class PoolThread implements Runnable {

    private Thread thread;
    private final Queue<Runnable> workQueue;
    private boolean isStopped = false;

    public PoolThread(Queue<Runnable> workQueue) {
        this.workQueue = workQueue;
    }

    public void run() {

        this.thread = Thread.currentThread();
        while (!isStopped()) {

            try {

                Runnable runnable = workQueue.poll();
                if (Objects.nonNull(runnable)) {
                    runnable.run();
                }
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
    }

    public synchronized void stop() {

        isStopped = true;
        this.thread.interrupt();
    }

    public synchronized boolean isStopped() {
        return isStopped;
    }
}
