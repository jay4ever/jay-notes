package chapter04;

import java.util.HashMap;
import java.util.HashSet;

public class Ordering {
    //运行到后面的结果：[a=0,b=0, a=1,b=0, a=0,b=1]
    //volatile保证了x、y赋值时的先后顺序
    static int x = 0, y = 0;

    //运行到后面的结果：[a=0,b=0, a=1,b=0, a=0,b=1, a=1,b=1]
    //因为指令重排序，代码的执行顺序出现了 2、4操作 在 1、3的前面，例如2413,4213
    //static int x = 0, y = 0;

    public static void main(String[] args) throws InterruptedException {
        new Object().wait();
        HashSet<String> hashSet = new HashSet<>();
        HashMap<String,Integer> hashMap = new HashMap<>();

        for (int i = 0; i<1000000; i++) {
            x = 0; y = 0;
            hashMap.clear();
            Thread thread1 = new Thread(new Runnable() {
                @Override
                public void run() {
                    int a = x; //1
                    y = 1; //2
                    hashMap.put("a",a);
                }
            });

            Thread thread2 = new Thread(new Runnable() {
                @Override
                public void run() {
                    int b = y; //3
                    x = 1; //4
                    hashMap.put("b",b);
                }
            });

            thread1.start();
            thread2.start();
            thread1.join();
            thread2.join();
            hashSet.add("a="+hashMap.get("a")+",b="+hashMap.get("b"));
            System.out.println(hashSet);

        }

    }

}