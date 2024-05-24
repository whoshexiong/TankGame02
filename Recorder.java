package TankGame03.TankGame02;

import java.io.*;
import java.util.Vector;

/**
 * @author YI
 * @version 1.0
 */
public class Recorder {

    //记录击中坦克数
    private static int allTankHit = 0;
    private static BufferedWriter bw =null;

    private static BufferedReader br = null;

    public static Vector<node> nodes = new Vector<>();

    public static Vector<EnemyTank> enemyTanks = new Vector<>();

    public static void setEnemyTanks(Vector<EnemyTank> enemyTanks1) {
        enemyTanks = enemyTanks1;
    }

    static String recordFile = "C:\\Users\\dad\\Desktop\\新建 文本文档.txt";//"D:\My_java_learn\Javahsp02\src\recordFile"

    public static void addAllTankHit(){
        allTankHit++;
    }

    public static int getAllTankHit() {
        return allTankHit;
    }

    public static void saveScore(){
        try {
            bw = new BufferedWriter(new FileWriter(recordFile));
            bw.write(getAllTankHit()+"\r\n");

            for (int i = 0; i < enemyTanks.size(); i++) {

                EnemyTank e = enemyTanks.get(i);
                if(e != null && e.isLive){
                    bw.write(e.getX() + " " + e.getY() + " " + e.getDirect() + "\r\n");
                }
            }


        } catch (IOException e) {
            throw new RuntimeException(e);
        } finally {
            try {
                if(bw != null){
                    bw.close();
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public static void getScore(){
        try {
            br = new BufferedReader(new FileReader(recordFile));
            //读取文件第一行（击毁数）
            allTankHit = Integer.parseInt(br.readLine());

            String s;
            //读取敌方坦克信息（坐标，方向）
            while ((s=br.readLine()) != null){

                String[] s2 = s.split(" ");

                EnemyTank e = new EnemyTank(Integer.parseInt(s2[0]),Integer.parseInt(s2[1]));
                e.setDirect(Integer.parseInt(s2[2]));

                //加入数组中
                enemyTanks.add(e);
            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        } finally {
            try {
                if(br != null){
                    br.close();
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }


        }
    }

    public static Vector<EnemyTank> getEnemyTanks() {
        return enemyTanks;
    }
}
