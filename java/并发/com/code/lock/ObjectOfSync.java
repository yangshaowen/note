package com.code.lock;

import java.util.HashMap;
import java.util.Map;

/**
 * @Description
 * @auther 阳少文
 * @create 2020-12-29 23:41
 */
public class ObjectOfSync {
    public static void main(String[] args) {
        ticketGrabbing1();
    }
    //卖火车票


    private static void ticketGrabbing1() {
        SellTokenStand sellTokenStand = new SellTokenStand();
        int numberOfPeople = 1000;
        for (int i = 0; i < numberOfPeople; i++) {
            new Thread(sellTokenStand, "黄牛-" + i + "号").start();
        }
    }

    public static class SellTokenStand implements Runnable {

        private int tokenCount = 100;

        private Map<String, Integer> pocketMap = new HashMap<>();

        @Override
        public void run() {
            while (true) {
                synchronized (this) {
                    String threadName = Thread.currentThread().getName();
                    System.out.println(threadName + "过来抢票了");
                    if (tokenCount == 0) {
                        System.out.println("售票站没有票了," + threadName + "抢到了:" + (pocketMap.get(threadName) == null ? 0 : pocketMap.get(threadName)) + "票");
                        return;
                    }
                    System.out.println("现在的票数: " + tokenCount);
                    int count = pocketMap.computeIfAbsent(threadName, (key) -> 0);
                    count++;
                    tokenCount--;
                    pocketMap.put(threadName, count);
                    System.out.println(threadName + "抢到一张票,并把票放到了口袋中");
                }
                System.out.println("抢到票休息一下哈");
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("休息好了,继续干");
            }
        }
    }
}
