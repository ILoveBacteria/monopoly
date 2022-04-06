import java.util.Random;

public class ChanceCard extends Area {
    public ChanceCard(int areaNumber) {
        super(areaNumber, false);
    }

    public void randomChoiceCard(Player player) {
        switch (new Random().nextInt(7) + 1) {
            case 1:
                player.collectRent(200);
                break;
            case 2:
                player.setLocation(13);
                player.inJail = true;
                break;
            case 3:
                player.payTax();
                break;
            case 4:
                player.setLocation(player.getLocation() + 3);
                break;
            case 5:
                break;
            case 6:
                break;
            case 7:
                break;
        }
    }
}
