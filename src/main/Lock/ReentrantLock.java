import java.util.concurrent.locks.Lock;

public class ReentrantLock {
    public static int count=0;
    private static Lock lock = new java.util.concurrent.locks.ReentrantLock();
    public static void main(String[] args){
        Thread[] threads =new Thread[100];
        for (int i=0;i<100;i++){
            threads[i]=new Thread(new countTask());
        }
        for (int i=0;i<100;i++)
            threads[i].start();
    }
    public static void add(){
        lock.lock();
        try{
            System.out.println(count++);
        }
        finally {
            lock.unlock();
        }
    }
    static class countTask implements Runnable{
        public void run(){
            while (true){
                add();
            }
        }
    }
}
