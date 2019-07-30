import java.util.concurrent.Semaphore;

//Thread A and Thread B should have mutually exclusive access to shared variable "count"
public class Mutex {

    int count = 0;
    Semaphore semaphore = new Semaphore(1);

    public void foo() {
        try {
            semaphore.acquire();
            System.out.println(String.format("Thread %s incremented the count to %d", Thread.currentThread().getName(), ++count));
            semaphore.release();
        } catch (Exception e) {

        }
    }

    public void bar() {
        try {
            semaphore.acquire();
            System.out.println(String.format("Thread %s incremented the count to %d", Thread.currentThread().getName(), ++count));
            semaphore.release();
        } catch (Exception e) {

        }
    }

    static class MainClass {

        public static void main(String[] args) {
            Mutex mutex = new Mutex();
            Runnable runnable1 = () -> mutex.foo(), runnable2 = () -> mutex.bar();
            new Thread(runnable2).start();
            new Thread(runnable1).start();
        }
    }
}
