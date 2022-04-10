import java.util.Random;

public class ChanceCard extends Area {
    public final int COLLECT_200 = 0;
    public final int GO_IN_PRISON = 1;
    public final int PAY_TAX = 2;
    public final int GO_FORWARD_3 = 3;
    public final int PAY_10_TO_PLAYERS = 4;

    public ChanceCard(int areaNumber) {
        super(areaNumber, false);
    }

    public void randomChoiceCard(Player player, Player[] players) {
        switch (new Random().nextInt(5)) {
            case COLLECT_200:
                player.collectRent(200);
                System.out.println("You earned 200$");
                break;
            case GO_IN_PRISON:
                player.setLocation(13);
                player.inJail = true;
                System.out.println("You were transferred to the prison area");
                break;
            case PAY_TAX:
                player.payTax();
                System.out.println("You taxed ten percent of your money");
                break;
            case GO_FORWARD_3:
                player.setLocation(player.getLocation() + 3);
                System.out.println("You went three areas forward");
                break;
            case PAY_10_TO_PLAYERS:
                for (int i = 0; i < players.length; i++) {
                    if (players[i] == player)
                        continue;
                    players[i].collectRent(10);
                    player.setAsset(player.getAsset() - 10);
                }
                System.out.println("You payed 10$ to each player");
                break;
        }
    }
}
