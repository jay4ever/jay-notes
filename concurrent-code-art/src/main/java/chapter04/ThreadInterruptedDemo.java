package chapter04;
public class ThreadInterruptedDemo {

    public static void main(String[] args) throws InterruptedException {
        Thread myThread = new MyThread();
        myThread.setName("my thread");
        myThread.start();
        Thread.sleep(10);
        System.out.println("call myThread interrupt method...");
        myThread.interrupt();
    }
}

class MyThread extends Thread {
    @Override
    public void run() {
        String threadName = Thread.currentThread().getName();
        //打印当前线程是否被中断
        System.out.println(threadName + " interrrupted is: " + isInterrupted());
        while (!isInterrupted()) {
            System.out.println(threadName + " is running");
            //打印当前线程是否被中断
            System.out.println(threadName + " circle println interrrupted is: "
                    + isInterrupted());
        }
        System.out.println(threadName + " exit circle interrrupted is: " + isInterrupted());
    }
}
