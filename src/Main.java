import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class Main {
    private static Game game = null;

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // Create and start game commands
        while (true) {
            String inputCommand = scanner.next();

            if (inputCommand.equals("create_game")) {
                ArrayList<Player> playerArrayList = new ArrayList<>();

                while (true) {
                    String inputName = scanner.next();

                    if (inputName.equals("start_game"))
                        break;

                    Player player = new Player(inputName);
                    playerArrayList.add(player);
                }

                game = new Game(playerArrayList.toArray(new Player[0]));
                break;
            }

            else if (inputCommand.equals("start_game")) {
                System.out.println("no game created");
            }
        }

        System.out.println("round " + game.getRound());

        // Player turn
        for (int i = 0;; i++) {
            if (i == game.getPlayers().length) {
                i = 0;
                game.goNextRound();
                System.out.println("round " + game.getRound());
            }

            Player playerTurn = game.getPlayers()[i];
            System.out.println(playerTurn.getName() + "'s turn:");

            // Roll dice
            int diceNumber = scanner.nextInt();

            if (diceNumber == 6) {
                int nextDiceNumber = scanner.nextInt();
                diceNumber += nextDiceNumber;
            }

            if (diceNumber == 12) {
                playerTurn.setLocation(13);
                playerTurn.inJail = true;
            } else {
                int newLocation = playerTurn.getLocation() + diceNumber;
                if (newLocation > 24) {
                    playerTurn.setLocation(newLocation - 24);
                } else {
                    playerTurn.setLocation(newLocation);
                }
            }

            // Pay rent
            while (true) {
                try {
                    playerTurn.payRent(game.getGameBoard().getAreas()[playerTurn.getLocation()]);
                    System.out.println("Payed: " +
                            game.getGameBoard().getAreas()[playerTurn.getLocation()].getRentPrice() + "$");
                    break;
                } catch (MustSellRealEstatesException e) {
                    System.out.println(e.getMessage());
                    commands(scanner, playerTurn);
                } catch (Exception e) {
                    System.out.println(e.getMessage()); // Not yet handling the bankruptcy
                    break;
                }
            }

            // Check chance card
            if (playerTurn.getLocation() == 24) {
                ((ChanceCard) game.getGameBoard().getAreas()[24]).randomChoiceCard(playerTurn);
            }
            // Pay tax
            else if (playerTurn.getLocation() == 17) {
                playerTurn.payTax();
            }

            // Player commands
            while (true) {
                boolean isExit = commands(scanner, playerTurn);
                if (isExit)
                    break;
            }
        }
    }

    private static boolean commands(Scanner scanner, Player player) {
        String inputCommand = scanner.next();

        switch (inputCommand) {
            case "index":
                int location = player.getLocation();
                System.out.println(location + ": " + game.getGameBoard().getAreas()[location].getClass().getSimpleName());
                break;
            case "property":
                System.out.println(player.printProperties());
                break;
            case "buy":
                try {
                    player.buy(game.getGameBoard().getAreas()[player.getLocation()]);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            case "build":
                try {
                    player.build(game.getGameBoard().getAreas()[player.getLocation()]);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            case "sell":
                int locationArea = scanner.nextInt();

                try {
                    player.sell(game.getGameBoard().getAreas()[locationArea]);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            case "fly":
                int destinationArea = scanner.nextInt();

                try {
                    player.fly(destinationArea);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            case "free":
                try {
                    player.free();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            case "invest":
                try {
                    player.invest();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            case "rank":
                int rank = 0;
                int[] totalProperties = new int[game.getPlayers().length];
                for (int j = 0; j < game.getPlayers().length; j++) {
                    totalProperties[j] = game.getPlayers()[j].totalProperty();
                }

                Arrays.sort(totalProperties);
                for (int j = 0; j < totalProperties.length; j++) {
                    if (player.totalProperty() == totalProperties[j]) {
                        rank = game.getPlayers().length - j;
                    }
                }

                System.out.println(rank);
                break;
            case "exit":
                return true;
            default:
                System.out.println("Invalid command!");
        }

        return false;
    }
}
