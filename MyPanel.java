package TankGame03.TankGame02;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Scanner;
import java.util.Vector;

/**
 * @author YI
 * @version 1.0
 */
public class MyPanel extends JPanel implements KeyListener, Runnable {
    //创建己方坦克对象，但并未初始化
    Hero hero = null;
    //创建敌方坦克集合
    public static Vector<EnemyTank> enemyTanks = new Vector<>();
    //创建爆炸效果的集合
    Vector<Bomb> bombs = new Vector<>();

    //记录击毁的敌方坦克数
    Integer enemyTankHitted;
    Integer heroHitted;

    Image image01 = null;
    Image image02 = null;
    Image image03 = null;

    int enemyTankSize = 7;

    int selectModel = 0;

    MyPanel(int selectModel) {

        //初始化己方坦克
        hero = new Hero(100, 100);
        hero.setSpeed(3);


        switch (selectModel) {
            case 0:
                //初始化敌方坦克
                for (int i = 0; i < enemyTankSize; i++) {

                    //初始化敌方坦克的位置和朝向
                    EnemyTank enemyTank = new EnemyTank((100 * (i + 1)), 320);
                    enemyTank.setDirect(2);
                    enemyTanks.add(enemyTank);
                    //将enemyTanks集合加入每个敌方坦克
                    enemyTank.setEnemyTanks(enemyTanks);

                    //新建子弹向敌方坦克添加子弹
                    //            Shot shot = new Shot(enemyTank.getX() + 20, enemyTank.getY() + 60, enemyTank.getDirect());
                    //            enemyTank.EnemyShots.add(shot);
                    //
                    //            //启动敌方子弹线程
                    //            new Thread(shot).start();


                    //启动敌方坦克线程
                    new Thread(enemyTank).start();

                }
                break;
            case 1:
                //得到敌方信息
                Recorder.getScore();
                enemyTanks = Recorder.getEnemyTanks();
                for (int i = 0; i < enemyTanks.size(); i++) {

                    EnemyTank e = enemyTanks.get(i);


                    //初始化敌方坦克的位置和朝向
                    EnemyTank enemyTank = new EnemyTank(e.getX(), e.getY());
                    enemyTank.setDirect(e.getDirect());

                    //将enemyTanks集合加入每个敌方坦克
                    enemyTank.setEnemyTanks(enemyTanks);

                    //启动敌方坦克线程
                    new Thread(e).start();

                }
                break;
            default:
                System.out.println("你的输入有误");
                break;
        }

        //初始化记录器
        Recorder.setEnemyTanks(enemyTanks);
        enemyTankHitted = 0;
        heroHitted = 0;

        //初始化爆炸图像
        image01 = Toolkit.getDefaultToolkit().getImage("D:\\dad\\Pictures\\坦克爆炸1.jpg");
        image02 = Toolkit.getDefaultToolkit().getImage("D:\\dad\\Pictures\\坦克爆炸2.jpg");
        image03 = Toolkit.getDefaultToolkit().getImage("D:\\dad\\Pictures\\坦克爆炸3.jpg");

    }

