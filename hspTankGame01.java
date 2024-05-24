package TankGame03.TankGame02;

import javax.swing.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Scanner;

/**
 * @author YI
 * @version 1.0
 */
public class hspTankGame01 extends JFrame {
    MyPanel mp = null;

    Scanner scanner = new Scanner(System.in);
    public static void main(String[] args) {
        System.out.println("main方法已调用");
        new hspTankGame01();
    }

    //构造器
    public hspTankGame01(){
        super();

        System.out.println("请选择游戏模式：");
        System.out.println("0（新游戏）  1（继续游戏）");

        int selectModel = scanner.nextInt();

        mp = new MyPanel(selectModel);

        new Thread(mp).start();//启动mp为对象的线程
        this.add(mp);//向框架中加入面板
        this.setSize(1300,750);//设置框架大小
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);//设置叉号的功能
        this.setVisible(true);
        this.addKeyListener(mp);
        System.out.println("JFrame构造已调用");

        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                super.windowClosing(e);
                Recorder.saveScore();

            }
        });
    }

}
