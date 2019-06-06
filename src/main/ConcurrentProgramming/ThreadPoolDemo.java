package main.ConcurrentProgramming;

import java.util.concurrent.*;
import java.util.concurrent.locks.ReentrantLock;

public class ThreadPoolDemo {
     private static int count = 0;
     private static ReentrantLock lock = new ReentrantLock();
     private static ThreadPoolDemo threadPoolDemo = new ThreadPoolDemo();
     public static void main(String[] args){
        ThreadPoolExecutor threadPoolExecutor =new ThreadPoolExecutor(
                10,//核心线程池大小： 类似于核心员工与外包员工的区别，外包员工不要了就直接撤走，但核心员工不会
                20,
                3, //线程存活的时间
                TimeUnit.MINUTES, //上面存活时间的单位
                new ArrayBlockingQueue<Runnable>(5)//阻塞队列的类型
                 );
        CountTask countTask = threadPoolDemo.new CountTask();
        for(int i=0;i<25;i++){
            //CountTask countTask = threadPoolDemo.new CountTask(); 如果将countTask放在这里，将无法实现同步，因为每个线程执行的是不同的task。
            threadPoolExecutor.execute(countTask);
        }
        threadPoolExecutor.shutdown();

     }

    class CountTask implements Runnable{
        public synchronized void run(){
                count++;
                System.out.println(Thread.currentThread().getName()+": "+count);
        }
    }
}
