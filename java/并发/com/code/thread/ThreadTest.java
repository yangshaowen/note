package com.code.thread;

/**
 * @Description
 * @auther 阳少文
 * @create 2020-12-29 22:21
 */
public class ThreadTest {

    public static void main(String[] args) throws Exception {
        new Thread(() -> {
            System.out.println("构造方法,Lambda启动的线程,使用了默认的线程名称: " + Thread.currentThread().getName());
        }).start();


        new Thread(() -> {
            System.out.println("构造方法,Lambda启动的线程,使用了自定义的名称: " + Thread.currentThread().getName());
        }, "thrad-自定义的名称").start();

        //毫秒
        Thread.sleep(1000);
        //毫秒,纳秒
        Thread.sleep(1000, 1000);

        //交出CPU,进入就绪状态
        Thread.yield();

        Thread join = new Thread(() -> {
            try {
                System.out.println(Thread.currentThread().getName() + "开始执行");
                Thread.sleep(2000);
                System.out.println(Thread.currentThread().getName() + "执行结束");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }, "Thread-join");
        join.start();

        //等待
        System.out.println(Thread.currentThread().getName() + "准备等待" + join.getName() + "结束");
        join.join();
        System.out.println(join.getName() + "直接结束, " + Thread.currentThread().getName() + "继续执行");

        //Runnable
        new Thread(new RunnableTest()).start();

    }

    public static class RunnableTest implements Runnable {

        @Override
        public void run() {
            System.out.println("直接通过实现Runnable接口的run方法启动的定时任务");
        }
    }

}
