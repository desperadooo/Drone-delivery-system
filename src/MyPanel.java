
import javax.swing.*;
import java.awt.*;

class MyPanel extends JPanel {

    public int size;
    public int height = 3;


    public MyPanel(int size) {
        this.size = size;
    }


    @Override//覆写JPanel的paint()方法 la méthode paint () de JPanel
    public void paint(Graphics g) {
        super.paint(g);//调用JPanel的paint /Appeler la paint de JPanel
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        //Draw the location of the goods
               for (int i = 0; i < Main.goodsList.size(); i++) {
            Goods goods = Main.goodsList.get(i);
            g.setColor(Color.GREEN);
            g.drawLine(goods.getX(), goods.getY(), goods.getTox(), goods.getToy());
            g.setColor(Color.MAGENTA);
            g.drawString("Destination", goods.getTox(), goods.getToy());
            g.fillOval(goods.getTox(), goods.getToy(), 10, 10);
            g.setColor(Color.RED);
            g.drawString("Goods"+i, goods.getX(), goods.getY()-10);
            g.fillOval(goods.getX(), goods.getY(), 10, 10);
        }


        //Draw the position of the point on the map
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                paint(g, Main.map[i][j], i, j);
            }
        }
            //Drawing plane flight dynamics
        for (int i = 0; i < Main.planes.size(); i++) {
            Plane plane = Main.planes.get(i);
            g.setColor(Color.black);
            Integer x = Double.valueOf(plane.getX()).intValue();
            Integer y = Double.valueOf(plane.getY()).intValue();
            g.fillOval(x,y, height, height);
            g.drawString("Drone"+plane.getId(),x,y);
        }
        


    }


    public void paint(Graphics g, int type, int i, int j) {
        switch (type) {
            case 0:
                //do nothing
                break;
            case 1:
                //only the warehouse
                g.setColor(Color.blue);
                g.fillRect(i, j, height, height);
                g.drawString("Depôt" + i + "" + j, i, j);
                break;
            case 2:
                //only the charge station
                g.setColor(Color.RED);
                g.fillRect(i, j, height, height);
                g.drawString("Pile de charge" + i + "" + j, i, j);
                break;
            case 3:
                //only the plane
                g.setColor(Color.black);
                g.fillOval(i, j, height, height);
                g.drawString("Drone" + i + "" + j, i, j);
                break;
            case 4:
                //only the warehouse
                g.setColor(Color.blue);
                g.fillRect(i, j, height, height);
                g.drawString("Depôt" + i + "" + j, i, j);

                g.setColor(Color.black);
                g.fillOval(i, j, height, height);
                g.drawString("Drone" + i + "" + j, i, j);

                break;
            case 5:
                //the plane on the charging station
                g.setColor(Color.RED);
                g.fillRect(i, j, height, height);
                g.drawString("Pile de charge" + i + "" + j, i, j);

                g.setColor(Color.black);
                g.fillOval(i, j, height, height);
                g.drawString("Drone" + i + "" + j, i, j);

                break;

        }

    }


}