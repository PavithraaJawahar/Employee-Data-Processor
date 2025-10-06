package threadpool;
import exceptions.*;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;



public class Custompool {
    private BlockingQueue<Runnable> taskqueue;
    private ThreadFactory factory;
    private Thread[] threads;
    private boolean running=true;
    
    public Custompool(ThreadFactory factory,int num_threads) 
    {
        this.taskqueue=new LinkedBlockingQueue<>();
        this.factory=(Factory) factory;
        this.threads=new Thread[num_threads];
        

        for(int i=0;i<num_threads;i++)
        {
        Runnable r = () -> {
        try {
        while (true) {
            Runnable task;

            if (running) {
                
                task = taskqueue.take();
            } else {
                
                task = taskqueue.poll(500, TimeUnit.MILLISECONDS);
                if (task == null) break; 
            }

            task.run(); 
        }
    } catch (InterruptedException e) {
        Thread.currentThread().interrupt();
    }
};


            threads[i]=this.factory.newThread(r);
            threads[i].start();
        }
    }
    public void execute(Runnable task)
    {
        if(running)
        {taskqueue.offer(task);}
        else throw new ThreadpoolException("Threadpool is going to shutdown");
    }

    public void shutdown() {
        running = false;
        for (Thread t : threads) t.interrupt();
        for (Thread t : threads) {
            try { t.join(); } catch (InterruptedException ignored) {}
        }
    }
}

    
