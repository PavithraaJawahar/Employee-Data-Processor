package threadpool;

import java.util.concurrent.ThreadFactory;

public class Factory implements ThreadFactory {
    private String name;
    private int count=1;
    public Factory(String name) {
        this.name= name;
    }

    @Override
    public Thread newThread(Runnable r) {
        Thread t = new Thread(r);
        t.setName(name + " thread " + count++); 
        t.setDaemon(false); 
        t.setPriority(Thread.NORM_PRIORITY);
        return t;
    }

}
