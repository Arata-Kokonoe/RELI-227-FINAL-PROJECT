package main;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ThreadPool {
    private static ExecutorService executor;
        
    public static void init() {
        executor = Executors.newCachedThreadPool();
    }
    
    public static void execute(Runnable r) {
        if (executor == null)
            init();
        executor.execute(r);
    }
    
    public static void shutdown() {
        executor.shutdown();
    }
    
    public static void shutdownNow() {
        executor.shutdownNow();
    }

}