    @Override//由于paint只相当于一个帧里面的操作，这里面画的东西都是一次性的
    public void paint(Graphics g) {
        super.paint(g);
        g.fillRect(0, 0, 1300, 750);

        //画出计分板
        showInfo(g);

        //画出己方坦克
        if (hero != null && hero.isLive) {
            System.out.println("己方移动后的位置已画出");
            drawTank(hero.getX(), hero.getY(), g, hero.getDirect(), 1);
        }
        //System.out.println("坦克已画出");

        //画出敌方坦克
        for (int i = 0; i < enemyTanks.size(); i++) {
            if (enemyTanks.get(i).isLive) {
                //画出敌方坦克
                drawTank(enemyTanks.get(i).getX(), enemyTanks.get(i).getY(), g, enemyTanks.get(i).getDirect(), 0);
                //画出敌方坦克子弹
                for (int j = 0; j < enemyTanks.get(i).EnemyShots.size(); j++) {
                    Shot enemyShot = enemyTanks.get(i).EnemyShots.get(j);
                    if (enemyShot.isLive) {
                        g.draw3DRect(enemyShot.x, enemyShot.y, 2, 2, false);
                    } else {
                        enemyTanks.get(i).EnemyShots.remove(j);
                    }
                }
            }
        }


        //遍历己方坦克的子弹集合，把里面所有的子弹都画出来
        for (int i = 0; i < hero.shots.size(); i++) {
            Shot shot = hero.shots.get(i);

            //如果有子弹，并且子弹存活
            if (shot != null && shot.isLive) {
                g.setColor(Color.green);
                g.draw3DRect(shot.x, shot.y, 2, 2, false);
            } else {
                hero.shots.remove(shot);
            }
        }

        //画出爆炸效果
        for (int i = 0; i < bombs.size(); i++) {
            Bomb bomb = bombs.get(i);

            if (bomb.life > 6) {
                g.drawImage(image01, bomb.x, bomb.y, 60, 60, this);
            } else if (bomb.life > 3) {
                g.drawImage(image02, bomb.x, bomb.y, 60, 60, this);
            } else {
                g.drawImage(image03, bomb.x, bomb.y, 60, 60, this);
            }

            bomb.lifeDown();

            //如果bomb的生命等于0，就将其从bombs中剔除
            if (bomb.life == 0) {
                bombs.remove(bomb);
            }
        }

        //画出击中坦克数
//        String showScore01 = "被击中敌方数（得分）：" + enemyTankHitted.toString();
//        g.drawString(showScore01,10,20);
//
//        //画出被击中数
//        String showScore02 = "被击中次数：" + heroHitted.toString();
//        g.drawString(showScore02,10,50);
    }

    public void drawTank(int x, int y, Graphics g, int direct, int type) {
        switch (type) {
            case 0:
                g.setColor(Color.cyan);
                break;
            case 1:
                g.setColor(Color.yellow);
                break;
        }

        //设置坦克的方向，用于移动（0：上 1：右 2：下 3：左
        switch (direct) {
            case 0://向上
                g.draw3DRect(x, y, 10, 60, false);
                g.draw3DRect(x + 10, y + 10, 20, 40, false);
                g.draw3DRect(x + 30, y, 10, 60, false);
                g.drawOval(x + 10, y + 20, 20, 20);
                g.drawLine(x + 20, y, x + 20, y + 30);
                break;
            case 1://向右
                g.draw3DRect(x, y, 60, 10, false);//上轮
                g.draw3DRect(x + 10, y + 10, 40, 20, false);
                g.draw3DRect(x, y + 30, 60, 10, false);//下轮
                g.drawOval(x + 20, y + 10, 20, 20);
                g.drawLine(x + 60, y + 20, x + 30, y + 20);
                break;
            case 2:
                g.draw3DRect(x, y, 10, 60, false);
                g.draw3DRect(x + 10, y + 10, 20, 40, false);
                g.draw3DRect(x + 30, y, 10, 60, false);
                g.drawOval(x + 10, y + 20, 20, 20);
                g.drawLine(x + 20, y + 60, x + 20, y + 30);
                break;
            case 3:
                g.draw3DRect(x, y, 60, 10, false);//上轮
                g.draw3DRect(x + 10, y + 10, 40, 20, false);
                g.draw3DRect(x, y + 30, 60, 10, false);//下轮
                g.drawOval(x + 20, y + 10, 20, 20);
                g.drawLine(x + 30, y + 20, x, y + 20);
                break;
            default:
                System.out.println("未定义");

        }
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        //0：上 1：右 2：下 3：左
        if (e.getKeyCode() == KeyEvent.VK_W) {
            hero.setDirect(0);

            System.out.println("上键已按下");

            //确定己方坦克的上界
            if (hero.getY() > 0) {
                hero.moveUp();
            }
        } else if (e.getKeyCode() == KeyEvent.VK_D) {
            hero.setDirect(1);

            if (hero.getX() + 60 < 1000) {
                hero.moveRight();
            }
        } else if (e.getKeyCode() == KeyEvent.VK_S) {
            hero.setDirect(2);

            if (hero.getY() + 60 < 750) {
                hero.moveDown();
            }
        } else if (e.getKeyCode() == KeyEvent.VK_A) {
            hero.setDirect(3);

            if (hero.getX() > 0) {
                hero.moveLeft();
            }
        } else if (e.getKeyCode() == KeyEvent.VK_J) {
            //建弹，装弹，发射
            hero.shotEnemyTank();
        }

        this.repaint();
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }

