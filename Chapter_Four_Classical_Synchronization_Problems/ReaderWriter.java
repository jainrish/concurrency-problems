import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;

public class ReaderWriter {

    LightSwitch lightSwitch = new LightSwitch();
    Semaphore writerLock = new Semaphore(1);
    StringBuilder sb = new StringBuilder();

    public void writer(String str) {
        try {
            writerLock.acquire();
            write(str);
            writerLock.release();
        } catch (InterruptedException e) {

        }
    }

    public void reader() {
        lightSwitch.lock();
        read();
        lightSwitch.unlock();
    }

    private void write(String str){
        sb.append(str).append("\n");
    }

    private void read() {
        System.out.println(sb.toString());
    }

    static class MainClass {

        public static void main(String[] args) {
            Random random = new Random();
            ReaderWriter readerWriter = new ReaderWriter();
            Runnable writer = () -> readerWriter.writer(Thread.currentThread().getName()),
                    reader = () -> readerWriter.reader();
            ExecutorService executorService = Executors.newFixedThreadPool(50);

            for (int i = 0; i < 50; i++) {
                int num = random.nextInt();
                if (num % 5 == 0) {
                    executorService.submit(writer);
                } else {
                    executorService.submit(reader);
                }
            }

            executorService.shutdown();
        }
    }

    class LightSwitch {
        Semaphore mutex = new Semaphore(1);
        int count = 0;

        public void lock() {
            try {
                mutex.acquire();
                count++;
                if (count == 1) writerLock.acquire();
                mutex.release();
            } catch (InterruptedException e) {

            }
        }

        public void unlock() {
            try {
                mutex.acquire();
                count--;
                if(count==0) writerLock.release();
                mutex.release();
            } catch (InterruptedException e) {

            }
        }
    }
}
