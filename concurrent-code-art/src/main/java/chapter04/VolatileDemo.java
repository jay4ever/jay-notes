package chapter04;

public class VolatileDemo {

    static int num = 0;


    public static void main(String[] args) throws InterruptedException {

//
        Thread t1 = new Thread(new Run());
        Thread t2 = new Thread(new Run());
        t1.start();
        t2.start();
        t1.join();
        t2.join();
//        System.out.println(Run.num);
    }

    public static class Run implements Runnable {

        @Override
        public void run() {
            for (int i = 0; i <10000; i++) {
                add();
            }
            System.out.println("done");
        }

        public static synchronized void add() {
            num++;
        }


    }


}
