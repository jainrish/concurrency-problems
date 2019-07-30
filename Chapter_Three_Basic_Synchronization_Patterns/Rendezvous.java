import java.util.concurrent.Semaphore;

//Statement a1 should execute before b2 and b1 should execute before a2
public class Rendezvous {

    private Semaphore sem1 = new Semaphore(0), sem2 = new Semaphore(0);

    public void foo() {
        try {
            System.out.println(String.format("Thread %s executing statemtent a1", Thread.currentThread().getName()));
            sem1.release();
            sem2.acquire();
            System.out.println(String.format("Thread %s executing statemtent a2", Thread.currentThread().getName()));
        } catch (Exception e) {

        }

    }

    public void bar() {
        try {
            System.out.println(String.format("Thread %s executing statemtent b1", Thread.currentThread().getName()));
            sem2.release();
            sem1.acquire();
            System.out.println(String.format("Thread %s executing statemtent b2", Thread.currentThread().getName()));
        } catch (Exception e) {

        }
    }

    static class MainClass {

        public static void main(String[] args) {
            Rendezvous rendezvous = new Rendezvous();
            Runnable runnable1 = () -> rendezvous.foo(), runnable2 = () -> rendezvous.bar();
            new Thread(runnable1).start();
            new Thread(runnable2).start();
        }
    }


}
