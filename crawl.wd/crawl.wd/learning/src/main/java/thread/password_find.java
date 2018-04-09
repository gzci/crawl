package thread;


import java.io.BufferedOutputStream;
import java.io.File;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;
/**
 * 还没解决的问题：

 * 用thread的借口类的形式 可以完成 密码的检索 但是用 这种匿名内部类的形式没有办法检索
 * 有几率能打印出list
 * 有时间解决一下用runnable 接口和  thread类都写一下这个
 * 写了 不能用 内部来来实现这个。。会有几率打印不出来 list现有的数据 。用thread可以  runnable
 *还没试。
 *已解决！！！！！
 * 问题不在于匿名内部类 而是join 和start的问题
 */
import com.sun.xml.internal.ws.api.pipe.Tube;

public class password_find {
    public static void main(String[] args) {
        String password="guo";
        List<String> passable = new ArrayList<>();
        //输出console到文件
        try {
            PrintStream out = new PrintStream( new BufferedOutputStream(new FileOutputStream("c:\\Temp\\1.txt")));
            System.setOut(out);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }


        Thread t1 =new Thread() {

            public void run() {
                for(char i='a';i<='z';i++) {
                    for(char j='a';j<='z';j++) {
                        for(char k='a';k<='z';k++) {
                            StringBuffer stemp= new StringBuffer();
                            stemp.append(i).append(j).append(k);
                            passable.add(stemp.toString());
                            if(stemp.toString().equals(password)) {
                                System.out.println("密码已找到！:"+stemp);
                                return ;

                            }
                        }
                    }
                }
            }
        };
        Thread t2=new Thread() {
            private boolean found = false;



            public void run() {
                while(true){

                    while(passable.isEmpty()){
                        try {
                            Thread.sleep(50);
                        } catch (InterruptedException e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        }
                    }

                    String password = passable.remove(0);
                    System.out.println("穷举法本次生成的密码是：" +password);



                }

            }

        };
        //守护线程
        t2.setDaemon(true);
        t2.start();
        t1.start();
        try {
            t1.join();
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        try {
            t2.join();
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }
}









