//import com.sun.org.apache.bcel.internal.generic.GOTO;

import java.awt.Color;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.util.TimerTask;

import javax.swing.*;


public class Main {
    public static int size = 1000;//size of the map
    public static int[][] map = new int[size][size];//0 representes nothing, 1 representes goods
    public static int colormask = 1;
    public static List<Goods> goodsList = new ArrayList<Goods>();//all the goods
    public static List<Plane> planeList = new ArrayList<Plane>();//all the planes
    public static List<Plane> planes = new ArrayList<Plane>();//Save all the planes
    public static List<ChargeAddress> chargeAddressList = new ArrayList<ChargeAddress>();//Charging station
    public static List<GetAddress> getAddressList = new ArrayList<GetAddress>();//save all the destinations
    private MyPanel mp = null;//map
    private JFrame f = new JFrame("Drone");//windows
    public static int d =0;


    public static void main(String[] args) {
        Out o = new Out();
        System.out.println("hello");
        for (int i = 0; i < size; i++)
            for (int j = 0; j < size; j++) {
                map[i][j] = 0;
            }
        new Main().HuiZhi();//Draw the map

    }

    public void HuiZhi() {
        f.setSize(1500, 1000);//length and width
        f.setLocation(30, 30);//position
        f.setBackground(Color.BLUE);//background

        final JPanel jp= new JPanel();
        jp.setLayout(null);
        final JTextField jt1 = new JTextField();
        final JTextField jt2 = new JTextField();
        final JTextField jt3 = new JTextField();
        jt1.setBounds(400,110,80,20);
        jt2.setBounds(400,150,80,20);
        jt3.setBounds(400,190,80,20);
        JLabel j1 = new JLabel("Enter the number of the plane: ");
        JLabel j2 = new JLabel("Enter the number of the charge station: ");
        JLabel j3 = new JLabel("Enter the number of the warehouse: ");
        JButton b=new JButton("valide ");
        j1.setBounds(40,110,300,20);
        j2.setBounds(40,150,300,20);
        j3.setBounds(40,190,300,20);
        b.setBounds(280,280,100,20);
        jp.add(j1);
        jp.add(j2);
        jp.add(j3);
        jp.add(jt1);
        jp.add(jt2);
        jp.add(jt3);
        jp.add(b);
        f.add(jp);
        b.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int a=Integer.parseInt(jt1.getText());
                int b=Integer.parseInt(jt2.getText());
                int c=Integer.parseInt(jt3.getText());
                int d =1;
                goodAddress(c);//initialise the goods
                chargeAddress(b);//initialize the charge stations
                for(int i =0;i<a;i++) {
                    Plane plane = new Plane(map, planes, goodsList, i+1);
                    planes.add(plane);
                    planeList.add(plane);
                }
                mp = new MyPanel(size);
                jp.setVisible(false);
                f.add(mp);
                f.setVisible(true);




                }



        });
        f.repaint();
        //f.add(mp);
        f.setVisible(true);
        while (true) {
            start();
            f.repaint();}



    }


    /**
     * 随机4个取货点 Générer au hasard 4 points de depot
     */
    public void goodAddress(int a) {
        Random random = new Random();
        for (int i = 0; i < a; i++) {
            int x = random.nextInt(size);
            int y = random.nextInt(size);
            if (map[x][y] != 0) {
                //If there is something else here, change it
                i--;
                continue;
            } else {
                map[x][y] = 1;
            }
            GetAddress address = new GetAddress(goodsList);
            Goods good = new Goods(GetAddress.count,x,y);
            goodsList.add(good);
            address.goods.add(good);
            GetAddress.count++;
            address.setX(x);
            address.setY(y);
            getAddressList.add(address);

        }
    }


    /**
     * 随机两个充电桩 Générer au hasard deux piles de chargement
     */
    public void chargeAddress(int b) {
        Random random = new Random();
        List<String> plansAddress = new ArrayList<>();
        for (int i = 0; i < b; i++) {
            int x = random.nextInt(size);
            int y = random.nextInt(size);
            if (map[x][y] != 0) {
                //If there is something else here, change it
                i--;
                continue;
            } else {
                map[x][y] = 2;
            }
            ChargeAddress address = new ChargeAddress();
            address.setId(i);
            address.setX(x);
            address.setY(y);
            chargeAddressList.add(address);
        }
    }

    /**
     * 计算两点之间的距离 Calculer la distance entre deux points
     */
    public static double length(double n, double m, double x, double y) {

        double sum = Math.sqrt((n - x) * (n - x) + (m - y) * (m - y));
        return sum;
    }


    /**
     * 开始运动
     */
    public void start() {
        //1 所有的飞机
        for (int i = 0; i < planeList.size(); i++) {
          /*  //先判断飞机是否有货物
            Plane plane = planeList.get(i);
            int state = plane.getState();
            if (state == 0) {
                //无货物 取货
               if (goodsList != null && goodsList.size() > 0){
                   for (int j = 0; j < goodsList.size(); j++) {
                       Goods goods = goodsList.get(j);
                       if (goods.getState() == 0) {
                           //没有呗运输
                           goods.setState(1);
                           plane.setGoods(goods);
                           plane.setState(1);
                           break;
                       }
                   }
               }

            }*/
        }
    }


}