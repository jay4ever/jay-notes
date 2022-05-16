package session1;

import java.util.PriorityQueue;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class ProducerAndCustomerByReentrantLock {
    private int queueSize = 1;
    private PriorityQueue<Integer> queue = new PriorityQueue<>(queueSize);
    private ReentrantLock lock = new ReentrantLock();
    private Condition shouldConsume = lock.newCondition();
    private Condition shouldProduce = lock.newCondition();


    public static void main(String[] args) throws InterruptedException {
        ProducerAndCustomerByReentrantLock test = new ProducerAndCustomerByReentrantLock();
        Thread consumer = test.new Consumer();
        Thread consumer2 = test.new Consumer();
        consumer.start();
        consumer2.start();
        Thread producer = test.new Producer();
        Thread producer2 = test.new Producer();
        producer.start();
        producer2.start();
    }

    //生产者线程
    class Producer extends Thread {

        @Override
        public void run() {
            while (true) {
                lock.lock();
                if (queue.size() < queueSize) {
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    queue.add(queue.size() + 1);
                    System.out.println("生产者向队列中加入产品P，队列剩余空间：" + (queueSize - queue.size()));
                    shouldConsume.signal();
                    try {
                        shouldProduce.await();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                } else {
                    try {
                        shouldProduce.await();//1)随机生产和消费
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                lock.unlock();
            }

        }
    }

    class Consumer extends Thread {
        @Override
        public void run() {
            while (true) {
                lock.lock();
                if (queue.isEmpty()) {
                    try {
                        shouldConsume.await();//1)随机生产和消费
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
                    shouldProduce.signal();//1)随机生产和消费
                    try {
                        shouldConsume.await();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                lock.unlock();
            }
        }
    }
}
