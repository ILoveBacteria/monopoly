import java.util.ArrayList;

public class Bank extends Area {
    private final ArrayList<Player> investors = new ArrayList<>();
    private final ArrayList<Integer> moneyInvested = new ArrayList<>();

    public Bank(int areaNumber) {
        super(areaNumber, false);
    }

    public void addInvestor(Player player) {
        investors.add(player);
        moneyInvested.add(player.getAsset() / 2);
    }

    public void removeInvestor(Player player) {
        int index = investors.indexOf(player);
        moneyInvested.remove(index);
        investors.remove(index);
    }
}
