import java.util.LinkedList;
import java.util.Queue;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

public class ProducerConsumer {

    private Semaphore mutex = new Semaphore(1), items = new Semaphore(0);
    Random random = new Random();
    Queue<Event> queue = new LinkedList<>();

    public void producerCode() {
        try {
            Event event = waitForEvent();
            mutex.acquire();
            queue.add(event);
            mutex.release();
            items.release();
        } catch (Exception e) {

        }

    }

    public void consumerCode() {
        try {
            items.acquire();
            mutex.acquire();
            Event event = queue.poll();
            mutex.release();
            event.process();
        } catch (Exception e) {

        }
    }

    public Event waitForEvent() {
        try {
            TimeUnit.SECONDS.sleep(random.nextInt(10)+1);
        } catch (Exception e) {

        }
        return new Event(random.nextInt());
    }

    class Event {
        int id;

        public Event(int id) {
            this.id = id;
        }

        public void process() {
            System.out.println(String.format("Processing Event %d", id));
        }
    }

    static class MainClass {

        public static void main(String[] args) {
            Random random = new Random();
            ProducerConsumer producerConsumer = new ProducerConsumer();
            Runnable producer = () -> producerConsumer.producerCode(), consumer = () -> producerConsumer.consumerCode();
            ExecutorService executorService = Executors.newFixedThreadPool(50);

            for(int i=0;i<50;i++) {
                int num = random.nextInt();
                if(num%2==1) {
                    executorService.submit(producer);
                } else {
                    executorService.submit(consumer);
                }
            }
        }
    }
}
