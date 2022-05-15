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
                //同步代码块，获取队列锁
                synchronized (queue) {
                    //当队列不满时生产者可以继续生产，生产之后唤醒消费者
                    //唤醒消费者，在生产者释放锁之后，消费者不一定就会获取锁，也许是生产者获取到锁继续执行
                    //但是如果不唤醒生产者，当队列满时，如果消费者处于阻塞状态，那么生产者和消费者都处于阻塞状态，程序就无法继续执行
                    if (queue.size() < queueSize) {
                        queue.add(queue.size() + 1);
                        System.out.println("生产者向队列中加入产品P，队列剩余空间：" + (queueSize - queue.size()));
                        try {
                            //模拟生产者生产过程，sleep不会释放锁
                            Thread.sleep(1000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        //唤醒消费者
                        queue.notify();//1)随机生产和消费
                    } else {
                        try {
                            System.out.println("队列已满等待消费者消费");
                            //队列已满，进入阻塞状态，等待消费者消费
                            queue.wait();//1)随机生产和消费
                            //System.out.println("生产者获取到队列锁准备生产");
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                            queue.notify();
                        }
                    }
                }
            }

        }
    }

    //消费者线程
    class Consumer extends Thread {
        @Override
        public void run() {
            while (true) {
                //同步代码块，获取队列锁
                synchronized (queue) {
                    //如果队列是空的，消费者进入阻塞状态,等待生产者生产并唤醒
                    if (queue.isEmpty()) {
                        System.out.println("没有产品可以消费，进入阻塞状态等待生产者生产。");
                        try {
                            //进入阻塞状态释放队列锁，因为只有两个线程，所以生产者一定会获取到队列锁执行
                            queue.wait();//1)随机生产和消费
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                            //如果发送异常，主动唤醒生产者线程执行
                            queue.notify();
                        }
                        //System.out.println("消费者获取到队列锁准备消费");
                    } else {
                        //如果队列不空，就消费产品，并唤醒生产者
                        //注意唤醒生产者，在消费者执行完毕释放锁之后，不一定生产者就会获得锁，也许消费者会继续获取锁执行
                        //但是如果不唤醒生产者，那么如果生产者处于阻塞状态，当队列为空，消费者也进入阻塞状态那么就没有线程可以获取锁继续执行了
                        queue.notify();//1)随机生产和消费
                        try {
                            //模拟消费者消费过程，sleep不会释放锁
                            Thread.sleep(1000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        queue.poll();
                        System.out.println("消费者消费了产品P，剩余空间：" + (queueSize - queue.size()));
                    }
                }
            }
        }
    }
}