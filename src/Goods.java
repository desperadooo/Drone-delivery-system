import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

public class Goods{
    private int id;
    private int x;//Abscissa
    private int y;//Y-axis
    private int weight;//weight
    private int volime = 1;//Volume
    private long time = 0;//Package waiting time
    private int state = 0;//Is it shipped
    private int tox;//The abscissa of the destination
    private int toy;//The abscissa of the destination

    private Random random = new Random();

    public Goods(int id, int x, int y) {
        this.id = id;
        this.x = x;
        this.y = y;
        this.tox = random.nextInt(1000);
        this.toy = random.nextInt(1000);
        this.weight=50*(random.nextInt(10)+1);
        Timer timer = new Timer();
        timer.schedule(task, 0, 500);
    }
    public int getTox() {
        return tox;
    }

    public void setTox(int tox) {
        this.tox = tox;
    }

    public int getToy() {
        return toy;
    }

    public void setToy(int toy) {
        this.toy = toy;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public int getVolime() {
        return volime;
    }

    public void setVolime(int volime) {
        this.volime = volime;
    }

    public long getTime() {
        return time;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setTime(long time) {
        this.time = time;
    }


    public void task() {

    }

    TimerTask task = new TimerTask() {
        public void run() {
            time++;
           /* System.out.println("飞机" + id + "已经等待了" + time  + "秒了");*/
        }
    };
}
