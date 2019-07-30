import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

public class Barrier {

    Semaphore mutex = new Semaphore(1), semaphore, semaphore2;
    int count = 0, threads;

    public Barrier(int threads) {
        this.threads = threads;
        semaphore = new Semaphore(0);
        semaphore2 = new Semaphore(1);
    }

    public void foo() {
        for(int i=0;i<5;i++) {
            try {

                rendezvous();

                mutex.acquire();
                count++;
                if(count==threads) {
                    System.out.println(String.format("rendezvous of %d threads completed", count));
                    semaphore.release();
                    semaphore2.acquire();
                }
                mutex.release();

                semaphore.acquire();semaphore.release(); //these two statements are called "Turnstile"

                criticalSection();

                mutex.acquire();
                count--;
                if(count==0) {
                    System.out.println(String.format("critical section of %d threads completed", threads-count));
                    semaphore.acquire();
                    semaphore2.release();
                }
                mutex.release();

                semaphore2.acquire();
                semaphore2.release();

            } catch (Exception e) {

            }
        }

    }

    private void rendezvous() {

    }

    private void criticalSection() {
        try {
            System.out.println(String.format("Inside critical section for thread %s", Thread.currentThread().getName()));
        } catch (Exception e) {

        }
    }

    public static void main(String[] args) throws Exception {
        int permits = 10;
        Barrier barrier = new Barrier(permits);
        Runnable runnableTask = () -> barrier.foo();
        ExecutorService executor = Executors.newFixedThreadPool(permits);
        for (int i = 0; i < 10; i++) executor.submit(runnableTask);
        executor.shutdown();
    }
}