    @Override
    public void run() {
        while (true) {
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }

            //我方子弹击中敌方坦克
            hitEnemyTank();

            //敌方子弹击中我方坦克
            hitHero();

            if (hero.isLive == false) {
                System.out.println("己方被消灭");
                heroHitted++;
                hero = new Hero(100, 100);
                hero.setSpeed(3);
            }

            this.repaint();
        }
    }

    public void hitTank(Shot s, Tank tank) {
        switch (tank.getDirect()) {
            case 0, 2://上下
                if (s.x > tank.getX() && s.x < tank.getX() + 40
                        && s.y > tank.getY() && s.y < tank.getY() + 60) {
                    //将相撞的子弹和坦克生命置为否
                    s.isLive = false;
                    tank.isLive = false;

                    //将爆炸效果实例化，并加到爆炸集合中
                    Bomb bomb01 = new Bomb(tank.getX(), tank.getY());
                    bombs.add(bomb01);

                    //从敌方坦克集合中移除这个爆炸的的敌方坦克
                    if (tank instanceof EnemyTank) {
                        enemyTanks.remove(tank);
                        enemyTankSize--;
                        enemyTankHitted++;
                        Recorder.addAllTankHit();
                    }
                }

                break;
            case 1, 3:
                if (s.x > tank.getX() && s.x < tank.getX() + 60
                        && s.y > tank.getY() && s.y < tank.getY() + 40) {
                    s.isLive = false;
                    tank.isLive = false;

                    Bomb bomb02 = new Bomb(tank.getX(), tank.getY());
                    bombs.add(bomb02);

                    if (tank instanceof EnemyTank) {
                        enemyTanks.remove(tank);
                        enemyTankSize--;
                        enemyTankHitted++;
                        Recorder.addAllTankHit();
                    }
                }

                break;
        }
    }

    public void hitHero() {
        //遍历敌方坦克的子弹
        for (int i = 0; i < enemyTanks.size(); i++) {
            EnemyTank enemyTank = enemyTanks.get(i);
            for (int j = 0; j < enemyTank.EnemyShots.size(); j++) {
                Shot shot = enemyTank.EnemyShots.get(j);

                if (shot != null && hero != null && shot.isLive && hero.isLive) {
                    hitTank(shot, hero);
                }
            }
        }

    }

    public void hitEnemyTank() {

        for (int j = 0; j < hero.shots.size(); j++) {
            Shot shot = hero.shots.get(j);
            //判断取出的这个子弹是否存在
            if (shot != null && shot.isLive) {
                for (int i = 0; i < enemyTankSize; i++) {
                    //从敌方坦克集合中依次取出坦克
                    EnemyTank enemyTank = enemyTanks.get(i);

                    hitTank(shot, enemyTank);
                }

            }
        }
    }


    //判断两坦克是否相撞
//    public void isStrike(Tank t,Tank anotherT){
//        boolean isStriked = false;
//        switch (t.getDirect()){
//            //己方上
//            case 0:
//                switch (anotherT.getDirect()){
//                    //另一方上下
//                    case 0,2:
//                        if((t.getX() > anotherT.getX() && t.getX() < anotherT.getX()+40
//                        && t.getY() > anotherT.getY() && t.getY() < anotherT.getY()+60)||
//                                (t.getX()+40 > anotherT.getX() && t.getX()+40 < anotherT.getX()+40
//                                        && t.getY() > anotherT.getY() && t.getY() < anotherT.getY()+60)){
//                            isStriked = true;
//                        }
//
//                }
//        }
//
//    }

    public void showInfo(Graphics g) {
        g.setColor(Color.RED);
        g.setFont(new Font("宋体", Font.BOLD, 25));
        g.drawString("您累计击中的坦克：", 1020, 30);
        drawTank(1020, 60, g, 0, 0);

        g.setColor(Color.GREEN);
        g.drawString(Recorder.getAllTankHit() + "", 1080, 100);


    }

}
