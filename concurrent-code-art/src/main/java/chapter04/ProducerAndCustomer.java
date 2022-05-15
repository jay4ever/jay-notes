package chapter04;

import java.util.PriorityQueue;

public class ProducerAndCustomer {
    private int queueSize = 10;
    private PriorityQueue<Integer> queue = new PriorityQueue<>(queueSize);

    public static void main(String[] args) {
        ProducerAndCustomer test = new ProducerAndCustomer();
        Thread producer = test.new Producer();
        Thread consumer = test.new Consumer();
        producer.start();
        consumer.start();
    }

    //生产者线程
    class Producer extends Thread {

        @Override
        public void run() {
            while (true) {
                synchronized (queue) {
                    if (queue.size() < queueSize) {
                        try {
                            Thread.sleep(1000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        queue.add(queue.size() + 1);
                        System.out.println("生产者向队列中加入产品P，队列剩余空间：" + (queueSize - queue.size()));
                        queue.notify();//1)随机生产和消费
                    } else {
                        try {
                            queue.wait();//1)随机生产和消费
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }

        }
    }

    class Consumer extends Thread {
        @Override
        public void run() {
            while (true) {
                synchronized (queue) {
                    if (queue.isEmpty()) {
                        try {
                            queue.wait();//1)随机生产和消费
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    } else {
                        try {
                            Thread.sleep(1000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        queue.poll();
                        System.out.println("消费者消费了产品P，剩余空间：" + (queueSize - queue.size()));
                        queue.notify();//1)随机生产和消费
                    }
                }
            }
        }
    }
}