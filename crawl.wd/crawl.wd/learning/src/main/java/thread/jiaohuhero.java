package thread;


public class jiaohuhero {
    public String name;
    public float hp;

    public int damage;

    public synchronized void recover() {
        //当血量大于或者等于1000的时候
        //this.wait() 让占用这个对象的线程等待，并临时释放锁
        if(hp>=1000){
            try {
                this.wait();
            } catch (InterruptedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }

        hp = hp + 1;
        System.out.printf("%s 回血1点,增加血后，%s的血量是%.0f%n", name, name, hp);
        this.notify();
    }

    public synchronized void hurt() {

        while (hp <= 1) {
            try {
                this.wait();
            } catch (InterruptedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }

        hp = hp - 1;
        System.out.printf("%s 减血1点,减少血后，%s的血量是%.0f%n", name, name, hp);
        //掉血之后，唤醒等待的线程
        this.notify();
    }

    public void attackHero(Hero h) {
        h.hp -= damage;
        System.out.format("%s 正在攻击 %s, %s的血变成了 %.0f%n", name, h.name, h.name, h.hp);
        if (h.isDead())
            System.out.println(h.name + "死了！");
    }

    public boolean isDead() {
        return 0 >= hp ? true : false;
    }

}