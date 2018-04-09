package exception_learning;

class EnemyHeroIsDeadException extends Exception{

    public EnemyHeroIsDeadException(){

    }
    public EnemyHeroIsDeadException(String msg){
        super(msg);
    }
}