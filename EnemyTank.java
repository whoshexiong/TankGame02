package TankGame03.TankGame02;

import java.util.Vector;

/**
 * @author YI
 * @version 1.0
 */
public class EnemyTank extends Tank implements Runnable {

    Vector<Shot> EnemyShots = new Vector<>();

    public int minShotsSize = 3;

    public EnemyTank(int x, int y) {
        super(x, y);
    }

    Vector<EnemyTank> enemyTanks = new Vector<>();


    @Override
    public void run() {

        while (true) {
            //0：上 1：右 2：下 3：左
            if (EnemyShots.size() < minShotsSize) {
                Shot shot = null;
                switch (getDirect()) {
                    case 0:
                        shot = new Shot(getX() + 20, getY(), 0);
                        break;
                    case 1:
                        shot = new Shot(getX() + 60, getY() + 20, 1);
                        break;
                    case 2:
                        shot = new Shot(getX() + 20, getY() + 60, 2);
                        break;
                    case 3:
                        shot = new Shot(getX(), getY() + 20, 3);
                        break;
                }

                EnemyShots.add(shot);

                new Thread(shot).start();
            }

            switch (getDirect()) {
                case 0:
                    //在一个方向进行30次移动，每次移动间要休眠50ms
                    for (int i = 0; i < 50; i++) {
                        //让坦克不能走出上界
                        if (getY() > 0 && !isTouchEnemyTank()) {
                            moveUp();
                        }

                        try {
                            Thread.sleep(50);
                        } catch (InterruptedException e) {
                            throw new RuntimeException(e);
                        }
                    }
                    break;
                case 1:
                    for (int i = 0; i < 50; i++) {
                        if (getX() + 60 < 1000 && !isTouchEnemyTank()) {
                            moveRight();
                        }

                        try {
                            Thread.sleep(50);
                        } catch (InterruptedException e) {
                            throw new RuntimeException(e);
                        }
                    }
                    break;
                case 2:
                    for (int i = 0; i < 50; i++) {
                        if (getY() + 60 < 750 && !isTouchEnemyTank()) {
                            moveDown();
                        }

                        try {
                            Thread.sleep(50);
                        } catch (InterruptedException e) {
                            throw new RuntimeException(e);
                        }
                    }
                    break;
                case 3:
                    for (int i = 0; i < 50; i++) {
                        if (getX() > 0 && !isTouchEnemyTank()) {
                            moveLeft();
                        }

                        try {
                            Thread.sleep(50);
                        } catch (InterruptedException e) {
                            throw new RuntimeException(e);
                        }
                    }
                    break;
            }

            setDirect((int) (Math.random() * 4));

            if (!isLive) {
                break;
            }


        }
    }

    public void setEnemyTanks(Vector<EnemyTank> enemyTanks) {
        this.enemyTanks = enemyTanks;
    }

