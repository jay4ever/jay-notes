package chapter04;

public class VisibilityDemo {

    //结果是 start -> assign -> complete -> 死循环
//    static boolean initFlag = false;

    //结果是 start -> assign -> complete -> end
    //加了volatile关键字使得initFlag变量的更改对其它线程可见
    static volatile boolean initFlag = false;

    public static void main(String[] args) throws InterruptedException {

        new Thread(() -> {
            System.out.println("=========start");
            while(!initFlag) {

            }
            System.out.println("=========end");
        }).start();

        Thread.sleep(2000);

        new Thread(() -> {
            System.out.println("=========assign");
            initFlag = true;
            System.out.println("=========complete");
        }).start();

    }

}
