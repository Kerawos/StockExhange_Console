
import java.util.Random;

public class Stock {
    private String name;
    private Double price;
    private double priceBuy;
    private double priceSell;
    private int type;
    private double progressPrice = 0.4;

    private Random random = new Random();

    public Stock (){
        this.name = generateName();
        this.price = (double) Math.round(generatePrice()*100)/100;
        this.priceBuy = updatePriceBuy();
        this.priceSell = updatePriceSell();
        this.type = generateType();
    }

    public String getName() {
        return name;
    }

    public Double getPrice() {
        return price;
    }

    public double getPriceBuy(){
        return this.priceBuy;
    }

    public  double getPriceSell(){
        return this.priceSell;
    }

    private double generatePrice(){
        return random.nextInt(89)+11;
    }

    private double updatePriceBuy(){
        return this.priceBuy = (double) Math.round((price*1.025) * 100) / 100;
    }

    private double updatePriceSell(){
        return this.priceSell = (double) Math.round((price*0.975)*100) / 100;
    }

    private int generateType(){
        return random.nextInt(9)+1;
    }

    private String generateName(){
        String[] slowaKluczowe = {"Tech", "Pol", "Tin", "Logi", "M", "TI", "i", "Wa", "Nasze", "My", "And", "Sob", " ", "Sata", "Gc", "Firm"};
        String workName = slowaKluczowe[random.nextInt(slowaKluczowe.length-1)];;
        for (int i = 0; i < random.nextInt(3)+3 ; i++) {
            workName+=slowaKluczowe[random.nextInt(slowaKluczowe.length-1)];
        }
        return  workName;
    }

    public void adjustPrice(){
        this.price += (this.type*((double)Math.round((random.nextDouble()-progressPrice)*100)/100));
        updatePriceBuy();
        updatePriceSell();
    }

    public String showProgressPrice(){
        double sum = 0;
        double rnd;
        for (int i = 0; i <100 ; i++) {
            rnd = (double)Math.round((random.nextDouble()-progressPrice)*100)/100;
            System.out.print(rnd+", ");
            sum+=rnd;
        }
        return "Suma to: " + sum;
    }


    @Override
    public String toString() {
        return "Stock{" +
                "Name = '" + name + '\'' +
                ", Buy price $" + priceBuy +
                ", Sell price $" + priceSell +
                '}';
    }
}