    public boolean isTouchEnemyTank() {

        switch (this.getDirect()) {
            //当坦克向上
            case 0:
                //遍历坦克集合
                for (int i = 0; i < enemyTanks.size(); i++) {
                    EnemyTank enemyTank = enemyTanks.get(i);

                    if (enemyTank != this) {
                        switch (enemyTank.getDirect()) {
                            //当other坦克上下
                            case 0, 2:
                                //左方标准点
                                if ((this.getX() >= enemyTank.getX()
                                        && this.getX() <= enemyTank.getX() + 40
                                        && this.getY() >= enemyTank.getY()
                                        && this.getY() <= enemyTank.getY() + 60)) {
                                    return true;
                                }
                                //右方标准点
                                if ((this.getX() + 40 >= enemyTank.getX()
                                        && this.getX() + 40 <= enemyTank.getX() + 40
                                        && this.getY() >= enemyTank.getY()
                                        && this.getY() <= enemyTank.getY() + 60)) {
                                    return true;
                                }
                                break;
                            //other坦克左右
                            case 1, 3:
                                if ((this.getX() >= enemyTank.getX()
                                        && this.getX() <= enemyTank.getX() + 60
                                        && this.getY() >= enemyTank.getY()
                                        && this.getY() <= enemyTank.getY() + 40)) {
                                    return true;
                                }
                                if ((this.getX() + 40 >= enemyTank.getX()
                                        && this.getX() + 40 <= enemyTank.getX() + 60
                                        && this.getY() >= enemyTank.getY()
                                        && this.getY() <= enemyTank.getY() + 40)) {
                                    return true;
                                }
                                break;
                        }
                    }
                }
                break;
            //向右
            case 1:
                //遍历坦克集合
                for (int i = 0; i < enemyTanks.size(); i++) {
                    EnemyTank enemyTank = enemyTanks.get(i);

                    if (enemyTank != this) {
                        switch (enemyTank.getDirect()) {
                            //当other坦克上下
                            case 0, 2:
                                //上方标准点
                                if ((this.getX() + 60 >= enemyTank.getX()
                                        && this.getX() + 60 <= enemyTank.getX() + 40
                                        && this.getY() >= enemyTank.getY()
                                        && this.getY() <= enemyTank.getY() + 60)) {
                                    return true;
                                }
                                //下方标准点
                                if ((this.getX() + 60 >= enemyTank.getX()
                                        && this.getX() + 60 <= enemyTank.getX() + 40
                                        && this.getY() + 40 >= enemyTank.getY()
                                        && this.getY() + 40 <= enemyTank.getY() + 60)) {
                                    return true;
                                }
                                break;
                            //other坦克左右
                            case 1, 3:
                                //右上基准点
                                if ((this.getX() + 60 >= enemyTank.getX()
                                        && this.getX() + 60 <= enemyTank.getX() + 60
                                        && this.getY() >= enemyTank.getY()
                                        && this.getY() <= enemyTank.getY() + 40)) {
                                    return true;
                                }
                                if ((this.getX() + 60 >= enemyTank.getX()
                                        && this.getX() + 60 <= enemyTank.getX() + 60
                                        && this.getY() + 40 >= enemyTank.getY()
                                        && this.getY() + 40 <= enemyTank.getY() + 40)) {
                                    return true;
                                }
                                break;

                        }
                    }
                }
                break;
            //向下
            case 2:
                //遍历坦克集合
                for (int i = 0; i < enemyTanks.size(); i++) {
                    EnemyTank enemyTank = enemyTanks.get(i);

                    if (enemyTank != this) {
                        switch (enemyTank.getDirect()) {
                            //当other坦克上下
                            case 0, 2:
                                //左方标准点
                                if ((this.getX() >= enemyTank.getX()
                                        && this.getX() <= enemyTank.getX() + 40
                                        && this.getY() + 60 >= enemyTank.getY()
                                        && this.getY() + 60 <= enemyTank.getY() + 60)) {
                                    return true;
                                }
                                //右方标准点
                                if ((this.getX() + 40 >= enemyTank.getX()
                                        && this.getX() + 40 <= enemyTank.getX() + 40
                                        && this.getY() + 60 >= enemyTank.getY()
                                        && this.getY() + 60 <= enemyTank.getY() + 60)) {
                                    return true;
                                }
                                break;
                            //other坦克左右
                            case 1, 3:
                                if ((this.getX() >= enemyTank.getX()
                                        && this.getX() <= enemyTank.getX() + 60
                                        && this.getY() + 60 >= enemyTank.getY()
                                        && this.getY() + 60 <= enemyTank.getY() + 40)) {
                                    return true;
                                }
                                if ((this.getX() + 40 >= enemyTank.getX()
                                        && this.getX() + 40 <= enemyTank.getX() + 60
                                        && this.getY() + 60 >= enemyTank.getY()
                                        && this.getY() + 60 <= enemyTank.getY() + 40)) {
                                    return true;
                                }
                                break;
                        }
                    }
                }
                break;
            //向左
            case 3:
                //遍历坦克集合
                for (int i = 0; i < enemyTanks.size(); i++) {
                    EnemyTank enemyTank = enemyTanks.get(i);

                    if (enemyTank != this) {
                        switch (enemyTank.getDirect()) {
                            //当other坦克上下
                            case 0, 2:
                                //上方标准点
                                if ((this.getX() >= enemyTank.getX()
                                        && this.getX() <= enemyTank.getX() + 40
                                        && this.getY() >= enemyTank.getY()
                                        && this.getY() <= enemyTank.getY() + 60)) {
                                    return true;
                                }
                                //下方标准点
                                if ((this.getX() >= enemyTank.getX()
                                        && this.getX() <= enemyTank.getX() + 40
                                        && this.getY() + 40 >= enemyTank.getY()
                                        && this.getY() + 40 <= enemyTank.getY() + 60)) {
                                    return true;
                                }
                                break;
                            //other坦克左右
                            case 1, 3:
                                if ((this.getX() >= enemyTank.getX()
                                        && this.getX() <= enemyTank.getX() + 60
                                        && this.getY() >= enemyTank.getY()
                                        && this.getY() <= enemyTank.getY() + 40)) {
                                    return true;
                                }
                                if ((this.getX() >= enemyTank.getX()
                                        && this.getX() <= enemyTank.getX() + 60
                                        && this.getY() + 40 >= enemyTank.getY()
                                        && this.getY() + 40 <= enemyTank.getY() + 40)) {
                                    return true;
                                }
                                break;

                        }
                    }
                }
                break;
        }
        return false;
    }

}
