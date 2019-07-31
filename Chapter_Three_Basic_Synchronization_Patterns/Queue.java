import java.util.concurrent.Semaphore;

public class Queue {

    Semaphore leaderQueue = new Semaphore(0), followerQueue = new Semaphore(0);

    public void leaderArrival() {
        try {
            followerQueue.release();
            leaderQueue.acquire();
            dance();
        } catch (Exception e) {

        }
    }

    public void followerArrival() {
        try {
            leaderQueue.release();
            followerQueue.acquire();
            dance();
        } catch (Exception e) {

        }
    }

    private void dance() {
        System.out.println(String.format("Thread '%s' dancing", Thread.currentThread().getName()));
    }

    static class MainClass {

        public static void main(String[] args) {
            Queue queue = new Queue();
            Runnable runnable1 = () -> queue.leaderArrival(), runnable2 = () -> queue.followerArrival();
            new Thread(runnable1, "Leader").start();
            new Thread(runnable2, "Follower").start();
        }
    }



}
