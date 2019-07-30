import java.util.concurrent.Semaphore;

public class Signal {

    private Semaphore semaphore = new Semaphore(0);

    //should always run before bar()
    public void foo() {
        System.out.println(String.format("Thread %s inside method foo()", Thread.currentThread().getName()));
        semaphore.release();
    }

    //should always run after foo()
    public void bar() {
        try {
            semaphore.acquire();
            System.out.println(String.format("Thread %s inside method bar()", Thread.currentThread().getName()));
        } catch (Exception e) {

        }
    }

    static class MainClass {

        public static void main(String[] args) {
            Signal signal = new Signal();
            Runnable runnable1 = () -> signal.foo(), runnable2 = () -> signal.bar();
            new Thread(runnable2).start();
            new Thread(runnable1).start();
        }
    }

}
