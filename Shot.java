package TankGame03.TankGame02;

/**
 * @author YI
 * @version 1.0
 */
public class Shot implements Runnable{
    int x;
    int y;

    int direct;

    int speed = 5;
    boolean isLive = true;

    public Shot(int x, int y, int direct) {
        this.x = x;
        this.y = y;
        this.direct = direct;
    }

    @Override
    public void run() {
        while (true){
            //子弹飞行途中需要休眠
            try {
                Thread.sleep(50);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            //根据子弹方向设置路径
            switch (direct){
                case 0:
                    y-=speed;
                    break;
                case 1:
                    x += speed;
                    break;
                case 2:
                    y += speed;
                    break;
                case 3:
                    x -= speed;
                    break;
            }

            System.out.println("子弹所在 x= " + x + " y= " + y);
            if(!(x>=0 && x<=1000 && y>=0 && y<= 750 && isLive)){
                isLive = false;
                break;
            }

        }
    }
}
