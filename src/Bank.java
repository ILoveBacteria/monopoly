import java.util.ArrayList;

public class Bank extends Area {
    private ArrayList<Player> investors = new ArrayList<>();
    private ArrayList<Integer> moneyInvested = new ArrayList<>();

    public Bank(int areaNumber) {
        super(areaNumber, false);
    }

    public void addInvestor(Player player) {
        investors.add(player);
        //moneyInvested.add(player.cash);
    }

    public void removeInvestor(Player player) {
        int index = investors.indexOf(player);
        moneyInvested.remove(index);
        investors.remove(index);
    }
}
