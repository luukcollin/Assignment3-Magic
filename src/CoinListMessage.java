import java.io.Serializable;
import java.util.List;

public class CoinListMessage implements Serializable {

    private List<Coin> coins;

    public CoinListMessage(List<Coin> coins){
        this.coins = coins;
    }

    public List<Coin> getCoins(){
        return coins;
    }
}
