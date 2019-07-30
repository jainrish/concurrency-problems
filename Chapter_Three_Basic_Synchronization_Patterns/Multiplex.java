import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

public class Multiplex {

    private Semaphore semaphore, mutex = new Semaphore(1);
    int count=0;
    public Multiplex(int permits) {
        semaphore = new Semaphore(permits);
    }

    private void foo() {
        try {
            semaphore.acquire();
            increment();
            criticalSection();
            decrement();
            semaphore.release();
        } catch (Exception e) {

        }
    }

    private void criticalSection() {
        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (Exception e) {

        }
    }

    private void increment() {
        try {
            mutex.acquire();
            System.out.println(++count);  // should never be more than permits count
            mutex.release();
        } catch (Exception e) {

        }
    }

    private void decrement() {
        try {
            mutex.acquire();
            --count;
            mutex.release();
        } catch (Exception e) {

        }
    }

    public static void main(String[] args) throws Exception {
        int permits = 10;
        Multiplex multiplex = new Multiplex(permits);
        Runnable runnableTask = () -> multiplex.foo();
        ExecutorService executor = Executors.newFixedThreadPool(permits);
        for (int i = 0; i <= 100; i++) executor.submit(runnableTask);
        executor.shutdown();
    }
}
