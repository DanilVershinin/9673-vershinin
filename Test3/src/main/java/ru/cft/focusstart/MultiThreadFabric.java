package ru.cft.focusstart;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.ArrayBlockingQueue;

public class MultiThreadFabric {
    public final static int S = 5;
    public final static int N = 5;
    public final static int TN = 1000;
    public final static int M = 3;
    public final static int TM = 800;

    public static ArrayBlockingQueue<Resource> storage = new ArrayBlockingQueue<Resource>(S);

    private static final Logger log = LoggerFactory.getLogger(MultiThreadFabric.class);

    public static void main(String[] args) {
        Runnable taskFabric = () -> {
            try {
                while (true) {
                    Thread.sleep(TN);
                    Resource res;
                    res = new Resource();
                    log.info("Resource N {} was create", res.getId());
                    storage.put(res);
                    log.info("Resource N {} push to storage", res.getId());
                }
            } catch (InterruptedException e) {
                log.error(e.getMessage());
            }
        };
        Runnable taskConsumers = () -> {
            try {
                while (true) {
                    Resource res = storage.take();
                    log.info("Resource N {} take from storage", res.getId());
                    Thread.sleep(TM);
                    log.info("Resource N {} was consumed", res.getId());
                }
            } catch (InterruptedException e) {
                log.error(e.getMessage());
            }
        };
        Thread[] fabrics = new Thread[N];
        for (int i = 0; i < N; i++) {
            fabrics[i] = new Thread(taskFabric, "Fabric N " + (i + 1));
            fabrics[i].start();
        }
        Thread[] consumers = new Thread[M];
        for (int i = 0; i < M; i++) {
            consumers[i] = new Thread(taskConsumers, "Consumer N " + (i + 1));
            consumers[i].start();
        }
    }
}
