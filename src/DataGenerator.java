import java.io.File;
import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.*;

public class DataGenerator {
    private int amountOfElements;
    private String[] cryptoNames;
    private List<Coin> allCoins;
    public DataGenerator(int amountOfElements, String[] cryptoNames){
        this.amountOfElements = amountOfElements;
        this.cryptoNames = cryptoNames;
        allCoins = new ArrayList<>();

    }


    public List<Coin> generate(){
        Random random = new Random();
        String[] months = new String[]{"JANUARY", "FEBRUARY", "MARCH", "APRIL", "MAY", "JUNE", "JULY", "AUGUST", "SEPTEMBER", "OCTOBER", "NOVEMBER", "DECEMBER"};
        double MINIMUMCAPVALUE = 1_000_000.0000;
        double MAXIMUMCAPVALUE = 2_000_000_000.0000;

        double MINIMUMPRICEVALUE = .0001;
        double MAXIMUMPRICEVALUE = 200_0.0000;

        double MINIMUMDAILYVOLUME = 100.0000;
        double MAXIMUMDAILYVOLUME = 10_000_000_00.0000;

        int MINIMUMYEAR = 2009;
        int MAXIMUMYEAR = 2020;

        int MINIMUMDATE = 1;
        int MAXIMUMDATE = 28;

        String name;
        double coinMarketCap;
        double coinPrice;
        double coinDailyVolume;
        int year;
        int date;
        for(int i = 0; i < amountOfElements; i++){
            name = "a";
            if(i < cryptoNames.length){
                name = cryptoNames[i];
            }
            coinMarketCap = MINIMUMCAPVALUE + (Math.random() * ((MAXIMUMCAPVALUE - MINIMUMCAPVALUE) +1));
            coinPrice = MINIMUMPRICEVALUE + (MAXIMUMPRICEVALUE - MINIMUMPRICEVALUE) * random.nextDouble();
            coinDailyVolume = MINIMUMDAILYVOLUME + (Math.random() * ((MAXIMUMDAILYVOLUME - MINIMUMDAILYVOLUME) +1));
            year = MINIMUMYEAR + (int)(Math.random() * ((MAXIMUMYEAR - MINIMUMYEAR) +1));
            date = MINIMUMDATE + (int)(Math.random() * ((MAXIMUMDATE - MINIMUMDATE) +1));

            allCoins.add(new Coin(name, coinMarketCap, coinPrice, coinDailyVolume, new GregorianCalendar( (1900+year), Calendar.FEBRUARY, date ), i));
        }
        return allCoins;
    }


    public String toString(String coinName, double coinMarketCap, double coinPrice, double coinDailyVolume, int year, String month, int date){
        return "coinData.add(new Coin(" + "\"" + coinName + "\"" + ", " + coinMarketCap + "," + coinPrice + ", " + coinDailyVolume + ", " + "new GregorianCalendar(" + (1900 + year) + ", Calendar." +  month + ", " + date +")));";
    }
}
