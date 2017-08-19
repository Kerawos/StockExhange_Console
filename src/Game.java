import java.util.*;
import java.util.concurrent.TimeUnit;

public class Game {

    //variables declaration
    public static String comend;
    public static int turns;
    public static int comendOption;
    public static double cash;
    public static boolean isAlreadyInWallet;
    public static Map<Stock, Integer> myStock;
    public static List<Stock> marketStock;
    public static Stock stock;
    public static Scanner sc;

    public static void welcomeScreen(){
        System.out.println("Stock Exhanege Simulator 2017 by Marek Sowa");
        try {
            TimeUnit.SECONDS.sleep(2);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("\nHey There!\nYou came to the stock exhange with yours savings $500\nStock Exhange open 8-18\nTry to multiply your money after near month\nGood Luck!");
        try {
            TimeUnit.SECONDS.sleep(5);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


    public static void start(){

        //initialization
        cash = 500.0;
        isAlreadyInWallet = false;

        Game game = new Game();
        sc = new Scanner(System.in);
        myStock = new HashMap<>();
        marketStock = new ArrayList<>();
        stock = new Stock();

        game.initializeStocksMarket(marketStock); // create market stock list


        //Game (from 1'st turn to 281'st turn
        for (turns = 1; turns < 281 ; turns++) {
            game.print30EmptyLines(); // print empty 30 lines
            System.out.println(game.timeOfPlay(turns) + "     *****     Currently you have $" + (double)(Math.round(cash)*100)/100 + " , Your STOCK: ");
            game.showMyStocks(myStock);
            //System.out.println("*****        *****       *****");
            System.out.println("\nStock value:");

            for (Stock stock1 : marketStock) {
                stock1.adjustPrice(); //adjust new prise
                if(stock1.getPrice()<10.0){ //check if company bankpurts
                    System.out.println("Company " + stock.getName() + " bankpurts...");
                    marketStock.remove(stock1);
                    marketStock.add(new Stock());
                }
            }
            game.showStockList(marketStock); //show to user current stock values

            System.out.println("\nTo buy stock type 'b', to sell 's', to sell All stocks 'sAll', to wait turn 'next', to Game Exit 'exit'. All you should confirm ENTER.");
            comend = sc.nextLine();
            switch (comend){
                case "b": {
                    game.buyStock();
                    break;
                }
                case "s":{
                    game.sellStock();
                    break;
                }
                case "sAll":{
                    game.sellAllStocks();
                    break;
                }
                case "next":{
                    continue;
                }
                case "exit":{
                    turns = 300;
                    break;
                }
                default:{
                    System.out.println("So loud here, Maklers doesn't understand yours intentions, try be more accurate.");
                    continue;
                }
            }
        }

        game.sellAllStocks();
        if (turns<300){
            System.out.println("Month gone, yopu earn: $" + (double)(Math.round(cash - 100)*100)/100);
        } else {
            System.out.println("You abort the game. You earn: $" + (double)(Math.round(cash-100)*100)/100);
        }

    }


    //return time of play
    public String timeOfPlay(int turns){
        int week = 1;
        int day = 1;
        int hour = 1;
        String hourS = null;


        if (turns > 210 ){
            week = 4;
            day = ((turns-210)/11)+1;
        } else if (turns > 140){
            week = 3;
            day = ((turns-140)/11)+1;
        } else if (turns > 70) {
            week = 2;
            day = ((turns-70)/11)+1;
        } else {
            week = 1;
            day = (turns/11)+1;
        }
        //check hour
        hourS = String.valueOf(turns-1);
        return "Week: " + week + ", Day: " + day + ", Hour " + (Character.getNumericValue(hourS.charAt(hourS.length()-1)) + 8) + ":00, Turn: " + turns;
    }


    public void showMyStocks(Map<Stock, Integer> myList) {
        int i = 0;
        for (Map.Entry<Stock, Integer> iterat : myList.entrySet()) { //iterrate thru our Map
            System.out.println(i + ". " + iterat.getKey() + ", ilosc x" + iterat.getValue()); // show our wallet
            i++;
        }
    }


    public void print30EmptyLines(){
        for (int i = 0; i < 30 ; i++) {
            System.out.println("");
        }
    }


    public void showStockList(List<Stock> myList){
        int i = 0;
        for (Stock stock : myList) {
            System.out.println(i + ". " + stock);
            i++;
        }
    }


    public void buyStock(){
        int stockIndicator;

        do {
            System.out.println("Type number of the Stock which you wanna buy:");
            comend = sc.nextLine();
            stockIndicator = Integer.parseInt(comend);
                if (stockIndicator>=marketStock.size()-1){
                    System.out.println("There is no such a Stock in the market, try again..");
                }
        }while (stockIndicator>=marketStock.size());

        System.out.println("You can buy max : " + (int)(cash/marketStock.get(stockIndicator).getPriceBuy()));
        System.out.println("How many Stock you wanna buy " + marketStock.get(stockIndicator).getName() + " ?");
        comend = sc.nextLine();
        comendOption = Integer.parseInt(comend);
        if (comendOption*marketStock.get(stockIndicator).getPriceBuy()>cash){
            System.out.println("You haven't cash for such of amount of Stocks..");
        } else {
            //we buy
            for(Map.Entry<Stock, Integer> iterat : myStock.entrySet()) { // iterate for our stocks (wallet)
                if (iterat.getKey().getName().equals(marketStock.get(stockIndicator).getName())) { // check if we have such Stock in our wallet
                    iterat.setValue(iterat.getValue() + comendOption); // increment Stock in wallet
                    isAlreadyInWallet = true;
                    cash -= marketStock.get(stockIndicator).getPriceBuy()*comendOption; // take cash
                    break; // break loop after bouy
                }
            } // exit loop
            if (!isAlreadyInWallet) { //if Stock has been not allocated in wallet we have to add it
                myStock.put(marketStock.get(stockIndicator), comendOption);
                isAlreadyInWallet = false; // reset position in wallet
                cash -= marketStock.get(stockIndicator).getPriceBuy()*comendOption; // pay
            }
        }
    }


    public void sellStock(){
        int iIteratorFor = 0;
        System.out.println("Type Stock number from your wallet which you wanna sell:");
        comend = sc.nextLine();
        comendOption = Integer.parseInt(comend);
        isAlreadyInWallet = false;
        for(Map.Entry<Stock, Integer> iterat : myStock.entrySet()){
            if (iIteratorFor==comendOption){ // check if we are in such index on our Map
                do {
                    System.out.println("You have " + iterat.getValue() + " Stocks " + iterat.getKey() + ", how many you wanna sell?:");
                    comend = sc.nextLine();
                    comendOption = Integer.parseInt(comend);
                    if (comendOption>iterat.getValue()){ // sprawdza czy wgl mamy tyle w portfelu
                        System.out.println("You haven't such amount... keep focus");
                    }
                }while(comendOption>iterat.getValue());
                iterat.setValue(iterat.getValue()- comendOption); // decrement stock from wallet
                    if(iterat.getValue()==0){ //if we got 0 stock
                        myStock.remove(iterat.getKey(), iterat.getValue()); // delete stock with 0
                    }
                for (Stock stock1 : marketStock) {//check market sell prise
                    if (stock1.getName().equals(iterat.getKey().getName())){ // if found Stock check amount
                        System.out.println(stock1.getPriceSell());
                        cash += stock1.getPriceSell()*comendOption; // increse cash in wallet * amount of indexing Stocks
                        isAlreadyInWallet = true;
                        break;
                    }
                }
            }
            if (isAlreadyInWallet){ // guard for infinit loop
                break;
            }

            iIteratorFor++;
        }
        isAlreadyInWallet = false;
    }


    public void sellAllStocks(){
        for (Map.Entry<Stock, Integer> iterat : myStock.entrySet()){ //loop thru our stock
            for (Stock stock1 : marketStock) {//loop for market for check if stock exists
                if (stock1.getName().equals(iterat.getKey().getName())){ // check sell price
                    cash += (stock1.getPriceSell()*2); // sell xTimes our stock
                    iterat.setValue(0);
                    myStock.remove(iterat.getKey(), iterat.getValue());
                    break;
                }
            }
        }
    }


    public void initializeStocksMarket(List<Stock> marketStock) { //create random stock to our market
        for (int i = 0; i < 5; i++) { // create 5 default stock
            marketStock.add(new Stock()); // add random Stock
            int repeats = 0;
            for (Stock stock1 : marketStock) { // loop thru our list
                if (stock1.getName().equals(marketStock.get(i).getName())) { // check repeats
                    repeats++;
                    if (repeats > 1) {// prevent comparing same stocks
                        marketStock.remove(i); //if same, then delete
                        i--; // decrement i
                    }
                    break; // exit 1'st loop
                }
            }
        }
    }





}
