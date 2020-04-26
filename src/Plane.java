import java.util.*;
import java.util.concurrent.Semaphore;


public class Plane {
    private int[][] map;
    private int id;
    private Double electric = 21.0;//Electricity
    private int weight = 100;//weight
    private double x;//Abscissa
    private double y;//Y-axis
    private int state = 0;//1 indicates that there is already a target 2 is being shipped
    private Goods goods;
    private Double length1;//Distance of the plane from the goods
    private Double lenght2;//Distance of the plane from the destinations
    private Double lenght3;//Distance of the plane from the charge station
    private Double _x;//Horizontal speed
    private Double _y;//Vertical speed
    private List<Plane> planes;//save the position of the plane
    private Double distance = 0.0;//istance
    private List<Goods> goodsList;//all the goods



    public Random random = new Random();

    public Plane(int[][] map, List<Plane> planes, List<Goods> goodsList, int id) {
        this.x = new Random().nextInt(Main.size);
        this.y = new Random().nextInt(Main.size);
        this.map = map;
        this.planes = planes;
        this.goodsList = goodsList;
        Timer timer = new Timer();
        timer.schedule(task, 0, 20);
        this.id = id;



    }


    //电耗
    public void oliConsumption(int o) {
        this.electric = this.electric - o;
    }

    public int getId() {
        return id;
    }

    public Goods getGoods() {
        return goods;
    }

    public void setGoods(Goods goods) {
        this.goods = goods;
    }

    public Double getElectric() {
        return electric;
    }

    public void setElectric(Double electric) {
        this.electric = electric;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public double getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }


    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public void task() {

    }

    TimerTask task = new TimerTask() {
        public void run() {
            if (goods != null && state == 1) { //Pick up de
                gotoGoods();
            } else if (goods != null && state == 2) { //deliver
                deliver();
            } else if (state == 3) {
                charge();
            } else if (state == 0) {
                chooseGoods();
            }
        }
    };


    //Delivery of goods is completed, go to the next charge station
    public void updegoods() {
        List<Goods> goodsList = Main.goodsList;
        for (int i = 0; i < goodsList.size(); i++) {
            if (goodsList.get(i).getState() == 0) {
                this.setGoods(goodsList.get(i));
                goodsList.get(i).setState(1);
                state = 1;
                break;
            }
        }
    }


    public void gotoGoods() {
        int tox = goods.getX();
        int toy = goods.getY();
        if (length1 == null) {
            length1 = Main.length(x, y, tox, toy);//Distance between two points
        }
        if (length1 != 0) {
            if (_x == null) {
                _x = (tox - x) / length1;
            }
            if (_y == null) {
                _y = (toy - y) / length1;
            }
            double v = Math.sqrt(_x * _x + _y * _y);//Distance
            //Calculate power consumption, weight *0.00005* distance = power consumption, when the oil quantity is lower than 20, go to refuel
            double e = weight * 0.00005 * v;//Power consumption
            electric -= e;
            if (electric == 50)
                System.out.println("Drone" + getId() + "La capacité restante de la batterie est de 50%");
            else if (electric == 30)
                System.out.println("Drone" + getId() + "La capacité restante de la batterie est de 30%");
            if (electric <= 20) {
                //charging
                System.out.println("Drone" + getId() + "Puissance insuffisante, abandonner la goods" + goods.getId());
                goods.setState(0);
                state = 3;
                init();
                charge();
            } else {
                distance += Math.sqrt(_x * _x + _y * _y);//Total driving record
                if (distance < length1) {
                    x += _x;
                    y += _y;
                } else {
                    state = 2;
                    init();
                }
            }
        }
    }

    public void deliver() {
        int tox = goods.getTox();//x of the destination
        int toy = goods.getToy();//y of the destination
        if (lenght2 == null) {
            lenght2 = Main.length(x, y, tox, toy);//the distance of 2 points
        }
        if (lenght2 != 0) {
            if (_x == null) {
                _x = (tox - x) / lenght2;
            }
            if (_y == null) {
                _y = (toy - y) / lenght2;
            }
            double v = Math.sqrt(_x * _x + _y * _y);//distance of flying
            //Calculate power consumption, weight *0.00005* distance = power consumption, when the oil quantity is lower than 20, go to refuel
            double e = weight * 0.00005 * v;//电耗
            electric -= e;
            if (electric == 50)
                System.out.println("Drone" + getId() + "La capacité restante de la batterie est de 50%");
            else if (electric == 30)
                System.out.println("Drone" + getId() + "La capacité restante de la batterie est de 50%");
            if (electric <= 20) {
                //放下货物
                System.out.println("Drone" + getId() + "Puissance insuffisante, abandonner la goods" + goods.getId());
                goods.setState(0);
                this.goods = null;
                init();
                state = 3;
                charge();
            } else {
                distance += Math.sqrt(_x * _x + _y * _y);
                if (distance < lenght2) {
                    x += _x;
                    y += _y;
                    goods.setX(Double.valueOf(x).intValue());
                    goods.setY(Double.valueOf(y).intValue());
                } else {
                    //Here is the plane that sent the goods to the place, should go to the next place
                    init();
                    distance = 0.0;
                    state = 0;
                    //updegoods();
                }
            }

        }
    }

    public void charge() {
        Map<Double, ChargeAddress> map = new HashMap<>();
        List<ChargeAddress> chargeAddressList = Main.chargeAddressList;
        for (int i = 0; i < chargeAddressList.size(); i++) {
            ChargeAddress address = chargeAddressList.get(i);
            Double length = Main.length(x, y, address.getX(), address.getY());
            map.put(length, address);
        }
        Set<Double> set = map.keySet();
        Object[] obj = set.toArray();
        Arrays.sort(obj);
        Double o = (Double) obj[0];
        ChargeAddress address = map.get(o);


        double tox = address.getX();
        double toy = address.getY();
        if (lenght3 == null) {
            lenght3 = Main.length(x, y, tox, toy);
        }
        if (lenght3 != 0) {
            if (_x == null) {
                _x = (tox - x) / lenght3;
            }
            if (_y == null) {
                _y = (toy - y) / lenght3;
            }

            distance += Math.sqrt(_x * _x + _y * _y);
            if (distance < lenght3) {
                x += _x;
                y += _y;
            } else {

                init();
                state = 0;
                electric = 100.0;
            }


        }
    }

    public void init() {
        _x = null;
        _y = null;
        lenght2 = null;
        lenght3 = null;
        length1 = null;
        distance = 0.0;
    }

    public  void chooseGoods() {
        synchronized(Plane.class) {
            Map<Double, Goods> map = new HashMap<>();

            for (int i = 0; i < goodsList.size(); i++) {
                if (goodsList.get(i).getState() == 0) {
                    double length = Main.length(x, y, goodsList.get(i).getX(), goodsList.get(i).getY());//distance
                    long time = goodsList.get(i).getTime();//waiting time
                    map.put(length - time * 3, goodsList.get(i));
                }
            }
            if (map.size() > 0) {
                Set<Double> set = map.keySet();
                Object[] obj = set.toArray();
                Arrays.sort(obj);
                Double o = (Double) obj[map.size() - 1];
                Goods good = map.get(o);
                map.get(o).setState(1);
                System.out.println("Drone" + getId() + " a choisi goods " + good.getId());
                this.setGoods(good);
                this.state = 1;
            }
        }

    }


}

