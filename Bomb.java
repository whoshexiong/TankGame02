package TankGame03.TankGame02;

/**
 * @author YI
 * @version 1.0
 */
public class Bomb {
    int x;
    int y;
    int life = 9;
    boolean isLive = true;
    public Bomb(int x, int y) {
        this.x = x;
        this.y = y;
    }

    //lifeDown方法实现life减小
    public void lifeDown(){
        if(life > 0){
            life--;
        }else {
            isLive = false;
        }
    }


}
