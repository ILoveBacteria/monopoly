import java.util.ArrayList;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Game game = null;
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

            // Dice
            int diceNumber = scanner.nextInt();

            if (diceNumber == 6) {
                int nextDiceNumber = scanner.nextInt();
                diceNumber += nextDiceNumber;
            }

            if (diceNumber == 12) {
                playerTurn.setLocation(13);
                playerTurn.inJail = true;
            }

            else {
                int newLocation = playerTurn.getLocation() + diceNumber;
                if (newLocation > 24) {
                    playerTurn.setLocation(newLocation - 24);
                } else {
                    playerTurn.setLocation(newLocation);
                }
            }

            try {
                playerTurn.payRent(game.getGameBoard().getAreas()[playerTurn.getLocation()]);
            } catch (Exception e) {
                e.printStackTrace();
            }

            // Check chance card
            if (playerTurn.getLocation() == 24) {
                ((ChanceCard) game.getGameBoard().getAreas()[24]).randomChoiceCard(playerTurn);
            }

            // Player commands
            while (true) {
                String inputCommand = scanner.next();

                switch (inputCommand) {
                    case "index":
                        int location = playerTurn.getLocation();
                        System.out.println(location + ": " + game.getGameBoard().getAreas()[location].getClass().getSimpleName());
                        break;
                    case "property":
                        System.out.println(playerTurn.printProperties());
                        break;
                    case "buy":
                        try {
                            playerTurn.buy(game.getGameBoard().getAreas()[playerTurn.getLocation()]);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        break;
                    case "build":
                        try {
                            playerTurn.build(game.getGameBoard().getAreas()[playerTurn.getLocation()]);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        break;
                    case "sell":
                        int locationArea = scanner.nextInt();

                        try {
                            playerTurn.sell(game.getGameBoard().getAreas()[locationArea], null);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        break;
                    case "fly":
                        int destinationArea = scanner.nextInt();

                        try {
                            playerTurn.fly(destinationArea);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        break;
                    case "free":
                        try {
                            playerTurn.free();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        break;
                    case "invest":
                        try {
                            playerTurn.invest();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        break;
                }
            }
        }
    }
}
