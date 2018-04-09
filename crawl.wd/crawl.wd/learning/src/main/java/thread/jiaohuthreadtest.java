package thread;



public class jiaohuthreadtest {

    public static void main(String[] args) {

        final jiaohuhero gareen = new jiaohuhero();
        gareen.name = "盖伦";
        gareen.hp = 616;
for(int i=0;i<6;i++){
    Thread t1 = new Thread(){
        public void run(){
            while(true){
                gareen.hurt();

                try {
                    //减慢掉血的速度
                    Thread.sleep(10);
                } catch (InterruptedException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }

        }
    };
    t1.start();
}

        for (int i = 0; i <3; i++) {
            Thread t2 = new Thread(){
                public void run(){
                    while(true){
                        gareen.recover();

                        try {
                            //加快回血的速度
                            Thread.sleep(20);
                        } catch (InterruptedException e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        }
                    }

                }
            };
            t2.start();
        }


    }

}