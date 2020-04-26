import java.util.*;

public class GetAddress {//warehouse
    public int x;
    public int y;
    public static int count=1;
    public List<Goods> goods = new ArrayList<>();

    public List<Goods> goodsList;

    public GetAddress(List<Goods> goodsList) {
        this.goodsList = goodsList;
        Timer timer = new Timer();
        timer.schedule(task, 0, 500);
    }


    public List<Goods> getGoods() {
        return goods;
    }

    public void setGoods(List<Goods> goods) {
        this.goods = goods;
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

    TimerTask task = new TimerTask() {
        public void run() {
            Random random = new Random();
            int i = random.nextInt(150);
            if(i == 2){
                count++;
                Goods good = new Goods(count,x,y);
                goodsList.add(good);
                goods.add(good);
                System.out.println();
                System.out.println("Goods"+good.getId()+"En attente... Poids:"+good.getWeight()+" ");
                System.out.println("Liste de goods：");
                for(int j=0;j<goodsList.size();j++){
                    if(goodsList.get(j).getState()==0)
                        System.out.println("Goods"+goodsList.get(j).getId()+" Temps d'attente:"+goodsList.get(j).getTime());
                }
            }
        }
    };

}
