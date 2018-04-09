package thread;

/**
 * 这段话理解线程很重要！！！！！！
 * 1.start：用start方法来启动线程，真正实现了多线程运行，这时无需等待run方法体代码执行完毕而直接继
 * 续执行下面的代码。通过调用Thread类的start()方法来启动一个线程，这时此线程处于就绪（可运行）状态，并没有运
 * 行，一旦得到cpu时间片，就开始执行run()方法，这里方法
 run()称为线程体，它包含了要执行的这个线程的内容，Run方法运行结束，此线程随即终止。
 * 2.为什么要用join()方法
 在很多情况下，主线程生成并起动了子线程，如果子线程里要进行大量的耗时的运算，
 主线程往往将于子线程之前结束，但是如果主线程处理完其他的事务后，
 需要用到子线程的处理结果，也就是主线程需要等待子线程执行完成之后再结束，这个时候就要用到join()方法了。

 */

import java.awt.GradientPaint;

import thread.Hero;


public class TestThread {

    public static void main(String[] args) {

        final Hero gareen = new Hero();
        gareen.name = "盖伦";
        gareen.hp = 10000;

        int n = 10000;

        Thread[] addThreads = new Thread[n];
        Thread[] reduceThreads = new Thread[n];

        for (int i = 0; i < n; i++) {
            Thread t = new Thread(){
                public void run(){

                    //recover自带synchronized
                    gareen.recover();

                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                }
            };
            t.start();
            System.out.println("up:"+gareen.getHp());
            addThreads[i] = t;

        }

        for (int i = 0; i < n; i++) {
            Thread t = new Thread(){
                public void run(){
                    //hurt自带synchronized
                    gareen.hurt();

                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                }
            };
            t.start();
            System.out.println("reduce:"+gareen.getHp());
            reduceThreads[i] = t;
        }

        //确保 每个t执行完成
        for (Thread t : addThreads) {
            try {
                t.join();
                System.out.println("join up:"+gareen.getHp());
            } catch (InterruptedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        for (Thread t : reduceThreads) {
            try {
                t.join();
                System.out.println("join reduce:"+gareen.getHp());
            } catch (InterruptedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }

        System.out.printf("%d个增加线程和%d个减少线程结束后%n盖伦的血量是 %.0f%n", n,n,gareen.hp);

    }

}