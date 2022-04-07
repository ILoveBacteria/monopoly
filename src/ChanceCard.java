import java.util.Random;

public class ChanceCard extends Area {
    public static final int COLLECT_200 = 1;
    public static final int GO_IN_PRISON = 2;
    public static final int PAY_TAX = 3;
    public static final int GO_FORWARD_3 = 4;
    public static final int OPPORTUNITY_FREEDOM = 5;
    public static final int DONT_PAY_TAX = 6;
    public static final int PAY_10_TO_PLAYERS = 7;


    public ChanceCard(int areaNumber) {
        super(areaNumber, false);
    }

    public void randomChoiceCard(Player player) {
        switch (new Random().nextInt(7) + 1) {
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
            case OPPORTUNITY_FREEDOM:
                break;
            case DONT_PAY_TAX:
                break;
            case PAY_10_TO_PLAYERS:
                break;
        }
    }
}
