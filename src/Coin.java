

import java.io.Serializable;
import java.util.Comparator;
import java.util.GregorianCalendar;

public class Coin implements Comparable<Coin>, Serializable {
    private String name;
    private double marketCap;
    private double price;
    private double dailyVolume;
    private GregorianCalendar launchedAt;
    private int id;

    public Coin(String name, double marketCap, double price, double dailyVolume, GregorianCalendar launchedAt, int id){
        this.name = name;
        this.marketCap = marketCap;
        this.price = price;
        this.dailyVolume = dailyVolume;
        this.launchedAt = launchedAt;
        this.id = id;
    }

    public double getCapValue(){
        return marketCap;
    }


    @Override
    public int compareTo(Coin o) {
        return Comparator.comparing((Coin c) -> c.marketCap).thenComparing(c -> c.dailyVolume).thenComparing(c-> c.name).thenComparing(c -> price).thenComparing(c -> c.launchedAt).compare(this, o);
    }

    @Override
    public String toString(){
        return "(Coin ID: " + id +  ", Marketcap: " + marketCap + ", Price: €" + price + ", Daily Volume: €" + dailyVolume +   ")\n";
    }


}
