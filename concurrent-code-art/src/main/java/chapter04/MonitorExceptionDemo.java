package chapter04;

public class MonitorExceptionDemo {
    static A obj = new A();

    public static void main(String[] args) {
        method1();
    }

    private static void method1() {
        try {
            obj.wait();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            e.printStackTrace();
        }
    }

    static class A {

    }
}
