import java.util.ArrayList;
import java.util.Scanner;

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

    public void collectMoney(Player player) {
        int index = investors.indexOf(player);
        player.collectRent(moneyInvested.get(index) * 2);
    }

    public boolean containPlayer(Player player) {
        return investors.contains(player);
    }

    public void removeInvestor(Player player) {
        int index = investors.indexOf(player);
        moneyInvested.remove(index);
        investors.remove(index);
    }

    public static void swapWealth(Player player1, Player player2) {
        ArrayList<Area> areaTemp = player1.getRealEstates();
        int assetTemp = player1.getAsset();
        player1.setRealEstates(player2.getRealEstates());
        player1.setAsset(player2.getAsset());
        player2.setRealEstates(areaTemp);
        player2.setAsset(assetTemp);
    }
}
