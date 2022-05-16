import session1.SleepUtils;

public class Test {
    static A obj = new A();

    public static void main(String[] args) {
        new Thread(() -> {
            try {
                getLockAndTimeWait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();
        new Thread(new Runnable() {
            @Override
            public void run() {
                getLockAndSleep();
            }
        }).start();
    }

    private static void getLockAndSleep() {
        synchronized (obj) {
            System.out.println("sleep start");
            SleepUtils.second(10);
            System.out.println("sleep end");
        }
    }

    private static void getLockAndTimeWait() throws InterruptedException {
        synchronized (obj) {
            System.out.println("wait start");
            obj.wait(5000);
            System.out.println("wait end");
        }
    }

    static class A {

    }
}
